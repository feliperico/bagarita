package br.frico.bagarita.domain.repos.question;

import br.frico.bagarita.domain.question.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Question}
 * Created by Felipe Rico on 8/18/2016.
 */
@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
}
