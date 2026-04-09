package items;

import combatants.Combatant;
import java.util.List;

// Interface for single-use consumable items
public interface Item {
    String getName();
    void use(Combatant user, List<Combatant> enemies);

    // Whether this item triggers the player's special skill (e.g. Power Stone)
    default boolean triggersSpecialSkill() {
        return false;
    }
}
