package mcu.pl.BENCLABSTER.sw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mcu.pl.BENCLABSTER.sw.Configs.ConfigManager;
import mcu.pl.BENCLABSTER.sw.Utils.WorldEditUtility;

import org.bukkit.entity.Player;

public class GameManager {

	static GameManager instance = new GameManager();

	private ArrayList<Game> games = new ArrayList<Game>();
	private HashMap<String, Integer> playerGame = new HashMap<String, Integer>();
	private HashMap<String, Integer> confirm = new HashMap<String, Integer>();
	private HashMap<String, Integer> editing = new HashMap<String, Integer>();

	public static GameManager getInstance() {

		return instance;
	}

	public void setUp(){

		this.games.clear();

		for(int x = -1; x < getArenaAmount(); x++){

			if(Main.getInstance().Arena.contains(Integer.toString(x))){

				if(Main.getInstance().Arena.getBoolean(Integer.toString(x) + ".Enabled")){

					games.add(new Game(x));
				}
			}
		}
	}

	public int createGame(Player p){

		int amount = this.getArenaAmount();

		int newGame = amount + 1;

		WorldEditUtility.getInstance().saveArena(p, newGame);

		Main.getInstance().Arena.set("Arena." + newGame + ".Enabled", true);

		Main.getInstance().Arena.set("Amount", newGame);

		ConfigManager.getInstance().saveYamls();

		games.add(new Game(newGame, true));

		return newGame;
	}

	public void overrideArena(Player p, Integer arena){

		Main.getInstance().Arena.set(Integer.toString(arena), null);
		Main.getInstance().Chest.set(Integer.toString(arena), null);
		Main.getInstance().Arena.set("Arena." + arena + ".Enabled", true);
		ConfigManager.getInstance().saveYamls();

		WorldEditUtility.getInstance().overrideSave(p, arena);

	}

	public ArrayList<Game> getGames() {
		return this.games;
	}

	public Game getPlayerGame(Player p){

		if(playerGame.get(p.getName()) != null){

			return this.getGames().get(playerGame.get(p.getName()));
		}

		return null;
	}

	public boolean isPlayerInGame(Player p){

		if(this.playerGame.containsKey(p.getName())){

			if(this.playerGame.get(p.getName()) != null){
				return true;
			}
		}

		return false;
	}

	public void setPlayerGame(Player p, Game g){

		playerGame.put(p.getName(), g.getGameID());
	}

	public int getArenaAmount(){
		return Main.getInstance().Arena.getInt("Amount");
	}

	public boolean leaveGame(Player p){

		if(isPlayerInGame(p)){

			getPlayerGame(p).removeFromGame(p);
			playerGame.put(p.getName(), null);

			return true;
		}

		return false;
	}

	public String listGames(){

		List<String> strings = new ArrayList<String>();

		for(int x = 0; x < games.size(); x++){

			String s = Integer.toString(games.get(x).getGameID());

			strings.add(s);
		}

		return strings.toString().replace("[", "").replace("]", "");
	}

	public void disableGame(int game){

	}

	public void enableGame(int game){

	}

	public boolean isEnabled(int game){

		if(Main.getInstance().Arena.getBoolean(game + ".Enabled")){
			return true;
		}

		return false;
	}

	public HashMap<String, Integer> getConfirming(){
		return this.confirm;
	}

	public HashMap<String, Integer> getEditing(){
		return this.editing;
	}

	public int getPlayerEditing(Player p){
		return getEditing().get(p.getName());
	}

	public void addEditor(Player p, Integer game){
		getEditing().put(p.getName(), game);
	}

	public boolean isEditing(Player p){
		if(getEditing().get(p.getName()) != null){
			return true;
		}
		return false;
	}

	public void removeEditor(Player p){
		getEditing().put(p.getName(), null);
	}

	public boolean isInteger(String s) {
	    try { 

	        Integer.parseInt(s);

	    } catch(NumberFormatException e) { 
	        return false;
	    }
	    return true;
	}

	public boolean checkGameByID(int id){

		for(Game g : getGames()){

			if(g.getGameID() == id){
				return true;
			}
		}
		return false;
	}

	public Game getGameByID(int id){

		for(Game g : getGames()){

			if(g.getGameID() == id){
				return g;
			}
		}
		return null;
	}
}