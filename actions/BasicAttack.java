package actions;

import combatants.Combatant;
import statuseffects.StatusEffect;
import java.util.List;

// Performs a single-target attack: damage = max(0, ATK - DEF)
public class BasicAttack implements Action {
    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public void execute(Combatant actor, List<Combatant> targets) {
        if (targets.isEmpty()) {
            System.out.println("No valid targets for " + actor.getName() + "'s attack!");
            return;
        }

        Combatant target = targets.get(0);
        int damage = Math.max(0, actor.getAttack() - target.getDefense());

        boolean invulnerable = false;
        for (StatusEffect effect : target.getStatusEffects()) {
            if (effect.negatesDamage()) {
                invulnerable = true;
                break;
            }
        }

        if (invulnerable) {
            damage = 0;
            System.out.println(actor.getName() + " attacks " + target.getName() + " but Smoke Bomb negates all damage!");
        } else {
            target.takeDamage(damage);
            System.out.println(actor.getName() + " attacks " + target.getName() + " for " + damage + " damage!");
            System.out.println(target.getName() + " HP: " + target.getCurrentHP() + "/" + target.getMaxHP());

            if (!target.isAlive()) {
                System.out.println(target.getName() + " has been eliminated!");
            }
        }
    }
}
