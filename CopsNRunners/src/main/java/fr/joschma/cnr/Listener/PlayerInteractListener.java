package fr.joschma.cnr.Listener;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.KeyMoments.JoinArena;

public class PlayerInteractListener implements Listener {

	CopsNRunners pl;

	public PlayerInteractListener(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getClickedBlock() != null) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (sign != null) {
					for (Arena ar : pl.getAm().getArenas()) {
						if (ar.getSigns() != null) {
							if (ar.getSigns().contains(sign)) {
								JoinArena.join(ar, p);
								return;
							}
						}
					}
				}
			}
		}

		Arena a = pl.getAm().getArenaPlayer(p);
		if (a != null) {
			if (e.getItem() != null) {
				if (e.getItem().getItemMeta() != null) {
					if (e.getItem().getType() == XMaterial.SLIME_BALL.parseMaterial()) {
						if (e.getItem().getItemMeta().getDisplayName()
								.equals(ChatColor.RED + "Leave")) {
							a.getQuitGame().playerQuitGame(p);
						}
					}
				}
			}
		}
	}
}
