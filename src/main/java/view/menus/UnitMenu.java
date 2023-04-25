package view.menus;

import controller.UnitMenuController;
import view.enums.Command;
import view.enums.Message;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private final UnitMenuController controller;

    {
        this.controller = new UnitMenuController();
    }

    public void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = Command.MOVE_UNIT.getMatcher(command)) != null)
                System.out.println(this.controller.moveUnit(matcher));
            else if ((matcher = Command.PATROL_UNIT.getMatcher(command)) != null)
                System.out.println(this.controller.patrolUnit(matcher));
            else if ((matcher = Command.SET_UNIT.getMatcher(command)) != null)
                System.out.println(this.controller.setUnitState(matcher));
            else if ((matcher = Command.ATTACK.getMatcher(command)) != null)
                System.out.println(this.controller.attack(matcher));
            else if ((matcher = Command.POUR_OIL.getMatcher(command)) != null)
                System.out.println(this.controller.pourOil(matcher));
            else if ((matcher = Command.DIG_TUNNEL.getMatcher(command)) != null)
                System.out.println(this.controller.digTunnel(matcher));
            else if ((matcher = Command.BUILD_EQUIPMENT.getMatcher(command)) != null)
                System.out.println(this.controller.buildEquipment(matcher));
            else if (Command.DISBAND_UNIT.getMatcher(command) != null)
                System.out.println(this.controller.disbandUnit());
            else if (Command.BACK_GAME_MENU.getMatcher(command) != null) {
                System.out.println(Message.BACK_GAME_MENU);
                return;
            } else
                System.out.println("Invalid command!");
        }
    }
}
