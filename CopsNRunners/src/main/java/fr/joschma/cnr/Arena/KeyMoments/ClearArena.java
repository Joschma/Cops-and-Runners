package fr.joschma.cnr.Arena.KeyMoments;

import org.bukkit.entity.Player;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Arena.Timer.Particules.Particules;

public class ClearArena {

	Arena a;

	public ClearArena(Arena a) {
		this.a = a;
	}
	
	public void clearArena() {
		a.setState(StateArena.CLEARING);
		a.getPrisoned().clear();
		
		a.getCops().clear();
		a.getRunners().clear();
		
		Particules.stopParticules();
		
		for(Player p : a.getPlayers()) {
			p.getInventory().clear();
			p.teleport(a.getEndSpawn());
		}
		
		a.getPlayers().clear();
		a.setPrison(null);
		a.setState(StateArena.CLEARED);
		a.getSignFile().updateSign();
	}
}
