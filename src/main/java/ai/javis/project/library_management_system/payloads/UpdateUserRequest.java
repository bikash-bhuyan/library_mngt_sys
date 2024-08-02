package ai.javis.project.library_management_system.payloads;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
