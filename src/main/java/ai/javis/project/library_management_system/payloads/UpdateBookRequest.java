package ai.javis.project.library_management_system.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateBookRequest {
    private String title;
    private String authorName;
    @PastOrPresent(message = "Book publish date must be in the past or present")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate publishedDate;
    private List<@NotBlank(message = "Book genre cannot be blank") String> genre;
    private Boolean status;

    public Boolean isEmpty(){
        return ((title == null || title.trim().isEmpty())
                && (authorName == null || authorName.trim().isEmpty())
                && publishedDate == null
                && (genre == null || genre.isEmpty())
                && status == null
        );
    }
}
