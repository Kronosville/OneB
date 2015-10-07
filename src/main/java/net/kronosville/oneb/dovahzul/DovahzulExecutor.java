package net.kronosville.oneb.dovahzul;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.kronosville.oneb.OneB;

import java.util.Arrays;

public final class DovahzulExecutor implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 1) {

			String untranslated = OneB.concat(Arrays.copyOfRange(args, 1, args.length));
			String translation = Dovahzul.translate(args[0], untranslated);

			if (translation != "incorrectSyntax") {
				OneB.sendMsg(sender, translation);
				return true;
			}

		}
		return false;
	}
}
