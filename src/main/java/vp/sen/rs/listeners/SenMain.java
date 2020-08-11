package vp.sen.rs.listeners;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.level.Location;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerTeleportEvent.TeleportCause;
import vp.sen.rs.utility.SenConfig;
import vp.sen.rs.Main;
import java.lang.Math;

public class SenMain implements Listener {
	public final Main plugin;
	public final SenConfig conf;
	
	public SenMain(Main plugin) {
		this.plugin = plugin;
		this.conf = new SenConfig(plugin);
	}

  public void roll(Position pos, Player player, PlayerRespawnEvent e) {
  	  Boolean found = false;
	 	  int y = 255;
	 		int x = new Long(Math.round(Math.random()*conf.getInt("maxRadius"))).intValue()+conf.getInt("minRadius");
	 		int z = new Long(Math.round(Math.random()*conf.getInt("maxRadius"))).intValue()+conf.getInt("minRadius");

	 		if(Math.round(Math.random()*2) == 1) x = x-(x*2);
	 		if(Math.round(Math.random()*2) == 1) z = z-(z*2);
	 		while(!found && y > 0) {
	 			  if(pos.getLevel().getBlock(x,y,z).isSolid() && pos.getLevel().getBlock(x,y+1,z).getId() == 0 && pos.getLevel().getBlock(x,y+2,z).getId() == 0) {
	 			  	e.setRespawnPosition(new Position(Double.valueOf(x),Double.valueOf(y+1),Double.valueOf(z)));
	 			  	found = true;
	 			  	}
	 			  	y--;
	 			  	if(y == 0 && !found){
	 			  	this.roll(pos,player,e);
	 			  	}
	 	    }
  	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onRespawn(PlayerRespawnEvent e) {
	 Position pos = e.getRespawnPosition();
	 Position spos = pos.getLevel().getSpawnLocation();
	 if(pos.getLevel().getBlock(new Long(Math.round(pos.getX())).intValue(), new Long(Math.round(pos.getY())).intValue(), new Long(Math.round(pos.getZ())).intValue()).getId() != BlockID.BED_BLOCK && e.getPlayer().getSpawn() == pos) roll(pos,e.getPlayer(),e);
  }
}