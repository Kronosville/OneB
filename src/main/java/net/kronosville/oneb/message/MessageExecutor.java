package net.kronosville.oneb.message;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kronosville.oneb.OneB;

public final class MessageExecutor implements org.bukkit.command.CommandExecutor {
	private final OneB plugin = OneB.inst();
	
	// A word of warning: This file may be a bit messy.
	
	private Set<Entry<String, Object>> getTemplates() {
		return plugin.getConfig().getConfigurationSection("templates").getValues(false).entrySet();
	}
	
	private String parse(String msg) {
		
		String[] msgArr = msg.split(" ");
		
		for (int i = 0; i < msgArr.length; i++) {
			if (msgArr[i].equals("{nick}")) {
				Player player = plugin.getServer().getPlayer(msgArr[i+1]);
				if (player != null) {
					msgArr[i] = player.getDisplayName();
					msgArr[i+1] = "";
				}
			}
		}
		return ChatColor.translateAlternateColorCodes('&', OneB.concat(msgArr));
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			String msg = parse(OneB.concat(Arrays.copyOfRange(args, 1, args.length)));
			
			switch (args[0].toLowerCase().charAt(0)) {
			case 'p':
				
				String[] split = msg.split(" ");
				
				Player target = plugin.getServer().getPlayer(split[0]);
				if (target != null) {
					target.sendMessage(OneB.concat(Arrays.copyOfRange(split, 1, split.length)));
				} else {
					OneB.sendMsg(target, "Player \"" + split[0] + "\" not found!");
				}
				break;
			case 'a':
				plugin.getServer().broadcastMessage(msg);
				break;
			default:
				for (Map.Entry<String, Object> template : getTemplates()) {
					if (args[0].equals(template.getKey())) {
						plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.format(template.getValue().toString(), msg)));
						return true;
					}
				}
				return false;
			}
			return true;
		} else if (args.length > 0 && args[0].toLowerCase().charAt(0) == 'l') {
			for (Map.Entry<String, Object> template : getTemplates()) {
				OneB.sendMsg(sender, template.getKey() + " : " + ChatColor.translateAlternateColorCodes('&', template.getValue().toString()).replaceAll("%s", "[message]"));
			}
			return true;
		}
		return false;
	}
}
