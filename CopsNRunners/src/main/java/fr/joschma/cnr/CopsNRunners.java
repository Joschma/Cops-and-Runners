package fr.joschma.cnr;

import java.io.File;

import fr.joschma.cnr.Listener.*;
import org.bukkit.plugin.java.JavaPlugin;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.InitializeArena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Command.Commands;
import fr.joschma.cnr.GUI.ArenaGui;
import fr.joschma.cnr.Manager.ArenaManager;
import fr.joschma.cnr.Messages.Debugger;
import fr.joschma.cnr.TabFinisher.TabFinisher;
import fr.joschma.cnr.Utils.UtilsPrefix;

public class CopsNRunners extends JavaPlugin {

	// TODO add power ups

	static CopsNRunners pl;
	UtilsPrefix utilsPrefix = new UtilsPrefix(this);
	Debugger debug = new Debugger(this);
	InitializeArena initializeArena = new InitializeArena(this);
	ArenaManager am = new ArenaManager();
	ArenaGui arenaGui = new ArenaGui(this);

	@Override
	public void onEnable() {
		pl = this;

		saveDefaultConfig();
		if(!new File(getDataFolder(), "Language.yml").exists())
			saveResource("Language.yml", false);

		getServer().getPluginManager().registerEvents(new onPlayerSneak(this), this);
		getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerBreakBlock(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerCommand(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerDisconect(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerDropItem(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerMoveListener(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerPlaceBlock(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerTakeDamage(this), this);
		getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		getServer().getPluginManager().registerEvents(new onPlayerClickInventory(this), this);

		getCommand("cnr").setExecutor(new Commands(this));
		getCommand("cnr").setTabCompleter(new TabFinisher(this));

		initializeArenaNames();
		for(String arNames : am.getArenaNames()) {
			initializeArena.initializeArena(arNames);
		}

		super.onEnable();
	}

	@Override
	public void onDisable() {
		for (Arena a : am.getArenas()) {
			if (a.getState() != StateArena.CLEARED) {
				a.getFinishGame().forceStop();
			}
		}

		for (Arena a : am.getArenas()) {
			initializeArena.saveArena(a);
		}
		super.onDisable();
	}

	void initializeArenaNames() {
		File folder = new File(getDataFolder() + File.separator + "Arenas");
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					am.addArenaNames(listOfFiles[i].getName().replace(".yml", ""));
				}
			}
		}
	}

	public static CopsNRunners getPl() {
		return pl;
	}

	public UtilsPrefix getUtilsPrefix() {
		return utilsPrefix;
	}

	public Debugger getDebug() {
		return debug;
	}

	public InitializeArena getInitializeArena() {
		return initializeArena;
	}

	public ArenaManager getAm() {
		return am;
	}

	public ArenaGui getArenaGui() {
		return arenaGui;
	}
	
}
