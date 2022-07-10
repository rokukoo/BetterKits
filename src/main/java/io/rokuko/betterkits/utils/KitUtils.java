package io.rokuko.betterkits.utils;

import io.rokuko.betterkits.BetterKits;
import io.rokuko.betterkits.kit.Kit;
import io.rokuko.betterkits.kit.KitBaker;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class KitUtils {

    public static Kit getKitByName(String name){
        return KitBaker.kitLinkedHashMap.get(name);
    }

    public static boolean checkExistsKit(String name){
        return KitBaker.kitLinkedHashMap.containsKey(name);
    }

    public static File getKitFileByName(String name){
        return new File(BetterKits.kitDirectories, name + ".yml");
    }

    public static YamlConfiguration getKitYamlByName(String name){
        return YamlConfiguration.loadConfiguration(getKitFileByName(name));
    }

    public static YamlConfiguration getKitYamlByFile(File file){
        return YamlConfiguration.loadConfiguration(file);
    }

}
