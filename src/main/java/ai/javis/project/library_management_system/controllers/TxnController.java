package ai.javis.project.library_management_system.controllers;

import ai.javis.project.library_management_system.payloads.ApiResponse;
import ai.javis.project.library_management_system.payloads.LendBookRequest;
import ai.javis.project.library_management_system.payloads.ReturnBookRequest;
import ai.javis.project.library_management_system.payloads.TxnDto;
import ai.javis.project.library_management_system.services.TxnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class TxnController {
    @Autowired
    private TxnService txnService;
    @PostMapping("/books/lend")
    public ResponseEntity<ApiResponse> lendBook(@Valid @RequestBody LendBookRequest lendBookRequest){
        try {
            ApiResponse response = txnService.lendBook(lendBookRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(), false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/books/return")
    public ResponseEntity<ApiResponse> returnBook(@Valid @RequestBody ReturnBookRequest returnBookRequest){
        try{
            ApiResponse response = txnService.returnBook(returnBookRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/lend/get")
    public ResponseEntity<?> getLendingRecords(){
        try{
            List<TxnDto> response = txnService.getLendingRecords();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/lend/get/{userId}")
    public ResponseEntity<?> getLendingRecordsByUserId(@PathVariable Integer userId){

        if(userId == null || userId < 1){
            return new ResponseEntity<>(new ApiResponse("","Provide valid user details", false), HttpStatus.BAD_REQUEST);
        }
        try{
            List<TxnDto> response = txnService.getLendingRecordsByUserId(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }


}
