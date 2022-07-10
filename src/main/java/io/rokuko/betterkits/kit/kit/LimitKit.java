package io.rokuko.betterkits.kit.kit;

import io.rokuko.betterkits.kit.Kit;
import lombok.Data;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.List;

public class LimitKit extends Kit {

    @Getter
    private Integer limit;

    public LimitKit(String name, String kitType, List<String> lines, Integer limit) {
        super(name, kitType, lines);
    }

    @Override
    public boolean canReward(Player player, String message) {



        return false;
    }

}
