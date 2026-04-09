package combatants;

// Base class for all enemy types
public abstract class Enemy extends Combatant {
    public Enemy(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
    }
}
