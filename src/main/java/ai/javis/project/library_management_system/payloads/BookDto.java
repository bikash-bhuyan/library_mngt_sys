package ai.javis.project.library_management_system.payloads;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Integer bookId;
    private String title;
    private String authorName;
    private LocalDate publishedDate;
    private List<String> genre;
    private Boolean status;
}
