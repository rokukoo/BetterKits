package io.rokuko.betterkits;

import cn.neptunex.cloudit.module.Module;
import io.rokuko.betterkits.config.ProxyConfig;
import org.bukkit.command.CommandSender;

public class BetterKits extends Module {

    private static ProxyConfig proxyConfig;

    public BetterKits() {
        super("BetterKits", "v1.0-SNAPSHOT", "rokuko");
    }

    @Override
    public boolean onActivate(Context context) {
        $log("正在加载插件中..");

        saveDefaultConfig();
        proxyConfig = ProxyConfig.proxy(getConfig());

        $command(this, "bk", (sender, args)->{
            switch (args[0]){
                case "add": addGift(sender, args); return;
                case "remove": removeGift(sender, args); return;
                case "open": openGift(sender, args); return;
                case "reload": reload(sender); return;
            }
        }, "add|添加当前背包至礼包",
                "remove|移除指定的礼包",
                "open|查看某个礼包",
                "reload|重载插件配置");
        $log("插件加载成功!");
        return true;
    }

    private void removeGift(CommandSender sender, String[] args) {

    }

    private void openGift(CommandSender sender, String[] args) {

    }

    private void reload(CommandSender sender) {
        reloadConfig();
        proxyConfig.refresh(getConfig());
    }

    private void addGift(CommandSender sender, String[] args) {

    }

    @Override
    public boolean onUnActivate(Context context) {

        return true;
    }


}
