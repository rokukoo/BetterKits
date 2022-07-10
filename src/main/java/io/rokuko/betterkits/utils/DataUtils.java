package io.rokuko.betterkits.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

import static io.rokuko.betterkits.BetterKits.dataDirectories;

public class DataUtils {

    public static FileConfiguration getPlayerData(Player player){
        File file = new File(dataDirectories, player.getUniqueId() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

}
