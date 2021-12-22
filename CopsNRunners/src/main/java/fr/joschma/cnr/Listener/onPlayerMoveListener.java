package fr.joschma.cnr.Listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Messages.Language.MSG;

public class onPlayerMoveListener implements Listener {

	CopsNRunners pl;

	public onPlayerMoveListener(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Arena a = pl.getAm().getArenaPlayer(p);

		if (a != null) {
			if (a.getState() == StateArena.INGAME) {
				if (a.getPrisoned().contains(p)) {
					double distance = a.getGetDistance();
					Location prisonLoc = a.getPrison();
					if (Math.abs(p.getLocation().getX() - prisonLoc.getX()) >= distance
							|| Math.abs(p.getLocation().getZ() - prisonLoc.getZ()) >= distance
							|| Math.abs(p.getLocation().getY() - prisonLoc.getY()) >= distance) {
						pl.getDebug().error(p, MSG.youCanNotLeaveThePrison.msg());
						p.teleport(prisonLoc);
					}
				}
			}
		}
	}
}
