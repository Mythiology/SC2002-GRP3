package combatants;

// Goblin: balanced enemy (HP:55, ATK:35, DEF:15, SPD:25)
public class Goblin extends Enemy {
    private static int goblinCounter = 0;

    public Goblin() {
        super("Goblin " + (char)('A' + goblinCounter++), 55, 35, 15, 25);
    }

    public static void resetCounter() {
        goblinCounter = 0;
    }
}
