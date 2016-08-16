package br.frico.bagarita.domain.repos;

import br.frico.bagarita.domain.Discipline;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Felipe Rico on 8/16/2016.
 */
public interface DisciplineRepository extends PagingAndSortingRepository<Discipline, Long> {
}
