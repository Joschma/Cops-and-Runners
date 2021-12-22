package fr.joschma.cnr.Listener;

import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.particles.XParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.Timer.Particules.Particules;

public class onPlayerPlaceBlock implements Listener {

    CopsNRunners pl;

    public onPlayerPlaceBlock(CopsNRunners pl) {
        super();
        this.pl = pl;
    }

    public boolean canPlaceHerePrison(Location loc, Player p) {
        Block upBlock = loc.getWorld().getBlockAt(loc.add(0, 1, 0));
        Block downBlock = loc.getWorld().getBlockAt(loc.add(0, -2, 0));

        if (downBlock.getType().isOccluding()) {
            if(upBlock.isEmpty()) {
                return true;
            } else {
                pl.getDebug().error(p, "There is not enough space above the prison");
            }
        } else {
            pl.getDebug().error(p, "You can not put the prison on a non full block");
        }

        return false;
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Arena a = pl.getAm().getArenaPlayer(p);
        if (a != null) {
            e.setCancelled(true);

            ItemStack it = new ItemStack(e.getBlockPlaced().getType());
            if (a.getCopBoss() == p) {
                if (it.getType() == XMaterial.IRON_BARS.parseMaterial()) {
                    Location loc = e.getBlockPlaced().getLocation();
                    if (canPlaceHerePrison(loc, p)) {
                            a.setPrison(new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5,
                                    loc.getYaw(), loc.getPitch()));

                            ItemStack bat = new ItemStack(XMaterial.BLAZE_ROD.parseMaterial());
                            ItemMeta batM = bat.getItemMeta();
                            batM.setDisplayName("Tazer");
                            bat.setItemMeta(batM);
                            p.getInventory().setItem(4, bat);

                            try {
                                Particules.startParticules(loc, Particle.valueOf(a.getParticule()));
                            } catch (Exception ex) {
                                pl.getDebug().error(p, "The particle " + a.getParticule() + " has not been recognized");
                            }

                    }
                }
            }
        }
    }
}
