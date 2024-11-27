package me.aujung.locationReminder.commands;

import com.google.gson.JsonObject;
import me.aujung.locationReminder.utils.ReadLocationData;
import me.aujung.locationReminder.utils.WriteLocationData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DeleteLocation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Please provide a location name. Usage: /deleteLocation <location_name>");
            return false;
        }

        String locationName = args[0];

        Player player = (Player) sender;
        String userId = player.getUniqueId().toString();

        try {
            JsonObject jsonData = ReadLocationData.read();

            if (!jsonData.has(userId)) {
                sender.sendMessage("You have no saved locations.");
                return true;
            }

            JsonObject userData = jsonData.getAsJsonObject(userId);

            if (!userData.has(locationName)) {
                sender.sendMessage("No location found with that name.");
                return true;
            }

            userData.remove(locationName);
            jsonData.add(userId, userData);

            WriteLocationData.write(jsonData);

            player.sendMessage(ChatColor.GOLD + "Location " + ChatColor.AQUA + locationName + ChatColor.GOLD + " has been deleted.");

        } catch (IOException e) {
            player.sendMessage("An error occurred while deleting your location.");
            e.printStackTrace();
        }

        return true;
    }
}