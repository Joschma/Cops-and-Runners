package fr.joschma.cnr.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;

public class FoodLevelChangeListener implements Listener {
	
	CopsNRunners pl;

	public FoodLevelChangeListener(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerLoseHunger(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Arena a = pl.getAm().getArenaPlayer(p);
			if (a != null) {
				e.setCancelled(true);
			}
		}
	}
}
