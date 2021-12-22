package fr.joschma.cnr.Messages;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.joschma.cnr.Manager.FileManager;

public class Language {

	File file = FileManager.load("Language");
	YamlConfiguration fc = FileManager.load(file);

	public enum MSG {
		titleYouAreCopBoss(fc().getString("titleYouAreCopBoss")),
		doNotForgetToPutDownThePrison(fc().getString("doNotForgetToPutDownThePrison")),
		noPrison(fc().getString("noPrison")), youAreNotAllowedToSendMsg(fc().getString("youAreNotAllowedToSendMsg")),
		youCanNotLeaveThePrison(fc().getString("youCanNotLeaveThePrison")), TpIn(fc().getString("TpIn")),
		EndIn(fc().getString("EndIn")), GameStartIn(fc().getString("GameStartIn")),
		stopSneak(fc().getString("stopSneak")), joinGame(fc().getString("joinGame")),
		waitingForPlayers(fc().getString("waitingForPlayers")),
		youHaveLeftTheGame(fc().getString("youHaveLeftTheGame")), hasLeftTheGame(fc().getString("hasLeftTheGame")),
		bossCopHasLeft(fc().getString("bossCopHasLeft"));

		String msg;

		MSG(String msg) {
			this.msg = msg;
		}

		public String msg() {
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			return msg;
		}

		public static YamlConfiguration fc() {
			return FileManager.load(FileManager.load("Language"));
		}
	}

	public static String parse(MSG msg) {
		return msg.msg();
	}
}
