package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    @Email(message = "Provide a valid email address")
    private String email;
    private Boolean primeMember;
    private Date primeMemberValidity;
    public Boolean isEmpty(){
        return (
                (name == null || name.trim().isEmpty())
                && (email == null || email.trim().isEmpty())
                && (primeMember == null)
                && (primeMemberValidity == null)
        );
    }
}
