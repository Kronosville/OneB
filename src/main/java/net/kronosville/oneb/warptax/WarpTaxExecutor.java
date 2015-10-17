package net.kronosville.oneb.warptax;

import java.util.Arrays;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.IWarps;

import net.ess3.api.Economy;

import net.kronosville.oneb.OneB;
import net.kronosville.oneb.mailstaff.MailStaffExecutor;

public final class WarpTaxExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && args.length > 0) {
			Player player = (Player) sender;

			switch (args[0].toLowerCase()) {
			case "apply":

				OneB.sendMsg(player,
						"Type \"/" + label
								+ " confirm [city name]\" to apply for a warp to your city at your current location for "
								+ Economy.format(WarpTax.getWarpPrice()) + '.');

				Confirming.setConfirming(player, Confirming.APPLYING);

				break;
			case "pending":
				if (player.hasPermission(OneB.adminPerm)) {

					for (Map.Entry<String, City> entry : City.getCities().entrySet()) {

						if (!entry.getValue().accepted) {
							OneB.sendMsg(player,
									'"' + entry.getKey() + "\" - registered by " + entry.getValue().owners.get(0));
						}

					}

					OneB.sendMsg(player, "Use \"/" + label + " tp [city]\" to teleport to a city, and \"/" + label
							+ " accept [city]\" to accept it and add it as a warp.");

				} else {
					OneB.sendMsg(player, OneB.ACCESS_DENIED);
				}
				break;
			case "tp":
			case "teleport":
				if (args.length > 1) {

					OneB.sendMsg(player, "Teleporting...");
					player.teleport(City.getCities().get(args[1]).location);

				} else {
					return false;
				}
				break;
			case "accept":
				if (player.hasPermission(OneB.adminPerm)) {
					if (args.length > 1) {

						City city = City.getCities().get(args[1]);

						if (city != null) {
							if (!city.isAccepted()) {
								city.setAccepted(true);
							} else {
								OneB.sendMsg(player, "That city has already been accepted.");
							}
						} else {
							OneB.sendMsg(player, "That city does not exist.");
						}

					} else {
						return false;
					}
				} else {
					OneB.sendMsg(player, OneB.ACCESS_DENIED);
				}
				
				break;
			case "confirm":

				switch (Confirming.getConfirming(player)) {

				case APPLYING:
					if (args.length > 1) {
						// TODO Set the warp and remove it, to make sure it's valid, as well as the check below
						
						if (!City.getCities().containsKey(args[1])) {
							
							City.setCity(new City(args[1], Arrays.asList(player.getUniqueId()),
									loc,
									false,
									true));
							
							/* 
							 * NOTES: Make it have "paid the warp tax" the first month, so it doesn't get charged,
							 * and in the block executed at the start of each month:
							 * if (city.isAccepted() && (!)city.hasPaidWarpTax()) [...]
							 * 
							 * But what if it gets accepted a different month?
							 */

							MailStaffExecutor.mailStaff("WarpTax", sender.getName()
									+ " wants to create a warp to his/her city! Type \"/warptax pending\" for more info.");

						} else {
							OneB.sendMsg(player, "The name \"" + args[1] + "\" is already taken!");
						}
					} else {
						return false;
					}
					break;
				case PAYING:

					break;
				case NONE:
					OneB.sendMsg(player, "What are you trying to confirm...?");
				}

				break;
			default:
				return false;
			}

		} else {
			return false;
		}
		return true;
	}

}