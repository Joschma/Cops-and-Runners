package fr.joschma.cnr.Listener;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Manager.ArenaManager;
import org.bukkit.scheduler.BukkitTask;

public class onPlayerClickInventory implements Listener {

	CopsNRunners pl;
	ArenaManager am;

	public onPlayerClickInventory(CopsNRunners pl) {
		super();
		this.pl = pl;
		this.am = pl.getAm();
	}

	@EventHandler
	public void onPlayerClickInventoryListener(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			Arena ar = am.getArenaPlayer(p);

			if (ar != null) {
				e.setCancelled(true);
			} else {
				if (e.getCurrentItem() == null)
					return;

				if (e.getCurrentItem().hasItemMeta()) {
					String name = e.getCurrentItem().getItemMeta().getDisplayName();
					name = ChatColor.stripColor(name);
					String title = e.getView().getTitle();
					Arena a = am.getArena(title);

					if (a == null)
						return;

					if (title.equals(a.getName())) {
						e.setCancelled(true);
						p.closeInventory();

						if (e.getCurrentItem().getType() == XMaterial.IRON_DOOR.parseMaterial()) {
							if (name.equals("End Spawn")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " endspawn");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.OAK_DOOR.parseMaterial()) {
							if (name.equals("Lobby Spawn")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " lobbySpawn");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.BLUE_CONCRETE.parseMaterial()) {
							if (name.equals("Add Cops Spawn")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " copsSpawn add");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.YELLOW_CONCRETE.parseMaterial()) {
							if (name.equals("Add Runners Spawn")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " runnersSpawn add");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.BLUE_CONCRETE_POWDER.parseMaterial()) {
							if (name.equals("Remove Cops Spawn")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " copsSpawn remove");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.YELLOW_CONCRETE_POWDER.parseMaterial()) {
							if (name.equals("Remove Runners Spawn")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " runnersSpawn remove");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.OAK_SIGN.parseMaterial()) {
							if (name.equals("Add Sign")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " sign add");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.DARK_OAK_SIGN.parseMaterial()) {
							if (name.equals("Remove Sign")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " sign remove");
							}
						} else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
							if (name.equals("Finished")) {
								p.performCommand("copsnrunners:cnr " + a.getName() + " finished");
							}
						}
					}
				}
			}
		}
	}
}
