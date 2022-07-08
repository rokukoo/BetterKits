package io.rokuko.betterkits.kit;

import lombok.Data;

import java.util.List;

@Data
public class Kit {

    private String name;
    private KitType kitType;
    private Integer limit;
    private Reward rewards;

    public Kit(){}

    public Kit(String name, KitType kitType, Integer limit) {
        this.name = name;
        this.kitType = kitType;
        this.limit = limit;
    }

    public static Kit of(String name, KitType kitType, Integer limit){
        return new Kit(name, kitType, limit);
    }

}
