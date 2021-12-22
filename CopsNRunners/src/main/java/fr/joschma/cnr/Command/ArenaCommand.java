package fr.joschma.cnr.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Manager.FileManager;
import fr.joschma.cnr.Messages.Debugger;
import fr.joschma.cnr.Utils.LocUtils;

public class ArenaCommand implements CommandExecutor {

    CopsNRunners pl;
    Debugger debug;

    public ArenaCommand(CopsNRunners pl) {
        super();
        this.pl = pl;
        debug = pl.getDebug();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = (Player) sender;

        Arena a = pl.getAm().getArena(args[0]);
        File file = a.getFile();
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);

        Location l = p.getLocation();
        if (args.length == 1) {
            pl.getArenaGui().openGui(p, a);
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("gui")) {
                pl.getArenaGui().openGui(p, a);
            } else if (args[1].equalsIgnoreCase("forceStop")) {
                a.getFinishGame().forceStop();
            } else if (args[1].equalsIgnoreCase("lobbySpawn")) {
                a.setLobbySpawn(l);
                fc.set("Arena.Spawns.LobbySpawn", LocUtils.locToString(l));
                debug.msg(p, "You have successfully set the lobby spawn");

            } else if (args[1].equalsIgnoreCase("endSpawn")) {
                a.setEndSpawn(l);
                fc.set("Arena.Spawns.EndSpawn", LocUtils.locToString(l));
                debug.msg(p, "You have successfully set the end spawn");

            } else if (args[1].equalsIgnoreCase("finished")) {
                if (!a.isFinished()) {
                    a.setFinished(true);
                    fc.set("Arena.Finished", true);
                    debug.msg(p, "You have successfully " + ChatColor.GOLD + "finished" + ChatColor.GRAY + " the arena");
                } else {
                    a.setFinished(false);
                    fc.set("Arena.Finished", false);
                    debug.msg(p, "You have successfully " + ChatColor.GOLD + "unfinished" + ChatColor.GRAY + " the arena");
                }
            } else if (args[1].equalsIgnoreCase("copsSpawn") || args[1].equalsIgnoreCase("runnersSpawn") || args[1].equalsIgnoreCase("sign")) {
                debug.error(p, "<arena> " + args[1] + " [add/remove]");
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("copsSpawn")) {
                List<Location> copsSpawns = a.getCopsSpawns();

                if (args[2].equalsIgnoreCase("add")) {
                    copsSpawns.add(l);
                    a.setCopsSpawns(copsSpawns);

                    List<String> copsSpawnsString = new ArrayList<String>();
                    for (Location loc : copsSpawns) {
                        copsSpawnsString.add((LocUtils.locToString(loc)));
                    }

                    fc.set("Arena.Spawns.CopsSpawns", copsSpawnsString);
                    debug.msg(p, "You have added a cop spawn");
                } else if (args[2].equalsIgnoreCase("remove")) {
                    if(copsSpawns.size() > 0) {
                        copsSpawns.remove(copsSpawns.size() - 1);
                        a.setCopsSpawns(copsSpawns);

                        List<String> copsSpawnsString = new ArrayList<String>();
                        for (Location loc : copsSpawns) {
                            copsSpawnsString.add((LocUtils.locToString(loc)));
                        }

                        fc.set("Arena.Spawns.CopsSpawns", copsSpawnsString);
                        debug.msg(p, "You have removed the last cop spawn");
                    }
                }
            } else if (args[1].equalsIgnoreCase("runnersSpawn")) {
                List<Location> runnersSpawns = a.getRunnersSpawns();

                if (args[2].equalsIgnoreCase("add")) {
                    runnersSpawns.add(l);
                    a.setRunnersSpawns(runnersSpawns);

                    List<String> runnersSpawnsString = new ArrayList<String>();
                    for (Location loc : runnersSpawns) {
                        runnersSpawnsString.add((LocUtils.locToString(loc)));
                    }

                    fc.set("Arena.Spawns.RunnersSpawns", runnersSpawnsString);
                    debug.msg(p, "You have added a runner spawn");
                } else if (args[2].equalsIgnoreCase("remove")) {
                    if(runnersSpawns.size() > 0) {
                        runnersSpawns.remove(runnersSpawns.size() - 1);
                        a.setRunnersSpawns(runnersSpawns);

                        List<String> runnersSpawnsString = new ArrayList<String>();
                        for (Location loc : runnersSpawns) {
                            runnersSpawnsString.add((LocUtils.locToString(loc)));
                        }

                        fc.set("Arena.Spawns.RunnersSpawns", runnersSpawnsString);
                        debug.msg(p, "You have removed the last runner spawn");
                    }
                }
            } else if (args[1].equalsIgnoreCase("sign")) {
                if (args[2].equalsIgnoreCase("add")) {
                    int i = 10;
                    Sign sign = null;

                    if (p.getTargetBlockExact(i).getState() instanceof Sign) {
                        sign = (Sign) p.getTargetBlockExact(i).getState();
                        sign.setLine(0, ChatColor.BLACK + "[" + ChatColor.BLUE + "Cops" + ChatColor.BLACK + "N" + ChatColor.RED + "Runners" + ChatColor.BLACK + "]");
                        sign.setLine(1, a.getName());
                        sign.setLine(2, "0/" + a.getMaxPlayer());
                        sign.update();

                        List<Sign> signs = a.getSigns();
                        signs.add(sign);
                        a.setSigns(signs);

                        List<String> signsString = new ArrayList<String>();
                        for (Sign sign1 : signs) {
                            signsString.add(LocUtils.locToString(sign1.getLocation()));
                        }

                        fc.set("Arena.Sign.Loc", signsString);
                        debug.msg(p, "You have added a sign");
                    } else {
                        debug.msg(p, "We didn't found a sign where you where looking");
                    }

                } else if (args[2].equalsIgnoreCase("remove")) {
                    List<Sign> signs = a.getSigns();

                    if(signs.size() > 0) {
                        signs.remove(signs.size() - 1);
                        a.setSigns(signs);

                        List<String> signsString = new ArrayList<String>();
                        for (Sign sign1 : signs) {
                            signsString.add(LocUtils.locToString(sign1.getLocation()));
                        }

                        fc.set("Arena.Sign.Loc", signsString);
                        debug.msg(p, "You have deleted the last sign");
                    }
                }
            }
        }

        FileManager.save(file, fc);
        return true;
    }
}
