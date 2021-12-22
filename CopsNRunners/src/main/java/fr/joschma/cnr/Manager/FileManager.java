package fr.joschma.cnr.Manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.joschma.cnr.CopsNRunners;

public class FileManager {
	
	static CopsNRunners pl = CopsNRunners.getPl();

	public static void reload(File file) {
		save(file, load(file));
	}

	public static YamlConfiguration load(File file) {
		YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);

		return fc;
	}

	public static File load(String name) {
		File file = new File(pl.getDataFolder(), name + ".yml");

		return file;
	}
	
	public static File loadArenaFile(String name) {
		File file = new File(pl.getDataFolder() + File.separator + "Arenas", name + ".yml");

		return file;
	}

	public static void save(File file, YamlConfiguration fc) {
		try {
			fc.save(file);
		} catch (IOException e) {
			pl.getDebug().sysout(ChatColor.RED + "Couldn't save " + file.getName());
			e.printStackTrace();
		}
	}

	public static File createFile(String name) {
		File file = new File(pl.getDataFolder(), name + ".yml");

		try {
			file.createNewFile();
		} catch (IOException e) {
			pl.getDebug().sysout(ChatColor.RED + "Coudn't create " + file.getName());
			e.printStackTrace();
		}

		return file;
	}

	public static File createArenaFile(String name) {
		File file = new File(pl.getDataFolder() + File.separator + "Arenas", name + ".yml");
		
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				pl.getDebug().sysout(ChatColor.RED + "Coudn't create " + file.getName());
				e.printStackTrace();
			}
		}

		return file;
	}
}
