package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.models.BookInventory;
import ai.javis.project.library_management_system.models.Txn;
import ai.javis.project.library_management_system.models.User;
import ai.javis.project.library_management_system.payloads.*;
import ai.javis.project.library_management_system.repositories.BookInventoryRepository;
import ai.javis.project.library_management_system.repositories.BookRepository;
import ai.javis.project.library_management_system.repositories.TxnRepository;
import ai.javis.project.library_management_system.repositories.UserRepository;
import ai.javis.project.library_management_system.helpers.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TxnService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private BookInventoryRepository bookInventoryRepository;

    @Transactional
    public ApiResponse lendBook(LendBookRequest lendBookRequest) throws Exception{

        User user = userRepository.findById(lendBookRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User", "userId", lendBookRequest.getUserId()));
        Book book = bookRepository.findById(lendBookRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", lendBookRequest.getBookId()));

        if(!book.getStatus()){
            return new ApiResponse("", ResponseMessages.BOOK_DOES_NOT_EXIST.getValue(), false);
        }

        BookInventory bookInventory = bookInventoryRepository.findFirstAvailableByBookId(book.getId());
        if(bookInventory == null){
            return new ApiResponse("", ResponseMessages.BOOK_NOT_AVAILABLE_TO_LEND.getValue() + " bookId:" + book.getId(), false);
        }

        bookInventory.setIsAvailable(false);

        bookInventory = bookInventoryRepository.save(bookInventory);

        Txn txn = new Txn();
        txn.setBook(book);
        txn.setBookInventory(bookInventory);
        txn.setUser(user);

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR,lendBookRequest.getDays());
        Date dueDate = calendar.getTime();

        txn.setLendDate(today);
        txn.setDueDate(dueDate);
        txn.setStatus(true);
        txn =  txnRepository.save(txn);

        return new ApiResponse(ResponseMessages.BOOK_LENDING_COMPLETED_SUCCESSFULLY.getValue(), "", true);
    }

//    public ApiResponse lendBookInAdvance(LendBookInAdvanceRequest lendBookInAdvanceRequest) throws Exception {
//
//    }
//

    @Transactional
    public ApiResponse returnBook(ReturnBookRequest returnBookRequest) throws Exception{

        User user = userRepository.findById(returnBookRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFound("User", "userId", returnBookRequest.getUserId()));

        BookInventory bookInventory = bookInventoryRepository.findById(returnBookRequest.getBookInventoryId())
                .orElseThrow(() -> new ResourceNotFound("Book", "bookInventoryId", returnBookRequest.getBookInventoryId()));

        BookInventory finalBookInventory = bookInventory;

        Book book = bookRepository.findById(bookInventory.getBook().getId())
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", finalBookInventory.getBook().getId()));

        if(bookInventory.getIsAvailable()){
            return new ApiResponse("", ResponseMessages.BOOK_HAS_NOT_BEEN_ISSUED.getValue(), false);
        }

        if(!book.getStatus()){
            return new ApiResponse("", ResponseMessages.BOOK_DOES_NOT_EXIST.getValue(), false);
        }

        Txn txn = txnRepository.findByBookInventoryIdAndUserIdAndStatusTrue(returnBookRequest.getBookInventoryId(),
                returnBookRequest.getUserId()).orElseThrow(() -> new ResourceNotFound("Transaction", "bookInventoryId : " + returnBookRequest.getBookInventoryId()
                + " userId ", returnBookRequest.getUserId()));

        bookInventory.setIsAvailable(true);
        bookInventory = bookInventoryRepository.save(bookInventory);

        txn.setStatus(false);
        Date returnDate = new Date();
        txn.setReturnDate(returnDate);
        txn = txnRepository.save(txn);
        return new ApiResponse(ResponseMessages.BOOK_RETURNED_SUCCESSFULLY.getValue(), "", true);
    }


    public List<TxnDto> getLendingRecords() throws Exception{

        List<Txn> txns = txnRepository.findByStatusTrue()
                .orElseThrow(() -> new ResourceNotFound("Transactions", "active status true", 1));

        return txns.stream()
                .map(CustomMapper::txnToTxnDtoMapper)
                .toList();
    }

    @Transactional
    public List<TxnDto> getLendingRecordsByUserId(Integer userId) throws Exception{

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFound("User", "userId", userId));

        List<Txn> txns = txnRepository.findByUserIdAndStatusTrue(userId)
                .orElseThrow(() -> new ResourceNotFound("Transactions", "for userId : " + userId + " and active status", 1));

        return txns.stream()
                .map(CustomMapper::txnToTxnDtoMapper)
                .toList();
    }
}
