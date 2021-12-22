package fr.joschma.cnr.Arena;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.joschma.cnr.Arena.KeyMoments.*;
import fr.joschma.cnr.Arena.Timer.WinCountDown;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Sign.Signs;
import fr.joschma.cnr.Arena.States.StateArena;
import fr.joschma.cnr.Arena.Timer.GameTimer;
import fr.joschma.cnr.Arena.Timer.LobbyTimer;

public class Arena {

	final CopsNRunners pl;
	final String name;
	String particule;
	File file;
	boolean finished;
	int maxPlayer;
	int minPlayer;
	int lobbyWaitTime;
	int gameTime;
	double getDistance;
	int glowingTime;
	int appreciateTime;
	StateArena state;
	List<Player> players = new ArrayList<Player>();
	List<Player> runners = new ArrayList<Player>();
	List<Player> prisoned = new ArrayList<Player>();
	List<Player> cops = new ArrayList<Player>();
	List<Player> winners = new ArrayList<Player>();
	Player copBoss;
	List<Location> runnersSpawns;
	List<Location> copsSpawns;
	Location lobbySpawn;
	Location endSpawn;
	Location prison;
	List<Sign> signs;
	List<String> allowedCommands;

	StartGame startGame = new StartGame(this);
	GameTimer gcd = new GameTimer(this);
	LobbyTimer lcd = new LobbyTimer(this);
	FinishGame finishGame = new FinishGame(this);
	Signs signFile = new Signs(this);
	QuitGame quitGame = new QuitGame(this);
	CheckWin checkWin = new CheckWin(this);
	WinCountDown wcd = new WinCountDown(this);
	ClearArena ca = new ClearArena(this);

	ItemStack leave = new ItemStack(Material.SLIME_BALL);
	ItemMeta leaveM = leave.getItemMeta();

	public Arena(CopsNRunners pl, String name, String particule, File file, boolean finished, int maxPlayer,
			int minPlayer, int lobbyWaitTime, int gameTime, double getDistance, int glowingTime, int appreciateTime,
			StateArena state, List<Player> players, List<Player> runners, List<Player> prisoned, List<Player> cops,
			List<Player> winners, Player copBoss, List<Location> runnersSpawns, List<Location> copsSpawns,
			Location lobbySpawn, Location endSpawn, Location prison, List<Sign> signs, List<String> allowedCommands) {
		super();
		leaveM.setDisplayName(ChatColor.RED + "Leave");
		leave.setItemMeta(leaveM);
		this.pl = pl;
		this.name = name;
		this.particule = particule;
		this.file = file;
		this.finished = finished;
		this.maxPlayer = maxPlayer;
		this.minPlayer = minPlayer;
		this.lobbyWaitTime = lobbyWaitTime;
		this.gameTime = gameTime;
		this.getDistance = getDistance;
		this.glowingTime = glowingTime;
		this.appreciateTime = appreciateTime;
		this.state = state;
		this.players = players;
		this.runners = runners;
		this.prisoned = prisoned;
		this.cops = cops;
		this.winners = winners;
		this.copBoss = copBoss;
		this.runnersSpawns = runnersSpawns;
		this.copsSpawns = copsSpawns;
		this.lobbySpawn = lobbySpawn;
		this.endSpawn = endSpawn;
		this.prison = prison;
		this.signs = signs;
		this.allowedCommands = allowedCommands;
	}

	public void setInprison(Player p) {
		Arena a = pl.getAm().getArenaPlayer(p);
		p.teleport(a.getPrison());

		for (int i = 0; i < 8; i++) {
			p.getInventory().setItem(i, new ItemStack(Material.BARRIER));
		}

		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 9));
		a.getPrisoned().add(p);

		checkWin.checkWin(this);
	}

	public void setOutPrison(Player p) {
		Arena a = pl.getAm().getArenaPlayer(p);
		a.getPrisoned().remove(p);
		p.getInventory().clear();

		ItemStack Ochestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta OchestplateM = (LeatherArmorMeta) Ochestplate.getItemMeta();
		OchestplateM.setColor(Color.ORANGE);
		Ochestplate.setItemMeta(OchestplateM);

		p.getInventory().setChestplate(Ochestplate);

		for (int i = 0; i < 8; i++) {
			p.getInventory().setItem(i, new ItemStack(Material.SHEARS));
		}
		
		p.sendTitle("", ChatColor.GOLD + "Out of prison", 1, 20 * 2, 1);

		pl.getDebug().msg(p, "You are out of prison");
	}

	public CopsNRunners getPl() {
		return pl;
	}

	public String getName() {
		return name;
	}

	public String getParticule() {
		return particule.toUpperCase();
	}

	public void setParticule(String particule) {
		this.particule = particule.toUpperCase();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public int getMinPlayer() {
		return minPlayer;
	}

	public void setMinPlayer(int minPlayer) {
		this.minPlayer = minPlayer;
	}

	public double getGetDistance() {
		return getDistance;
	}

	public void setGetDistance(double getDistance) {
		this.getDistance = getDistance;
	}

	public StateArena getState() {
		return state;
	}

	public void setState(StateArena state) {
		this.state = state;
	}

	public List<Player> getPrisoned() {
		return prisoned;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Player> getRunners() {
		return runners;
	}

	public List<Player> getCops() {
		return cops;
	}

	public Location getLobbySpawn() {
		return lobbySpawn;
	}

	public void setLobbySpawn(Location lobbySpawn) {
		this.lobbySpawn = lobbySpawn;
	}

	public Location getEndSpawn() {
		return endSpawn;
	}

	public void setEndSpawn(Location endSpawn) {
		this.endSpawn = endSpawn;
	}

	public int getGlowingTime() {
		return glowingTime;
	}

	public void setGlowingTime(int glowingTime) {
		this.glowingTime = glowingTime;
	}

	public int getLobbyWaitTime() {
		return lobbyWaitTime;
	}

	public void setLobbyWaitTime(int lobbyWaitTime) {
		this.lobbyWaitTime = lobbyWaitTime;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public int getAppreciateTime() {
		return appreciateTime;
	}

	public void setAppreciateTime(int appreciateTime) {
		this.appreciateTime = appreciateTime;
	}

	public List<Player> getWinners() {
		return winners;
	}

	public void setWinners(List<Player> winners) {
		this.winners = winners;
	}

	public Player getCopBoss() {
		return copBoss;
	}

	public void setCopBoss(Player copBoss) {
		this.copBoss = copBoss;
	}

	public List<Location> getRunnersSpawns() {
		return runnersSpawns;
	}

	public void setRunnersSpawns(List<Location> runnersSpawns) {
		this.runnersSpawns = runnersSpawns;
	}

	public List<Location> getCopsSpawns() {
		return copsSpawns;
	}

	public void setCopsSpawns(List<Location> copsSpawns) {
		this.copsSpawns = copsSpawns;
	}

	public List<Sign> getSigns() {
		return signs;
	}

	public void setSigns(List<Sign> signs) {
		this.signs = signs;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setRunners(List<Player> runners) {
		this.runners = runners;
	}

	public void setPrisoned(List<Player> prisoned) {
		this.prisoned = prisoned;
	}

	public void setCops(List<Player> cops) {
		this.cops = cops;
	}

	public Location getPrison() {
		return prison;
	}

	public void setPrison(Location prison) {
		this.prison = prison;
	}

	public StartGame getStartGame() {
		return startGame;
	}

	public void setStartGame(StartGame startGame) {
		this.startGame = startGame;
	}

	public GameTimer getGcd() {
		return gcd;
	}

	public void setGcd(GameTimer gcd) {
		this.gcd = gcd;
	}

	public LobbyTimer getLcd() {
		return lcd;
	}

	public void setLcd(LobbyTimer lcd) {
		this.lcd = lcd;
	}

	public FinishGame getFinishGame() {
		return finishGame;
	}

	public void setFinishGame(FinishGame finishGame) {
		this.finishGame = finishGame;
	}

	public Signs getSignFile() {
		return signFile;
	}

	public void setSignFile(Signs signFile) {
		this.signFile = signFile;
	}

	public QuitGame getQuitGame() {
		return quitGame;
	}

	public void setQuitGame(QuitGame quitGame) {
		this.quitGame = quitGame;
	}

	public CheckWin getCheckWin() {
		return checkWin;
	}

	public void setCheckWin(CheckWin checkWin) {
		this.checkWin = checkWin;
	}

	public List<String> getAllowedCommands() {
		return allowedCommands;
	}

	public void setAllowedCommands(List<String> allowedCommands) {
		this.allowedCommands = allowedCommands;
	}

	public WinCountDown getWcd(){return wcd;}

	public ClearArena getCa(){return ca;}
}
