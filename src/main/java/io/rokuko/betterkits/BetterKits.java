package io.rokuko.betterkits;

import cn.neptunex.cloudit.bukkit.event.EventChain;
import cn.neptunex.cloudit.module.Module;
import cn.neptunex.cloudit.utils.ChatColorUtils;
import io.rokuko.betterkits.api.PreRemoveKitEvent;
import io.rokuko.betterkits.config.ProxyConfig;
import io.rokuko.betterkits.kit.Kit;
import io.rokuko.betterkits.kit.KitBaker;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

public class BetterKits extends Module {

    public static ProxyConfig proxyConfig;
    public static File kitDirectories;
    private final static String PREFIX = ChatColorUtils.colorization("&3Better&bKits&r ");

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
                case "remove": removeKit(sender, args); return;
                case "open": openKit(sender, args); return;
                case "give": giveKit(sender, args); return;
                case "list": listKit(sender, args); return;
                case "reload": reloadKit(sender); return;
            }
        }, "create|添加当前背包至礼包",
                "remove|移除指定的礼包",
                "open|查看某个礼包",
                "give|发送礼包至玩家",
                "list|查看所有礼包",
                "reload|重载插件配置");

        $log("插件加载成功!");
        return true;
    }

    private void listKit(CommandSender sender, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(" " + PREFIX);
        sender.sendMessage("");
        String filter = args.length >=2
                ? args[1].toUpperCase(Locale.ROOT)
                : "";
        KitBaker.kitLinkedHashMap.entrySet().stream()
                .filter(entry->entry.getValue().getKitType().name().contains(filter))
                .forEach(entry -> sender.sendMessage(ChatColorUtils.colorization(String.format("  &8- &f%s &8[&7%s&8]", entry.getKey(), entry.getValue().getKitType().name()))));
        sender.sendMessage("");
    }

    private void giveKit(CommandSender sender, String[] args) {

    }

    private void removeKit(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7Please input an kit name."));
            return;
        }
        String name = args[1];
        if (!KitBaker.kitLinkedHashMap.containsKey(name)){
            sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7There're no kit named &e" + name + "&7."));
            return;
        }
        EventChain.create()
                .during(PreRemoveKitEvent.class, 5000)
                .when(AsyncPlayerChatEvent.class, e->{
                    sender.sendMessage(e.getMessage().equals("yes")
                            ? PREFIX + ChatColorUtils.colorization("&7Successfully removed the kit &e" + name + "&7.")
                            : PREFIX + ChatColorUtils.colorization("&7Failed to remove the kit &e" + name + "&7."));
                    e.setCancelled(true);
                });
        sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7Confirm to remove kit &e" + name + "&7?(Please input &eyes&7/&eno&7 in 5s)"));
        $emit(new PreRemoveKitEvent());
    }

    private void openKit(CommandSender sender, String[] args) {

    }

    private void reloadKit(CommandSender sender) {
        reloadConfig();
        proxyConfig.refresh(getConfig());
        loadKits();
        sender.sendMessage(PREFIX + ChatColorUtils.colorization("&7successfully reload!"));
    }

    private void createKit(CommandSender sender, String[] args) {

    }

    private void loadKits(){
        for (File file : Objects.requireNonNull(kitDirectories.listFiles())) {
            KitBaker.bake(YamlConfiguration.loadConfiguration(file));
        }
    }

    @Override
    public boolean onUnActivate(Context context) {

        return true;
    }


}
