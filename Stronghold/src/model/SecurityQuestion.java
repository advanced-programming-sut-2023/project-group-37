package model;

public class SecurityQuestion {

    private static final String[] questions;

    private final String question;

    private final String answer;

    static {
        questions = new String[]{
                "What is my father’s name?",
                "What was my first pet’s name?",
                "What is my mother’s last name?"
        };
    }

    public SecurityQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestionByIndex(int index) {
        //TODO: Validation check needed?
        return questions[index - 1];
    }

    public boolean isCorrectAnswer(String answer) {
        return this.answer.equals(answer);
    }
}