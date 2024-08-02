package ai.javis.project.library_management_system.services;

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

    @Value("${library.inventory.add.book.at.one.time.limit}")
    private int maxLimitToAddBookAtOneTime;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookInventoryRepository bookInventoryRepository;

    @Transactional
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
        return new ApiResponse( Math.min(maxLimitToAddBookAtOneTime,addBookToInventoryRequest.getQuantity())+ " Books added to inventory successfully (Max Limit per operation: " + maxLimitToAddBookAtOneTime + ")", "", true);
    }


    @Transactional
    public ApiResponse deleteBookFromInventory(Integer bookInventoryId) throws Exception {
        BookInventory bookInventory = bookInventoryRepository.findById(bookInventoryId)
                .orElseThrow(() -> new ResourceNotFound("BookInventory", "bookInventoryId", bookInventoryId));

        BookInventory finalBookInventory = bookInventory;
        Book book = bookRepository.findById(bookInventory.getBook().getId())
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", finalBookInventory.getBook().getId()));

        if(!book.getStatus() || !bookInventory.getStatus()){
            return new ApiResponse("", "Book doesn't exist in the library", false);
        }
        if(!bookInventory.getIsAvailable()){
            return new ApiResponse("", "Book has been issued, can't delete it now", false);
        }
        bookInventory.setStatus(false);
        bookInventory = bookInventoryRepository.save(bookInventory);

        return new ApiResponse("Book with bookInventoryId" + bookInventoryId + "deleted successfully", "", true);
    }
}
