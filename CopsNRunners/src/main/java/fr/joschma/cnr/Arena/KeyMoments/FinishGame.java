package fr.joschma.cnr.Arena.KeyMoments;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Arena.Timer.WinCountDown;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

public class FinishGame {
	
	Arena a;

	public FinishGame(Arena a) {
		super();
		this.a = a;
	}

	public void CopsWin() {
		a.getGcd().stopTimer();
		toAll();
		for (Player runner : a.getRunners()) {
			runner.sendTitle(ChatColor.RED + "LOST", "", 1, 20 * 4, 1);
			runner.sendMessage(ChatColor.RED + "You have lost");
		}
		for (Player cop : a.getCops()) {
			cop.sendTitle(ChatColor.YELLOW + "WIN", "", 1, 20 * 4, 1);
			cop.sendMessage(ChatColor.YELLOW + "You have won");
		}

		a.getWcd().startCountDown();
	}

	public void RunnersWin() {
		a.getGcd().stopTimer();
		toAll();
		for (Player runner : a.getRunners()) {
			runner.sendTitle(ChatColor.RED + "WIN", "", 1, 20 * 4, 1);
			runner.sendMessage(ChatColor.RED + "You have won");
		}
		for (Player cop : a.getCops()) {
			cop.sendTitle(ChatColor.YELLOW + "LOST", "", 1, 20 * 4, 1);
			cop.sendMessage(ChatColor.YELLOW + "You have lost");
		}

		a.getWcd().startCountDown();
	}

	public void forceStop() {
		if(a.getState() == StateArena.CLEARED || a.getState() == StateArena.WATTING) {
			a.getLcd().stopTimer();
		} else {
			a.getGcd().stopTimer();
		}
		
		toAll();
		new ClearArena(a);
	}
	
	public void toAll() {
		for(Player p : a.getPlayers()) {
			for (int i = 0; i < 8; i++) {
				p.getInventory().setItem(i, new ItemStack(Material.AIR));
			}

			p.setMaxHealth(20);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setWalkSpeed(0.2F);
			p.setGameMode(GameMode.SURVIVAL);
			p.setLevel(0);
			p.getActivePotionEffects().forEach(effect -> {
				p.removePotionEffect(effect.getType());
			});
		}
	}
}
