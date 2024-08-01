package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBookToInventoryRequest {
    @NotNull(message = "book id must be provided")
    @Positive(message = "book id must be positive")
    private Integer bookId;
    @NotNull(message = "numbers of books to be added must be provided")
    @Positive(message = "quantity must be positive")
    private Integer quantity;
}
