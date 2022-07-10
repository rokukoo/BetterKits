package io.rokuko.betterkits.kit.kit;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CdkeyKit extends LimitKit{

    public CdkeyKit(String name, List<String> lines, Integer limit) {
        super(name, "cdkey", lines, limit);
    }

    @Override
    public boolean canReward(Player player, String message) {
        return super.canReward(player, message) && ((Supplier<Boolean>) () -> {
            // Your code here
            return true;
        }).get();
    }

}
