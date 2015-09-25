package net.kronosville.oneb.dovahzul;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class DovahzulListener implements org.bukkit.event.Listener {

	// When a player inputs a chat message
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (Dovahzul.hasDChatOn(player)) {
			String oldMsg = event.getMessage();

			event.setMessage(Dovahzul.translate("D", oldMsg));

			Bukkit.getServer().broadcastMessage(
					String.format(Dovahzul.T_PREFIX + event.getFormat(), player.getDisplayName(), event.getMessage()));
			Dovahzul.sendAutoTranslated(player, oldMsg);
			event.setCancelled(true);
		}
	}

	// When a player leaves
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLeave(PlayerQuitEvent event) {
		Dovahzul.setDChatState(event.getPlayer(), false);
	}
}
