package net.kronosville.oneb.warptax;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import net.kronosville.oneb.OneB;

public final class WarpTax {

	private static Map<String, City> cities = null;

	private static BigDecimal warpPrice = null;
	private static BigDecimal taxAmount = null;

	private static final ConfigurationSection getConfig() {
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
			taxAmount = new BigDecimal(600 + (200 * getCities().size()));
		}
		return taxAmount;
	}

	static Map<String, City> getCities() {
		if (cities == null) {
			final ConfigurationSection citiesConfig = getConfig().getConfigurationSection("cities");
			final Map<String, City> cityMap = new HashMap<String, City>();

			for (String entry : citiesConfig.getKeys(false)) {
				ConfigurationSection cityConfig = citiesConfig.getConfigurationSection(entry);
				
				cityMap.put(entry, new City(cityConfig.getStringList("owners"),
						Location.deserialize(cityConfig.getConfigurationSection("location").getValues(false)),
						cityConfig.getBoolean("taxPaid", false)));
				
			}
			cities = cityMap;
		}
		return cities;
	}

}
