package net.kronosville.oneb;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MailStaffExecutor implements CommandExecutor {
	private final OneB plugin;

	MailStaffExecutor(OneB plugin) {
		this.plugin = plugin;
		essInstalled = plugin.getServer().getPluginManager().getPlugin("Essentials") != null;
	}

	boolean essInstalled;

	public static void mailStaff(String senderName, String msg, OneB plugin) {
		List<String> staff = plugin.getConfig().getStringList("staff");
		String fMsg = OneB.makePrefix("StaffMail") + ChatColor.GOLD + '[' + ChatColor.RESET + senderName
				+ ChatColor.GOLD + "] " + ChatColor.RESET + msg;
		for (String player : staff) {
			Player craftPlayer = plugin.getServer().getPlayer(player);
			if (craftPlayer != null) {
				craftPlayer.sendMessage(fMsg);
			}
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
					"mail send " + player + " " + fMsg);
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {

			String msg = OneB.concat(args);
			mailStaff(sender.getName(), msg, plugin);

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
