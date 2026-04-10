package statuseffects;

import combatants.Combatant;

// Base class for all timed effects that persist across turns
public abstract class StatusEffect {
    protected int duration;
    protected String name;

    public StatusEffect(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        if (duration > 0) {
            duration--;
        }
    }

    public boolean hasExpired() {
        return duration <= 0;
    }

    // Whether this effect prevents the combatant from acting (e.g. Stun)
    public boolean preventsAction() {
        return false;
    }

    // Whether this effect negates all incoming damage (e.g. Smoke Bomb)
    public boolean negatesDamage() {
        return false;
    }

    // Called once when the effect is first applied
    public abstract void apply(Combatant target);

    // Called at the start of each turn while the effect is active
    public abstract void onTurnStart(Combatant target);

    // Called when the effect expires; subclasses override to clean up stat changes
    public void remove(Combatant target) {
    }
}
