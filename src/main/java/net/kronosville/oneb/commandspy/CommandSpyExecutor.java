package net.kronosville.oneb.commandspy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.kronosville.oneb.OneB;

public final class CommandSpyExecutor implements org.bukkit.command.CommandExecutor {
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String id;
		if (sender instanceof Player) {
			id = ((Player) sender).getUniqueId().toString();
		} else if (sender instanceof ConsoleCommandSender) {
			id = CommandSpy.CONSOLE_ID;
		} else {
			// Something weird is happening!
			return false;
		}
		
		String state;
		if (!CommandSpy.spies.contains(id)) {
			CommandSpy.spies.add(id);
			state = OneB.ENABLED;
		} else {
			CommandSpy.spies.remove(id);
			state = OneB.DISABLED;
		}
		CommandSpy.saveSpies();
		OneB.sendMsg(sender, "OneBCommandSpy " + state + '.');
		return true;
	}

}
