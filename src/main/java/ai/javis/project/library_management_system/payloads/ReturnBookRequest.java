package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReturnBookRequest {
    @NotNull(message = "Unified book id is mandatory")
    @Positive(message = "Unified book id isn't valid")
    private Integer bookInventoryId;
    @NotNull(message = "User id is mandatory")
    @Positive(message = "User id isn't valid")
    private Integer userId;
}
