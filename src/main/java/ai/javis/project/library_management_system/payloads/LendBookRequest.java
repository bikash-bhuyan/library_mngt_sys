package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LendBookRequest {
    @NotNull(message = "Book id must be provided to lend a book")
    @Positive(message = "Book id isn't valid")
    private Integer bookId;
    @NotNull(message = "User id must be provided to lend a book")
    @Positive(message = "User id isn't valid")
    private Integer userId;
    @NotNull(message = "duration of lending must be provided to lend a book")
    @Positive(message = "provide a valid duration")
    private Integer days;
}
