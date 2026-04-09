package actions;

import combatants.Combatant;
import combatants.Player;
import java.util.List;

// Executes the player's class-specific special ability (Shield Bash / Arcane Blast)
public class SpecialSkill implements Action {
    @Override
    public String getName() {
        return "Special Skill";
    }

    @Override
    public void execute(Combatant actor, List<Combatant> targets) {
        if (actor instanceof Player) {
            Player player = (Player) actor;
            System.out.println(player.getName() + " uses " + player.getClassName() + " Special Skill!");
            player.performSpecialSkill(targets);
        }
    }
}
