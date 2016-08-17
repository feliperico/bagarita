package br.frico.bagarita.domain.repos;

import br.frico.bagarita.domain.Discipline;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Discipline}
 * Created by Felipe Rico on 8/16/2016.
 */
@Repository
public interface DisciplineRepository extends PagingAndSortingRepository<Discipline, Long> {
}
