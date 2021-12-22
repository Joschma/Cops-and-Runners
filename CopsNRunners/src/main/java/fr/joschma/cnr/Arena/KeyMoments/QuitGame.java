package fr.joschma.cnr.Arena.KeyMoments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Messages.Debugger;
import fr.joschma.cnr.Messages.Language.MSG;

public class QuitGame {
	
	Arena a;

	public QuitGame(Arena a) {
		this.a = a;
	}

	public void playerQuitGame(Player p) {
		Debugger debug = a.getPl().getDebug();
		a.getPlayers().remove(p);
		
		if(a.getCops().contains(p)) {
			a.getCops().remove(p);
			
			if(a.getCopBoss() == p) {
				if(a.getCops().size() > 0 && a.getPrison() == null) {
					Player newCopC = a.getCops().get(0);
					
					a.setCopBoss(newCopC);
					ItemStack barre = new ItemStack(Material.IRON_BARS);
					ItemMeta barreM = barre.getItemMeta();
					barre.setItemMeta(barreM);
					
					newCopC.getInventory().setItem(4, barre);
					
					newCopC.sendTitle(ChatColor.AQUA + "Boss Cop", MSG.bossCopHasLeft.msg(), 1,
							20 * 5, 1);
					debug.msg(newCopC, MSG.doNotForgetToPutDownThePrison.msg());
				}
			}
		} else if (a.getRunners().contains(p)) {
			if(a.getPrisoned().contains(p)) {
				a.getPrisoned().remove(p);
			}
			
			a.getRunners().remove(p);
		}

		for (Player pla : a.getPlayers()) {
			debug.msg(pla, MSG.hasLeftTheGame.msg().replace("%player%", p.getName()));
		}

		if (a.getState().equals(StateArena.INGAME)) {
			a.getCheckWin().checkWin(a);
		} else if (a.getState().equals(StateArena.WATTING)) {
			if (a.getPlayers().size() < a.getMinPlayer()) {
				a.getLcd().stopTimer();
				a.setState(StateArena.CLEARED);
				
				for (Player pla : a.getPlayers()) {
					debug.msg(pla, MSG.waitingForPlayers.msg());
				}
			}
		}
		
		Bukkit.getScoreboardManager().getNewScoreboard().clearSlot(DisplaySlot.BELOW_NAME);
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setWalkSpeed(0.2F);
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().clear();
		p.setLevel(0);
		p.getActivePotionEffects().forEach(effect -> {
			p.removePotionEffect(effect.getType());
		});
		p.teleport(a.getEndSpawn());
		
		debug.msg(p, MSG.youHaveLeftTheGame.msg());
		a.getSignFile().updateSign();
	}
}
