package io.rokuko.betterkits.kit.reward;

import org.bukkit.inventory.ItemStack;

public class RewardResolver {

    public static Reward resolveReward(String line) {
        String[] split = line.replace("$", " ").split(":");
        switch (split[0]){
            case "item": return ItemReward.of(new ItemStack(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            case "command": return CommandReward.of(split[1].equals("op"), split[2]);
            default: return null;
        }
    }

}
