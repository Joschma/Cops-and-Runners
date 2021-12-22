package fr.joschma.cnr.Listener;

import java.util.ArrayList;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Messages.Language.MSG;

public class onPlayerTakeDamage implements Listener {

	CopsNRunners pl;

	public onPlayerTakeDamage(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player damageTaken = (Player) e.getEntity();
			Arena a = pl.getAm().getArenaPlayer(damageTaken);
			if (a != null) {
				if (e.getCause() != DamageCause.ENTITY_SWEEP_ATTACK) {
					if (a.getPlayers().contains(damageTaken)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player damageTaken = (Player) e.getEntity();
			Arena a = pl.getAm().getArenaPlayer(damageTaken);
			if (a != null) {
				// out of prison
				if (e.getDamager() instanceof Player) {
					ArrayList<PotionEffectType> potion = new ArrayList<PotionEffectType>();
					ArrayList<PotionEffectType> playerPotion = new ArrayList<PotionEffectType>();
					potion.add(XPotion.BLINDNESS.parsePotionEffectType());
					potion.add(XPotion.SLOW.parsePotionEffectType());

					Player damager = (Player) e.getDamager();

					if (a.getCops().contains(damager) || a.getCops().contains(damageTaken)) {
						if (a.getRunners().contains(damager) || a.getRunners().contains(damageTaken)) {
							e.setCancelled(true);
						}
					}

					for (PotionEffect effect : damager.getActivePotionEffects()) {
						playerPotion.add(effect.getType());
					}

					if (potion.equals(playerPotion)) {
						return;
					}

					if (a.getCops().contains(damager)) {
						if (damager.getInventory().getItemInMainHand().getType() == XMaterial.BLAZE_ROD.parseMaterial()) {
							if (a.getRunners().contains(damageTaken)) {
								if (a.getPrison() != null) {
									if (!a.getPrisoned().contains(damageTaken)) {
										a.setInprison(damageTaken);
										return;
									}
								} else {
									damager.sendTitle(MSG.noPrison.msg(), "", 1, 20 * 5, 1);
								}
							}
						}
					} else if (a.getRunners().contains(damager)) {
						if (damager.getInventory().getItemInMainHand().getType() == XMaterial.SHEARS.parseMaterial()) {
							if (a.getRunners().contains(damageTaken)) {
								if (a.getPrisoned().contains(damageTaken)) {
									if (a.getPrison() != null) {
										a.setOutPrison(damageTaken);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
