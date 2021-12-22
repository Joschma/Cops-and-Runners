package fr.joschma.cnr.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;

public class onPlayerDisconect implements Listener {

	CopsNRunners pl;

	public onPlayerDisconect(CopsNRunners pl) {
		super();
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		Arena a = pl.getAm().getArenaPlayer(p);
		
		if (a != null) {
			if (a.getPlayers().contains(p) || a.getCops().contains(p) || a.getRunners().contains(p)) {
				a.getQuitGame().playerQuitGame(p);
			}
		}
	}
}
