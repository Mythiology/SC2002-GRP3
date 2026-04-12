package combatants;

import statuseffects.ArcaneBlastEffect;
import statuseffects.StatusEffect;
import java.util.List;

// Wizard: high ATK glass cannon with Arcane Blast AoE that gains ATK per kill
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
            for (Combatant target : targets) {
                if (target.isAlive()) {
                    int damage = Math.max(0, this.attack - target.getDefense());
                    target.takeDamage(damage);

                    // Each kill immediately grants +10 ATK, affecting subsequent targets
                    if (!target.isAlive()) {
                        this.attack += 10;

                        ArcaneBlastEffect effect = getArcaneBlastEffect();
                        if (effect == null) {
                            effect = new ArcaneBlastEffect();
                            this.addStatusEffect(effect);
                        }
                        effect.addStack();
                    }
                }
            }
            this.specialSkillCooldown = 3;
        }
    }

    private ArcaneBlastEffect getArcaneBlastEffect() {
        for (StatusEffect effect : this.getStatusEffects()) {
            if (effect instanceof ArcaneBlastEffect) {
                return (ArcaneBlastEffect) effect;
            }
        }
        return null;
    }
}
