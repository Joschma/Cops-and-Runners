package fr.joschma.cnr.Arena.Timer;

import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.messages.Titles;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Messages.Language.MSG;

public class SneakCountDown extends BukkitRunnable {

	int timer;
	int maxTimer = 5;
	Arena a;
	Player p;

	public SneakCountDown(Arena a, Player p) {
		this.a = a;
		this.p = p;
		timer = maxTimer;
	}

	@Override
	public void run() {
		if (!p.isSneaking()) {
			cancel();
		} else {
			if (timer < 0) {
				cancel();
				return;
			} else if (timer < 1) {
				p.addPotionEffect(new PotionEffect(XPotion.GLOWING.parsePotionEffectType(), 20 * a.getGlowingTime(), 1));
				Titles.sendTitle(p, 1, 20 * 2, 1, "", MSG.stopSneak.msg());
			}
		}

		timer--;
	}

}
