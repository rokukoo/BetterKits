package io.rokuko.betterkits;

import cn.neptunex.cloudit.module.Module;
import cn.neptunex.cloudit.utils.ChatColorUtils;
import cn.neptunex.cloudit.utils.FileUtils;
import io.rokuko.betterkits.config.ProxyConfig;
import io.rokuko.betterkits.kit.*;
import io.rokuko.betterkits.kit.reward.ItemReward;
import io.rokuko.betterkits.kit.reward.RewardResolver;
import io.rokuko.betterkits.utils.KitUtils;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class BetterKits extends Module {

    public static ProxyConfig proxyConfig;
    public static File kitDirectories;
    public final static String PREFIX = ChatColorUtils.colorization("&3Better&bKits&r ");

    public BetterKits() {
//        super("&9&lBetter&b&lKits", "v1.0-SNAPSHOT", "rokuko");
        super("BetterKits", "v1.0-SNAPSHOT", "rokuko");
    }

    @SneakyThrows
    @Override
    public boolean onActivate(Context context) {
        $log("正在加载插件中..");

        saveDefaultConfig();
        proxyConfig = ProxyConfig.proxy(getConfig());

        kitDirectories = new File(getDataFolder(), "kits");
        if (!kitDirectories.exists()) kitDirectories.mkdirs();

        loadKits();

        saveResource("kits/示例礼包.yml", true);

        $command(this, "bk", (sender, args)->{
            switch (args[0]){
                case "create": createKit(sender, args); return;
                case "add": addKit(sender, args); return;
                case "remove": removeKit(sender, args); return;
                case "open": openKit(sender, args); return;
                case "give": giveKit(sender, args); return;
                case "list": listKit(sender, args); return;
                case "reload": reloadKit(sender); return;
            }
        }, "create|创建某个礼包",
                "add|添加物品或指令至礼包",
                "remove|移除指定的礼包",
                "open|查看某个礼包",
                "give|发送礼包至玩家",
                "list|查看所有礼包",
                "reload|重载插件配置");

        $log("插件加载成功!");
        return true;
    }

    public String checkArgsReturnName(CommandSender sender, String[] args){
        if (args.length < 2) {
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7Please input an kit name."));
            return "";
        }
        return args[1];
    }

    @SneakyThrows
    private void addKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        if (name.isEmpty()) return;
        if (!KitUtils.checkExistsKit(name)) {
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7There're no kit named &e" + name + "&7."));
            return;
        }

        Kit kit = Objects.requireNonNull(KitUtils.getKitByName(name));
        if (args.length >= 3 && args[2].contains(":")){
            kit.getRewards().add(RewardResolver.resolveReward(args[2]));
        }else if (sender instanceof Player){
            if (((Player) sender).getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7Failed to add the reward to &e" + name + "!"));
                return;
            }
            kit.getRewards().add(ItemReward.of(((Player) sender).getInventory().getItemInMainHand()));
        }
        kit.save();

        sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7successfully add the reward to &e" + name + "!"));
    }

    private void listKit(CommandSender sender, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(" " + PREFIX);
        String filter = args.length >=2
                ? args[1].toUpperCase(Locale.ROOT)
                : "";

        KitBaker.kitLinkedHashMap.entrySet().stream()
                .filter(entry->entry.getValue().getKitType().contains(filter))
                .forEach(entry -> {
                    TextComponent textComponent = new TextComponent(ChatColorUtils.colorization(String.format("  &8- &f%s &8[&7%s&8]", entry.getKey(), entry.getValue().getKitType())));
                    List<String> messages = new LinkedList<>();
                    messages.add(ChatColorUtils.colorization(String.format(" &3%s", entry.getKey())));
                    messages.add("");
                    entry.getValue().getRewards().forEach( reward -> messages.add(ChatColorUtils.colorization(String.format("&8- &f%s", reward.toString()))));
                    messages.add("");
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(String.join("\n", messages))));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/bk give %s", entry.getKey())));
                    Player player = (Player) sender;
                    player.spigot().sendMessage(textComponent);
                });
        sender.sendMessage("");
    }

    private void giveKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        if (!KitUtils.checkExistsKit(name)) {
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7There're no kit named &e" + name + "&7."));
            return;
        }
        KitBaker.kitLinkedHashMap.get(name).rewardPlayer((Player) sender);
    }

    private void openKit(CommandSender sender, String[] args) {

    }

    private void reloadKit(CommandSender sender) {
        reloadConfig();
        proxyConfig.refresh(getConfig());
        loadKits();
        sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7successfully reload!"));
    }

    private void removeKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        if (name.isEmpty()) return;
        if (!KitUtils.checkExistsKit(name)) {
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7There're no kit named &e" + name + "&7."));
            return;
        }
        KitController.removeKitByName(this, name, sender);
    }

    @SneakyThrows
    private void createKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        if (name.isEmpty()) return;
        if (KitUtils.checkExistsKit(name)) {
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7The kit named &e" + name + "&7 has existed."));
            return;
        }

        KitController.createKit(this, name, true);
        sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7Successfully created the kit &e" + name + "&7."));
    }

    private void loadKits(){
        FileUtils.getFiles(kitDirectories).forEach(KitBaker::bakeFile2Kit);
    }

    @Override
    public boolean onUnActivate(Context context) {

        return true;
    }

}
