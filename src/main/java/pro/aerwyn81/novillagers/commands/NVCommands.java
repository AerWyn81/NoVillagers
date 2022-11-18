package pro.aerwyn81.novillagers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import pro.aerwyn81.novillagers.NoVillagers;
import pro.aerwyn81.novillagers.handlers.ConfigService;
import pro.aerwyn81.novillagers.handlers.LanguageService;
import pro.aerwyn81.novillagers.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NVCommands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendCommandHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("nv.reload")) {
                sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Commands.NoPerms")));
                return true;
            }

            NoVillagers.getInstance().reloadConfig();
            ConfigService.load();
            LanguageService.loadLanguage(ConfigService.getLanguage());
            LanguageService.pushMessages();

            sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Commands.Reload")));
            return true;
        }

        if (args[0].equalsIgnoreCase("debug")) {
            if (!sender.hasPermission("nv.debug")) {
                sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Commands.NoPerms")));
                return true;
            }

            if (!(sender instanceof Player player)) {
                sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Commands.PlayerOnly")));
                return true;
            }

            if (args.length == 2) {
                var hasDebugEnabled = NoVillagers.playerDebugList.get(player.getUniqueId());

                if ("true".equalsIgnoreCase(args[1])) {
                    if (hasDebugEnabled != null && hasDebugEnabled) {
                        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Errors.AlreadyDebugEnabled")));
                        return true;
                    }

                    NoVillagers.playerDebugList.put(player.getUniqueId(), true);
                    sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Messages.DebugEnabled")));
                    return true;
                } else if ("false".equalsIgnoreCase(args[1])) {
                    if (hasDebugEnabled != null && !hasDebugEnabled) {
                        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Errors.AlreadyDebugDisabled")));
                        return true;
                    }

                    NoVillagers.playerDebugList.put(player.getUniqueId(), false);
                    sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Messages.DebugDisabled")));
                    return true;
                }
            }

            sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.DebugCommand")));
            return true;
        }

        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Commands.Unknown")));
        return true;
    }

    private void sendCommandHelp(CommandSender sender) {
        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.Header")));
        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.Spacer1")));
        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.ReloadCommand")));
        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.DebugCommand")));
        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.Spacer2")));
        sender.sendMessage(Utils.parseColor(LanguageService.getMessage("Help.Footer")));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Stream.of("debug", "reload").filter(a -> a.startsWith(args[0])).collect(Collectors.toList());
        }

        if (args.length == 2 && Objects.equals(args[0], "debug")) {
            return Stream.of("true", "false").filter(a -> a.startsWith(args[1])).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
