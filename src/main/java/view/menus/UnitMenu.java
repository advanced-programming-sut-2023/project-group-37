package view.menus;

import controller.MultiMenuFunctions;
import controller.UnitMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private final UnitMenuController controller;
    private final Scanner scanner;

    public UnitMenu(Scanner scanner, UnitMenuController unitMenuController) {
        this.scanner = scanner;
        this.controller = unitMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.SELECT_TYPE_UNIT.getMatcher(command)) != null)
                System.out.println(controller.selectUnitWithType(MultiMenuFunctions.deleteQuotations(matcher.group("type"))));

            else if ((matcher = Command.MOVE_UNIT.getMatcher(command)) != null) {
                try {
                    System.out.println(this.controller.moveUnit(Integer.parseInt(matcher.group("x")),
                            Integer.parseInt(matcher.group("y"))));
                } catch (Exception ex) {
                    System.out.println(Message.EMPTY_FIELD);
                }
            } else if ((matcher = Command.PATROL_UNIT.getMatcher(command)) != null) {
                try {
                    System.out.println(this.controller.patrolUnit(Integer.parseInt(matcher.group("x1")), Integer.parseInt(matcher.group("y1")),
                            Integer.parseInt(matcher.group("x2")), Integer.parseInt(matcher.group("y2"))));
                } catch (Exception ex) {
                    System.out.println(Message.EMPTY_FIELD);
                }
            } else if ((matcher = Command.SET_UNIT.getMatcher(command)) != null)
                System.out.println(this.controller.setUnitState(MultiMenuFunctions.deleteQuotations(matcher.group("state"))));
            else if ((matcher = Command.ATTACK.getMatcher(command)) != null) {
                try {
                    System.out.println(this.controller.attack(Integer.parseInt(matcher.group("x")),
                            Integer.parseInt(matcher.group("y")), matcher.group("isEarth") != null));
                } catch (Exception ex) {
                    System.out.println(Message.EMPTY_FIELD);
                }
            } else if ((matcher = Command.POUR_OIL.getMatcher(command)) != null)
                System.out.println(this.controller.pourOil(matcher));
            else if ((matcher = Command.DIG_TUNNEL.getMatcher(command)) != null)
                System.out.println(this.controller.digTunnel(matcher));
            else if ((matcher = Command.BUILD_EQUIPMENT.getMatcher(command)) != null)
                System.out.println(this.controller.buildEquipment(matcher));
            else if ((matcher = Command.DIG_MOAT.getMatcher(command)) != null)
                System.out.println(this.controller.digMoat(matcher));
            else if ((matcher = Command.CANCEL_DIG_MOAT.getMatcher(command)) != null)
                System.out.println(this.controller.cancelDigMoat(matcher));
            else if (command.matches(Command.CANCEL_MOVE.toString()))
                System.out.println(this.controller.cancelMove());
            else if (command.matches(Command.CANCEL_PATROL.toString()))
                System.out.println(this.controller.cancelPatrol());
            else if (command.matches(Command.DISBAND_UNIT.toString()))
                System.out.println(this.controller.disbandUnit());
            else if (command.matches(Command.BACK_GAME_MENU.toString())) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else
                System.out.println(Message.INVALID_COMMAND);
        }
    }
}
