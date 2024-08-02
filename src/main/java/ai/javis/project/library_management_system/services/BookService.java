package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.models.BookInventory;
import ai.javis.project.library_management_system.payloads.*;
import ai.javis.project.library_management_system.repositories.BookInventoryRepository;
import ai.javis.project.library_management_system.repositories.BookRepository;
import ai.javis.project.library_management_system.repositories.TxnRepository;
import ai.javis.project.library_management_system.helpers.BookSpecification;
import ai.javis.project.library_management_system.helpers.CustomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private BookInventoryRepository bookInventoryRepository;

    @Transactional
    public ApiResponse addBook(AddBookRequest addBookRequest) throws Exception{

        Book book = CustomMapper.addBookRequestToBookMapper(addBookRequest);
        book = bookRepository.save(book);

        BookInventory bookInventory = new BookInventory();
        bookInventory.setBook(book);

        bookInventoryRepository.save(bookInventory);
        return new ApiResponse(ResponseMessages.BOOK_CREATED_SUCCESSFULLY.getValue(), "",true);
    }

    @Transactional
    public ApiResponse updateBook(UpdateBookRequest updateBookRequest, Integer bookId) throws Exception{

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", bookId));

        if(updateBookRequest.getStatus() != null){

            Integer count = bookInventoryRepository.countByBookIdAndIsAvailableFalse(bookId);

            if(!updateBookRequest.getStatus() && (count != null && count > 0)){
                return new ApiResponse("" , ResponseMessages.BOOK_STATUS_CANNOT_BE_CHANGED_BOOK_ON_ISSUE.getValue(), false);
            }
        }

        if(updateBookRequest.getTitle() != null && !(updateBookRequest.getTitle()).isEmpty()){
            book.setTitle(updateBookRequest.getTitle().trim());
        }

        if(updateBookRequest.getAuthorName() != null && !(updateBookRequest.getAuthorName()).isEmpty()){
            book.setAuthorName(updateBookRequest.getAuthorName().trim());
        }

        if(updateBookRequest.getStatus() != null){
            book.setStatus(updateBookRequest.getStatus());
        }

        if(updateBookRequest.getPublishedDate() != null){
            book.setPublishedDate(updateBookRequest.getPublishedDate());
        }

        if(updateBookRequest.getGenre() != null){
            String genres = updateBookRequest.getGenre()
                    .stream().map(String::trim)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet())
                    .stream()
                    .collect(Collectors.joining(","));

            book.setGenres(genres);
        }

        bookRepository.save(book);
        return new ApiResponse(ResponseMessages.BOOK_UPDATED_SUCCESSFULLY.getValue(), "" , true);
    }

    @Transactional
    public Map<Integer, String> deleteBooks(DeleteBookRequest deleteBookRequest) throws Exception{

        Map<Integer, String> failedToDeleteMap = new HashMap<>();

        Map<Integer,Book> bookIdToBookMap =  bookRepository.findAllById(deleteBookRequest.getBookIds())
                .stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

        List<Integer> bookIds = new ArrayList<>();

        for (Integer bookId : deleteBookRequest.getBookIds()){

            Book book = bookIdToBookMap.get(bookId);

            if (book == null || !book.getStatus()){
                failedToDeleteMap.put(bookId, ResponseMessages.BOOK_DOES_NOT_EXIST.getValue());
                continue;
            }

            bookIds.add(bookId);
        }

        Map<Integer,Integer> inventoryStatusFalseCountOfBookIds = new HashMap<>();

        for(Integer bookId : bookIds){
             inventoryStatusFalseCountOfBookIds.put(bookId,bookInventoryRepository.countByBookIdAndIsAvailableFalse(bookId));
        }

        List<Book> qualifiedToDeleteBooks = new ArrayList<>();

        for (Map.Entry<Integer,Integer> result : inventoryStatusFalseCountOfBookIds.entrySet()){

            Integer bookId = result.getKey();
            Integer count = result.getValue();
            if(count == null || count.equals(0)){
                Book updatedBook = bookIdToBookMap.get(bookId);
                updatedBook.setStatus(false);
                qualifiedToDeleteBooks.add(updatedBook);
            }
            else{

                failedToDeleteMap.put(bookId,ResponseMessages.BOOK_CANNOT_BE_DELETED_BOOK_ON_ISSUE.getValue());
            }
        }
        bookRepository.saveAll(qualifiedToDeleteBooks);
        return failedToDeleteMap;
    }

    public BookDto getBookById(Integer bookId) throws Exception{
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFound("Book", "bookId", bookId));

        if(!book.getStatus()){
            throw new Exception(ResponseMessages.BOOK_DOES_NOT_EXIST.getValue() + " bookId : " + bookId);
        }

        BookDto bookDto  = CustomMapper.bookToBookDtoMapper(book);

        List<String> genres = new ArrayList<>(Arrays.asList(book.getGenres().split(",")));
        bookDto.setGenre(genres);
        return bookDto;
    }

    public List<GetBooksResponse> getBooks(GetBooksRequest getBooksRequest) throws Exception{
        Specification<Book> specification = BookSpecification.getBooksSpecification(getBooksRequest);
        List<Book> books = bookRepository.findAll(specification);

        List<GetBooksResponse> filteredBooks = books.stream()
                .map(book -> {
                    GetBooksResponse getBooksResponse = CustomMapper.bookToGetBookResponseMapper(book);
                    getBooksResponse.setGenre(new ArrayList<>(Arrays.asList(book.getGenres().split(",")))
                    );
                    return getBooksResponse;
                })
                .toList();

        return filteredBooks;
    }

}
