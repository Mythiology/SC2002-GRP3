package combatants;

import statuseffects.StunEffect;
import java.util.List;

// Warrior: high HP/DEF tank with Shield Bash that stuns a single target
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
            target.addStatusEffect(new StunEffect(2));
            this.specialSkillCooldown = 3;
        }
    }
}
