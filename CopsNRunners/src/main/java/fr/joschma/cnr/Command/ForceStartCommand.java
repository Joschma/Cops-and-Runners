package fr.joschma.cnr.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;

public class ForceStartCommand  implements CommandExecutor {

	CopsNRunners pl;
	
	public ForceStartCommand(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		Arena a = pl.getAm().getArenaPlayer(p);

		if (a != null) {
			if (a.getState() == StateArena.WATTING) {
				a.getLcd().stopTimer();
				a.getStartGame().startGame();
				return true;
			}
		}
		
		return false;
	}
}
