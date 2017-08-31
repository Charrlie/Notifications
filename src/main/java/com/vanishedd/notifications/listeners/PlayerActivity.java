package com.vanishedd.notifications.listeners;

import com.vanishedd.notifications.Notifications;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerActivity implements Listener {
    private Notifications plugin = Notifications.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();

        for (String word : message.split(" ")) {
            String stripped = word.replaceAll("\\W", "");

            if (stripped.equals("")) {
                continue;
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getName().equalsIgnoreCase(stripped)) {
                    continue;
                }

                if (player == e.getPlayer()) {
                    return;
                }

                if (message.equalsIgnoreCase(player.getName())) {
                    String fullMessage = String.format(e.getFormat(), e.getPlayer().getDisplayName(), e.getMessage().replace(word, ChatColor.AQUA + player.getName()));

                    e.getRecipients().remove(player);
                    player.sendMessage(fullMessage);
                    player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound", "ANVIL_BREAK").toUpperCase()), 1, 1);
                    return;
                }

                String fullMessage = String.format(e.getFormat(), e.getPlayer().getDisplayName(), e.getMessage());
                String lastColor = ChatColor.getLastColors(fullMessage.split(word)[0]);
                String coloredMessage = fullMessage.replace(word, ChatColor.AQUA + player.getName() + lastColor);

                e.getRecipients().remove(player);
                player.sendMessage(coloredMessage);
                player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound", "ANVIL_BREAK").toUpperCase()), 1, 1);
                return;
            }
        }
    }
}
