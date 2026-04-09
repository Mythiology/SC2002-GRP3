package actions;

import combatants.Combatant;
import statuseffects.DefendEffect;
import java.util.List;

// Increases the actor's defense by 10 for the current round and the next round
public class Defend implements Action {
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void execute(Combatant actor, List<Combatant> targets) {
        DefendEffect defendEffect = new DefendEffect(2);
        defendEffect.apply(actor);
        actor.addStatusEffect(defendEffect);
        System.out.println(actor.getName() + " takes a defensive stance! Defense increased by 10 for this turn and the next turn.");
    }
}
