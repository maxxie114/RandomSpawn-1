package vp.sen.rs.listeners;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerRespawnEvent;
import vp.sen.rs.utility.SenConfig;
import vp.sen.rs.Main;
import java.lang.Math;

public class SenMain implements Listener {
	public final Main plugin;
	public final SenConfig conf;
	public Position npos;
	
	public SenMain(Main plugin) {
		this.plugin = plugin;
		this.npos = new Position(128,0,128);
		this.conf = new SenConfig(plugin);
	}

  public void roll(Position pos) {
  	Boolean found = false;
	 	int y = conf.getInt("maxY");
	 		int x = new Long(Math.round(Math.random()*conf.getInt("maxRadius"))).intValue();
	 		int z = new Long(Math.round(Math.random()*conf.getInt("maxRadius"))).intValue();
	 		if(Math.round(Math.random()*2) == 1) x = x-(x*2);
	 		if(Math.round(Math.random()*2) == 1) z = z-(z*2);
	 		while(!found && y != conf.getInt("minY")) {
	 			  if(pos.getLevel().getBlock(x,y,z).isSolid()) if(pos.getLevel().getBlock(x,y+1,z).getId() == 0 && pos.getLevel().getBlock(x,y+2,z).getId() == 0) {
	 			  	npos = new Position(Double.valueOf(x),Double.valueOf(y+1),Double.valueOf(z));
	 			  	found = true;
	 			  	}
	 			  	y--;
	 	    }
	 	   if(!found) roll(pos);
  	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onSpawn(PlayerRespawnEvent e) {
	 Position pos = e.getRespawnPosition();
	 Position spos = pos.getLevel().getSpawnLocation();
	 if(pos.getX() == spos.getX() && pos.getY() == spos.getY() && pos.getZ() == spos.getZ()) {
	 	 roll(pos);
	 	 e.setRespawnPosition(npos);
	 	}
	}
}