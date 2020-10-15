package vp.sen.rs;

import cn.nukkit.block.BlockID;
import cn.nukkit.event.*;
import cn.nukkit.event.player.*;
import cn.nukkit.level.*;
import cn.nukkit.Server;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.*;
import java.util.*;

public class Main extends PluginBase implements cn.nukkit.event.Listener {
  private int max = 1024;
  private int min = 128;
  private boolean onRespawn = false;
  private boolean onJoin = false;
  private Timer timer = new Timer();
  
  @Override
  public void onEnable() {
    this.saveDefaultConfig();
    Config config = this.getConfig();
    this.max = config.getInt("max",1024);
    this.min = config.getInt("min",128);
    this.onRespawn = config.getBoolean("onRespawn",true);
    this.onJoin = config.getBoolean("onJoin",true);
    if(max < min || max < 0 || min < 0 || max == min) {
      this.max = 1024;
      this.min = 128;
    }
    this.getLogger().info("§dRandom spawn by §bNameDoesCode.");
    this.getServer().getPluginManager().registerEvents(this, this);
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
    switch(cmd.getName().toLowerCase()) {
      case "wild":
        if(!sender.isPlayer()) {
          sender.sendMessage("Console can not use this command.");
          return false;
        }
        Player target = (Player) sender;
        Position pos = findPos();
        Boolean op = false;
        if((sender.hasPermission("rs.command.wild.bypass") || sender.hasPermission("op"))) {
          if(!Arrays.asList(args).isEmpty()) {
            for(Player p : this.getServer().getOnlinePlayers().values()) {
              if(p.getName().toLowerCase().startsWith(String.join((String) " ",args).toLowerCase())) target = p;
            }
            sender.sendMessage("§dYou've randomly teleported §b"+target.getName()+" §dto §b"+pos.getFloorX()+", "+pos.getFloorY()+", "+pos.getFloorZ());
          }
        }
        target.sendMessage("§dYou've been teleported randomly.");
        target.teleport(pos);
      return true;
   	}
   	return false;
  }
  
  public Position findPos() {
    Level deflvl = this.getServer().getDefaultLevel();
    while(true) {
	   	int x = (int) Math.round(Math.random()*(max-min))+min;
	  	int z = (int) Math.round(Math.random()*(max-min))+min;

	   	if(Math.round(Math.random()*2) == 1) x = x-(x*2);
    	if(Math.round(Math.random()*2) == 1) z = z-(z*2);

      FullChunk chunk = deflvl.getChunk((int) Math.floor(x/16),(int) Math.floor(z/16));
      if(chunk == null || !(chunk.isGenerated() || chunk.isPopulated())) deflvl.generateChunk((int) Math.floor(x/16),(int) Math.floor(z/16), true);
      int y = deflvl.getHighestBlockAt(x,z);
      if(deflvl.getBlock(x,y,z).isSolid()) {
        return new Position(x+0.5,y+1,z+0.5,deflvl);
      }
    }
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onJoin(PlayerJoinEvent e) {
	  if(!onJoin) return;
	  if(!e.getPlayer().hasPlayedBefore()) {
	    e.getPlayer().setGamemode(3);
	    timer.schedule(new TimerTask() {
	      @Override
	      public void run() {
	        e.getPlayer().setGamemode(0);
	        e.getPlayer().teleport(findPos());
	      }
	    },2000);
	  }
	}

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onRespawn(PlayerRespawnEvent e) {
	  if(!onRespawn) return;
	  Position pos = e.getRespawnPosition();
	  Level deflvl = this.getServer().getDefaultLevel();
    if(deflvl.getBlock(Long.valueOf(Math.round(pos.getX())).intValue(), Long.valueOf(Math.round(pos.getY())).intValue(), Long.valueOf(Math.round(pos.getZ())).intValue()).getId() != BlockID.BED_BLOCK && e.getPlayer().getSpawn() == pos) {
      e.getPlayer().setSpawn(new Position(0,-1024,0,deflvl));
      e.setRespawnPosition(findPos());
    }
	}

  @Override
  public void onDisable() {
    this.getLogger().info("§4Plugin Disabled!"); 
  }
}