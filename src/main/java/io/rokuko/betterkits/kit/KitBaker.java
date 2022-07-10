package io.rokuko.betterkits.kit;

import io.rokuko.betterkits.kit.kit.CdkeyKit;
import io.rokuko.betterkits.kit.kit.ConditionKit;
import io.rokuko.betterkits.kit.kit.LimitKit;
import io.rokuko.betterkits.kit.kit.TimeKit;
import io.rokuko.betterkits.kit.reward.Reward;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class KitBaker {

    public static final HashMap<String, Kit> kitLinkedHashMap = new LinkedHashMap<>();

    public static Kit bakeFile2Kit(File file){
        return bake(YamlConfiguration.loadConfiguration(file));
    }

    public static Kit bake(FileConfiguration configuration){
        String name = configuration.getString("name");
        String type = configuration.getString("type");
        Integer limit = configuration.getInt("limit");
        List<String> rewardLines = configuration.getStringList("rewards");
        return bake(name, type, rewardLines, limit);
    }

    public static Kit bake(String name, String type, List<String> rewardLines, Integer limit){
        Kit kit;
        switch (type.toLowerCase(Locale.ROOT)){
            case "time": kit = new TimeKit(name, rewardLines, limit); break;
            case "condition": kit = new ConditionKit(name, rewardLines, limit); break;
            case "cdkey": kit = new CdkeyKit(name, rewardLines, limit); break;
            default: kit = new LimitKit(name, "limit", rewardLines, limit); break;
        }
        kitLinkedHashMap.put(name, kit);
        return kit;
    }

}
