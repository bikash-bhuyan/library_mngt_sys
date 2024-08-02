package ai.javis.project.library_management_system.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "book_inventory")
public class BookInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Book book;

    @NotNull
    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @NotNull
    @Column(name = "status")
    private Boolean status = true;
}
