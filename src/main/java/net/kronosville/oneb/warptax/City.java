package net.kronosville.oneb.warptax;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

public final class City {
	
	List<UUID> owners = new ArrayList<UUID>();
	Location location;
	boolean warpTaxPaid;
	
	public City(List<String> owners, Location location, boolean warpTaxPaid) {
		for (String idStr : owners) {
			this.owners.add(UUID.fromString(idStr));
		}
		this.location = location;
		this.warpTaxPaid = warpTaxPaid;
	}
}
