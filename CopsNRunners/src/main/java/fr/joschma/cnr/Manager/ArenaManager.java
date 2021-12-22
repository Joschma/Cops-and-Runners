package fr.joschma.cnr.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.joschma.cnr.Arena.Arena;

public class ArenaManager {

	List<Arena> arenas = new ArrayList<Arena>();
	
	List<String> arenaNames = new ArrayList<String>();

	public void addArenaListNFile(Arena a) {
		arenas.add(a);
		
		addArenaNames(a.getName());
	}
	
	public void rmvArenaListNFile(Arena a) {
		arenas.remove(a);
		
		rmvArenaNames(a.getName());
		a.getFile().delete();
	}
	
	public Arena getArena(String arena) {
		for (Arena a : getArenas()) {
			if (a.getName().equals(arena)) {
				return a;
			}
		}
		return null;
	}

	public Arena getArenaPlayer(Player p) {
		for (Arena a : arenas) {
			if (a.getPlayers().contains(p)) {
				return a;
			}
		}
		return null;
	}

	public void addArenaNames(String name) {
		if (!arenaNames.contains(name)) {
			arenaNames.add(name);
		}
	}
	
	public void rmvArenaNames(String name) {
		if (arenaNames.contains(name)) {
			arenaNames.remove(name);
		}
	}

	public List<String> getArenaNames() {
		return arenaNames;
	}
	
	public void setArenaNames(List<String> arenaNames) {
		this.arenaNames = arenaNames;
	}

	public List<Arena> getArenas() {
		return arenas;
	}
	
	public void addArena(Arena a) {
		arenas.add(a);
	}
	
	public void rmvArena(Arena a) {
		arenas.remove(a);
	}

	public void setArenas(List<Arena> arenas) {
		this.arenas = arenas;
	}
}
