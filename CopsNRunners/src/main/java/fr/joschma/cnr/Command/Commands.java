package fr.joschma.cnr.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;

public class Commands implements CommandExecutor {

	CopsNRunners pl;
	HelpCommand helpcmd;
	RemoveArenaCommand rac;
	CreateArenaCommand cac;
	JoinArenaCommand jac;
	ArenaCommand ac;
	ForceStartCommand fsc;
	ReloadCommand rc;

	public Commands(CopsNRunners pl) {
		this.pl = pl;
		helpcmd = new HelpCommand(pl);
		rac = new RemoveArenaCommand(pl);
		cac = new CreateArenaCommand(pl);
		jac = new JoinArenaCommand(pl);
		ac = new ArenaCommand(pl);
		fsc = new ForceStartCommand(pl);
		rc = new ReloadCommand(pl);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (p.hasPermission("Cnr.Admin") || p.isOp()) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("list")) {
						pl.getDebug().msg(p, "Arenas : " + pl.getAm().getArenaNames());
					} else if (args[0].equalsIgnoreCase("forcestart")) {
						fsc.onCommand(sender, cmd, label, args);
					} else if (args[0].equalsIgnoreCase("remove")) {
						if (p.hasPermission("Cnr.Admin")) {
							rac.onCommand(sender, cmd, label, args);
						}
					} else if (args[0].equalsIgnoreCase("create")) {
						if (p.hasPermission("Cnr.Admin")) {
							cac.onCommand(sender, cmd, label, args);
						}
					} else if (pl.getAm().getArenaNames().contains(args[0])) {
						ac.onCommand(sender, cmd, label, args);
					} else if (args[0].equalsIgnoreCase("help")) {
						helpcmd.onCommand(sender, cmd, label, args);
					} else if (args[0].equalsIgnoreCase("join")) {
						jac.onCommand(sender, cmd, label, args);
					} else if (args[0].equalsIgnoreCase("reload")) {
						rc.onCommand(sender, cmd, label, args);
					}
				}
			}
		}
		return false;
	}
}
