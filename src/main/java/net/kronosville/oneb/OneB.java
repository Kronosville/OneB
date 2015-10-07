package net.kronosville.oneb;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;

import net.kronosville.oneb.commandspy.CommandSpyExecutor;
import net.kronosville.oneb.commandspy.CommandSpyListener;
import net.kronosville.oneb.dovahzul.DovahzulChatExecutor;
import net.kronosville.oneb.dovahzul.DovahzulExecutor;
import net.kronosville.oneb.dovahzul.DovahzulListener;
import net.kronosville.oneb.items.HatExecutor;
import net.kronosville.oneb.items.RenameExecutor;
import net.kronosville.oneb.mailstaff.MailStaffExecutor;
import net.kronosville.oneb.message.MessageExecutor;
import net.kronosville.oneb.pvp.PvPExecutor;
import net.kronosville.oneb.pvp.PvPListener;

// import net.kronosville.oneb.mmo.OneBMMOExecutor;

/**
 * A small note: most methods don't have JavaDocs; it's somewhat erratic.
 * 
 * @author 1b8
 *
 */
public final class OneB extends org.bukkit.plugin.java.JavaPlugin {

	public static String makePrefix(String str) {
		return ChatColor.DARK_AQUA + "[" + str + "] " + ChatColor.RESET;
	}

	// Prefix
	public static final String PREFIX = makePrefix("OneB");

	// Enabled/disabled strings
	public static final String ENABLED = ChatColor.GREEN + "enabled" + ChatColor.RESET;
	public static final String DISABLED = ChatColor.RED + "disabled" + ChatColor.RESET;

	// Access denied message
	private static final String ACCESS_DENIED = PREFIX + "Access denied.";

	private static void prefixUsages(PluginCommand... cmds) {
		for (PluginCommand cmd : cmds) {
			cmd.setUsage(PREFIX + "Usage: /<command> " + cmd.getUsage());
		}
	}

	private static void setPermMsgs(PluginCommand... cmds) {
		for (PluginCommand cmd : cmds) {
			cmd.setPermissionMessage(ACCESS_DENIED);
		}
	}

	/*
	 * Once again, Eclipse's Source > Format did this
	 *
	 * 'Twas for Extra Hearts Per Nearby Player BukkitTask ehTask; boolean
	 * ehTaskCancelled = false;
	 * 
	 * ConfigurationSection getEhConfig() { return
	 * getConfig().getConfigurationSection("extra-hearts"); }
	 * 
	 * /* void startEhTask() { // TODo: Maybe make delay shorter? if
	 * (getEhConfig().getBoolean("enabled")) { ehTask = new
	 * ExtraHeartsTask(this).runTaskTimer(this, 100, 180); } }
	 */

	public static String transChatPerm;

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

		// PluginCommand mmoCmd = getCommand("oneb-mmo");

		// Prefix usage messages
		prefixUsages(transCmd, pvpCmd, nameCmd, loreCmd, mailStaffCmd, oneBCmd, msgCmd);

		// prefixUsage(mmoCmd);

		// Set permission messages
		setPermMsgs(transCmd, transChatCmd, nameCmd, loreCmd, hatCmd, mailStaffCmd, oneBCmd, spyCmd, msgCmd);

		// setPermMsg(mmoCmd);

		transChatPerm = transChatCmd.getPermission();

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
		
		
		// Set event listeners
		addListeners(new DovahzulListener(), new PvPListener(), new CommandSpyListener());

		// Here lies the stuff that got trashed (or not done at all)
		// R.I.P
		// mmoCmd.setExecutor(new OneBMMOExecutor(this));
		// Set up the more-hearts-per-player task
		// startEhTask();
	}

	// Send prefixed chat message
	public static void sendMsg(CommandSender receiver, String msg) {
		receiver.sendMessage(OneB.PREFIX + msg);
	}

	public static void sendPlayerOnlyMsg(CommandSender console) {
		sendMsg(console, "Only a player can use this command!");
	}

	public static String concat(String[] array) {
		String conced = "";
		for (int i = 0; i < array.length; i++) {
			conced += array[i];
			if (i < array.length - 1) {
				conced += " ";
			}
		}
		return conced;
	}

	// How many times does it say "listener" in the next few lines?
	// The word is losing meaning.
	private void addListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			getServer().getPluginManager().registerEvents(listener, this);
		}
	}

	private static OneB inst;

	/**
	 * Get the instance of this class
	 * 
	 * @return the instance
	 */
	public static OneB inst() {
		return inst;
	}
}