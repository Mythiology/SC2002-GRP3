package combatants;

import items.Item;
import java.util.ArrayList;
import java.util.List;

// Abstract player class with inventory management and special skill cooldown
public abstract class Player extends Combatant {
    protected List<Item> inventory;
    protected int specialSkillCooldown;

    public Player(String name, int hp, int attack, int defense, int speed) {
        super(name, hp, attack, defense, speed);
        this.inventory = new ArrayList<>();
        this.specialSkillCooldown = 0;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public boolean hasItems() {
        return !inventory.isEmpty();
    }

    @Override
    public int getSpecialSkillCooldown() { return specialSkillCooldown; }

    @Override
    public void setSpecialSkillCooldown(int cooldown) { this.specialSkillCooldown = cooldown; }

    @Override
    public void decrementCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    // Whether this player's special skill targets all enemies (AoE)
    public abstract boolean isAoeSkill();

    public abstract String getClassName();
    public abstract void performSpecialSkill(List<Combatant> targets);
}
