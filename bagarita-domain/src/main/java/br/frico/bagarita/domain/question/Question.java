package br.frico.bagarita.domain.question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a question.
 *
 * Created by Felipe Rico on 8/17/2016.
 */
@Entity
@Table(name = "BAG_QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "BAG_SEQUENCE")
    private Long id;

    @Column(name = "BODY", nullable = false)
    private String body;

    @Column(name = "DOCUMENT_BODY")
    private String documentBody;

    @Column(name = "PRESENTATION_BODY")
    private String presentationBody;

    private String solution;

    @Column(name = "DOCUMENT_SOLUTION")
    private String documentSolution;

    @OneToMany(mappedBy = "question")
    private List<AnswerOption> answerOptions = new ArrayList<>();

    @Column(name = "QUESTION_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "BAG_QUESTION_DISCIPLINE",
        joinColumns = {@JoinColumn(name = "QUESTION_ID")},
        inverseJoinColumns = {@JoinColumn(name = "DISCIPLINE_ID")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"QUESTION_ID","DISCIPLINE_ID"})})
    private List<Discipline> disciplines = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "BAG_QUESTION_SUBJECT",
            joinColumns = {@JoinColumn(name = "QUESTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SUBJECT_ID")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"QUESTION_ID","SUBJECT_ID"})})
    private List<Subject> subjects = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "QUESTION_SOURCE_ID")
    private QuestionSource source;

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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getDocumentSolution() {
        return documentSolution;
    }

    public void setDocumentSolution(String documentSolution) {
        this.documentSolution = documentSolution;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public QuestionSource getSource() {
        return source;
    }

    public void setSource(QuestionSource source) {
        this.source = source;
    }

    public String getPresentationBody() {
        return presentationBody;
    }

    public void setPresentationBody(String consolidatePresentation) {
        this.presentationBody = consolidatePresentation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
