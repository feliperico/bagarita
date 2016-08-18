package br.frico.bagarita.domain.repos.question;

import br.frico.bagarita.domain.question.QuestionSource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link QuestionSource}
 * Created by Felipe Rico on 8/18/2016.
 */
@Repository
public interface QuestionSourceRepository extends PagingAndSortingRepository<QuestionSource, Long> {
}
