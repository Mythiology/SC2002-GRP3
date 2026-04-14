package combatants;

import java.util.List;

// Wizard: high ATK glass cannon with Arcane Blast AoE that gains ATK per kill
public class Wizard extends Player {
    private int arcaneBlastStacks;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        this.arcaneBlastStacks = 0;
    }

    @Override
    public String getClassName() {
        return "Wizard";
    }

    @Override
    public boolean isAoeSkill() {
        return true;
    }

    // Effective ATK = base ATK + permanent Arcane Blast bonus (+10 per kill)
    @Override
    public int getAttack() {
        return super.getAttack() + arcaneBlastStacks * 10;
    }

    @Override
    public void performSpecialSkill(List<Combatant> targets) {
        if (specialSkillCooldown == 0) {
            for (Combatant target : targets) {
                if (target.isAlive()) {
                    int damage = Math.max(0, getAttack() - target.getDefense());
                    target.takeDamage(damage);

                    // Each kill immediately grants +10 ATK, affecting subsequent targets
                    if (!target.isAlive()) {
                        arcaneBlastStacks++;
                    }
                }
            }
            this.specialSkillCooldown = 3;
        }
    }
}
