package view.menus;

import controller.RegisterMenuController;
import model.utils.Captcha;
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

    public RegisterMenu(Scanner scanner, RegisterMenuController registerMenuController) {
        this.scanner = scanner;
        this.controller = registerMenuController;
    }

    public Result run() {
        Matcher matcher;

        while (true) {
            this.command = scanner.nextLine();

            if ((matcher = Command.REGISTER.getMatcher(this.command)) != null
                    || (matcher = Command.REGISTER_RANDOM_PASSWORD.getMatcher(this.command)) != null) {
                if (register(matcher))
                    return Result.USER_CREATED;
            }

            else if (command.matches(Command.ENTER_LOGIN_MENU.toString())) {
                System.out.println(Message.ENTERED_LOGIN_MENU);
                return Result.ENTER_LOGIN_MENU;
            }

            else if (command.matches(Command.EXIT.toString()))
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

                if (Message.DO_CAPTCHA.equals(this.message)) {
                    Captcha captcha = new Captcha();
                    captcha.createCaptcha();
                    while (!command.equals("cancel")) {
                        this.command = scanner.nextLine();
                        if (command.equals(captcha.getCaptchaNumber())) {
                            System.out.println(controller.captcha());
                            return true;
                        } else if (!command.equals("cancel")) {
                            System.out.println(Message.WRONG_CAPTCHA);
                        }
                    }
                    System.out.println(Message.CANCEL);
                    return false;
                }
            } else if (Command.CANCEL.getMatcher(this.command) != null) {
                System.out.println(Message.CANCEL);
                return false;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }

    private boolean register(Matcher matcher) {
        this.message = this.controller.register(matcher);
        System.out.println(message);

        if (this.message.contains("re-enter")) {
            do {
                this.command = this.scanner.nextLine();

                this.message = this.controller.checkPasswordConfirm(this.command);
                System.out.println(this.message);

            } while (Message.REENTER_AGAIN.equals(message));

            if (Message.ASK_FOR_SECURITY_QUESTION.equals(this.message))
                return pickQuestion();
        } else if (this.message.contains("Pick"))
            return pickQuestion();


        return false;
    }
}