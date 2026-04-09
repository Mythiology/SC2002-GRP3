package combatants;

import statuseffects.StatusEffect;
import java.util.ArrayList;
import java.util.List;

// Base class for all entities that participate in combat (players and enemies)
public abstract class Combatant {
    protected String name;
    protected int maxHP;
    protected int currentHP;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<StatusEffect> statusEffects;

    public Combatant(String name, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHP = hp;
        this.currentHP = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.statusEffects = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxHP() { return maxHP; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public List<StatusEffect> getStatusEffects() { return statusEffects; }

    public void setAttack(int attack) { this.attack = attack; }
    public void setDefense(int defense) { this.defense = defense; }

    public void takeDamage(int damage) {
        currentHP = Math.max(0, currentHP - damage);
    }

    public void heal(int amount) {
        currentHP = Math.min(maxHP, currentHP + amount);
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public void addStatusEffect(StatusEffect effect) {
        statusEffects.add(effect);
    }

    public void removeStatusEffect(StatusEffect effect) {
        statusEffects.remove(effect);
    }

    // Cooldown defaults — only Player overrides with actual tracking
    public int getSpecialSkillCooldown() { return 0; }
    public void setSpecialSkillCooldown(int cooldown) { }
    public void decrementCooldown() { }
}
