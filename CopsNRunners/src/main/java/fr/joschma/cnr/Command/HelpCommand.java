package fr.joschma.cnr.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Messages.Debugger;

public class HelpCommand implements CommandExecutor {

	CopsNRunners pl;
	Debugger debug;

	public HelpCommand(CopsNRunners pl) {
		super();
		this.pl = pl;
		debug = pl.getDebug();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;

		debug.msg(p, "-----------Permission-----------");
		debug.msg(p, "The only permission is: " + ChatColor.GOLD + "Cnr.Admin");
		debug.msg(p, "-------------Commands-----------");
		debug.msg(p, "list: shows all the arenas");
		debug.msg(p, "forcestart: starts the game when you are in the waiting room");
		debug.msg(p, "remove: deletes an arena");
		debug.msg(p, "create: creates a new arena");
		debug.msg(p, "join: let's you join a game");
		debug.msg(p, "<ArenaName>: opens a gui to easily set up an arena");
		debug.msg(p, "<ArenaName> gui: opens a gui to easily set up an arena");
		debug.msg(p, "<ArenaName> forceStop: stops the game");
		debug.msg(p, "<ArenaName> lobbySpawn: the waiting room before the game starts");
		debug.msg(p, "<ArenaName> endSpawn: where the player are being teleported after the game");
		debug.msg(p, "<ArenaName> finished: lets people play in the arena");
		debug.msg(p, "<ArenaName> copsSpawn: add or remove a cop spawn");
		debug.msg(p, "<ArenaName> runnersSpawn: add or remove a runner spawn");
		debug.msg(p, "<ArenaName> sign: add or remove a sign linked to the arena");
		debug.msg(p, "-------------Config-------------");
		debug.msg(p, "Particle: The name of the particle composing the jail");
		debug.msg(p, "MaxPlayer: The maximum number of players allowed in a game");
		debug.msg(p, "MinPlayer: The minimum number of players to start a game");
		debug.msg(p, "Distance: The radius of the prison");
		//
		debug.msg(p, "Finished: Can player play the arena");
		//
		debug.msg(p, "LobbyWaitTime: The time the player wait before the game starts (can be 0)");
		debug.msg(p, "GameTime: The time the runners have to run await from the cops before winning");
		debug.msg(p, "GlowingTime: The time a runner sneaking will glow (can be 0)");
		debug.msg(p, "AppreciateTime: The time after a team win before they are being teleported to the end spawn (can be 0)");
		//
		debug.msg(p, "AllowedCommands: All commands allowed in game");
		//
		debug.msg(p, "Signs and Spawn: Should not be modified");
		return true;
	}
}
