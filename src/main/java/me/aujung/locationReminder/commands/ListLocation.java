package me.aujung.locationReminder.commands;

import com.google.gson.*;
import me.aujung.locationReminder.utils.ReadLocationData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Map;

public class ListLocation implements CommandExecutor {
    /* locations.json file is used to store the locations */
//    {
//        "17934e0d-5c04-459a-aba3-c31175535e41": {
//        "location_name": {
        //        "world" : "world",
//                "x": -55.04776962779134,
//                "y": 68.0,
//                "z": -9.069628132926287
//    }
//  }
//    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        String userUuid = player.getUniqueId().toString();

        try {
            JsonObject jsonData = ReadLocationData.read();
            JsonObject userData = jsonData.getAsJsonObject(userUuid);

            if (userData == null) {
                player.sendMessage(ChatColor.YELLOW + "You don't have any saved locations.");
                return true;
            }

            player.sendMessage(ChatColor.GOLD + "====== Your Saved Locations ======");
            for (Map.Entry<String, JsonElement> entry : userData.entrySet()) {
                String locationName = entry.getKey();
                JsonObject locationData = entry.getValue().getAsJsonObject();

                String world = locationData.get("world").getAsString();
                double x = Math.round(locationData.get("x").getAsDouble() * 1000.0) / 1000.0;
                double y = Math.round(locationData.get("y").getAsDouble() * 1000.0) / 1000.0;
                double z = Math.round(locationData.get("z").getAsDouble() * 1000.0) / 1000.0;

                player.sendMessage(ChatColor.AQUA + "Location Name: " + ChatColor.WHITE + locationName);
                player.sendMessage(ChatColor.GREEN + "World: " + ChatColor.WHITE + world);
                player.sendMessage(ChatColor.GREEN + "Coordinates: " +
                        ChatColor.WHITE + "X: " + x + ", Y: " + y + ", Z: " + z);
                player.sendMessage(ChatColor.DARK_GRAY + "--------------------------");
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "An error occurred while retrieving your locations.");
            return false;
        }
    }
}
