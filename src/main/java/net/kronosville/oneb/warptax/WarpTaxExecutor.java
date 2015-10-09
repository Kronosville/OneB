package net.kronosville.oneb.warptax;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.ess3.api.Economy;

import net.kronosville.oneb.OneB;
import net.kronosville.oneb.mailstaff.MailStaffExecutor;

public final class WarpTaxExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player && args.length > 0) {
			Player player = (Player) sender;

			switch (args[0]) {
			case "apply":

				OneB.sendMsg(player,
						"Type \"/" + label
								+ " confirm [city name]\" to apply for a warp to your city at your current location for "
								+ Economy.format(WarpTax.getWarpPrice()) + '.');

				Confirming.setConfirming(player, Confirming.APPLYING);

			case "confirm":

				switch (Confirming.getConfirming(player)) {
				case APPLYING:

					// TODO WarpTax.addPendingCity()

					MailStaffExecutor.mailStaff("WarpTax", sender.getName()
							+ " wants to create a warp to his/her city! Type \"/warptax pending\" for more info.");

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