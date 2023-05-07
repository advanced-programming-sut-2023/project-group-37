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
    private String message;

    public RegisterMenu(Scanner scanner, RegisterMenuController registerMenuController){
        this.scanner = scanner;
        this.controller = registerMenuController;
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
            }

            else if (Command.ENTER_LOGIN_MENU.getMatcher(this.command) != null) {
                System.out.println(Message.ENTERED_LOGIN_MENU);
                return Result.ENTER_LOGIN_MENU;
            }

            else if (Command.EXIT.getMatcher(this.command) != null)
                return Result.EXIT;

            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean pickQuestion() {
        Matcher matcher;

        while (true) {
            this.command = this.scanner.nextLine();

            if ((matcher = Command.PICK_QUESTION.getMatcher(this.command)) != null) {
                this.message = this.controller.pickQuestion(matcher);
                System.out.println(this.message);

                if (Message.REGISTER_SUCCESSFUL.equals(this.message))
                    return true;
            }

            else if (Command.CANCEL.getMatcher(this.command) != null) {
                System.out.println(Message.CANCEL);
                return false;
            }

            else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean register(Matcher matcher) {
        this.message = this.controller.register(matcher);
        System.out.println(message);

        if(this.message.contains("re-enter")) {
            do {
                this.command = this.scanner.nextLine();

                this.message = this.controller.checkPasswordConfirm(this.command);
                System.out.println(this.message);

            } while (Message.REENTER_AGAIN.equals(message));

            if (Message.ASK_FOR_SECURITY_QUESTION.equals(this.message))
                return pickQuestion();
        }

        else if(this.message.contains("Pick"))
            return pickQuestion();


        return false;
    }
}