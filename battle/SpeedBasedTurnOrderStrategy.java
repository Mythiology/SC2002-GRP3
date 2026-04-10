package battle;

import combatants.Combatant;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

// Sorts combatants by speed descending: higher speed acts first
public class SpeedBasedTurnOrderStrategy implements TurnOrderStrategy {
    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> orderedCombatants = new ArrayList<>(combatants);
        orderedCombatants.sort(new Comparator<Combatant>() {
            @Override
            public int compare(Combatant c1, Combatant c2) {
                return Integer.compare(c2.getSpeed(), c1.getSpeed());
            }
        });
        return orderedCombatants;
    }
}
