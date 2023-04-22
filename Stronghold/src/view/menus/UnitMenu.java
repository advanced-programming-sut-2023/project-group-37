package view.menus;

import controller.UnitMenuController;
import view.enums.Results;
import view.enums.commands.UnitMenuCommands;
import view.enums.messages.MapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    private final UnitMenuController controller = new UnitMenuController();
    private final Scanner scanner;

    public UnitMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.MOVE_UNIT)) != null)
                System.out.println(controller.moveUnit(matcher));

            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.PATROL_UNIT)) != null)
                System.out.println(controller.patrolUnit(matcher));

            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.SET_UNIT)) != null)
                System.out.println(controller.setUnitState(matcher));

            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.ATTACK)) != null)
                System.out.println(controller.attack(matcher));

            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.POUR_OIL)) != null)
                System.out.println(controller.pourOil(matcher));

            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.DIG_TUNNEL)) != null)
                System.out.println(controller.digTunnel(matcher));

            else if ((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.BUILD_EQUIPMENT)) != null)
                System.out.println(controller.buildEquipment(matcher));

            else if (UnitMenuCommands.getMatcher(command, UnitMenuCommands.DISBAND_UNIT) != null)
                System.out.println(controller.disbandUnit());

            else if(UnitMenuCommands.getMatcher(command, UnitMenuCommands.BACK_GAME_MENU) != null)
            {
                System.out.println(MapMenuMessages.BACK_GAME_MENU);
                return;
            }

            else System.out.println("Invalid command!");
        }
    }
}
