package ui;

import combatants.Player;
import combatants.Warrior;
import combatants.Wizard;
import items.*;
import battle.Difficulty;

import java.util.Scanner;

// Handles game setup: character selection, item picks, difficulty, and replay
public class GameUI {
    private Scanner scanner;

    public GameUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcomeScreen() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║                                                ║");
        System.out.println("║       TURN-BASED COMBAT ARENA                  ║");
        System.out.println("║                                                ║");
        System.out.println("║       SC2002 Group Assignment                  ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println();
    }

    public Player selectPlayer() {
        System.out.println("\n=== SELECT YOUR CHARACTER ===\n");

        System.out.println("1. WARRIOR");
        System.out.println("   HP: 260 | ATK: 40 | DEF: 20 | SPD: 30");
        System.out.println("   Special Skill: Shield Bash");
        System.out.println("   - Deal BasicAttack damage to selected enemy");
        System.out.println("   - Stun target for current turn and next turn");
        System.out.println("   - Cooldown: 3 turns");
        System.out.println();

        System.out.println("2. WIZARD");
        System.out.println("   HP: 200 | ATK: 50 | DEF: 10 | SPD: 20");
        System.out.println("   Special Skill: Arcane Blast");
        System.out.println("   - Deal BasicAttack damage to ALL enemies");
        System.out.println("   - Each enemy defeated adds +10 ATK (permanent)");
        System.out.println("   - Cooldown: 3 turns");
        System.out.println();

        while (true) {
            System.out.print("Enter choice (1-2): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("\nYou selected: WARRIOR");
                return new Warrior();
            } else if (choice.equals("2")) {
                System.out.println("\nYou selected: WIZARD");
                return new Wizard();
            } else {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
    }

    public void selectItems(Player player) {
        System.out.println("\n=== SELECT YOUR ITEMS (2 items, duplicates allowed) ===\n");

        displayItemInfo();

        for (int i = 1; i <= 2; i++) {
            System.out.println("\n--- Select Item " + i + " ---");
            Item item = getItemChoice();
            player.addItem(item);
            System.out.println("Added " + item.getName() + " to inventory.");
        }

        System.out.println("\nFinal Inventory:");
        for (Item item : player.getInventory()) {
            System.out.println("  - " + item.getName());
        }
    }

    private void displayItemInfo() {
        System.out.println("1. POTION");
        System.out.println("   Effect: Heal 100 HP");
        System.out.println("   New HP = min(Current HP + 100, Max HP)");
        System.out.println();

        System.out.println("2. POWER STONE");
        System.out.println("   Effect: Trigger special skill without cooldown");
        System.out.println("   Does not start or change cooldown timer");
        System.out.println();

        System.out.println("3. SMOKE BOMB");
        System.out.println("   Effect: Enemy attacks deal 0 damage");
        System.out.println("   Lasts for current turn and next turn");
        System.out.println();
    }

    private Item getItemChoice() {
        while (true) {
            System.out.print("Enter choice (1-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    return new Potion();
                case "2":
                    return new PowerStone();
                case "3":
                    return new SmokeBomb();
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public Difficulty selectDifficulty() {
        System.out.println("\n=== SELECT DIFFICULTY ===\n");

        System.out.println("Enemy Stats:");
        System.out.println("  Goblin: HP: 55 | ATK: 35 | DEF: 15 | SPD: 25");
        System.out.println("  Wolf:   HP: 40 | ATK: 45 | DEF: 5  | SPD: 35");
        System.out.println();

        System.out.println("1. EASY");
        System.out.println("   Initial Spawn: 3 Goblins");
        System.out.println("   Backup Spawn: None");
        System.out.println("   Total Enemies: 3");
        System.out.println();

        System.out.println("2. MEDIUM");
        System.out.println("   Initial Spawn: 1 Goblin, 1 Wolf");
        System.out.println("   Backup Spawn: 2 Wolves");
        System.out.println("   Total Enemies: 4");
        System.out.println();

        System.out.println("3. HARD");
        System.out.println("   Initial Spawn: 2 Goblins");
        System.out.println("   Backup Spawn: 1 Goblin, 2 Wolves");
        System.out.println("   Total Enemies: 5");
        System.out.println();

        while (true) {
            System.out.print("Enter choice (1-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\nYou selected: EASY");
                    return Difficulty.EASY;
                case "2":
                    System.out.println("\nYou selected: MEDIUM");
                    return Difficulty.MEDIUM;
                case "3":
                    System.out.println("\nYou selected: HARD");
                    return Difficulty.HARD;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public int askPlayAgain() {
        System.out.println("\nWould you like to play again?");
        System.out.println("1. Yes (same settings)");
        System.out.println("2. Yes (new game)");
        System.out.println("3. No (exit)");

        while (true) {
            System.out.print("\nEnter choice (1-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public void displayGameSummary(Player player, Difficulty difficulty) {
        System.out.println("\n=== GAME SUMMARY ===");
        System.out.println("Player: " + player.getClassName());
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Items:");
        for (Item item : player.getInventory()) {
            System.out.println("  - " + item.getName());
        }
        System.out.println("==================\n");
    }

    public void displayExitMessage() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║                                                ║");
        System.out.println("║       Thanks for playing!                      ║");
        System.out.println("║                                                ║");
        System.out.println("║       SC2002 Group Assignment                  ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
