package fr.joschma.cnr.Arena.Sign;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;

public class Signs {

	Arena a;

	public Signs(Arena a) {
		super();
		this.a = a;
	}

	public void updateSign() {
		for (Sign sign : a.getSigns()) {
			String lign2 = sign.getLine(2);
			if (a.getState().equals(StateArena.INGAME)) {
				if (!lign2.equalsIgnoreCase(ChatColor.RED + "In Game")) {
					sign.setLine(2, ChatColor.RED + "In Game");
					sign.update();
				}
			} else {
				String display = a.getPlayers().size() + "/" + a.getMaxPlayer();
				sign.setLine(2, display);
				sign.update();
			}
		}
	}
}
