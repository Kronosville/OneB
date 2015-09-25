package net.kronosville.oneb.message;

import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.kronosville.oneb.OneB;

public final class CommandSpy implements org.bukkit.command.CommandExecutor, org.bukkit.event.Listener {
	private final OneB plugin;
	public CommandSpy(OneB plugin) {
		this.plugin = plugin;
		plugin.getConfig().getStringList("spies");
	}
	
	private final String consoleId = "console";
	
	private List<String> spies;
	
	/**
	 * Save the "spies" list to config.yml
	 * so we can store who has commandspy on
	 * between restarts and relogs
	 */
	private void saveSpies() {
		plugin.getConfig().set("spies", spies);
		plugin.saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String id;
		if (sender instanceof Player) {
			id = ((Player) sender).getUniqueId().toString();
		} else if (sender instanceof ConsoleCommandSender) {
			id = consoleId;
		} else {
			// Something weird is happening!
			return false;
		}
		
		String state;
		if (!spies.contains(id)) {
			spies.add(id);
			state = OneB.ENABLED;
		} else {
			spies.remove(id);
			state = OneB.DISABLED;
		}
		saveSpies();
		OneB.sendMsg(sender, "OneBCommandSpy " + state + '.');
		return true;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
		String msg = event.getPlayer().getDisplayName() + " : " + event.getMessage();
		for (String spy : spies) {
			OneB.sendMsg(plugin.getServer().getPlayer(UUID.fromString(spy)),
					msg);
		}
		if (spies.contains(consoleId)) {
			plugin.getLogger().info(msg);
		}
	}
}
