package net.kronosville.oneb;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class OneBExecutor implements org.bukkit.command.CommandExecutor {
	private final OneB plugin;

	OneBExecutor(OneB plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch (args[0]) {
		case "reload":
			plugin.reloadConfig();
			OneB.sendMsg(sender, "Config reloaded.");
			break;

		/* 
		 * Eclipse's Source > Format did this...
		 * 
		 * case "extrahearts": case "eh": String state; if
		 * (plugin.ehTaskCancelled) {
		 * 
		 * plugin.startEhTask(); plugin.ehTaskCancelled = false; state =
		 * OneB.ENABLED; } else {
		 * 
		 * plugin.ehTask.cancel(); plugin.ehTaskCancelled = true; state =
		 * OneB.DISABLED; } OneB.sendMsg(sender, "ExtraHearts task " + state +
		 * "."); break;
		 */

		default:
			return false;
		}
		return true;
	}
}
