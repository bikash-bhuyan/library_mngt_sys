package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class LendBookInAdvanceRequest {

    @NotNull(message = "BookId is mandatory")
    @Positive(message = "BookId provided isn't valid")
    private Integer bookId;

    @NotNull(message = "UserId is mandatory")
    @Positive(message = "UserId provided isn't valid")
    private Integer userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Book date is mandatory")
    private Date bookingDate;

    @NotNull(message = "duration of lending must be provided to lend a book")
    @Positive(message = "provide a valid duration")
    private Integer days;
}
