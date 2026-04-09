package items;

import combatants.Combatant;
import combatants.Player;
import java.util.List;

// Triggers the player's special skill once without affecting the cooldown timer
public class PowerStone implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public boolean triggersSpecialSkill() {
        return true;
    }

    @Override
    public void use(Combatant user, List<Combatant> enemies) {
        if (user instanceof Player) {
            Player player = (Player) user;
            int originalCooldown = player.getSpecialSkillCooldown();
            player.setSpecialSkillCooldown(0);
            System.out.println(user.getName() + " used Power Stone to trigger their special skill!");
            player.performSpecialSkill(enemies);
            player.setSpecialSkillCooldown(originalCooldown);
        }
    }
}
