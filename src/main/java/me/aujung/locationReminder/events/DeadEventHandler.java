package me.aujung.locationReminder.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeadEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Get the player who died
        Player deadPlayer = event.getEntity();

        // Get the location where the player died
        Location deathLocation = deadPlayer.getLocation();

        // Format the death location coordinates
        String locationString = String.format("%s (X: %.2f, Y: %.2f, Z: %.2f)",
                deathLocation.getWorld().getName(),
                deathLocation.getX(),
                deathLocation.getY(),
                deathLocation.getZ()
        );

        // Broadcast the death message to all online players
        String deathMessage = String.format("§c%s has died at %s!",
                deadPlayer.getName(),
                locationString
        );
        Bukkit.broadcastMessage(deathMessage);
    }
}