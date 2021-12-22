package fr.joschma.cnr.Messages;

import org.bukkit.entity.Player;

import fr.joschma.cnr.CopsNRunners;
import net.md_5.bungee.api.ChatColor;

public class Debugger {
	
	CopsNRunners pl;
	
	public Debugger(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	public void sysout(String msg) {
		System.out.println(pl.getUtilsPrefix().getPrefix() + " : " + ChatColor.RED +  msg);
	}
	
	public void sysout(Player p, String msg) {
		p.sendMessage(pl.getUtilsPrefix().getPrefix() + " : " + ChatColor.RED +  msg);
		System.out.println(pl.getUtilsPrefix().getPrefix() + " : " + ChatColor.RED +  msg);
	}
	
	public void error(Player p, String msg) {
		p.sendMessage(pl.getUtilsPrefix().getPrefix() + " : " + ChatColor.RED + msg);
	}
	
	public void msg(Player p, String msg) {
		p.sendMessage(pl.getUtilsPrefix().getPrefix() + " : " + ChatColor.GRAY + msg);
	}
}
