package mcu.pl.BENCLABSTER.sw;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import mcu.pl.BENCLABSTER.sw.Commands.SW;
import mcu.pl.BENCLABSTER.sw.Configs.ConfigManager;
import mcu.pl.BENCLABSTER.sw.Listeners.PlayerDeath;
import mcu.pl.BENCLABSTER.sw.Listeners.PlayerLeave;
import mcu.pl.BENCLABSTER.sw.Utils.WorldEditUtility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener{

	private static Main instance;

	public File configFile;
	public FileConfiguration Config;

	public File arenaFile;
	public FileConfiguration Arena;

	public File invFile;
	public FileConfiguration Inv;

	public File chestFile;
	public FileConfiguration Chest;

	private PluginManager pm = Bukkit.getServer().getPluginManager();

	private Logger log = Bukkit.getLogger();

	public static Main getInstance(){
		return instance;
	}

	@Override
	public void onEnable(){

		instance = this;

		configFile = new File(getDataFolder(), "config.yml");
		arenaFile = new File(getDataFolder(), "arena.yml");
		invFile = new File(getDataFolder(), "inventorys.yml");
		chestFile = new File(getDataFolder(), "chests.yml");

		try {

			ConfigManager.getInstance().firstRun();

		} catch (Exception e) {

			e.printStackTrace();
		}

		this.Config = new YamlConfiguration();
		this.Arena = new YamlConfiguration();
		this.Inv = new YamlConfiguration();
		this.Chest = new YamlConfiguration();
		ConfigManager.getInstance().loadYamls();
		ConfigManager.getInstance().saveYamls();

		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(this, this);

		getCommand("skyblockw").setExecutor(new SW());

		setUp();
       
	}

	@Override
	public void onDisable(){


	}    

	public Location getLobby(){

		if(this.Config.contains("Lobby")){

			World world = Bukkit.getServer().getWorld(this.Config.getString("Lobby.World"));

			int X = this.Config.getInt("Lobby.X");
			int Y = this.Config.getInt("Lobby.Y");
			int Z = this.Config.getInt("Lobby.Z");
			float Yaw = this.Config.getLong("Lobby.Yaw");
			float Pitch = this.Config.getLong("Lobby.Pitch");

			Location lobby = new Location(world, X, Y, Z, Yaw, Pitch);

			return lobby;
		}
		log.log(Level.SEVERE, "Skyblock Wars lobby not found.");
		return null;

	}

	public void setUp(){

		new BukkitRunnable(){

			  @Override
			  public void run(){

				  GameManager.getInstance().setUp();
				  WorldEditUtility.getInstance().regenAllIslands();

			  }
			}.run();

	}

	public void setLobby(Player p){

		this.Config.set("Lobby.X", p.getLocation().getBlockX());

		this.Config.set("Lobby.Y", p.getLocation().getBlockY());

		this.Config.set("Lobby.Z", p.getLocation().getBlockZ());

		this.Config.set("Lobby.YAW", p.getLocation().getPitch());

		this.Config.set("Lobby.PITCH", p.getLocation().getYaw());

		this.Config.set("Lobby.WORLD", p.getLocation().getWorld().getName());

		ConfigManager.getInstance().saveYamls();

	}

	public boolean teleportToLobby(Player p){

		if(!this.Config.contains("Lobby")){
			return false;
		}

		Location location = new Location(Bukkit.getServer().getWorld(Main.getInstance().Config.getString("Lobby.WORLD")), Main.getInstance().Config.getDouble("Lobby.X"), Main.getInstance().Config.getDouble("Lobby.Y"), Main.getInstance().Config.getDouble("Lobby.Z"), Main.getInstance().Config.getLong("Lobby.YAW"), Main.getInstance().Config.getLong("Lobby.YAW"));

		p.teleport(location);

		return true;
		
	}
	
	  Main plugin;

	  public Main()
	  {
	    this.plugin = this;
	  }
	
	//ArenaMenu Manager
	@EventHandler
	public void BookClickEvent(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(p.getItemInHand().getItemMeta().getDisplayName().contains("Arenas")) {
		        IconMenu menu = new IconMenu("Arenas", 9, new IconMenu.OptionClickEventHandler() {
		            @Override
		            public void onOptionClick(IconMenu.OptionClickEvent event) {
		                event.getPlayer().sendMessage(ChatColor.GRAY + "You have chosen " + ChatColor.GOLD + ChatColor.stripColor(event.getName()));
		                event.setWillClose(true);
		                if(event.getName().contains("1")) {
		                	p.performCommand("sw join 1");
		                }
		                if(event.getName().contains("2")) {
		                	p.performCommand("sw join 2");
		                }
		                if(event.getName().contains("3")) {
		                	p.performCommand("sw join 3");
		                }
		                if(event.getName().contains("4")) {
		                	p.performCommand("sw join 4");
		                }
		                if(event.getName().contains("5")) {
		                	p.performCommand("sw join 5");
		                }
		                if(event.getName().contains("6")) {
		                	p.performCommand("sw join 6");
		                }
		                if(event.getName().contains("7")) {
		                	p.performCommand("sw join 7");
		                }
		            }
		        }, plugin)
		        .setOption(1, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 1!")
		        .setOption(2, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 2!")
		        .setOption(3, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 3!")
		        .setOption(4, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 4!")
		        .setOption(5, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 5!")
		        .setOption(6, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 6!")
		        .setOption(7, new ItemStack(Material.GRASS, 1), ChatColor.GREEN + "Arena 7!");
		        menu.open(p);
			}
		}
	}
}