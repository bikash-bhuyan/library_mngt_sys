package ai.javis.project.library_management_system.helpers;

import ai.javis.project.library_management_system.models.Book;
import ai.javis.project.library_management_system.payloads.GetBooksRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookSpecification {
    public static Specification<Book> getBooksSpecification(GetBooksRequest getBooksRequest) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (getBooksRequest.getTitle() != null) {
                predicates.add(criteriaBuilder.equal(root.get("title"), getBooksRequest.getTitle()));
            }

            if (getBooksRequest.getAuthor() != null) {
                predicates.add(criteriaBuilder.like(root.get("authorName"), "%" + getBooksRequest.getAuthor() + "%"));
            }

            if (getBooksRequest.getGenre() != null && !getBooksRequest.getGenre().isEmpty()) {

                Predicate genrePredicate = criteriaBuilder.disjunction();

                for(String genre : getBooksRequest.getGenre()){
                    genrePredicate = criteriaBuilder.or(genrePredicate, criteriaBuilder.like(root.get("genres") , "%" + genre + "%"));
                }

                predicates.add(genrePredicate);
            }

            predicates.add(criteriaBuilder.isTrue(root.get("status")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
