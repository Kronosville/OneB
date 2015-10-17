package net.kronosville.oneb.dovahzul;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kronosville.oneb.OneB;

public class DovahzulChatExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Dovahzul.toggleDChatState((Player) sender);
		} else {
			OneB.sendMsg(sender, OneB.PLAYERS_ONLY);
		}
		return true;
	}

}
