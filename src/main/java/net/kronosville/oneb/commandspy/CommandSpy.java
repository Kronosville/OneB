package net.kronosville.oneb.commandspy;

import java.util.ArrayList;
import java.util.List;

import net.kronosville.oneb.OneB;

public final class CommandSpy {
	
	static List<String> spies;
	
	static final String CONSOLE_ID = "console";
	
	private static final OneB PLUGIN = OneB.inst();
	
	static {
		List<String> list = PLUGIN.getConfig().getStringList("spies");
		spies = list != null ? list : new ArrayList<String>();
	}
	
	/**
	 * Save the `spies` list to config.yml
	 * so we can store who has commandspy on
	 * between restarts and relogs
	 */
	public static void saveSpies() {
		PLUGIN.getConfig().set("spies", spies);
		PLUGIN.saveConfig();
	}
	
}
