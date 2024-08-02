package ai.javis.project.library_management_system.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="books" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "author_name"})
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "published_date")
    @Temporal(TemporalType.DATE)
    private LocalDate publishedDate;
    private String genres;
    private Boolean status = true;
    @OneToMany(mappedBy = "book")
    List<Txn> txnList;
    @OneToMany(mappedBy = "book")
    List<BookInventory> booksList;
}
