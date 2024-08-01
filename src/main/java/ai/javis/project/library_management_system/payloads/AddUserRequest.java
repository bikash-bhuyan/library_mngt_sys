package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUserRequest {
    @NotNull(message = "User name is mandatory")
    @NotBlank(message = "User name cannot be blank")
    private String name;
    @NotNull(message = "User email is mandatory")
    @NotBlank(message = "User email cannot be blank")
    @Email(message = "Provide a correct email address")
    private String email;
    @NotNull(message = "Membership date is mandatory")
    @PastOrPresent(message = "Membership date must be in the past or present")
    private Date membershipDate; // required? or we should add internally
}
