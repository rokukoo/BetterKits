package io.rokuko.betterkits.utils;

import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static String toString(ItemStack itemStack){
        return itemStack.getType().name() + " x " + itemStack.getAmount();
    }

}
