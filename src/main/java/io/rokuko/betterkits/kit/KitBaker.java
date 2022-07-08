package io.rokuko.betterkits.kit;

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
        Kit kit = new Kit();
        kit.setName(name);
        kit.setKitType(type == null ? KitType.CDKEY : KitType.valueOf(type.toUpperCase(Locale.ROOT)));
        kit.setLimit(limit);
        Reward reward = Reward.from(configuration.getStringList("rewards"));
        kit.setRewards(reward);
        kitLinkedHashMap.put(name, kit);
        return kit;
    }

}
