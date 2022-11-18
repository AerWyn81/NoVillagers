package pro.aerwyn81.novillagers.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pro.aerwyn81.novillagers.NoVillagers;
import pro.aerwyn81.novillagers.utils.Utils;

import java.io.File;
import java.util.ArrayList;

public class ConfigService {
    private static File configFile;
    private static FileConfiguration config;

    public static void initialize(File file) {
        configFile = file;
        load();

        NoVillagers.log.sendMessage(Utils.parseColor("[NoVillagers] &eConfigurations loaded!"));
    }

    public static void load() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static String getLanguage() {
        return config.getString("language", "en").toLowerCase();
    }

    public static ArrayList<String> getAllowedVillagerWorlds() {
        return new ArrayList<>(config.getStringList("allowedVillagerWorlds"));
    }
}
