package net.kronosville.oneb.mailstaff;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kronosville.oneb.OneB;

public class MailStaffExecutor implements org.bukkit.command.CommandExecutor {

	private static final OneB PLUGIN = OneB.inst();

	private boolean essInstalled = PLUGIN.getServer().getPluginManager().getPlugin("Essentials") != null;

	public static void mailStaff(String senderName, String msg) {
		List<String> staff = PLUGIN.getConfig().getStringList("staff");

		String fMsg = OneB.makePrefix("StaffMail") + ChatColor.GOLD + '[' + ChatColor.RESET + senderName
				+ ChatColor.GOLD + "] " + ChatColor.RESET + msg;

		for (String playerName : staff) {
			Player player = PLUGIN.getServer().getPlayer(playerName);
			if (player != null) {
				player.sendMessage(fMsg);
			}

			PLUGIN.getServer().dispatchCommand(PLUGIN.getServer().getConsoleSender(),
					"mail send " + player + " " + fMsg);
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {

			String msg = OneB.concat(args);
			mailStaff(sender.getName(), msg);

			if (essInstalled) {
				OneB.sendMsg(sender, "Mail sent!");
			} else {
				OneB.sendMsg(sender,
						"Huh... it looks like this server doesn't have Essentials on it. Please contact an admin.");
			}
			return true;
		}

		return false;
	}
}
