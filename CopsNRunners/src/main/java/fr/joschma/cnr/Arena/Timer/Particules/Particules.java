package fr.joschma.cnr.Arena.Timer.Particules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

import fr.joschma.cnr.CopsNRunners;

public class Particules {
	
	static int taskID;
	
	public static void startParticules(Location loc, Particle particule) {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CopsNRunners.getPl(), new Runnable() {
			double a = 0;
			
			@Override
			public void run() {
				a += Math.PI / 16;
				Location first = loc.clone().add(Math.cos(a) * 2.8, 0, Math.sin(a) * 2.8);
				Location second = loc.clone().add(Math.cos(a + Math.PI) * 2.8, 0, Math.sin(a + Math.PI) * 2.8);
				
				loc.getWorld().spawnParticle(particule, first, 1, 0, 0, 0);
				loc.getWorld().spawnParticle(particule, second, 1, 0, 0, 0);
				
				loc.getWorld().spawnParticle(particule, first, 2, 0, 2, 0);
				loc.getWorld().spawnParticle(particule, second, 2, 0, 2, 0);
				
				Location third = loc.clone().add(Math.cos(a) * 2.8, 1, Math.sin(a) * 2.8);
				Location fourth = loc.clone().add(Math.cos(a + Math.PI) * 2.8, 1, Math.sin(a + Math.PI) * 2.8);
				
				loc.getWorld().spawnParticle(particule, third, 1, 0, 0, 0);
				loc.getWorld().spawnParticle(particule, fourth, 1, 0, 0, 0);
				
				
			}
		}, 0, 1);
	}
	
	public static void stopParticules() {
		Bukkit.getScheduler().cancelTask(taskID);
	}
}
