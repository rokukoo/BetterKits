package io.rokuko.betterkits.kit;

import cn.neptunex.cloudit.bukkit.event.EventChain;
import cn.neptunex.cloudit.utils.ChatColorUtils;
import io.rokuko.betterkits.BetterKits;
import io.rokuko.betterkits.api.PreRemoveKitEvent;
import io.rokuko.betterkits.utils.KitUtils;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;

public class KitController {

    @SneakyThrows
    public static Kit createKit(BetterKits module, String name, boolean isEmpty){
        Kit kit = null;
        if (isEmpty){
            kit = Kit.of(name, KitType.CDKEY, 0);
            File kitFile = new File(module.kitDirectories, name + ".yml");
            kitFile.createNewFile();
            YamlConfiguration kitYaml = YamlConfiguration.loadConfiguration(kitFile);
            kitYaml.set("name", kit.getName());
            kitYaml.set("type", kit.getKitType().name());
            kitYaml.set("limit", kit.getLimit());
            kitYaml.save(kitFile);
            KitBaker.kitLinkedHashMap.put(name, kit);
        }
        return kit;
    }

    public static void removeKitByName(BetterKits betterKits, String name, CommandSender sender) {
        EventChain.create()
                .distinctPlayer()
                .during(PreRemoveKitEvent.class, 5000)
                .when(AsyncPlayerChatEvent.class, e->{
                    if (e.getMessage().equals("yes")){
                        sender.sendMessage(betterKits.PREFIX + ChatColorUtils.colorization("&7Successfully removed the kit &e" + name + "&7."));
                        KitUtils.getKitFileByName(name).delete();
                    }else{
                        sender.sendMessage(betterKits.PREFIX + ChatColorUtils.colorization("&7Failed to remove the kit &e" + name + "&7."));
                    }
                    e.setCancelled(true);
                });
        sender.sendMessage(betterKits.PREFIX + ChatColorUtils.colorization("&7Confirm to remove kit &e" + name + "&7?(Please input &eyes&7/&eno&7 in 5s)"));
        betterKits.$emit(new PreRemoveKitEvent());
    }

}
