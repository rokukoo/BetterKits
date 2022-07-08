package io.rokuko.betterkits.kit;

import io.rokuko.betterkits.kit.reward.Reward;
import io.rokuko.betterkits.kit.reward.RewardResolver;
import io.rokuko.betterkits.utils.KitUtils;
import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Kit {

    private String name;
    private KitType kitType;
    private Integer limit;
    private List<Reward> rewards;

    public Kit(){}

    private Kit(String name, KitType kitType, Integer limit) {
        this.name = name;
        this.kitType = kitType;
        this.limit = limit;
    }

    public static Kit of(String name, KitType kitType, Integer limit){
        return new Kit(name, kitType, limit);
    }

    public static Kit of(String name, KitType kitType, Integer limit, List<String> lines){
        return Kit.of(name, kitType, limit).resolveRewards(lines);
    }

    private Kit resolveRewards(List<String> lines) {
        this.rewards = lines.stream()
                .map(RewardResolver::resolveReward)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return this;
    }

    public void rewardPlayer(Player player){
        this.rewards.forEach(reward -> reward.rewardPlayer(player));
    }

    public File getFile(){
        return KitUtils.getKitFileByName(name);
    }

    public FileConfiguration getYamlFile(){
        return KitUtils.getKitYamlByName(name);
    }

    public Kit save(){
        KitController.saveKit(this);
        return this;
    }

}
