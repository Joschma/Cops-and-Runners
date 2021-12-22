package fr.joschma.cnr.Arena.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.KeyMoments.ClearArena;
import fr.joschma.cnr.Messages.Language.MSG;

public class WinCountDown {
	
	Integer[] t = { 10, 5, 4, 3, 2, 1 };
	Arena a;
	int timer;
	int taskID;
	
	public WinCountDown(Arena a) {
		this.a = a;
	}

	public void startCountDown() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		timer = a.getAppreciateTime();
		
		taskID = scheduler.scheduleSyncRepeatingTask(a.getPl(), new Runnable() {

			@Override
			public void run() {
				if (timer == 0) {
					a.getCa().clearArena();
					stopTimer();
					timer--;
					return;
				} else if (timer > 0) {
					for (Player p : a.getPlayers()) {
						p.setLevel(timer);
						for (int i : t) {
							if (timer == i) {
								String msg = MSG.TpIn.msg().replace("%time%", String.valueOf(timer));
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
	}
}
