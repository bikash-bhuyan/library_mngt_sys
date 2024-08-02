package ai.javis.project.library_management_system.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // LOOK INTO THIS
public class DeleteBookRequest {

    @NotEmpty(message = "At least one book id must be specified")
    private List< @Positive(message = "Book id is invalid") Integer> bookIds;

}
