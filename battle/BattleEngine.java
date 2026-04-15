package battle;

import combatants.*;
import actions.*;
import items.Item;
import statuseffects.*;
import ui.BattleUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Core battle engine: manages rounds, turn order, actions, and win/lose conditions
public class BattleEngine {
    private Player player;
    private List<Enemy> enemies;
    private List<Enemy> backupEnemies;
    private TurnOrderStrategy turnOrderStrategy;
    private int roundNumber;
    private Difficulty difficulty;
    private boolean backupSpawned;
    private boolean battleEnded;
    private BattleUI ui;

    public BattleEngine(Player player, Difficulty difficulty, BattleUI ui) {
        this.player = player;
        this.difficulty = difficulty;
        this.turnOrderStrategy = new SpeedBasedTurnOrderStrategy();
        this.roundNumber = 0;
        this.backupSpawned = false;
        this.battleEnded = false;
        this.ui = ui;

        this.enemies = new ArrayList<>();
        this.backupEnemies = new ArrayList<>();
        initializeEnemies();
    }

    private void initializeEnemies() {
        Goblin.resetCounter();
        Wolf.resetCounter();

        for (int i = 0; i < difficulty.getInitialGoblins(); i++) {
            enemies.add(new Goblin());
        }
        for (int i = 0; i < difficulty.getInitialWolves(); i++) {
            enemies.add(new Wolf());
        }

        for (int i = 0; i < difficulty.getBackupGoblins(); i++) {
            backupEnemies.add(new Goblin());
        }
        for (int i = 0; i < difficulty.getBackupWolves(); i++) {
            backupEnemies.add(new Wolf());
        }
    }

    public void startBattle() {
        System.out.println("\n=== BATTLE START ===");
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Initial Enemies: " + getAliveEnemyCount());
        if (difficulty.hasBackup()) {
            System.out.println("Backup Enemies: " + backupEnemies.size());
        }
        System.out.println("===================\n");

        while (!battleEnded) {
            roundNumber++;
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║        ROUND " + roundNumber + "                      ║");
            System.out.println("╚════════════════════════════════════╝\n");

            executeRound();

            if (!battleEnded) {
                if (checkBattleEnd()) {
                    break;
                }
                checkBackupSpawn();
            }
        }
    }

    private void executeRound() {
        List<Combatant> allCombatants = new ArrayList<>();
        allCombatants.add(player);
        allCombatants.addAll(getAliveEnemies());

        List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(allCombatants);

        System.out.println("Turn Order (by speed): ");
        for (Combatant c : turnOrder) {
            System.out.println("  - " + c.getName() + " (Speed: " + c.getSpeed() + ")");
        }
        System.out.println();

        for (Combatant combatant : turnOrder) {
            if (!combatant.isAlive()) {
                continue;
            }

            executeTurn(combatant);

            if (checkBattleEnd()) {
                return;
            }
        }

        // Status effects are updated once per round (not per turn) so that
        // effects like SmokeBomb and Defend persist through all enemy turns
        updateAllStatusEffects();

        System.out.println("\n--- End of Round " + roundNumber + " ---");
        displayStatus();
    }

    private void executeTurn(Combatant combatant) {
        System.out.println("\n>>> " + combatant.getName() + "'s Turn <<<");

        applyStatusEffects(combatant);

        if (isStunned(combatant)) {
            System.out.println(combatant.getName() + " is stunned and cannot act!");
            return;
        }

        // Cooldown decrements at start of turn (before action selection) so that
        // "3 turns including current round" makes the skill available on the 4th round
        int preDecrementCooldown = combatant.getSpecialSkillCooldown();
        combatant.decrementCooldown();

        boolean usedPowerStone = false;

        if (combatant instanceof Player) {
            usedPowerStone = executePlayerTurn((Player) combatant);
        } else if (combatant instanceof Enemy) {
            executeEnemyTurn((Enemy) combatant);
        }

        // Power Stone "does not start or change the cooldown timer",
        // so restore the cooldown to its value before this turn's decrement
        if (usedPowerStone) {
            combatant.setSpecialSkillCooldown(preDecrementCooldown);
        }
    }

    private boolean executePlayerTurn(Player player) {
        ui.displayBattleStatus(player, enemies, roundNumber);

        String choice = ui.getPlayerAction(player);
        boolean usedPowerStone = false;

        switch (choice) {
            case "1":
                List<Enemy> aliveEnemies = getAliveEnemies();
                if (aliveEnemies.isEmpty()) {
                    System.out.println("No enemies to attack!");
                    break;
                }
                Enemy target = ui.selectTarget(aliveEnemies);
                if (target != null) {
                    List<Combatant> targets = new ArrayList<>();
                    targets.add(target);
                    new BasicAttack().execute(player, targets);
                }
                break;

            case "2":
                new Defend().execute(player, new ArrayList<>());
                break;

            case "3":
                if (player.hasItems()) {
                    Item selectedItem = ui.selectItem(player);
                    if (selectedItem != null) {
                        List<Combatant> enemyTargets = new ArrayList<>(getAliveEnemies());
                        new UseItem(selectedItem).execute(player, enemyTargets);
                        usedPowerStone = selectedItem.triggersSpecialSkill();
                    }
                } else {
                    System.out.println("No items available!");
                }
                break;

            case "4":
                if (player.getSpecialSkillCooldown() == 0) {
                    List<Combatant> skillTargets;
                    if (player.isAoeSkill()) {
                        skillTargets = new ArrayList<>(getAliveEnemies());
                    } else {
                        Enemy skillTarget = ui.selectTarget(getAliveEnemies());
                        skillTargets = new ArrayList<>();
                        if (skillTarget != null) {
                            skillTargets.add(skillTarget);
                        }
                    }
                    if (!skillTargets.isEmpty()) {
                        new SpecialSkill().execute(player, skillTargets);
                    }
                } else {
                    System.out.println("Special skill is on cooldown! " + player.getSpecialSkillCooldown() + " rounds remaining.");
                }
                break;
        }

        return usedPowerStone;
    }

    private void executeEnemyTurn(Enemy enemy) {
        List<Combatant> targets = new ArrayList<>();
        targets.add(player);
        new BasicAttack().execute(enemy, targets);
    }

    private void applyStatusEffects(Combatant combatant) {
        for (StatusEffect effect : combatant.getStatusEffects()) {
            effect.onTurnStart(combatant);
        }
    }

    private void updateStatusEffects(Combatant combatant) {
        Iterator<StatusEffect> iterator = combatant.getStatusEffects().iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            effect.decrementDuration();

            if (effect.hasExpired()) {
                effect.remove(combatant);
                System.out.println(combatant.getName() + "'s " + effect.getName() + " effect has expired.");
                iterator.remove();
            }
        }
    }

    private void updateAllStatusEffects() {
        List<Combatant> allCombatants = new ArrayList<>();
        allCombatants.add(player);
        allCombatants.addAll(enemies);
        for (Combatant combatant : allCombatants) {
            if (combatant.isAlive()) {
                updateStatusEffects(combatant);
            }
        }
    }

    private boolean isStunned(Combatant combatant) {
        for (StatusEffect effect : combatant.getStatusEffects()) {
            if (effect.preventsAction()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBattleEnd() {
        if (battleEnded) {
            return true;
        }

        if (!player.isAlive()) {
            displayDefeatScreen();
            battleEnded = true;
            return true;
        }

        if (getAliveEnemyCount() == 0 && backupEnemies.isEmpty()) {
            displayVictoryScreen();
            battleEnded = true;
            return true;
        }

        return false;
    }

    private void checkBackupSpawn() {
        if (!backupSpawned && getAliveEnemyCount() == 0 && !backupEnemies.isEmpty()) {
            System.out.println("\n");
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║    ⚠️  BACKUP ENEMIES HAVE ARRIVED! ⚠️     ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();

            enemies.addAll(backupEnemies);

            System.out.println("Backup enemies joining the battle:");
            for (Enemy enemy : backupEnemies) {
                System.out.println("  - " + enemy.getName() + " (HP: " + enemy.getMaxHP() +
                                 ", ATK: " + enemy.getAttack() + ", DEF: " + enemy.getDefense() +
                                 ", SPD: " + enemy.getSpeed() + ")");
            }

            backupEnemies.clear();
            backupSpawned = true;
            System.out.println();
        }
    }

    private void displayVictoryScreen() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║           🎉 VICTORY! 🎉                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Congratulations, you have defeated all your enemies!");
        System.out.println();
        System.out.println("Statistics:");
        System.out.println("  Remaining HP: " + player.getCurrentHP() + " / " + player.getMaxHP());
        System.out.println("  Total Rounds: " + roundNumber);
        System.out.println();
    }

    private void displayDefeatScreen() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║           ☠️  DEFEATED  ☠️                  ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Defeated. Don't give up, try again!");
        System.out.println();
        System.out.println("Statistics:");
        System.out.println("  Enemies remaining: " + (getAliveEnemyCount() + backupEnemies.size()));
        System.out.println("  Total Rounds Survived: " + roundNumber);
        System.out.println();
    }

    private void displayStatus() {
        System.out.println(player.getName() + " HP: " + player.getCurrentHP() + "/" + player.getMaxHP());
        System.out.println("Enemies alive: " + getAliveEnemyCount());
        if (!backupEnemies.isEmpty()) {
            System.out.println("Backup enemies remaining: " + backupEnemies.size());
        }
    }

    private List<Enemy> getAliveEnemies() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                alive.add(enemy);
            }
        }
        return alive;
    }

    private int getAliveEnemyCount() {
        int count = 0;
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                count++;
            }
        }
        return count;
    }
}
