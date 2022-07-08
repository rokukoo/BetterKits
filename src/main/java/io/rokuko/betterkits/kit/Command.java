package io.rokuko.betterkits.kit;

import cn.neptunex.cloudit.utils.ChatColorUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AllArgsConstructor(staticName = "of")
public class Command {

    private boolean isOp;
    private String commandLine;

    public void run(Player player){
        boolean playerOp = player.isOp();
        player.setOp(isOp);
        Bukkit.getServer().dispatchCommand(player, commandLine);
        player.setOp(playerOp);
    }

    @Override
    public String toString(){
        return (isOp
                ? ChatColorUtils.colorization("&4指令&r")
                : ChatColorUtils.colorization("&f指令&r"))
                + commandLine;
    }

}
