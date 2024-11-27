package me.aujung.locationReminder;

import me.aujung.locationReminder.commands.AddLocation;
import me.aujung.locationReminder.commands.DeleteLocation;
import me.aujung.locationReminder.commands.ListLocation;
import me.aujung.locationReminder.utils.ReadLocationData;
import me.aujung.locationReminder.utils.WriteLocationData;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class LocationReminder extends JavaPlugin {

    private File locationFile;

    @Override
    public void onEnable() {
        locationFile = new File(getDataFolder(), "locations.json");
        ReadLocationData.setLocationData(locationFile);
        WriteLocationData.setLocationData(locationFile);

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        if(!locationFile.exists()) {
            try {
                locationFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        // Plugin startup logic
        Objects.requireNonNull(getCommand("postLocation")).setExecutor(new AddLocation());
        Objects.requireNonNull(getCommand("deleteLocation")).setExecutor(new DeleteLocation());
        Objects.requireNonNull(getCommand("listLocation")).setExecutor(new ListLocation());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
