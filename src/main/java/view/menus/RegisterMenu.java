package view.menus;

import controller.RegisterMenuController;
import view.enums.Message;
import view.enums.Result;
import view.enums.Command;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenu {
    private final RegisterMenuController controller;
    private final Scanner scanner;
    private String command;
    private Message message;

    {
        this.controller = new RegisterMenuController();
    }

    public RegisterMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public Result run() {
        Matcher matcher;

        while (true) {
            this.command = scanner.nextLine();

            // TODO: test if works fine!
            if ((matcher = Command.REGISTER.getMatcher(this.command)) != null
                    || (matcher = Command.REGISTER_RANDOM_PASSWORD.getMatcher(this.command)) != null) {
                if (register(matcher))
                    return Result.USER_CREATED;
            } else if (Command.ENTER_LOGIN_MENU.getMatcher(this.command) != null)
                return Result.ENTER_LOGIN_MENU;
            else if (Command.EXIT.getMatcher(this.command) != null)
                return Result.EXIT;
            else
                System.out.println("Invalid command!");
        }
    }

    private boolean pickQuestion() {
        Matcher matcher;

        while (true) {
            this.command = this.scanner.nextLine();

            if ((matcher = Command.PICK_QUESTION.getMatcher(this.command)) != null) {
                this.message = this.controller.pickQuestion(matcher);
                System.out.println(this.message);
                if (this.message == Message.REGISTER_SUCCESSFUL)
                    return true;
            } else if (Command.CANCEL.getMatcher(this.command) != null) {
                System.out.println(Message.CANCEL);
                return false;
            } else
                System.out.println("Invalid command!");
        }
    }

    private boolean register(Matcher matcher) {
        this.message = this.controller.register(matcher);

        String randomSlogan;
        if ((randomSlogan = this.controller.getRandomSlogan()) != null)
            System.out.println();
            // TODO: handle!
//            System.out.println(Message.RANDOM_SLOGAN.continueOutput(randomSlogan));

        switch (this.message) {
            case ASK_FOR_SECURITY_QUESTION -> {
                System.out.println(this.message);
                return pickQuestion();
            }
            // TODO: handle!
//            case RANDOM_PASSWORD -> {
//                String randomPassword = this.controller.getRandomPassword();
//                System.out.println(Message.RANDOM_PASSWORD.continueOutput(randomPassword));
//                do {
//                    this.command = this.scanner.nextLine();
//
//                    this.message = this.controller.checkPasswordConfirm(this.command);
//                    System.out.println(this.message);
//
//                } while (this.message != Message.ASK_FOR_SECURITY_QUESTION &&
//                        this.message != Message.CANCEL);
//
//                if (this.message == Message.ASK_FOR_SECURITY_QUESTION)
//                    return pickQuestion();
//            }
        }
        return false;
    }
}