package statuseffects;

import combatants.Combatant;

// Prevents the affected combatant from acting for the duration
public class StunEffect extends StatusEffect {
    public StunEffect(int duration) {
        super("Stunned", duration);
    }

    @Override
    public boolean preventsAction() {
        return true;
    }

    @Override
    public void apply(Combatant target) {
    }

    @Override
    public void onTurnStart(Combatant target) {
    }
}
