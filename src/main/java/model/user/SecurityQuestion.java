package model.user;

public enum SecurityQuestion {

    Q1("What is my father's name?"),
    Q2("What was my first pet's name?"),
    Q3("What is my mother's last name?");

    public static String getAllQuestions(){
        StringBuilder result = new StringBuilder();

        int index = 1;

        for (SecurityQuestion question : values()) {
            result.append(index).append(". ").append(question.question).append(" ");
            if (index % 2 == 1)
                result.append("\n");
            index++;
        }

        return result.toString().trim();
    }

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public static String getQuestion(int number) {
        return values()[number - 1].question;
    }
}
