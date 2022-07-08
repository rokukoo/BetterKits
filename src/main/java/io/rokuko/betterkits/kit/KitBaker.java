package io.rokuko.betterkits.kit;

import io.rokuko.betterkits.kit.reward.Reward;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class KitBaker {

    public static final HashMap<String, Kit> kitLinkedHashMap = new LinkedHashMap<>();

    public static Kit bakeFile2Kit(File file){
        return bake(YamlConfiguration.loadConfiguration(file));
    }

    public static Kit bake(FileConfiguration configuration){
        String name = configuration.getString("name");
        String type = configuration.getString("type");
        Integer limit = configuration.getInt("limit");
        Kit kit = Kit.of(name, type == null ? KitType.CDKEY : KitType.valueOf(type.toUpperCase(Locale.ROOT)), limit, configuration.getStringList("rewards"));
        kitLinkedHashMap.put(name, kit);
        return kit;
    }

}
