package fr.joschma.cnr.Command;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;

public class RemoveArenaCommand implements CommandExecutor {

	CopsNRunners pl;

	public RemoveArenaCommand(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			if (args.length == 2) {
				String arenaN = args[1];
				if (pl.getAm().getArenaNames().contains(arenaN)) {
					Arena a = pl.getAm().getArena(arenaN);
					File f = a.getFile();
					
					f.delete();
					pl.getAm().getArenaNames().remove(a.getName());
					pl.getAm().getArenas().remove(a);

					pl.getDebug().msg(p, "You have successfully delete the arena");
					return true;
				} else {
					pl.getDebug().msg(p, "This arena does not exist");
				}
			} else {
				pl.getDebug().msg(p, "/remove [arena]");
			}
		}
		return false;
	}
}
