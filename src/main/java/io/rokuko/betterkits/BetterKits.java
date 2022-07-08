package io.rokuko.betterkits;

import cn.neptunex.cloudit.bukkit.event.EventChain;
import cn.neptunex.cloudit.module.Module;
import cn.neptunex.cloudit.utils.ChatColorUtils;
import cn.neptunex.cloudit.utils.FileUtils;
import io.rokuko.betterkits.api.PreRemoveKitEvent;
import io.rokuko.betterkits.config.ProxyConfig;
import io.rokuko.betterkits.kit.*;
import io.rokuko.betterkits.utils.ItemUtils;
import io.rokuko.betterkits.utils.KitUtils;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.lang.reflect.Field;
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

        saveResource("kits/example.yml", true);

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
        String name = args[1];
        if (!KitBaker.kitLinkedHashMap.containsKey(name)){
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7There're no kit named &e" + name + "&7."));
            return "";
        }
        return name;
    }

    @SneakyThrows
    private void addKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        if (name.isEmpty()) return;

        if (sender instanceof Player){
            Player player = (Player) sender;
            File file = new File(kitDirectories, name + ".yml");
            YamlConfiguration kitYaml = KitUtils.getKitYamlByFile(file);
            List rewards = new LinkedList();
//            List<String> rewards = kitYaml.getItemStack("rewards");
//            Arrays.stream(player.getInventory().getStorageContents())
//                    .filter(Objects::nonNull)
//                    .forEach(rewards::add);
            kitYaml.set("rewards", rewards);
            kitYaml.save(file);
        }
    }

    private void listKit(CommandSender sender, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(" " + PREFIX);
        String filter = args.length >=2
                ? args[1].toUpperCase(Locale.ROOT)
                : "";

        KitBaker.kitLinkedHashMap.entrySet().stream()
                .filter(entry->entry.getValue().getKitType().name().contains(filter))
                .forEach(entry -> {
                    TextComponent textComponent = new TextComponent(ChatColorUtils.colorization(String.format("  &8- &f%s &8[&7%s&8]", entry.getKey(), entry.getValue().getKitType().name())));
                    List<String> messages = new LinkedList<>();
                    messages.add(ChatColorUtils.colorization(String.format(" &3%s", entry.getKey())));
                    entry.getValue().getRewards().getItemStacks().forEach(itemStack->messages.add(ChatColorUtils.colorization(String.format("&8- &f%s", ItemUtils.toString(itemStack)))));
                    entry.getValue().getRewards().getCommands().forEach(command->messages.add(ChatColorUtils.colorization(String.format("&8- &f%s", command.toString()))));
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(String.join("\n", messages))));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/bk give %s", entry.getKey())));
                    Player player = (Player) sender;
                    player.spigot().sendMessage(textComponent);
                });
        sender.sendMessage("");
    }

    private void giveKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        KitBaker.kitLinkedHashMap.get(name).getRewards().reward2Player((Player) sender);
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

        KitController.removeKitByName(this, name, sender);
    }

    @SneakyThrows
    private void createKit(CommandSender sender, String[] args) {
        String name = checkArgsReturnName(sender, args);
        if (name.isEmpty()) return;
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
