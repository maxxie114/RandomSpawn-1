package vp.sen.rs;

import cn.nukkit.block.BlockID;
import cn.nukkit.event.*;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.level.*;
import cn.nukkit.Server;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.*;

public class Main extends PluginBase implements cn.nukkit.event.Listener {
  private int max = 1024;
  private int min = 128;
  
  @Override
  public void onEnable() {
    this.saveResource("config.yml",false);
    Config config = this.getConfig();
    this.max = config.getInt("max",1024);
    this.min = config.getInt("min",128);
    this.getLogger().info("§dRandom spawn by §bNameDoesCode.");
    this.getServer().getPluginManager().registerEvents(this, this);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onRespawn(PlayerRespawnEvent e) {
	  Position pos = e.getRespawnPosition();
	  Level deflvl = this.getServer().getDefaultLevel();
    if(deflvl.getBlock(Long.valueOf(Math.round(pos.getX())).intValue(), Long.valueOf(Math.round(pos.getY())).intValue(), Long.valueOf(Math.round(pos.getZ())).intValue()).getId() != BlockID.BED_BLOCK && e.getPlayer().getSpawn() == pos) {
      boolean found = false;
      while(!found) {
	    	int x = (int) Math.round(Math.random()*(max-min))+min;
	    	int z = (int) Math.round(Math.random()*(max-min))+min;

	    	if(Math.round(Math.random()*2) == 1) x = x-(x*2);
    		if(Math.round(Math.random()*2) == 1) z = z-(z*2);

        FullChunk chunk = deflvl.getChunk((int) Math.floor(x/16),(int) Math.floor(z/16));
        if(chunk == null || !(chunk.isGenerated() || chunk.isPopulated())) deflvl.generateChunk((int) Math.floor(x/16),(int) Math.floor(z/16), true);
        int y = deflvl.getHighestBlockAt(x,z);
        if(deflvl.getBlock(x,y,z).isSolid()) {
          e.setRespawnPosition(new Position(x+0.5,y+1,z+0.5,deflvl));
          found = true;
        }
      }
    }
	}

  @Override
  public void onDisable() {
    this.getLogger().info("§4Plugin Disabled!"); 
  }
}