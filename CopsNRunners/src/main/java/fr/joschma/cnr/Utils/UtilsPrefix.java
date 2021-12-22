package fr.joschma.cnr.Utils;

import fr.joschma.cnr.CopsNRunners;
import net.md_5.bungee.api.ChatColor;

public class UtilsPrefix {

	CopsNRunners pl;

	public UtilsPrefix(CopsNRunners pl) {
		super();
		this.pl = pl;
	}

	public String getPrefix() {
		String prefix = pl.getConfig().getString("Prefix");

		if (prefix == null) {
			setPrefix(ChatColor.GOLD + "[BP]");
		}

		return prefix;
	}

	public void setPrefix(String name) {
		pl.getConfig().set("Prefix", name);
		pl.saveConfig();
	}
}
