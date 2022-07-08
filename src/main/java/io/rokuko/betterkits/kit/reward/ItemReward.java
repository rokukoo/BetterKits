package io.rokuko.betterkits.kit.reward;

import cn.neptunex.cloudit.utils.ChatColorUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor(staticName = "of")
public class ItemReward implements Reward{

    @Getter
    private ItemStack itemStack;

    @Override
    public void rewardPlayer(Player player) {
        player.getInventory().addItem(itemStack);
    }

    @Override
    public String toString(){
        return ChatColorUtils.colorization("&8[&r" + itemStack.getType().name() + "&8&l] &7x&r &e" + itemStack.getAmount());
    }


}
