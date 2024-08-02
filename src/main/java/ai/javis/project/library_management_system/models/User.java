package ai.javis.project.library_management_system.models;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @Column(name = "membership_date")
    @Temporal(TemporalType.DATE)
    private Date membershipDate;

    private Boolean primeMember = false;

    @Temporal(TemporalType.DATE)
    private Date primeMemberValidity;

    @OneToMany(mappedBy = "user")
    List<Txn> txnList;
}
