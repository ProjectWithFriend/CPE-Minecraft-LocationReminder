package me.aujung.locationReminder;

import me.aujung.locationReminder.commands.AddLocation;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LocationReminder extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Objects.requireNonNull(getCommand("addlocation")).setExecutor(new AddLocation());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
