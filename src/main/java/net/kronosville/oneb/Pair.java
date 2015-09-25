package net.kronosville.oneb;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

/**
 * A small class to denote a pair
 * of a {@link PluginCommand} and a {@link CommandExecutor},
 * for use by {@link OneB#setExecutors(Pair... pairs)}
 * 
 * @author 1b8
 *
 */

final class Pair {
	final PluginCommand cmd;
	final CommandExecutor ex;
	
	Pair(final PluginCommand cmd, final CommandExecutor ex) {
		this.cmd = cmd;
		this.ex = ex;
	}
	
}
