package me.aujung.locationReminder.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastLocation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        // Check if a location name is provided, otherwise set a default name
        String locationName = args.length > 0 ? args[0] : "Current Location";

        String formattedMessage = getBroadcastingMessage(player, locationName);

        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "-------------------------------");
        Bukkit.broadcastMessage(formattedMessage);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "-------------------------------");

        return true;
    }

    private static String getBroadcastingMessage(Player player, String locationName) {
        Location location = player.getLocation();
        String username = player.getName();

        String worldName = location.getWorld().getName();
        double xPos = location.getX();
        double yPos = location.getY();
        double zPos = location.getZ();

        return String.format(
                ChatColor.AQUA + "%s's " + ChatColor.GOLD + "location " + ChatColor.GOLD + ": " +
                        ChatColor.GREEN + "(%s)" + ChatColor.WHITE + " in " +
                        ChatColor.YELLOW + "%s" + ChatColor.WHITE + "\nat " +
                        ChatColor.AQUA + "X: %.3f, Y: %.3f, Z: %.3f",
                username, locationName, worldName, xPos, yPos, zPos
        );
    }
}
