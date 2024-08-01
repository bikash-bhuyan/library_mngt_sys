package ai.javis.project.library_management_system.services.impl;

import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.payloads.*;
import ai.javis.project.library_management_system.repositories.BookRepository;
import ai.javis.project.library_management_system.repositories.TxnRepository;
import ai.javis.project.library_management_system.services.BookService;
import ai.javis.project.library_management_system.utils.BookSpecification;
import ai.javis.project.library_management_system.utils.CustomMapper;
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
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TxnRepository txnRepository;

    @Override
    public ApiResponse addBook(AddBookRequest addBookRequest) throws Exception{
        Book book = CustomMapper.addBookRequestToBookMapper(addBookRequest);
        book = bookRepository.save(book);
        return new ApiResponse("Book created successfully", "",true);
    }
    @Override
    @Transactional
    public ApiResponse updateBook(UpdateBookRequest updateBookRequest, Integer bookId) throws Exception{

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", bookId));

        if(updateBookRequest.getIsAvailable() != null && !book.getIsAvailable()){
            return new ApiResponse("", "Can't change the availability or status fields as book is issued by an user and hasn't returned", false);
        }
        if(updateBookRequest.getStatus() != null && !book.getIsAvailable()){
            return new ApiResponse("" , "Can't change the availability or status fields as book is issued by an user and hasn't returned", false);
        }

        if(updateBookRequest.getTitle() != null && !(updateBookRequest.getTitle()).isEmpty()){
            book.setTitle(updateBookRequest.getTitle());
        }
        if(updateBookRequest.getAuthorName() != null && !(updateBookRequest.getAuthorName()).isEmpty()){
            book.setAuthorName(updateBookRequest.getAuthorName());
        }
        if(updateBookRequest.getStatus() != null){
            book.setStatus(updateBookRequest.getStatus());
        }
        if(updateBookRequest.getIsAvailable() != null){
            book.setIsAvailable(updateBookRequest.getIsAvailable());
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
        return new ApiResponse("Book updated successfully", "" , true);
    }

    @Override
    @Transactional
    public Map<Integer, String> deleteBooks(DeleteBookRequest deleteBookRequest) throws Exception{
        Map<Integer, String> failedToDeleteMap = new HashMap<>();
        Map<Integer,Book> bookIdToBookMap =  bookRepository.findAllById(deleteBookRequest.getBookIds())
                .stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

        for (Integer bookId : deleteBookRequest.getBookIds()){
            Book book = bookIdToBookMap.get(bookId);
            if (book == null){
                failedToDeleteMap.put(bookId, "Book with bookId : " + bookId + " doesn't exist");
                continue;
            }
            if(!book.getIsAvailable() || !book.getStatus()){
                failedToDeleteMap.put(bookId, "Book with bookId : " + bookId + " isn't available");
                continue;
            }
            book.setStatus(false);
        }
        bookRepository.saveAll(bookIdToBookMap.values());
        return failedToDeleteMap;
    }
    @Override
    public BookDto getBookById(Integer bookId) throws Exception{
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFound("Book", "bookId", bookId));
        BookDto bookDto  = CustomMapper.bookToBookDtoMapper(book);

        List<String> genres = new ArrayList<>(Arrays.asList(book.getGenres().split(",")));
        bookDto.setGenre(genres);
        return bookDto;
    }
    @Override
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
