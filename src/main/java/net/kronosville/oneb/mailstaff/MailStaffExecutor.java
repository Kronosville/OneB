package net.kronosville.oneb.mailstaff;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ess3.api.IEssentials;

import net.kronosville.oneb.OneB;

public class MailStaffExecutor implements org.bukkit.command.CommandExecutor {

	private static final OneB PLUGIN = OneB.getInst();

	public static void mailStaff(String senderName, String msg) {
		List<String> staff = PLUGIN.getConfig().getStringList("staff");

		String fMsg = OneB.makePrefix("StaffMail") + ChatColor.GOLD + '[' + ChatColor.RESET + senderName
				+ ChatColor.GOLD + "] " + ChatColor.RESET + msg;

		for (String uuidStr : staff) {
			UUID uuid = UUID.fromString(uuidStr);
			
			Player player = PLUGIN.getServer().getPlayer(uuid);
			if (player != null) {
				player.sendMessage(fMsg);
			}
			
			IEssentials ess = OneB.getEss();
			if (ess != null) {
				OneB.getEss().getUser(uuid).addMail(fMsg);
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length > 0) {
			mailStaff(sender.getName(), OneB.concat(args));
			return true;
		}

		return false;
	}
}
