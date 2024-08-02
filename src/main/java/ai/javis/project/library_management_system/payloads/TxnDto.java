package ai.javis.project.library_management_system.payloads;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TxnDto {

    private Integer id;

    private Integer bookId;

    private Integer unifiedBookId;

    private Integer userId;

    @Temporal(TemporalType.DATE)
    private Date lendDate;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private Boolean status; // 0 closed 1 active
}
