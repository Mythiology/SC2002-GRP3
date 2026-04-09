package battle;

// Defines difficulty levels with initial enemy compositions
public enum Difficulty {
    EASY(3, 0),
    MEDIUM(1, 1),
    HARD(2, 0);

    private final int initialGoblins;
    private final int initialWolves;

    Difficulty(int initialGoblins, int initialWolves) {
        this.initialGoblins = initialGoblins;
        this.initialWolves = initialWolves;
    }

    public int getInitialGoblins() { return initialGoblins; }
    public int getInitialWolves() { return initialWolves; }
}
