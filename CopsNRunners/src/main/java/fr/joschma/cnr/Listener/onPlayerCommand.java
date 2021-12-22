package fr.joschma.cnr.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Messages.Language.MSG;

public class onPlayerCommand implements Listener {

	CopsNRunners pl;

	public onPlayerCommand(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		Arena a = pl.getAm().getArenaPlayer(p);
		
		if (a != null) {
			if (!a.getAllowedCommands().contains(e.getMessage())) {
				pl.getDebug().error(p, MSG.youAreNotAllowedToSendMsg.msg());
				e.setCancelled(true);
			}
		}
	}
}
