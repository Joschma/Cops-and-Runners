package fr.joschma.cnr.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.KeyMoments.JoinArena;

public class JoinArenaCommand implements CommandExecutor {

	CopsNRunners pl;
	
	public JoinArenaCommand(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Arena a = pl.getAm().getArena(args[1]);
		Player p = (Player) sender;
		
        if (a != null) {
            JoinArena.join(a, p);
            return true;
        } else {
        	pl.getDebug().error(p, "This arena does not exist");
        }
        
		return false;
	}
}
