package me.aujung.locationReminder.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.aujung.locationReminder.utils.ReadLocationData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class AddLocation implements CommandExecutor {

    private final File locationFile;
    private final Gson gson;

    public AddLocation(File locationFile) {
        this.locationFile = locationFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("Please provide a location name. Usage: /postLocation <location_name> or /pl <location_name>");
            return false;
        }

        String locationName = args[0];

        Player player = (Player) sender;
        String userId = player.getUniqueId().toString();
        Location location = player.getLocation();

        try {
            String worldName = location.getWorld().getName();

            JsonObject jsonData = ReadLocationData.read();

            JsonObject userData = jsonData.has(userId)
                    ? jsonData.getAsJsonObject(userId)
                    : new JsonObject();

            boolean isLocationNameExisting = userData.has(locationName);

            double xPos = location.getX();
            double yPos = location.getY();
            double zPos = location.getZ();

            JsonObject locationObject = new JsonObject();
            locationObject.addProperty("world", worldName);
            locationObject.addProperty("x", xPos);
            locationObject.addProperty("y", yPos);
            locationObject.addProperty("z", zPos);

            userData.add(locationName, locationObject);
            jsonData.add(userId, userData);

            writeLocationData(jsonData);

            String actionName = isLocationNameExisting ? "updated" : "saved";

            // Format coordinates to 3 decimal places
            String formattedMessage = String.format(
                    ChatColor.GOLD + "Location " + ChatColor.AQUA + "%s" + ChatColor.GOLD + ": " +
                            ChatColor.GREEN + "(%s)" + ChatColor.WHITE + " in " +
                            ChatColor.YELLOW + "%s" + ChatColor.WHITE + " at " +
                            ChatColor.AQUA + "X: %.3f, Y: %.3f, Z: %.3f",
                    actionName, locationName, worldName, xPos, yPos, zPos
            );

            player.sendMessage(ChatColor.DARK_GRAY + "-------------------------------");
            player.sendMessage(formattedMessage);
            player.sendMessage(ChatColor.DARK_GRAY + "-------------------------------");

        } catch (IOException e) {
            player.sendMessage("An error occurred while saving your location.");
            e.printStackTrace();
        }

        return true;
    }

    private JsonObject readLocationData() throws IOException {
        if (!locationFile.exists() || locationFile.length() == 0) {
            return new JsonObject(); // Return an empty JSON object if the file doesn't exist or is empty
        }

        try (FileReader reader = new FileReader(locationFile)) {
            return gson.fromJson(reader, JsonObject.class);
        }
    }

    private void writeLocationData(JsonObject jsonData) throws IOException {
        try (FileWriter writer = new FileWriter(locationFile)) {
            gson.toJson(jsonData, writer); // Pretty-print JSON
        }
    }
}
