package ai.javis.project.library_management_system.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name= "lending_books")
public class Txn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn
    private Book book; // reference to a book
    @ManyToOne
    @JoinColumn
    private BookInventory bookInventory; // reference to a bookInventory
    @ManyToOne
    @JoinColumn
    private User user; // reference to a user
    @Temporal(TemporalType.DATE)
    private Date lendDate;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    private Boolean status; // 0 closed 1 active
}
