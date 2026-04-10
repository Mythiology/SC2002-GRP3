package battle;

import combatants.Combatant;
import java.util.List;

// Strategy interface for determining turn order each round (OCP)
public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
