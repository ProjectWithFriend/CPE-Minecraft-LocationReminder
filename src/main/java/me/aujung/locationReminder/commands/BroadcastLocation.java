package me.aujung.locationReminder.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class BroadcastLocation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        String locationName = args[0];

        Player player = (Player) sender;

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
                        ChatColor.YELLOW + "%s" + ChatColor.WHITE + " at " +
                        ChatColor.AQUA + "X: %.3f, Y: %.3f, Z: %.3f",
                username, locationName, worldName, xPos, yPos, zPos
        );
    }
}