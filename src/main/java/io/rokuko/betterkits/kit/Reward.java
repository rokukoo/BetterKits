package io.rokuko.betterkits.kit;

import lombok.Data;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Reward {

    private List<Command> commands = new LinkedList<>();
    private List<ItemStack> itemStacks = new LinkedList<>();

    private Reward(){}

    public void reward2Player(Player player){
        itemStacks.forEach(player.getInventory()::addItem);
        commands.forEach(command -> command.run(player));
    }

    public static Reward from(List<String> lines){
        Reward reward = new Reward();
        lines.forEach(reward::resolveItemStack);
        lines.forEach(reward::resolveCommand);
        return reward;
    }

    public void resolveItemStack(String line){
        String[] split = line.split(":");
        if (!split[0].equals("item")) return;
        ItemStack itemStack = new ItemStack(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        this.itemStacks.add(itemStack);
    }

    public void resolveCommand(String line){
        String[] split = line.split(":");
        if (!split[0].equals("command")) return;
        Command command = Command.of(split[1].equals("op"), split[2]);
        this.commands.add(command);
    }

}
