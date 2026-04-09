package items;

import combatants.Combatant;
import statuseffects.SmokeBombEffect;
import java.util.List;

// Applies invulnerability for the current turn and next turn
public class SmokeBomb implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public void use(Combatant user, List<Combatant> enemies) {
        user.addStatusEffect(new SmokeBombEffect(2));
        System.out.println(user.getName() + " used Smoke Bomb! Enemy attacks will deal 0 damage for this turn and the next turn!");
    }
}
