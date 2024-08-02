package ai.javis.project.library_management_system.enums;


public enum ResponseMessages {
    TOKEN_NOT_FOUND("Token not found"),
    TOKEN_IS_INVALID("Provided token is invalid"),
    USER_DETAILS_NOT_VALID("User details aren't valid"),
    USER_NAME_CANNOT_BE_BLANK("User name can't be blank"),
    USER_EMAIL_CANNOT_BE_BLANK("User email can't be blank"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_CREATED_SUCCESSFULLY("User created successfully"),
    USER_UPDATED_SUCCESSFULLY("User updated successfully"),
    USER_ALREADY_REGISTERED_AS_PRIME_MEMBER("User is already registered as prime member"),
    BOOK_UPDATED_SUCCESSFULLY("Book updated successfully"),
    BOOK_ID_NOT_VALID("Book id isn't valid"),
    BOOK_TITLE_CANNOT_BE_BLANK("Book title can't be blank"),
    BOOK_TITLE_IS_MANDATORY("Book title is mandatory"),
    BOOK_DOES_NOT_EXIST("Book doesn't exist in the library"),
    NOT_ABLE_TO_FETCH_EARLIEST_DATE( "Not able to fetch earliest date"),
    BOOKS_ADDED_TO_INVENTORY(" Books added to inventory successfully (Max Limit per operation) "),
    BOOK_IS_ON_ISSUE("Book has been issued by an user"),
    BOOK_DELETED_SUCCESSFULLY("Book deleted successfully"),
    BOOK_CREATED_SUCCESSFULLY("Book created successfully"),
    BOOK_NOT_AVAILABLE_TO_LEND("Book is not available to lend "),
    BOOK_LENDING_COMPLETED_SUCCESSFULLY("Book lending completed successfully"),
    BOOK_HAS_NOT_BEEN_ISSUED("Book hasn't been issued from the library"),
    BOOK_RETURNED_SUCCESSFULLY("Book returned successfully"),
    BOOK_STATUS_CANNOT_BE_CHANGED_BOOK_ON_ISSUE("Can't change status field as book/books is/are issued to user(s)"),
    BOOK_CANNOT_BE_DELETED_BOOK_ON_ISSUE("Book can't be deleted as some copy/copies is/are issued"),
    AUTHOR_NAME_CANNOT_BE_BLANK("Author name can't be blank"),
    ATLEAST_PROVIDE_ONE_FIELD("At least one field need to be provided");

    private String value;

    ResponseMessages(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
