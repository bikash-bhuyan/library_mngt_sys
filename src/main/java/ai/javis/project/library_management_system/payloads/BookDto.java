package ai.javis.project.library_management_system.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Integer bookId;
    private String title;
    private String authorName;
    private LocalDate publishedDate;
    private List<String> genre;
    private Boolean status;
}
