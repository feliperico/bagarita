package br.frico.bagarita.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a question.
 *
 * Created by Felipe Rico on 8/17/2016.
 */
public class Question {

    private String body;

    private String documentBody;

    private String consolidatedBody;

    private String solution;

    private String documentSolution;

    private List<AnswerOption> answerOptions = new ArrayList<>();

    private QuestionType questionType;

    private List<Discipline> disciplines = new ArrayList<>();

    private List<Subject> subjects = new ArrayList<>();

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

    public String getConsolidatedBody() {
        return consolidatedBody;
    }

    public void setConsolidatedBody(String consolidatedBody) {
        this.consolidatedBody = consolidatedBody;
    }
}
