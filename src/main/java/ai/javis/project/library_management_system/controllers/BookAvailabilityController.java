package ai.javis.project.library_management_system.controllers;

import ai.javis.project.library_management_system.enums.ResponseMessages;
import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.services.BookAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availability")
public class BookAvailabilityController {
    @Autowired
    private BookAvailabilityService bookAvailabilityService;
    @GetMapping("/books/{bookId}")
    public ResponseEntity<?> checkAvailabilityOfBook(@PathVariable(name = "bookId") Integer bookId){

        if(bookId <= 0){
            ApiResponse apiResponse = new ApiResponse("", ResponseMessages.BOOK_ID_NOT_VALID.getValue(), false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        try{
            return bookAvailabilityService.checkAvailabilityOfBook(bookId);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(), false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

    }
}
