package actions;

import combatants.Combatant;
import java.util.List;

// Interface for all combat actions (BasicAttack, Defend, SpecialSkill, UseItem)
public interface Action {
    String getName();
    void execute(Combatant actor, List<Combatant> targets);
}
