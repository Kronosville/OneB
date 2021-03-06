package net.kronosville.oneb.pvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import net.kronosville.oneb.OneB;

public final class PvP {
	// Let no one instantiate this
	private PvP() {}
	private static final OneB PLUGIN = OneB.inst();
	
	private static final String PVP_META = "pvpOn";
	
	public static boolean getPvPState(Player player) {
		return player.hasMetadata(PVP_META);
	}
	
	public static String getPvPStateString(Player player) {
		String state;
		if (getPvPState(player)) {
			state = OneB.ENABLED;
		} else {
			state = OneB.DISABLED;
		}
		return state + ChatColor.RESET;
	}
	
	public static void setPvPState(Player player, boolean state) {
		if (state) {
			player.setMetadata(PVP_META, new FixedMetadataValue(PLUGIN, true));
			player.setPlayerListName(ChatColor.RED + player.getName());
		} else {
			player.removeMetadata(PVP_META, PLUGIN);
			player.setPlayerListName(null);
		}
		OneB.sendMsg(player, "PvP " + getPvPStateString(player) + ".");
	}
	
	public static void togglePvPState(Player player) {
		setPvPState(player, !getPvPState(player));
	}
	
	/**
	 * Tab now shows the names of players with PvP enabled in red,
	 * making this function redundant.
	 * @param receiver The receiver of the player list
	 */
	public static void listPlayers(CommandSender receiver) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			ChatColor colour;
			if (getPvPState(player)) {
				colour = ChatColor.RED;
			} else {
				colour = ChatColor.GREEN;
			}
			OneB.sendMsg(receiver, colour + player.getName());
		}
	}
}
