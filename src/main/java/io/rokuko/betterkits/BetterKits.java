package io.rokuko.betterkits;

import cn.neptunex.cloudit.module.Module;

public class BetterKits extends Module {

    public BetterKits() {
        super("BetterKits", "v1.0-SNAPSHOT", "rokuko");
    }

    @Override
    public boolean onActivate(Context context) {
        $log("正在加载插件中..");
        $command(this, "bk", (sender, args)->{
            switch (args[0]){

            }
        }, "add|添加当前背包至礼包",
                "remove|移除指定的礼包",
                "open|查看某个礼包",
                "reload|重载插件配置");
        $log("插件加载成功!");
        return true;
    }

    @Override
    public boolean onUnActivate(Context context) {

        return true;
    }
}
