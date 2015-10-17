package net.kronosville.oneb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import net.ess3.api.IEssentials;

import net.kronosville.oneb.commandspy.CommandSpyExecutor;
import net.kronosville.oneb.commandspy.CommandSpyListener;
import net.kronosville.oneb.dovahzul.DovahzulChatExecutor;
import net.kronosville.oneb.dovahzul.DovahzulExecutor;
import net.kronosville.oneb.dovahzul.DovahzulListener;
import net.kronosville.oneb.items.*;
import net.kronosville.oneb.mailstaff.*;
import net.kronosville.oneb.message.*;
import net.kronosville.oneb.pvp.PvPExecutor;
import net.kronosville.oneb.pvp.PvPListener;
import net.kronosville.oneb.warptax.WarpTaxExecutor;

// import net.kronosville.oneb.mmo.OneBMMOExecutor;

/**
 * A small note: most methods don't have JavaDocs; it's somewhat erratic.
 * 
 * @author 1b8
 *
 */
public final class OneB extends org.bukkit.plugin.java.JavaPlugin {

	// Prefix
	public static final String PREFIX = makePrefix("OneB");

	// Enabled/disabled strings
	public static final String ENABLED = ChatColor.GREEN + "enabled" + ChatColor.RESET;
	public static final String DISABLED = ChatColor.RED + "disabled" + ChatColor.RESET;
	
	// Messages
	public static final String ACCESS_DENIED = "Access denied.";
	public static final String PLAYERS_ONLY = "Only a player can use this command!";

	private static OneB inst;

	public static String transChatPerm;
	public static String adminPerm;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		inst = this;

		// Commands
		PluginCommand transCmd = getCommand("dovahzul");
		PluginCommand transChatCmd = getCommand("dovahzulchat");

		PluginCommand pvpCmd = getCommand("pvp");

		PluginCommand loreCmd = getCommand("addlore");
		PluginCommand nameCmd = getCommand("rename");
		PluginCommand hatCmd = getCommand("hat");

		PluginCommand mailStaffCmd = getCommand("mailstaff");
		PluginCommand oneBCmd = getCommand("oneb");

		PluginCommand spyCmd = getCommand("onebcommandspy");
		PluginCommand msgCmd = getCommand("onebfakemessage");

		PluginCommand warpTaxCmd = getCommand("warptax");

		// Prefix usage messages
		prefixUsages(transCmd, pvpCmd, nameCmd, loreCmd, mailStaffCmd, oneBCmd, msgCmd);

		// Set permission messages
		setPermMsgs(transCmd, transChatCmd, nameCmd, loreCmd, hatCmd, mailStaffCmd, oneBCmd, spyCmd, msgCmd,
				warpTaxCmd);

		transChatPerm = transChatCmd.getPermission();
		adminPerm = oneBCmd.getPermission();

		// Set command executors

		transCmd.setExecutor(new DovahzulExecutor());
		transChatCmd.setExecutor(new DovahzulChatExecutor());

		pvpCmd.setExecutor(new PvPExecutor());

		RenameExecutor re = new RenameExecutor();
		loreCmd.setExecutor(re);
		nameCmd.setExecutor(re);

		hatCmd.setExecutor(new HatExecutor());

		mailStaffCmd.setExecutor(new MailStaffExecutor());
		oneBCmd.setExecutor(new OneBExecutor());

		spyCmd.setExecutor(new CommandSpyExecutor());
		msgCmd.setExecutor(new MessageExecutor());

		warpTaxCmd.setExecutor(new WarpTaxExecutor());

		// Set event listeners
		addListeners(new DovahzulListener(), new PvPListener(), new CommandSpyListener());
	}

	// How many times does it say "listener" in the next few lines?
	// The word is losing meaning.
	private void addListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	private static void prefixUsages(PluginCommand... cmds) {
		for (PluginCommand cmd : cmds) {
			cmd.setUsage(PREFIX + "Usage: /<command> " + cmd.getUsage());
		}
	}

	private static void setPermMsgs(PluginCommand... cmds) {
		for (PluginCommand cmd : cmds) {
			cmd.setPermissionMessage(PREFIX + ACCESS_DENIED);
		}
	}

	/**
	 * Make a dark aqua prefix enclosed in square brackets out of the given
	 * string
	 * 
	 * @param str
	 *            The string to make into a prefix
	 * @return The generated prefix
	 */

	public static String makePrefix(String str) {
		return ChatColor.DARK_AQUA + "[" + str + "] " + ChatColor.RESET;
	}

	/**
	 * Get metadata from a {@link Metadatable} object
	 * 
	 * @param player
	 *            The object to get metadata from
	 * @param key
	 *            The key of the metadata to get
	 * @return the first metadata value
	 */

	public static MetadataValue getMetadata(Metadatable player, String key) {
		return player.getMetadata(key).get(0);
	}

	/**
	 * Set the metadata of a {@link Player} or other {@link Metadatable} object to a
	 * {@link FixedMetadataValue}
	 * 
	 * @param player
	 *            The object to add metadata to
	 * @param key
	 *            The metadata key to add
	 * @param value
	 *            The metadata value to add
	 */

	public static void setFixedMetadata(Metadatable player, String key, Object value) {
		player.setMetadata(key, new FixedMetadataValue(inst, value));
	}

	/**
	 * Remove metadata from a {@link Metadatable} object
	 * 
	 * @param player
	 *            The object to remove metadata from
	 * @param key
	 *            The key of the metadata to remove
	 */

	public static void removeMetadata(Metadatable player, String key) {
		player.removeMetadata(key, inst);
	}

	/**
	 * Send a chat message prefixed with [OneB]
	 * 
	 * @param receiver
	 *            The receiver of the message
	 * @param msg
	 *            The message to send
	 */

	public static void sendMsg(CommandSender receiver, String msg) {
		receiver.sendMessage(OneB.PREFIX + msg);
	}

	/**
	 * Concatenate an array of strings into a single string, with each element
	 * seperated by a space
	 * 
	 * @param array
	 *            The array to concatenate
	 * @return The concatenated string
	 */

	public static String concat(String[] array) {
		String conced = "";
		for (int i = 0; i < array.length; i++) {
			conced += array[i];
			if (i < array.length - 1) {
				conced += ' ';
			}
		}
		return conced;
	}

	/**
	 * Get an instance of the plugin "Essentials"
	 * 
	 * @return The instance
	 */

	public static IEssentials getEss() {
		IEssentials ess = (IEssentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		if (ess == null) {
			String msg = "Essentials isn't installed! Error! Error! Error!";
			Bukkit.getLogger().severe(msg);
			Bukkit.getServer().broadcastMessage(PREFIX + msg);
		}
		return ess;
	}

	/**
	 * Get the instance of this class
	 * 
	 * @return the instance
	 */

	public static OneB getInst() {
		return inst;
	}
}