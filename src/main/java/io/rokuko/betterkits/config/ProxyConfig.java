package io.rokuko.betterkits.config;

import org.bukkit.configuration.file.FileConfiguration;

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
    }

}
