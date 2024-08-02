package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetBooksRequest {
    private String title;
    private String author;
    private List<@NotBlank(message = "Book genre cannot be blank") String> genre;
}
