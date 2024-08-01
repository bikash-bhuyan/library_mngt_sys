package ai.javis.project.library_management_system.utils;

import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.models.Txn;
import ai.javis.project.library_management_system.models.User;
import ai.javis.project.library_management_system.payloads.*;

import java.util.stream.Collectors;

public class CustomMapper {
    public static Book addBookRequestToBookMapper(AddBookRequest addBookRequest){
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthorName(addBookRequest.getAuthorName());
        book.setPublishedDate(addBookRequest.getPublishedDate());
        book.setIsAvailable(addBookRequest.getIsAvailable());
        book.setStatus(addBookRequest.getStatus());
        String genres = addBookRequest.getGenre()
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.joining(","));

        book.setGenres(genres);
        return book;
    }
    public static BookDto bookToBookDtoMapper(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthorName(book.getAuthorName());
        bookDto.setPublishedDate(book.getPublishedDate());
        bookDto.setIsAvailable(book.getIsAvailable());
        bookDto.setStatus(book.getStatus());
        return bookDto;
    }
    public static GetBooksResponse bookToGetBookResponseMapper(Book book){
        GetBooksResponse getBooksResponse = new GetBooksResponse();

        getBooksResponse.setTitle(book.getTitle());
        getBooksResponse.setAuthorName(book.getAuthorName());
        getBooksResponse.setPublishedDate(book.getPublishedDate());
        getBooksResponse.setIsAvailable(book.getIsAvailable());
        return getBooksResponse;
    }
    public static User addUserRequestToUserMapper(AddUserRequest addUserRequest){
        User user = new User();
        user.setName(addUserRequest.getName());
        user.setEmail(addUserRequest.getEmail());
        user.setMembershipDate(addUserRequest.getMembershipDate());
        return user;
    }
    public static TxnDto txnToTxnDtoMapper(Txn txn){
        TxnDto txnDto = new TxnDto();
        txnDto.setId(txn.getId());
        txnDto.setBookId(txn.getBook().getId());
        txnDto.setUserId(txn.getUser().getId());
        txnDto.setLendDate(txn.getLendDate());
        txnDto.setDueDate(txn.getDueDate());
        txnDto.setReturnDate(txn.getReturnDate());
        txnDto.setStatus(txn.getStatus());
        return txnDto;
    }

}
