package fr.joschma.cnr.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Manager.FileManager;

public class CreateArenaCommand implements CommandExecutor {

	CopsNRunners pl;

	public CreateArenaCommand(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if(args.length == 2) {
				String arenaN = args[1];
				if (!pl.getAm().getArenaNames().contains(arenaN)) {
					File file = FileManager.createArenaFile(arenaN);
					YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);
					List<String> allowedCommands = new ArrayList<String>();
					allowedCommands.add("/hub");
					allowedCommands.add("/lobby");
					allowedCommands.add("/cnr leave");
					allowedCommands.add("/cnr forcestart");

					Arena a = new Arena(pl, arenaN, "VILLAGER_HAPPY", file, false, 8, 2, 40, 90,
							2.0, 5, 10, StateArena.CLEARED, new ArrayList<Player>(),
							new ArrayList<Player>(), new ArrayList<Player>(), new ArrayList<Player>(), new ArrayList<Player>(),
							null, new ArrayList<Location>(), new ArrayList<Location>(), null, null, null, new ArrayList<Sign>(), allowedCommands);

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
					fc.set("AllowedCommands", allowedCommands);
					
					a.setFile(file);
					pl.getAm().addArena(a);
					FileManager.save(file, fc);
					
					pl.getAm().addArenaNames(arenaN);
					pl.getDebug().msg(p, "The arena has been created successfully");
					return true;
				} else {
					pl.getDebug().error(p, "This arena already exist");
				}
			} else if (args.length == 1) {
				pl.getDebug().error(p, "create <arena_name>");
			}
		}
		return false;
	}
}
