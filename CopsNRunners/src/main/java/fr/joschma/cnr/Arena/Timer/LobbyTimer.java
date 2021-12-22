package fr.joschma.cnr.Arena.Timer;

import com.cryptomorin.xseries.messages.Titles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Messages.Language.MSG;

public class LobbyTimer {

	Integer[] t = { 180, 120, 90, 60, 30, 15, 10, 5, 4, 3, 2, 1 };
	Arena a;
	int time;
	int taskID;

	public LobbyTimer(Arena a) {
		this.a = a;
	}

	public void startCountDown() {
		time = a.getLobbyWaitTime();
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		taskID = scheduler.scheduleSyncRepeatingTask(a.getPl(), new Runnable() {

			@Override
			public void run() {
				if (time == 0) {
					a.getStartGame().startGame();
					stopTimer();
				} else if (time > 0) {
					for (Player p : a.getPlayers()) {
						p.setLevel(time);

						for (int i : t) {
							if (time == i) {
								if (i <= 5) {
									Titles.sendTitle(p, 1, 20, 1, ChatColor.RED + String.valueOf(i), "");
								}

								String msg = MSG.GameStartIn.msg().replace("%time%", String.valueOf(time));
								a.getPl().getDebug().msg(p, msg);
							}
						}
					}

					time--;
				}
			}
		}, 0L, 20L);
	}

	public void stopTimer() {
		Bukkit.getScheduler().cancelTask(taskID);
		time = -1;
		for (Player p : a.getPlayers()) {
			p.setLevel(0);
		}

	}
}
