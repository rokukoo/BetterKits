package io.rokuko.betterkits.config;

import io.rokuko.betterkits.kit.Kit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ProxyConfig {

    private FileConfiguration configuration;

    private ProxyConfig(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public static ProxyConfig proxy(FileConfiguration configuration){
        return new ProxyConfig(configuration);
    }

    public ProxyConfig refresh(FileConfiguration configuration){
        this.configuration = configuration;
        return this;
    }

    public List<Kit> getAllKits(){

        return null;
    }

}
