package io.rokuko.betterkits.kit;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class Kit {

    private String name;
    private KitType kitType;
    private List<ItemStack> rewards;
    private Integer count;

}
