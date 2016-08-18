package br.frico.bagarita.domain.repos.question;

import br.frico.bagarita.domain.question.AnswerOption;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Reporitory for {@link AnswerOption}
 * Created by Felipe Rico on 8/18/2016.
 */
@Repository
public interface AnswerOptionRepository extends PagingAndSortingRepository<AnswerOption, Long> {
}
