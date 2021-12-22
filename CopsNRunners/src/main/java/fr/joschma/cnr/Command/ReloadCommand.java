package fr.joschma.cnr.Command;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Manager.ArenaManager;
import fr.joschma.cnr.Manager.FileManager;
import fr.joschma.cnr.Messages.Debugger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    CopsNRunners pl;
    Debugger debug;

    public ReloadCommand(CopsNRunners pl) {
        super();
        this.pl = pl;
        debug = pl.getDebug();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;
        pl.getDebug().msg(p, "Reloading the plugin...");

        for (Arena a : pl.getAm().getArenas()) {
            FileManager.reload(a.getFile());
        }

        pl.getAm().getArenas().clear();
        for (String str : pl.getAm().getArenaNames()) {
            pl.getInitializeArena().initializeArena(str);
        }
        pl.getDebug().msg(p, "Plugin reloaded");
        return true;
    }
}

