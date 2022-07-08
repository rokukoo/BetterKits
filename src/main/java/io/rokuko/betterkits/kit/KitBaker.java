package io.rokuko.betterkits.kit;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class KitBaker {

    public static final HashMap<String, Kit> kitLinkedHashMap = new LinkedHashMap<>();

    public static Kit bake(FileConfiguration configuration){
        String name = configuration.getString("name");
        String type = configuration.getString("type");
        Integer limit = configuration.getInt("limit");
        Kit kit = new Kit();
        kit.setName(name);
        kit.setKitType(KitType.valueOf(type.toUpperCase(Locale.ROOT)));
        kit.setLimit(limit);
        Reward reward = new Reward();

        kitLinkedHashMap.put(name, kit);
        return kit;
    }

}
