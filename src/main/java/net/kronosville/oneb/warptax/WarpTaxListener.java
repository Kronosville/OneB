package net.kronosville.oneb.warptax;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import net.ess3.api.Economy;

import net.kronosville.oneb.OneB;

public final class WarpTaxListener implements org.bukkit.event.Listener {
	
	// When a player joins...
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		for (Map.Entry<String, City> entry : City.getCities().entrySet()) {
			City city = entry.getValue();
			
			if (city.accepted && !city.warpTaxPaid) {
				Player player = event.getPlayer();
				String cityName = entry.getKey();
				
				OneB.sendMsg(player, "You haven't paid the warp tax for " + cityName +
						" yet! (" + Economy.format(WarpTax.getTaxAmount()) + "). Type \"/warptax pay " +
						cityName + "\" to pay.");
			}
		}
	}
	
}
