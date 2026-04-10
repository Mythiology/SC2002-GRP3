package statuseffects;

import combatants.Combatant;

// Tracks cumulative ATK bonus from Arcane Blast kills (permanent, never expires)
public class ArcaneBlastEffect extends StatusEffect {
    private int stacks;

    public ArcaneBlastEffect() {
        super("Arcane Blast", Integer.MAX_VALUE);
        this.stacks = 0;
    }

    @Override
    public void apply(Combatant target) {
    }

    @Override
    public void onTurnStart(Combatant target) {
    }

    public void addStack() {
        stacks++;
    }

    public int getStacks() {
        return stacks;
    }

    public int getTotalBonus() {
        return stacks * 10;
    }

    @Override
    public boolean hasExpired() {
        return false;
    }
}
