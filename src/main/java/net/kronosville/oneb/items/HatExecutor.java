package net.kronosville.oneb.items;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.kronosville.oneb.OneB;

public final class HatExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {

			Player player = (Player) sender;
			Player target;

			if (sender.hasPermission("oneb.admin") && args.length > 0) {
				target = Bukkit.getServer().getPlayer(args[0]);

				if (target == null) {
					OneB.sendMsg(player, "Player \"" + args[0] + "\" not found!");
					return true;
				}

			} else {
				target = player;
			}

			ItemStack newHat = player.getItemInHand();

			if (newHat.getType().getMaxDurability() == 0) {

				ItemStack oldHat = target.getInventory().getHelmet();

				player.setItemInHand(oldHat);
				target.getInventory().setHelmet(newHat);

				OneB.sendMsg(player, "Hat set.");

			} else {
				OneB.sendMsg(player, "You cannot use that item as a hat!");
			}

		} else {
			OneB.sendPlayerOnlyMsg(sender);
		}
		return true;
	}

}
