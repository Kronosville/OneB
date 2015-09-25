package net.kronosville.oneb.dovahzul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import net.kronosville.oneb.OneB;

public final class Dovahzul {
	// Don't let this class be instantiated
	private Dovahzul(){}
	
	public static final String T_PREFIX = OneB.makePrefix("T");
	
	public static boolean hasDChatOn(Player player) {
		return player.hasMetadata("dovahzulChat");
	}
	
	public static String hasDChatOnString (Player player) {
		if (hasDChatOn(player)) {
			return OneB.ENABLED;
		} else {
			return OneB.DISABLED;
		}
	}
	
	public static void setDChatState(Player player, boolean state) {
		if (state) {
			player.setMetadata("dovahzulChat", new FixedMetadataValue(OneB.inst(), true));
		} else {
			player.removeMetadata("dovahzulChat", OneB.inst());
		}
		OneB.sendMsg(player, "Dovahzul chat " + hasDChatOnString(player) + ".");
	}
	
	public static void toggleDChatState(Player player) {
		if (hasDChatOn(player)) {
			setDChatState(player, false);
		} else {
			setDChatState(player, true);
		}
	}
	
	public static void sendAutoTranslated(Player sender, String untranslated) {
		String msg = T_PREFIX + sender.getDisplayName() + ": " + ChatColor.ITALIC + untranslated;
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.hasPermission(OneB.transChatPerm)) {
				player.sendMessage(msg);
			}
		}
		Bukkit.getServer().getConsoleSender().sendMessage(msg);
	}
	
	public static String translate(String shortLang, String msg) {
		String lang;
		switch (shortLang.toUpperCase()) {
			case "E":
				lang = "english";
				break;
			case "D":
				lang = "dragon";
				break;
			default:
				return "incorrectSyntax";
		}
		
		try {
			URL translator = new URL("http://thuum.org/translate-" + lang + ".php?text=" + ChatColor.translateAlternateColorCodes('&', msg.replaceAll(" ", "%20")));
			BufferedReader in = new BufferedReader(
					new InputStreamReader(translator.openStream()));
			String outputLine;
			String output = "";
			while ((outputLine = in.readLine()) != null) {
				// TODO: There may be other funky stuff, apart from apostrophes.
				output += outputLine
				.replaceAll("&#39;", "'");
			}
			in.close();
			return output;
			
		} catch (IOException e) {
			Bukkit.getLogger().warning("Error! " + e.getMessage());
			return "Error! " + e.toString();
		}
	}
 }