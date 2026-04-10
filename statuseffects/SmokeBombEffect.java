package statuseffects;

import combatants.Combatant;

// Grants invulnerability: all incoming attacks deal 0 damage while active
public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect(int duration) {
        super("Smoke Bomb Active", duration);
    }

    @Override
    public boolean negatesDamage() {
        return true;
    }

    @Override
    public void apply(Combatant target) {
    }

    @Override
    public void onTurnStart(Combatant target) {
    }
}
