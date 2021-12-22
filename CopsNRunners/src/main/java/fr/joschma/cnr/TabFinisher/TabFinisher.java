package fr.joschma.cnr.TabFinisher;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;

public class TabFinisher implements TabCompleter {

    CopsNRunners pl;

    public TabFinisher(CopsNRunners pl) {
        super();
        this.pl = pl;
    }

    List<String> arguments = new ArrayList<String>();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            arguments.clear();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("CNR.admin") || p.isOp()) {
                    if (args.length == 1) {
                        arguments.add("create");
                        arguments.add("help");
                        arguments.add("remove");
                        arguments.add("list");
                        arguments.add("reload");
                        arguments.add("forcestart");

                        for (String name : pl.getAm().getArenaNames()) {
                            arguments.add(name);
                        }

                        arguments.add("leave");

                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("help")) {
                            return null;
                        } else if (pl.getAm().getArenaNames().contains(args[0])) {
                            arguments.add("copsSpawn");
                            arguments.add("runnersSpawn");
                            arguments.add("lobbySpawn");
                            arguments.add("endSpawn");
                            arguments.add("sign");
                            arguments.add("finished");
                            arguments.add("gui");
                        } else if (args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("remove")) {
                            if (args.length == 2) {
                                for (String name : pl.getAm().getArenaNames()) {
                                    arguments.add("" + name);
                                }
                            }
                        }
                    } else if (args.length == 3) {
                        if (args[1].equalsIgnoreCase("copsSpawn") || args[1].equalsIgnoreCase("runnersSpawn")
                                || args[1].equalsIgnoreCase("sign")) {
                            arguments.add("add");
                            arguments.add("remove");
                        }
                    }
                }

                if (args.length == 1) {
                    arguments.add("join");
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("join")) {
                        for (String name : pl.getAm().getArenaNames()) {
                            arguments.add("" + name);
                        }
                    }
                }

                List<String> results = new ArrayList<String>();
                for (String a : arguments) {
                    if (a.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                        results.add(a);
                    }
                }

                return results;
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
