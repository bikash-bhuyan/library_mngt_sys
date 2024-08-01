package ai.javis.project.library_management_system.services;

import ai.javis.project.library_management_system.payloads.*;

import java.util.List;
import java.util.Map;

public interface BookService {
    ApiResponse addBook(AddBookRequest addBookRequest) throws Exception;
    ApiResponse updateBook(UpdateBookRequest updateBookRequest, Integer bookId) throws Exception;
    Map<Integer, String> deleteBooks(DeleteBookRequest deleteBookRequest) throws Exception;
    BookDto getBookById(Integer bookId) throws Exception;
    List<GetBooksResponse> getBooks(GetBooksRequest getBooksRequest) throws Exception;
}
