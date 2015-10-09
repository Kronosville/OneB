package net.kronosville.oneb;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class OneBExecutor implements org.bukkit.command.CommandExecutor {
	private final OneB plugin = OneB.getInst();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			OneB.sendMsg(sender, "Config reloaded.");
		} else {
			OneB.sendMsg(sender, "Server running OneB v" + plugin.getDescription().getVersion() + '.');
		}
		return true;
	}
}
