package statuseffects;

import combatants.Combatant;

// Temporarily increases defense by 10; restores original defense on expiry
public class DefendEffect extends StatusEffect {
    private static final int DEFENSE_BOOST = 10;
    private int originalDefense;

    public DefendEffect(int duration) {
        super("Defending", duration);
    }

    @Override
    public void apply(Combatant target) {
        originalDefense = target.getDefense();
        target.setDefense(originalDefense + DEFENSE_BOOST);
    }

    @Override
    public void remove(Combatant target) {
        target.setDefense(originalDefense);
    }
}
