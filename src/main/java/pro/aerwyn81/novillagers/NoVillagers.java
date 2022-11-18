package pro.aerwyn81.novillagers;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pro.aerwyn81.novillagers.commands.NVCommands;
import pro.aerwyn81.novillagers.handlers.ConfigService;
import pro.aerwyn81.novillagers.handlers.LanguageService;
import pro.aerwyn81.novillagers.handlers.SpawnHandler;
import pro.aerwyn81.novillagers.utils.Utils;
import pro.aerwyn81.novillagers.utils.config.ConfigUpdater;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class NoVillagers extends JavaPlugin {
    private static NoVillagers instance;
    public static ConsoleCommandSender log;

    public static HashMap<UUID, Boolean> playerDebugList;

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();
        playerDebugList = new HashMap<>();

        log.sendMessage(Utils.parseColor("[NoVillagers] &eInitializing..."));

        File configFile = new File(getDataFolder(), "config.yml");

        saveDefaultConfig();
        try {
            ConfigUpdater.update(this, "config.yml", configFile, Collections.singletonList("AllowVillagers"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();

        ConfigService.initialize(configFile);
        LanguageService.initialize(ConfigService.getLanguage());
        LanguageService.pushMessages();

        Bukkit.getPluginManager().registerEvents(new SpawnHandler(), this);

        getCommand("nv").setExecutor(new NVCommands());

        log.sendMessage(Utils.parseColor("[NoVillagers] &eNoVillagers enabled!"));
    }

    @Override
    public void onDisable() {
        log.sendMessage(Utils.parseColor("[NoVillagers] &cNoVillagers disabled!"));
    }

    public static NoVillagers getInstance() {
        return instance;
    }
}
