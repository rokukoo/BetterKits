package io.rokuko.betterkits.kit;

import cn.neptunex.cloudit.bukkit.event.EventChain;
import cn.neptunex.cloudit.utils.ChatColorUtils;
import io.rokuko.betterkits.BetterKits;
import io.rokuko.betterkits.api.PreRemoveKitEvent;
import io.rokuko.betterkits.kit.kit.LimitKit;
import io.rokuko.betterkits.kit.reward.CommandReward;
import io.rokuko.betterkits.kit.reward.ItemReward;
import io.rokuko.betterkits.utils.KitUtils;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KitController {

    @SneakyThrows
    public static Kit createKit(BetterKits betterKits, String name, boolean isEmpty){
        Kit kit = null;
        if (isEmpty){
            ArrayList<String> lines = new ArrayList<>();
            lines.add("");
            kit = KitBaker.bake(name, "CDKEY", lines, 0);
            saveKit(kit);
        }
        return kit;
    }

    public static void removeKitByName(BetterKits betterKits, String name, CommandSender sender) {
        EventChain.create()
                .distinctPlayer()
                .during(PreRemoveKitEvent.class, 5000)
                .when(AsyncPlayerChatEvent.class, e->{
                    if (e.getMessage().equals("yes")){
                        sender.sendMessage(BetterKits.PREFIX + ChatColorUtils.colorization("&7Successfully removed the kit &e" + name + "&7."));
                        KitUtils.getKitFileByName(name).delete();
                    }else{
                        sender.sendMessage(BetterKits.PREFIX + ChatColorUtils.colorization("&7Failed to remove the kit &e" + name + "&7."));
                    }
                    KitBaker.kitLinkedHashMap.remove(name);
                    e.setCancelled(true);
                });
        sender.sendMessage(BetterKits.PREFIX + ChatColorUtils.colorization("&7Confirm to remove kit &e" + name + "&7?(Please input &eyes&7/&eno&7 in 5s)"));
        betterKits.$emit(new PreRemoveKitEvent());
    }

    @SneakyThrows
    public static void saveKit(Kit kit){
        File kitFile = new File(BetterKits.kitDirectories, kit.getName() + ".yml");
        if (!kitFile.exists()) kitFile.createNewFile();
        YamlConfiguration kitYaml = YamlConfiguration.loadConfiguration(kitFile);
        kitYaml.set("name", kit.getName());
        kitYaml.set("type", kit.getKitType());
        if (kit instanceof LimitKit){
            kitYaml.set("limit", ((LimitKit) kit).getLimit());
        }
        kitYaml.set("rewards", extractRewards(kit));
        kitYaml.save(kitFile);
        KitBaker.kitLinkedHashMap.put(kit.getName(), kit);
    }

    public static List<String> extractRewards(Kit kit){
        List<String> lines = new ArrayList<>();
        if (kit.getRewards().size() == 0){
            lines.add("");
        }else {
            kit.getRewards().forEach(reward -> {
                if (reward instanceof ItemReward){
                    lines.add(String.format("item:%d:%d", ((ItemReward) reward).getItemStack().getTypeId(), ((ItemReward) reward).getItemStack().getAmount()));
                }else if(reward instanceof CommandReward){
                    lines.add(String.format("command:%s:%s", ((CommandReward) reward).isOp()?"op":"player", ((CommandReward) reward).getCommandLine()));
                }
            });
        }
        return lines;
    }

}
