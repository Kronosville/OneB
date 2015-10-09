package net.kronosville.oneb.warptax;

import org.bukkit.entity.Player;

import net.kronosville.oneb.OneB;

public enum Confirming {

	/**
	 * Applying for a warp
	 */

	APPLYING,

	/**
	 * Paying tax for a registered warp
	 */

	PAYING,
	
	NONE;

	private static final String META = "warpTaxConfirming";

	static Confirming getConfirming(Player player) {
		return player.hasMetadata(META) ? (Confirming) OneB.getMetadata(player, META).value() : NONE;
	}

	static void setConfirming(Player player, Confirming confirming) {
		if (confirming == NONE) {
			OneB.removeMetadata(player, META);
		} else {
			OneB.setFixedMetadata(player, META, confirming);
		}
	}
}
