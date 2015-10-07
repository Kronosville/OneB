package net.kronosville.oneb.pvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kronosville.oneb.OneB;

public final class PvPExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equalsIgnoreCase("list")) {
			// List the players anyway, then tell them to use tab next time.
			PvP.listPlayers(sender);
			OneB.sendMsg(sender, "NOTICE: Please just use the player (tab) list next time; the names of players who have PvP on will show up in red.");
		} else if (sender instanceof Player) {
			PvP.togglePvPState((Player) sender);
		} else {
			OneB.sendPlayerOnlyMsg(sender);
		}
		return true;
	}
}
