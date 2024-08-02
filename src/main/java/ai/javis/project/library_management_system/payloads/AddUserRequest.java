package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddUserRequest {
    @NotNull(message = "User name is mandatory")
    @NotBlank(message = "User name cannot be blank")
    private String name;
    @NotNull(message = "User email is mandatory")
    @NotBlank(message = "User email cannot be blank")
    @Email(message = "Provide a correct email address")
    private String email;
}
