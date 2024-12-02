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
        String locationString = String.format("§a%s §7(X: §b%.2f§7, Y: §b%.2f§7, Z: §b%.2f§7)",
                deathLocation.getWorld().getName(),
                deathLocation.getX(),
                deathLocation.getY(),
                deathLocation.getZ()
        );

        // Get the death message or reason (optional)
        String deathReason = event.getDeathMessage();

        // Format the broadcast message
        String deathMessage = String.format("§c§l%s §6has died at §e%s! §7(Reason: §f%s§7)",
                deadPlayer.getName(),
                locationString,
                deathReason != null ? deathReason : "Skill issue"
        );

        // Broadcast the death message to all online players
        Bukkit.broadcastMessage(deathMessage);
    }
}