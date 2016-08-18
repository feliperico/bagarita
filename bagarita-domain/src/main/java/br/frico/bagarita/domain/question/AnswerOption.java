package br.frico.bagarita.domain.question;

import javax.persistence.*;

/**
 * Option for a multiple choice question.
 *
 * Created by Felipe Rico on 8/17/2016.
 */
@Entity
@Table(name = "BAG_ANSWER_OPTION")
public class AnswerOption {

    @Id
    @GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence", sequenceName = "BAG_SEQUENCE")
    private Long id;

    @Column(name = "IS_RIGHT")
    private Boolean right;

    @Column(nullable = false)
    private String body;

    @Column(name = "DOCUMENT_BODY")
    private String documentBody;

    @ManyToOne(optional = false)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDocumentBody() {
        return documentBody;
    }

    public void setDocumentBody(String documentBody) {
        this.documentBody = documentBody;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
