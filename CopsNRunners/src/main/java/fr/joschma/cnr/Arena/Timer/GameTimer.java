package fr.joschma.cnr.Arena.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Messages.Language.MSG;

public class GameTimer {

	Integer[] t = { 180, 120, 90, 60, 30, 15, 10, 5 };
	int timer;
	Arena a;

	public GameTimer(Arena a) {
		this.a = a;
	}

	static int taskID;

	public void startCountDown() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		timer = a.getGameTime();

		taskID = scheduler.scheduleSyncRepeatingTask(a.getPl(), new Runnable() {

			@Override
			public void run() {
				if (timer == 0) {
					a.getFinishGame().CopsWin();
					timer--;
					return;
				} else if (timer > 0) {
					for (Player p : a.getPlayers()) {
						p.setLevel(timer);
						for (int i : t) {
							if (timer == i) {
								String msg = MSG.EndIn.msg().replace("%time%", String.valueOf(timer));
								a.getPl().getDebug().msg(p, msg);
							}
						}
					}

					timer--;
				}
			}
		}, 0L, 20L);
	}

	public void stopTimer() {
		Bukkit.getScheduler().cancelTask(taskID);
		timer = -1;
	}
}
