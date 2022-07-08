package io.rokuko.betterkits.kit;

import lombok.Data;

import java.util.List;

@Data
public class Kit {

    private String name;
    private KitType kitType;
    private Integer limit;
    private List<Reward> rewards;

}
