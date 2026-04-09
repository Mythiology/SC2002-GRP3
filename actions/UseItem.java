package actions;

import combatants.Combatant;
import combatants.Player;
import items.Item;
import java.util.List;

// Consumes a single-use item from the player's inventory
public class UseItem implements Action {
    private Item item;

    public UseItem(Item item) {
        this.item = item;
    }

    @Override
    public String getName() {
        return "Use Item";
    }

    @Override
    public void execute(Combatant actor, List<Combatant> targets) {
        item.use(actor, targets);
        if (actor instanceof Player) {
            ((Player) actor).removeItem(item);
        }
    }

    public Item getItem() {
        return item;
    }
}
