package mcu.pl.BENCLABSTER.sw.Events;

import mcu.pl.BENCLABSTER.sw.Game;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveArenaEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player player;
  private Game game;

  public PlayerLeaveArenaEvent(Player p, Game g)
  {
    this.player = p;
    this.game = g;
  }

  public Player getPlayer() {
    return this.player;
  }

  public Game getGame() {
    return this.game;
  }

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}