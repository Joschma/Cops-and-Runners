package fr.joschma.cnr.Arena.KeyMoments;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Messages.Debugger;
import fr.joschma.cnr.Messages.Language.MSG;

public class JoinArena {

    private static boolean canJoin(Arena a, Player p) {
        Debugger debug = a.getPl().getDebug();

        for (Arena as : a.getPl().getAm().getArenas()) {
            if (as.getPlayers().contains(p)) {
                debug.error(p, "You are already in game");
                return false;
            }
        }

        if (a.isFinished()) {
            if (a.getPlayers().size() <= a.getMaxPlayer()) {
                if (!a.getState().equals(StateArena.INGAME) && !a.getState().equals(StateArena.CLEARING)) {
                    if (a.getLobbySpawn() != null) {
                        if (a.getEndSpawn() != null) {
                            if (a.getParticule() != null) {
                                List<Particle> particules = Arrays.asList(Particle.values());
                                if (particules.contains(Particle.valueOf(a.getParticule()))) {
                                    if (a.getAppreciateTime() >= 0) {
                                        if (a.getCopsSpawns().size() > 0) {
                                            if (a.getRunnersSpawns().size() > 0) {
                                                if (a.getFile() != null) {
                                                    if (a.getGameTime() > 0) {
                                                        if (a.getGetDistance() > 0) {
                                                            if (a.getLobbyWaitTime() >= 0) {
                                                                return true;
                                                            } else {
                                                                debug.error(p, "The lobby wait time is negative");
                                                                return false;
                                                            }
                                                        } else {
                                                            debug.error(p, "The distance is negative");
                                                            return false;
                                                        }
                                                    } else {
                                                        debug.error(p, "The game time is negative");
                                                        return false;
                                                    }
                                                } else {
                                                    debug.error(p, "There is no file for the arena");
                                                    return false;
                                                }
                                            } else {
                                                debug.error(p, "There is no runners spawn");
                                                return false;
                                            }
                                        } else {
                                            debug.error(p, "There is no cops spawn");
                                            return false;
                                        }
                                    } else {
                                        debug.error(p, "The appreciate time is negative");
                                        return false;
                                    }
                                } else {
                                    debug.error(p, "The particle does not exists");
                                    return false;
                                }
                            } else {
                                debug.error(p, "There is no particle");
                                return false;
                            }
                        } else {
                            debug.error(p, "There is no end spawn");
                            return false;
                        }
                    } else {
                        debug.error(p, "There is no lobby spawn");
                        return false;
                    }
                } else {
                    debug.error(p, "The game already started");
                    return false;
                }
            } else {
                debug.error(p, "The number min of player is greater then the number max of player");
                return false;
            }
        } else {
            debug.error(p, "The arena is not finished");
            return false;
        }
    }

    public static void join(Arena a, Player p) {
        Debugger debug = a.getPl().getDebug();

        if (canJoin(a, p)) {
            p.teleport(a.getLobbySpawn());
            a.getPlayers().add(p);
            String msg = MSG.joinGame.msg().replace("%player%", p.getName()).replace("%player_number%",
                    String.valueOf(a.getPlayers().size()));
            debug.msg(p, msg);

            Bukkit.getScheduler().runTaskLater(CopsNRunners.getPl(), new Runnable() {
                public void run() {
                    giveStuff(p);
                }
            }, 20L);

            a.getSignFile().updateSign();

            if (a.getState().equals(StateArena.CLEARED)) {
                if (a.getPlayers().size() >= a.getMinPlayer()) {
                    a.setState(StateArena.WATTING);
                    a.getLcd().startCountDown();
                } else {
                    debug.msg(p, MSG.waitingForPlayers.msg());
                }
            }
        }
    }

    private static void giveStuff(Player p) {
        p.getInventory().clear();
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setGameMode(GameMode.SURVIVAL);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        ItemStack leave = new ItemStack(Material.SLIME_BALL);
        ItemMeta leaveM = leave.getItemMeta();
        leaveM.setDisplayName(ChatColor.RED + "Leave");
        leave.setItemMeta(leaveM);

        p.getInventory().setItem(7, creatBook());
        p.getInventory().setItem(8, leave);
    }

    private static ItemStack creatBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookM = (BookMeta) book.getItemMeta();
        bookM.setTitle(ChatColor.DARK_RED + "Rules");
        bookM.setDisplayName("Rules");
        bookM.setAuthor("Jesus");

        bookM.addPage("§4Rules for cops and runners game :" + " \n\n§0The players are split in 2 teams:"
                + " \n§1The cops§0and §6the runners§0.");
        bookM.addPage("§1The cops team :" + " \n\n§0A boss cop will be designated at the beginning,"
                + " \nhe can put the prison where he wants.");
        bookM.addPage("§6The goal of the runners: " + "\n\n§0Escape cops until the time hit 0."
                + " \nThey can help their friend in jail by hiting them with their crowbar.");
        bookM.addPage("§aEnd of the game:"
                + " \n\n§0A game end's when the timer end or when all the runners are in prison. In the first case the runners win, in the second case the cops win");

        book.setItemMeta(bookM);
        return book;
    }
}
