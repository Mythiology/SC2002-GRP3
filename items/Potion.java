package items;

import combatants.Combatant;
import java.util.List;

// Restores 100 HP to the user, capped at max HP
public class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public void use(Combatant user, List<Combatant> enemies) {
        user.heal(HEAL_AMOUNT);
        System.out.println(user.getName() + " used Potion and healed " + HEAL_AMOUNT + " HP!");
        System.out.println(user.getName() + " HP: " + user.getCurrentHP() + "/" + user.getMaxHP());
    }
}
