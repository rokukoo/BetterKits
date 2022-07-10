package io.rokuko.betterkits.kit.kit;

import org.bukkit.entity.Player;

import java.util.List;

public class CdkeyKit extends LimitKit{

    public CdkeyKit(String name, List<String> lines, Integer limit) {
        super(name, "cdkey", lines, limit);
    }

    @Override
    public boolean canReward(Player player, String message) {
        boolean yourResult = true;

        return super.canReward(player, message) && yourResult;
    }

}
