import combatants.Player;
import combatants.Warrior;
import combatants.Wizard;
import items.*;
import battle.BattleEngine;
import battle.Difficulty;
import ui.GameUI;
import ui.BattleUI;

import java.util.ArrayList;
import java.util.List;

// Entry point: orchestrates game setup, battle, and replay loop
public class Main {
    public static void main(String[] args) {
        GameUI gameUI = new GameUI();
        BattleUI battleUI = new BattleUI();

        gameUI.displayWelcomeScreen();

        Player player = gameUI.selectPlayer();
        gameUI.selectItems(player);
        Difficulty difficulty = gameUI.selectDifficulty();

        String playerClass = player.getClassName();
        List<String> itemNames = snapshotItemNames(player);

        while (true) {
            gameUI.displayGameSummary(player, difficulty);

            System.out.println("Press Enter to start the battle...");
            try {
                System.in.read();
            } catch (Exception e) {
            }

            BattleEngine battleEngine = new BattleEngine(player, difficulty, battleUI);
            battleEngine.startBattle();

            int replayChoice = gameUI.askPlayAgain();

            if (replayChoice == 1) {
                player = createPlayerByClass(playerClass);
                restoreItems(player, itemNames);
            } else if (replayChoice == 2) {
                player = gameUI.selectPlayer();
                gameUI.selectItems(player);
                difficulty = gameUI.selectDifficulty();
                playerClass = player.getClassName();
                itemNames = snapshotItemNames(player);
            } else {
                break;
            }
        }

        gameUI.displayExitMessage();
        gameUI.close();
        battleUI.close();
    }

    private static List<String> snapshotItemNames(Player player) {
        List<String> names = new ArrayList<>();
        for (Item item : player.getInventory()) {
            names.add(item.getName());
        }
        return names;
    }

    private static Player createPlayerByClass(String className) {
        if (className.equals("Warrior")) {
            return new Warrior();
        }
        return new Wizard();
    }

    private static void restoreItems(Player player, List<String> itemNames) {
        for (String name : itemNames) {
            switch (name) {
                case "Potion":
                    player.addItem(new Potion());
                    break;
                case "Power Stone":
                    player.addItem(new PowerStone());
                    break;
                case "Smoke Bomb":
                    player.addItem(new SmokeBomb());
                    break;
            }
        }
    }
}
