package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.models.BookInventory;
import ai.javis.project.library_management_system.payloads.AddBookToInventoryRequest;
import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.repositories.BookInventoryRepository;
import ai.javis.project.library_management_system.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookInventoryService {

    @Value("${library.inventory.add_book_at_onetime_limit}")
    private int maxLimitToAddBookAtOneTime;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookInventoryRepository bookInventoryRepository;

    public ApiResponse addBookToInventory(AddBookToInventoryRequest addBookToInventoryRequest) throws Exception{

        Book book = bookRepository.findById(addBookToInventoryRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", addBookToInventoryRequest.getBookId()));

        List<BookInventory> bookInventories = new ArrayList<>();

        for(int i = 0; i < Math.min(maxLimitToAddBookAtOneTime,addBookToInventoryRequest.getQuantity()); i++){
            BookInventory bookInventory = new BookInventory();
            bookInventory.setBook(book);
            bookInventories.add(bookInventory);
        }

        bookInventoryRepository.saveAll(bookInventories);
        return new ApiResponse( Math.min(maxLimitToAddBookAtOneTime,addBookToInventoryRequest.getQuantity())+ ResponseMessages.BOOKS_ADDED_TO_INVENTORY.getValue() + maxLimitToAddBookAtOneTime, "", true);
    }


    public ApiResponse deleteBookFromInventory(Integer bookInventoryId) throws Exception {

        BookInventory bookInventory = bookInventoryRepository.findById(bookInventoryId)
                .orElseThrow(() -> new ResourceNotFound("BookInventory", "bookInventoryId", bookInventoryId));

        BookInventory finalBookInventory = bookInventory;

        Book book = bookRepository.findById(bookInventory.getBook().getId())
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", finalBookInventory.getBook().getId()));

        if(!book.getStatus() || !bookInventory.getStatus()){
            return new ApiResponse("", ResponseMessages.BOOK_DOES_NOT_EXIST.getValue(), false);
        }

        if(!bookInventory.getIsAvailable()){
            return new ApiResponse("", ResponseMessages.BOOK_IS_ON_ISSUE.getValue(), false);
        }

        bookInventory.setStatus(false);
        bookInventory = bookInventoryRepository.save(bookInventory);

        return new ApiResponse(ResponseMessages.BOOK_DELETED_SUCCESSFULLY.getValue(), "", true);
    }
}
