package fr.joschma.cnr.Arena.KeyMoments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.messages.Titles;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.joschma.cnr.Arena.Arena;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Messages.Language.MSG;
import net.md_5.bungee.api.ChatColor;

public class StartGame {

	Arena a;

	public StartGame(Arena a) {
		this.a = a;
	}

	public void startGame() {
		a.setState(StateArena.INGAME);
		giveTeam(a);
		giveStuff(a);
		teleportPlayers(a);
		a.getSignFile().updateSign();
		a.getGcd().startCountDown();
	}

	private void giveStuff(Arena a) {
		ItemStack barre = new ItemStack(XMaterial.IRON_BARS.parseMaterial());
		ItemStack bat = new ItemStack(XMaterial.BLAZE_ROD.parseMaterial());
		ItemStack Bchestplate = new ItemStack(XMaterial.LEATHER_CHESTPLATE.parseMaterial());
		ItemStack Ochestplate = new ItemStack(XMaterial.LEATHER_CHESTPLATE.parseMaterial());
		ItemMeta barreM = barre.getItemMeta();
		ItemMeta batM = bat.getItemMeta();
		LeatherArmorMeta BchestplateM = (LeatherArmorMeta) Bchestplate.getItemMeta();
		LeatherArmorMeta OchestplateM = (LeatherArmorMeta) Ochestplate.getItemMeta();
		barreM.setDisplayName("Prison");
		batM.setDisplayName("Taser");
		BchestplateM.setColor(Color.BLUE);
		OchestplateM.setColor(Color.ORANGE);
		barre.setItemMeta(barreM);
		bat.setItemMeta(batM);
		Bchestplate.setItemMeta(BchestplateM);
		Ochestplate.setItemMeta(OchestplateM);
		ItemStack leave = new ItemStack(XMaterial.SLIME_BALL.parseMaterial());
		ItemMeta leaveM = leave.getItemMeta();
		leaveM.setDisplayName(ChatColor.RED + "Leave");
		leave.setItemMeta(leaveM);

		for (Player p : a.getPlayers()) {
			p.getInventory().clear();
			p.setFlying(false);
			p.setAllowFlight(false);
		}

		for (Player ru : a.getRunners()) {
			for (int i = 0; i < 8; i++) {
				ru.getInventory().setItem(i, new ItemStack(XMaterial.SHEARS.parseMaterial()));
			}
			
			ru.getInventory().setChestplate(Ochestplate);
			ru.getInventory().setItem(8, leave);
		}

		for (Player co : a.getCops()) {
			for (int i = 0; i < 8; i++) {
				co.getInventory().setItem(i, bat);
			}
			
			co.getInventory().setChestplate(Bchestplate);
			co.getInventory().setItem(8, leave);
		}

		a.getCopBoss().getInventory().setItem(4, barre);
	}

	public ItemStack createIT(Material ma, String displayName, String... lore) {
		ItemStack it = new ItemStack(ma);
		ItemMeta im = it.getItemMeta();
		if (displayName != null) {
			im.setDisplayName(displayName);
		}
		if (lore != null) {
			im.setLore(Arrays.asList(lore));
		}
		it.setItemMeta(im);
		return it;
	}

	private void teleportPlayers(Arena a) {
		Random rand = new Random();

		for (Player cops : a.getCops()) {
			int rand_int = rand.nextInt(a.getCopsSpawns().size());
			cops.teleport(a.getCopsSpawns().get(rand_int));
		}

		for (Player runner : a.getRunners()) {
			int rand_int = rand.nextInt(a.getRunnersSpawns().size());
			runner.teleport(a.getRunnersSpawns().get(rand_int));
		}
	}

	public void giveTeam(Arena a) {
		ArrayList<Player> players = new ArrayList<Player>();
		players.addAll(a.getPlayers());
		ArrayList<Player> cops = new ArrayList<Player>();

		if (players.size() < 1) {
			a.getFinishGame().forceStop();
			return;
		}

		int randPlayer = generateRandomInt(players.size());

		for (int i = 0; i < 10; i++){
			players.get(randPlayer).sendMessage("" + players.get(generateRandomInt(players.size())).getName());
		}

		players.get(randPlayer).sendMessage(randPlayer + " rand numb " + (players.size()));

		cops.add(players.get(randPlayer));
		a.setCopBoss(players.get(randPlayer));
		players.remove(randPlayer);

		if (a.getPlayers().size() > 5) {
			randPlayer = generateRandomInt(players.size());
			cops.add(players.get(randPlayer));
			players.remove(randPlayer);
		}
		if (a.getPlayers().size() > 7) {
			randPlayer = generateRandomInt(players.size());
			cops.add(players.get(randPlayer));
			players.remove(randPlayer);
		}

		a.getRunners().addAll(players);
		a.getCops().addAll(cops);

		for (Player r : players) {
			r.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
			Titles.sendTitle(r, 1, 20 * 4, 1, ChatColor.GOLD + "Runner", "");
		}

		for (Player c : cops) {
			c.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));
			if (c == a.getCopBoss()) {
				Titles.sendTitle(c, 1, 20 * 4, 1, MSG.titleYouAreCopBoss.msg(), MSG.doNotForgetToPutDownThePrison.msg());
				c.sendMessage(MSG.doNotForgetToPutDownThePrison.msg());

			} else {
				Titles.sendTitle(c, 1, 20 * 4, 1, ChatColor.BLUE + "Cop", "");
			}
		}
	}

	private int generateRandomInt(int size) {
		Random rand = new Random();
		return rand.nextInt(size);
	}
}
