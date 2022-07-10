package io.rokuko.betterkits.kit.kit;

import org.bukkit.entity.Player;

import java.util.List;

public class ConditionKit extends LimitKit{

    public ConditionKit(String name, List<String> lines, Integer limit) {
        super(name, "condition", lines, limit);
    }

    @Override
    public boolean canReward(Player player, String message) {
        boolean yourResult = true;

        return super.canReward(player, message) && yourResult;
    }

}
