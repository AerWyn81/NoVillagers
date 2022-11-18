package pro.aerwyn81.novillagers.utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utils {
    public static String parseColor(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String parseLocation(Location loc) {
        return (loc.getWorld() == null ? "&cUnknownWorld" : loc.getWorld().getName())
                + ", x: " + loc.getBlockX()
                + ", y: " + loc.getBlockY()
                + ", z: " + loc.getBlockZ();
    }
}
