package net.kronosville.oneb.pvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.projectiles.ProjectileSource;

import net.kronosville.oneb.OneB;

public final class PvPListener implements org.bukkit.event.Listener {
	
	// When a player joins
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (PvP.getPvPState(player)) {
			player.setPlayerListName(ChatColor.RED + player.getPlayerListName());
		}
	}

	// When an entity hits another entity
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onAttack(EntityDamageByEntityEvent event) {
		
		Entity defenderE = event.getEntity();
		
		// Make sure there's a player on both sides of the attack
		if (defenderE instanceof Player) {
			
			Entity attackerE = event.getDamager();
			Player attacker = null;
			
			// If it's a projectile attacking, set "attacker" to the shooter of that projectile
			if (attackerE instanceof Projectile) {
				ProjectileSource attackerS = ((Projectile) attackerE).getShooter();
				if (attackerS instanceof Player) {
					attacker = (Player) attackerS;
				} // else if (attackerS instanceof BlockProjectileSource) {
					// event.setCancelled(true);
			} else if (attackerE instanceof Player) {
				attacker = (Player) attackerE;
			}
			
			if (attacker != null) {
				
				// It's a player on both sides!
				
				String exc = ChatColor.RED + "!";
				Player defender = (Player) defenderE;
				
				if (!PvP.getPvPState(attacker)) {
					// If the attacker doesn't have PvP on...
					OneB.sendMsg(attacker, ChatColor.RED + "You have PvP " + PvP.getPvPStateString(attacker) + exc);
					event.setCancelled(true);
				} else if (!PvP.getPvPState(defender)) {
					// Otherwise, if the defender doesn't have PvP on...
					OneB.sendMsg(attacker,
							ChatColor.RED + defender.getName() + " has PvP " + PvP.getPvPStateString(defender) + exc);
					event.setCancelled(true);
				}
			}
		}
	}
}
