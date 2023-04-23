package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands {
    REGISTER("\\s*user\\s+create(\\s+((-u\\s+(?<username>\".+\"|\\S+))|" +
            "(-p\\s+(?<password>\".+\"|\\S+)\\s+(?<passwordConfirm>\".+\"|\\S+))|" +
            "(-e\\s+(?<email>\".+\"|\\S+))|(-s\\s+(?<slogan>\".+\"|\\S+))|" +
            "(-n\\s+(?<nickName>\".+\"|\\S+))))*\\s*"),
    REGISTER_RANDOM_PASSWORD("\\s*user\\s+create(\\s+((-u\\s+(?<username>\".+\"|\\S+))|" +
            "(-p\\s+(?<password>\".+\"|\\S+))|" +
            "(-e\\s+(?<email>\".+\"|\\S+))|(-s\\s+(?<slogan>\".+\"|\\S+))|" +
            "(-n\\s+(?<nickName>\".+\"|\\S+))))*\\s*"),
    PICK_QUESTION("\\s*question\\s+pick(\\s+((-q\\s+(?<questionNumber>\\d+))|" +
            "(-a\\s+(?<answer>\".+\"|\\S+))|(-c\\s+(?<answerConfirm>\".+\"|\\S+))))*\\s*"),
    CANCEL("\\s*cancel\\s*"),
    ENTER_LOGIN_MENU("\\s*enter\\s+login\\s+menu\\s*"),
    EXIT("\\s*exit\\s*");


    private final String regex;
    RegisterMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, RegisterMenuCommands registerLoginMenuCommands) {
        Matcher matcher = Pattern.compile(registerLoginMenuCommands.regex).matcher(command);

        if (matcher.matches())
            return matcher;
        return null;
    }
}