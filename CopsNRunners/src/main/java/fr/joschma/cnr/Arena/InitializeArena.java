package fr.joschma.cnr.Arena;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Manager.FileManager;
import fr.joschma.cnr.Utils.LocUtils;

public class InitializeArena {

	CopsNRunners pl;

	public InitializeArena(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	public void initializeArena(String arenaName) {
		File file = FileManager.loadArenaFile(arenaName);
		YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);

		String name = fc.getString("Arena.Name");
		String particule = fc.getString("Arena.Game.Particle");
		boolean finished = fc.getBoolean("Arena.Finished");
		int maxPlayer = fc.getInt("Arena.Game.MaxPlayer");
		int minPlayer = fc.getInt("Arena.Game.MinPlayer");
		int lobbyWaitTime = fc.getInt("Arena.Timer.LobbyWaitTime");
		int gameTime = fc.getInt("Arena.Timer.GameTime");
		double distance = fc.getDouble("Arena.Game.Distance");
		int glowingTime = fc.getInt("Arena.Timer.GlowingTime");
		int appreciateTime = fc.getInt("Arena.Timer.AppreciateTime");
		List<Location> runnersSpawns = new ArrayList<Location>();
		List<Location> copsSpawns = new ArrayList<Location>();
		List<Sign> signs = new ArrayList<Sign>();
		List<String> allowedCommands = fc.getStringList("AllowedCommands");

		for (String loc : fc.getStringList("Arena.Spawns.RunnersSpawns")) {
			runnersSpawns.add(LocUtils.stringToLoc(loc));
		}
		for (String loc : fc.getStringList("Arena.Spawns.CopsSpawns")) {
			copsSpawns.add(LocUtils.stringToLoc(loc));
		}

		for (String sign : fc.getStringList("Signs")) {
			BlockState bl = LocUtils.stringToLoc(sign).getBlock().getState();

			if (bl instanceof Sign) {
				signs.add((Sign) bl);
			}
		}
		Location lobbySpawn = null;
		if(fc.getString("Arena.Spawns.LobbySpawn") != null){
			lobbySpawn = LocUtils.stringToLoc(fc.getString("Arena.Spawns.LobbySpawn"));
		}
		Location endSpawn = null;
		if(fc.getString("Arena.Spawns.EndSpawn") != null) {
			endSpawn = LocUtils.stringToLoc(fc.getString("Arena.Spawns.EndSpawn"));
		}

		Arena a = new Arena(pl, name, particule, file, finished, maxPlayer, minPlayer, lobbyWaitTime, gameTime,
				distance, glowingTime, appreciateTime, StateArena.CLEARED, new ArrayList<Player>(),
				new ArrayList<Player>(), new ArrayList<Player>(), new ArrayList<Player>(), new ArrayList<Player>(),
				null, runnersSpawns, copsSpawns, lobbySpawn, endSpawn, null, signs, allowedCommands);

		pl.getAm().addArena(a);
	}

	public void saveArena(Arena a) {
		File file = a.getFile();
		YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);

		fc.set("Arena.Name", a.getName());
		fc.set("Arena.Game.Particle", a.getParticule());
		fc.set("Arena.Finished", a.isFinished());
		fc.set("Arena.Game.MaxPlayer", a.getMaxPlayer());
		fc.set("Arena.Game.MinPlayer", a.getMinPlayer());
		fc.set("Arena.Timer.LobbyWaitTime", a.getLobbyWaitTime());
		fc.set("Arena.Timer.GameTime", a.getGameTime());
		fc.set("Arena.Game.Distance", a.getGetDistance());
		fc.set("Arena.Timer.GlowingTime", a.getGlowingTime());
		fc.set("Arena.Timer.AppreciateTime", a.getAppreciateTime());

		if (a.getRunnersSpawns() != null) {
			List<String> locStr = new ArrayList<String>();
			for(Location loc: a.getRunnersSpawns()){
				locStr.add(LocUtils.locToString(loc));
			}

			fc.set("Arena.Spawns.RunnersSpawns", locStr);
		}

		if (a.getCopsSpawns() != null) {
			List<String> locStr = new ArrayList<String>();
			for(Location loc: a.getCopsSpawns()){
				locStr.add(LocUtils.locToString(loc));
			}

			fc.set("Arena.Spawns.CopsSpawns", locStr);
		}

		if (a.getLobbySpawn() != null) {
			fc.set("Arena.Spawns.LobbySpawn", LocUtils.locToString(a.getLobbySpawn()));
		}

		if (a.getEndSpawn() != null) {
			fc.set("Arena.Spawns.EndSpawn", LocUtils.locToString(a.getEndSpawn()));
		}

		List<String> signsLoc = new ArrayList<String>();

		if (a.getSigns() != null) {
			for (Sign sign : a.getSigns()) {
				signsLoc.add(LocUtils.locToString(sign.getLocation()));
			}
		}

		fc.set("Signs", signsLoc);
		fc.set("AllowedCommands", a.getAllowedCommands());

		FileManager.save(file, fc);
	}
}
