package io.rokuko.betterkits.kit.kit;

import io.rokuko.betterkits.kit.Kit;
import io.rokuko.betterkits.utils.DataUtils;
import lombok.Data;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class LimitKit extends Kit {

    @Getter
    private Integer limit;

    public LimitKit(String name, String kitType, List<String> lines, Integer limit) {
        super(name, kitType, lines);
        this.limit = limit;
    }

    @Override
    public boolean canReward(Player player, String message) {
        FileConfiguration playerData = DataUtils.getPlayerData(player);
        if (playerData.isSet(getName()) || playerData.getInt(getName()) < getLimit()){
            return true;
        }
        return false;
    }

    @Override
    public void rewardPlayer(Player player) {
        FileConfiguration playerData = DataUtils.getPlayerData(player);
        // 判断玩家是否领取过礼包, 如果领取过则+1, 否则设置为0
        if (!playerData.isSet(getName())){
            playerData.set(getName(), 0);
        }else{
            playerData.set(getName(), playerData.getInt(getName()) + 1);
        }
        super.rewardPlayer(player);
    }
}
