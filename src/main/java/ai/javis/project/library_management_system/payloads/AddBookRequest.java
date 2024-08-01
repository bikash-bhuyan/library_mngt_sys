package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddBookRequest {
    @NotNull(message = "Book title is mandatory")
    @NotBlank(message = "Book title cannot be blank")
    private String title;
    @NotNull(message = "Author is mandatory")
    @NotBlank(message = "Author name cannot be blank")
    private String authorName;
    @NotNull(message = "Book published date is mandatory")
    @PastOrPresent(message = "Book publish date must be in the past or present")
    private LocalDate publishedDate;
    @NotEmpty(message = "At least one book genre must be specified")
    private List<@NotBlank(message = "Book genre cannot be blank") String> genre;
}
