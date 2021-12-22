package fr.joschma.cnr.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;

public class onPlayerBreakBlock implements Listener {

	CopsNRunners pl;

	public onPlayerBreakBlock(CopsNRunners pl) {
		super();
		this.pl = pl;
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		Player pla = e.getPlayer();
		Arena a = pl.getAm().getArenaPlayer(pla);
		if (a != null) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBreakBlock(HangingBreakByEntityEvent e) {
		if(e.getRemover() instanceof Player) {
			Player pla = (Player) e.getRemover();
			Arena a = pl.getAm().getArenaPlayer(pla);
			
			if (a != null) {
				e.setCancelled(true);
			}
		}
	}
}
