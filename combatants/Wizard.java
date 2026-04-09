package combatants;

import java.util.List;

// Wizard: high ATK glass cannon with Arcane Blast AoE
public class Wizard extends Player {
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public String getClassName() {
        return "Wizard";
    }

    @Override
    public boolean isAoeSkill() {
        return true;
    }

    @Override
    public void performSpecialSkill(List<Combatant> targets) {
        if (specialSkillCooldown == 0) {
            int enemiesDefeated = 0;
            for (Combatant target : targets) {
                if (target.isAlive()) {
                    int damage = Math.max(0, this.attack - target.getDefense());
                    target.takeDamage(damage);

                    if (!target.isAlive()) {
                        enemiesDefeated++;
                    }
                }
            }
            this.attack += (enemiesDefeated * 10);
            this.specialSkillCooldown = 3;
        }
    }
}
