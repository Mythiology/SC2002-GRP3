package combatants;

// Wolf: fast, high-ATK but fragile enemy (HP:40, ATK:45, DEF:5, SPD:35)
public class Wolf extends Enemy {
    private static int wolfCounter = 0;

    public Wolf() {
        super("Wolf " + (char)('A' + wolfCounter++), 40, 45, 5, 35);
    }

    public static void resetCounter() {
        wolfCounter = 0;
    }
}
