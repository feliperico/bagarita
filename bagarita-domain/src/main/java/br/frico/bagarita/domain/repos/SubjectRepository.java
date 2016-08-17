package br.frico.bagarita.domain.repos;

import br.frico.bagarita.domain.Subject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Subject}
 * Created by Felipe Rico on 8/17/2016.
 */
@Repository
public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
}
