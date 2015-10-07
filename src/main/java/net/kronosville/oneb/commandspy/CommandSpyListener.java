package net.kronosville.oneb.commandspy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.kronosville.oneb.OneB;

public final class CommandSpyListener implements org.bukkit.event.Listener {
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String msg = event.getPlayer().getDisplayName() + " : " + event.getMessage();
		for (String spy : CommandSpy.spies) {
			if (spy == CommandSpy.CONSOLE_ID) {
				Bukkit.getLogger().info(msg);
			} else {
				Player player = Bukkit.getServer().getPlayer(UUID.fromString(spy));
				// It'll be null if the "spy" isn't online.
				if (player != null) {
					OneB.sendMsg(player, msg);
				}
			}
		}
	}
}
