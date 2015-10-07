package net.kronosville.oneb.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kronosville.oneb.OneB;

/**
 * 
 * The RenameExecutor class is the executor
 * for both the /rename and /lore commands,
 * as they perform extremely similar operations.
 * 
 * @author 1b8
 *
 */
public final class RenameExecutor implements org.bukkit.command.CommandExecutor {
	
	private String convert(String[] arr) {
		return ChatColor.translateAlternateColorCodes('&', OneB.concat(arr));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String cmdName = cmd.getName();
		
		if (sender instanceof Player) {

			if (args.length > 0) {

				Player player = (Player) sender;

				ItemStack item = player.getItemInHand();

				if (item.getType() != Material.AIR) {

					ItemMeta meta = item.getItemMeta();

					String newName;

					String prop;

					if (cmdName.equalsIgnoreCase("rename")) {
						// Rename command
						prop = "name";

						newName = convert(args);
						meta.setDisplayName(newName);
						// If the args are null, it will set it to null, and
						// therefore remove the name.

					} else if (cmdName.equalsIgnoreCase("addlore")) {
						// Add lore command
						prop = "lore";

						newName = convert(Arrays.copyOfRange(args, 1, args.length));
						List<String> lore;

						switch (args[0]) {
						case "set":
							lore = new ArrayList<String>(1);
							lore.add(newName);
							break;
						case "add":
							lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>(1);
							lore.add(newName);
							break;
						case "remove":
						case "delete":
							lore = new ArrayList<String>(0);
							break;
						default:
							return false;
						}
						meta.setLore(lore);

					} else {
						return false;
					}

					item.setItemMeta(meta);
					player.setItemInHand(item);
					OneB.sendMsg(player, "Item " + prop + " set.");
					return true;
				}
				OneB.sendMsg(player, "You cannot rename air!");
				return true;
			}
			return false;
		}
		OneB.sendPlayerOnlyMsg(sender);
		return true;
	}
}
