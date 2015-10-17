package net.kronosville.oneb.warptax;

import java.math.BigDecimal;

import org.bukkit.configuration.ConfigurationSection;

import net.kronosville.oneb.OneB;

public final class WarpTax {

	private static BigDecimal warpPrice = null;
	private static BigDecimal taxAmount = null;

	static final ConfigurationSection getConfig() {
		return OneB.getInst().getConfig().getConfigurationSection("warp-tax");
	}
	
	static BigDecimal getWarpPrice() {
		if (warpPrice == null) {
			warpPrice = new BigDecimal(getConfig().getString("initial-price"));
		}
		return warpPrice;
	}

	static BigDecimal getTaxAmount() {
		if (taxAmount == null) {
			taxAmount = new BigDecimal(600 + (200 * City.getAcceptedCities().size()));
		}
		return taxAmount;
	}
}
