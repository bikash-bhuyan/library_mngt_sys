package ai.javis.project.library_management_system.controllers;

import ai.javis.project.library_management_system.payloads.*;
import ai.javis.project.library_management_system.services.BookInventoryService;
import ai.javis.project.library_management_system.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookInventoryService bookInventoryService;
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@Valid @RequestBody AddBookRequest addBookRequest){
        try{
            ApiResponse response = bookService.addBook(addBookRequest);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }catch (Exception ex){
            ApiResponse response = new ApiResponse("", ex.getMessage(), false);
            return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@Valid @RequestBody UpdateBookRequest updateBookRequest, @PathVariable(name="bookId") Integer bookId){

        if(updateBookRequest.isEmpty()){
            ApiResponse apiResponse = new ApiResponse("","At least one field need to be provided", false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        if(updateBookRequest.getTitle() != null && updateBookRequest.getTitle().trim().isEmpty()){
            ApiResponse apiResponse = new ApiResponse("", "Book title cannot be blank", false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        if(updateBookRequest.getAuthorName() != null && updateBookRequest.getAuthorName().trim().isEmpty()){
            ApiResponse apiResponse = new ApiResponse("", "Author name cannot be blank", false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        try {
            ApiResponse response = bookService.updateBook(updateBookRequest, bookId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception ex){
            ApiResponse response = new ApiResponse("", ex.getMessage(), false);
            return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBooks(@Valid @RequestBody DeleteBookRequest deleteBookRequest){
        try {
            Map<Integer, String> response = bookService.deleteBooks(deleteBookRequest);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get")
    public ResponseEntity<?> getBooks(@Valid @RequestBody GetBooksRequest getBooksRequest){
        if(getBooksRequest.getTitle() != null && getBooksRequest.getTitle().trim().isEmpty()){
            ApiResponse apiResponse = new ApiResponse("", "Book title cannot be blank", false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        if(getBooksRequest.getAuthor() != null && getBooksRequest.getAuthor().trim().isEmpty()){
            ApiResponse apiResponse = new ApiResponse("", "Author name cannot be blank", false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        try{
            List<GetBooksResponse> response = bookService.getBooks(getBooksRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("",ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get/{bookId}")
    public ResponseEntity<?> getBookById(@Valid @PathVariable(name="bookId") Integer bookId){
        if(bookId <= 0){
            return new ResponseEntity<>(
             new ApiResponse("","bookId isn't valid", false),
                    HttpStatus.BAD_REQUEST);
        }
        try{
            BookDto response = bookService.getBookById(bookId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("",ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<?> addBookToInventory(@RequestBody AddBookToInventoryRequest addBookToInventoryRequest){
        try{
            ApiResponse apiResponse = bookInventoryService.addBookToInventory(addBookToInventoryRequest);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("", ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/inventory/delete/{bookInventoryId}")
    public ResponseEntity<?> deleteBookFromInventory(@PathVariable(name="bookInventoryId") Integer bookInventoryId){
        try{
            ApiResponse apiResponse = bookInventoryService.deleteBookFromInventory(bookInventoryId);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse("",ex.getMessage(),false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
