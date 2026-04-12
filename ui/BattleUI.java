package ui;

import combatants.Enemy;
import combatants.Player;
import items.Item;
import statuseffects.StatusEffect;

import java.util.List;
import java.util.Scanner;

// Handles display and input during active combat (SRP: separated from game logic)
public class BattleUI {
    private Scanner scanner;

    public BattleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayBattleStatus(Player player, List<Enemy> enemies, int roundNumber) {
        System.out.println("\n┌─────────────── Battle Status ───────────────┐");
        System.out.println("│ " + player.getName() + " (" + player.getClassName() + ")");
        System.out.println("│   HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
        System.out.println("│   ATK: " + player.getAttack() + " | DEF: " + player.getDefense() + " | SPD: " + player.getSpeed());

        if (!player.getStatusEffects().isEmpty()) {
            System.out.print("│   Status Effects: ");
            for (StatusEffect effect : player.getStatusEffects()) {
                System.out.print(effect.getName() + "(" + effect.getDuration() + ") ");
            }
            System.out.println();
        }

        if (player.getSpecialSkillCooldown() > 0) {
            System.out.println("│   Special Skill Cooldown: " + player.getSpecialSkillCooldown() + " rounds");
        } else {
            System.out.println("│   Special Skill: READY");
        }

        System.out.println("│");
        System.out.println("│ Enemies:");

        int aliveCount = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                aliveCount++;
                System.out.println("│   " + enemy.getName() + " - HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP());

                if (!enemy.getStatusEffects().isEmpty()) {
                    System.out.print("│     Effects: ");
                    for (StatusEffect effect : enemy.getStatusEffects()) {
                        System.out.print(effect.getName() + "(" + effect.getDuration() + ") ");
                    }
                    System.out.println();
                }
            }
        }

        if (aliveCount == 0) {
            System.out.println("│   (No enemies remaining)");
        }

        System.out.println("└─────────────────────────────────────────────┘");
    }

    public String getPlayerAction(Player player) {
        System.out.println("\nChoose your action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item" + (player.hasItems() ? " (" + player.getInventory().size() + " available)" : " (none available)"));
        System.out.println("4. Special Skill" + (player.getSpecialSkillCooldown() == 0 ? " (READY)" : " (Cooldown: " + player.getSpecialSkillCooldown() + ")"));

        while (true) {
            System.out.print("\nEnter choice (1-4): ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1") || choice.equals("2")) {
                return choice;
            }

            if (choice.equals("3")) {
                if (!player.hasItems()) {
                    System.out.println("You have no items! Choose another action.");
                    continue;
                }
                return choice;
            }

            if (choice.equals("4")) {
                if (player.getSpecialSkillCooldown() > 0) {
                    System.out.println("Special skill is on cooldown! Choose another action.");
                    continue;
                }
                return choice;
            }

            System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
        }
    }

    public Enemy selectTarget(List<Enemy> aliveEnemies) {
        if (aliveEnemies.isEmpty()) {
            System.out.println("No enemies available!");
            return null;
        }

        if (aliveEnemies.size() == 1) {
            return aliveEnemies.get(0);
        }

        System.out.println("\nSelect target:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            Enemy enemy = aliveEnemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP: " + enemy.getCurrentHP() + "/" + enemy.getMaxHP() + ")");
        }

        while (true) {
            System.out.print("\nEnter choice (1-" + aliveEnemies.size() + "): ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= aliveEnemies.size()) {
                    return aliveEnemies.get(choice - 1);
                }
            } catch (NumberFormatException e) {
            }

            System.out.println("Invalid choice. Please enter a number between 1 and " + aliveEnemies.size() + ".");
        }
    }

    public Item selectItem(Player player) {
        List<Item> inventory = player.getInventory();

        if (inventory.isEmpty()) {
            System.out.println("No items in inventory!");
            return null;
        }

        System.out.println("\nSelect item to use:");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).getName());
        }

        while (true) {
            System.out.print("\nEnter choice (1-" + inventory.size() + "): ");
            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= inventory.size()) {
                    return inventory.get(choice - 1);
                }
            } catch (NumberFormatException e) {
            }

            System.out.println("Invalid choice. Please enter a number between 1 and " + inventory.size() + ".");
        }
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
