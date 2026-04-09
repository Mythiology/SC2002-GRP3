package combatants;

import java.util.List;

// Warrior: high HP/DEF tank with Shield Bash that damages a single target
public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public String getClassName() {
        return "Warrior";
    }

    @Override
    public boolean isAoeSkill() {
        return false;
    }

    @Override
    public void performSpecialSkill(List<Combatant> targets) {
        if (!targets.isEmpty() && specialSkillCooldown == 0) {
            Combatant target = targets.get(0);
            int damage = Math.max(0, this.attack - target.getDefense());
            target.takeDamage(damage);
            this.specialSkillCooldown = 3;
        }
    }
}
