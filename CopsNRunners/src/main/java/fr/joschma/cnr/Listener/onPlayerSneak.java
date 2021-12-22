package fr.joschma.cnr.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Arena.Timer.SneakCountDown;

public class onPlayerSneak implements Listener {
	
	CopsNRunners pl;

	public onPlayerSneak(CopsNRunners pl) {
		super();
		this.pl = pl;
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		Arena a = pl.getAm().getArenaPlayer(p);
		
		if (a != null) {
			if (a.getState() == StateArena.INGAME) {
				if(a.getRunners().contains(p))
					new SneakCountDown(a, p).runTaskTimer(pl, 0, 20);
			}
		}
	}
}
