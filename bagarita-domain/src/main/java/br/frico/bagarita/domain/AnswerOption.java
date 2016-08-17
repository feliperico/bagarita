package br.frico.bagarita.domain;

/**
 * Option for a multiple choice question.
 *
 * Created by Felipe Rico on 8/17/2016.
 */
public class AnswerOption {

    private Boolean right;

    private String body;

    private String documentBody;

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
}
