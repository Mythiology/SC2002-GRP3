package battle;

// Defines difficulty levels with initial and backup enemy compositions
public enum Difficulty {
    EASY(3, 0, 0, 0),
    MEDIUM(1, 1, 0, 2),
    HARD(2, 0, 1, 2);

    private final int initialGoblins;
    private final int initialWolves;
    private final int backupGoblins;
    private final int backupWolves;

    Difficulty(int initialGoblins, int initialWolves, int backupGoblins, int backupWolves) {
        this.initialGoblins = initialGoblins;
        this.initialWolves = initialWolves;
        this.backupGoblins = backupGoblins;
        this.backupWolves = backupWolves;
    }

    public int getInitialGoblins() { return initialGoblins; }
    public int getInitialWolves() { return initialWolves; }
    public int getBackupGoblins() { return backupGoblins; }
    public int getBackupWolves() { return backupWolves; }

    public boolean hasBackup() {
        return backupGoblins > 0 || backupWolves > 0;
    }

    public int getTotalEnemies() {
        return initialGoblins + initialWolves + backupGoblins + backupWolves;
    }
}
