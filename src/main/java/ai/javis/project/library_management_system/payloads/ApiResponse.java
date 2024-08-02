package ai.javis.project.library_management_system.payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private String successMessage;
    private String errorMessage;
    private Boolean success;
}
