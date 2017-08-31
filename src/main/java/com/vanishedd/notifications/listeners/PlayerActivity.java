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

            Player mentioned = Bukkit.getPlayer(stripped);

            if (mentioned == null) {
                continue;
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getName().equalsIgnoreCase(stripped)) {
                    continue;
                }

                if (message.equalsIgnoreCase(player.getName())) {
                    String fullMessage = String.format(e.getFormat(), e.getPlayer().getDisplayName(), e.getMessage().replace(word, ChatColor.AQUA + mentioned.getName()));

                    e.getRecipients().remove(player);
                    player.sendMessage(fullMessage);
                    mentioned.playSound(mentioned.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound", "ANVIL_BREAK").toUpperCase()), 1, 1);
                    return;
                }

                String lastColor = ChatColor.getLastColors(e.getMessage().split(word)[0]);
                String fullMessage = String.format(e.getFormat(), e.getPlayer().getDisplayName(), e.getMessage().replace(word, ChatColor.AQUA + mentioned.getName() + lastColor));

                e.getRecipients().remove(player);
                player.sendMessage(fullMessage);
                mentioned.playSound(mentioned.getLocation(), Sound.valueOf(plugin.getConfig().getString("Sound", "ANVIL_BREAK").toUpperCase()), 1, 1);
                return;
            }
        }
    }
}
