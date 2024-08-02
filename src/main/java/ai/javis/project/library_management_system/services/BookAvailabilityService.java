package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.Utils.Utils;
import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.exceptions.ResourceNotFound;
import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.models.BookInventory;
import ai.javis.project.library_management_system.models.Txn;
import ai.javis.project.library_management_system.repositories.BookInventoryRepository;
import ai.javis.project.library_management_system.repositories.BookRepository;
import ai.javis.project.library_management_system.repositories.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookAvailabilityService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookInventoryRepository bookInventoryRepository;
    @Autowired
    private TxnRepository txnRepository;

    public ResponseEntity<?> checkAvailabilityOfBook(Integer bookId) throws Exception{

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFound("Book", "bookId", bookId));

        if(!book.getStatus()){

            Map<String, String> errorResp = new HashMap<>();
            errorResp.put("message", ResponseMessages.BOOK_DOES_NOT_EXIST.getValue());
            return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
        }

        BookInventory bookInventory = bookInventoryRepository.findFirstAvailableByBookId(bookId);

        if(bookInventory != null){

            Map<String,String> resp = new HashMap<>();
            Date date = new Date();
            String formattedDate = Utils.dateFormatter(date);

            resp.put("Available on", formattedDate);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        Txn txn = txnRepository.findByBookIdAndStatusTrueAndEarliestAvailable(bookId);

        if(txn == null){

            Map<String,String> errorResp = new HashMap<>();
            errorResp.put("message",ResponseMessages.NOT_ABLE_TO_FETCH_EARLIEST_DATE.getValue());
            return new ResponseEntity<>(errorResp,HttpStatus.REQUEST_TIMEOUT);
        }

        String formattedDate = Utils.dateFormatter(txn.getDueDate());

        Map<String,String> resp = new HashMap<>();
        resp.put("Available on", formattedDate);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
