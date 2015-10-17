package net.kronosville.oneb.warptax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import com.earth2me.essentials.api.IWarps;

import net.kronosville.oneb.OneB;

public final class City {
	
	String name;
	List<UUID> owners = new ArrayList<UUID>();
	Location location;
	boolean accepted;
	boolean warpTaxPaid;

	static final ConfigurationSection CITY_CONFIG = WarpTax.getConfig().getConfigurationSection("cities");

	private static Map<String, City> cities = null;

	City(String name, List<UUID> owners, Location location, boolean accepted, boolean warpTaxPaid) {
		this.owners = owners;
		this.location = location;
		this.accepted = accepted;
		this.warpTaxPaid = warpTaxPaid;
	}
	
	private static final IWarps warps = OneB.getEss().getWarps();

	List<UUID> getOwners() {return owners;}
	Location getLocation() {return location;}
	boolean isAccepted() {return accepted;}
	boolean hasPaidWarpTax() {return warpTaxPaid;}

	void setName(String name) {this.name = name;}
	
	void setAccepted(boolean bool) {
		accepted = bool;
		
		if (accepted) {
			setWarp();
		} else {
			removeWarp();
		}
	}
	
	void setWarp() {
		// XXX Not entirely sure if any of this is going to work right
		try {
			warps.setWarp(name, location);
		} catch (Exception e) {
			
		}
	}
	
	void removeWarp() {
		try {
			warps.removeWarp(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// STATIC METHODS

	/**
	 * Get a map of ALL cities (pending and accepted).
	 * Keys are city names.
	 * 
	 * @return the map
	 */

	static Map<String, City> getCities() {
		if (cities == null) {

			final Map<String, City> cityMap = new HashMap<String, City>();

			for (String entry : CITY_CONFIG.getKeys(false)) {
				ConfigurationSection cityConfig = CITY_CONFIG.getConfigurationSection(entry);
				
				List<String> stringList = cityConfig.getStringList("owners");
				List<UUID> idList = new ArrayList<UUID>(stringList.size());
				
				for (String str : stringList) {
					idList.add(UUID.fromString(str));
				}

				cityMap.put(entry,
						new City(entry, idList,
								Location.deserialize(cityConfig.getConfigurationSection("location").getValues(false)),
								cityConfig.getBoolean("accepted"), cityConfig.getBoolean("tax-paid")));

			}
			cities = cityMap;
		}
		return cities;
	}
	
	/**
	 * Get a map of all accepted cities (those with a warp)
	 * This method loops through {@link getCities}()
	 */
	
	static Map<String, City> getAcceptedCities() {
		Map<String, City> result = new HashMap<String, City>();
		for (Map.Entry<String, City> entry : getCities().entrySet()) {
			City city = entry.getValue();
			
			if (city.accepted) {
				result.put(entry.getKey(), city);
			}

		}
		return result;
	}
	
	/**
	 * Add a {@link City} to the list of cities
	 * @param name The name of the city
	 * @param city The {@link City} to set
	 */
	
	static void setCity(City city) {

		ConfigurationSection section = CITY_CONFIG.createSection(city.name);
		section.set("owners", city.owners);
		section.set("location", city.location);
		section.set("accepted", city.accepted);
		section.set("tax-paid", city.warpTaxPaid);

		// TODO Not sure if this works!
		OneB.getInst().saveConfig();

		// Reset so it reloads next time
		cities = null;
	}
}
