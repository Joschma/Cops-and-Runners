package fr.joschma.cnr.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;

public class onPlayerDropItem implements Listener {

	CopsNRunners pl;

	public onPlayerDropItem(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Player pla = e.getPlayer();
		Arena a = pl.getAm().getArenaPlayer(pla);
		if (a != null) {
			e.setCancelled(true);
		}
	}
}
