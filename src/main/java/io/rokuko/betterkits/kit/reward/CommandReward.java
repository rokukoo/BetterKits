package io.rokuko.betterkits.kit.reward;

import cn.neptunex.cloudit.utils.ChatColorUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AllArgsConstructor(staticName = "of")
public class CommandReward implements Reward{

    @Getter
    private boolean op;

    @Getter
    private String commandLine;

    @Override
    public String toString(){
        return ChatColorUtils.colorization(
                (op
                ? ChatColorUtils.colorization("&8[&fOP指令&r&8] ")
                : ChatColorUtils.colorization("&8[&f玩家指令&r&8] "))
                + "&e" + commandLine);
    }

    @Override
    public void rewardPlayer(Player player) {
        boolean playerOp = player.isOp();
        player.setOp(op);
        Bukkit.getServer().dispatchCommand(player, commandLine.replace("%player%", player.getName()));
        player.setOp(playerOp);
    }

}
