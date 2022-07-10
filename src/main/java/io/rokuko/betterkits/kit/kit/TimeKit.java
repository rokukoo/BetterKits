package io.rokuko.betterkits.kit.kit;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Supplier;

public class TimeKit extends LimitKit{

    public TimeKit(String name, List<String> lines, Integer limit) {
        super(name, "time", lines, limit);
    }

    @Override
    public boolean canReward(Player player, String message) {
        return super.canReward(player, message) && ((Supplier<Boolean>) () -> {
            // Your code here
            return true;
        }).get();
    }
}
