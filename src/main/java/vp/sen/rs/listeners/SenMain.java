package vp.sen.rs.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.level.Position;
import cn.nukkit.level.Location;
import vp.sen.rs.Main;
import vp.sen.rs.utility.SenConfig;
import java.util.Map;
import java.util.HashMap;

public class SenMain implements Listener {
	public final Main plugin;
	public final SenConfig conf;
	public final Map<Integer,String>ms;
    public final int minRadius;
    public final int maxRadius;
	
	public SenMain(Main plugin, SenConfig conf) {
		this.plugin = plugin;
		this.conf = conf;
        this.minRadius = conf.getInt("minRadius");
        this.maxRadius = conf.getInt("maxRadius");
        this.ms = new HashMap<Integer,String>();
             ms.put(0,"");
             ms.put(6,"");
             ms.put(27,"");
             ms.put(28,"");
             ms.put(31,"");
             ms.put(32,"");
             ms.put(37,"");
             ms.put(38,"");
             ms.put(39,"");
             ms.put(40,"");
             ms.put(50,"");
             ms.put(55,"");
             ms.put(59,"");
             ms.put(63,"");
             ms.put(66,"");
             ms.put(68,"");
             ms.put(69,""); //Nice
             ms.put(70,"");
             ms.put(72,"");
             ms.put(75,"");
             ms.put(76,"");
             ms.put(77,"");
             ms.put(78,"");
             ms.put(83,"");
             ms.put(92,"");
             ms.put(93,"");
             ms.put(94,"");
             ms.put(96,"");
             ms.put(104,"");
             ms.put(105,"");
             ms.put(106,"");
             ms.put(115,"");
             ms.put(126,"");
             ms.put(131,"");
             ms.put(132,"");
             ms.put(141,"");
             ms.put(142,"");
             ms.put(143,"");
             ms.put(147,"");
             ms.put(148,"");
             ms.put(149,"");
             ms.put(150,"");
             ms.put(151,"");
             ms.put(167,"");
             ms.put(171,"");
             ms.put(175,"");
             ms.put(176,"");
             ms.put(177,"");
             ms.put(178,"");
             ms.put(199,"");
	}
  public Boolean safe(int id, int spec) {
  	 return (ms.containsKey(id) || id == spec);
  	}
  public void roll(Position pos, Player player) {
		boolean found = false;
		int y = 255;
		int x = Long.valueOf(Math.round(Math.random() *maxRadius)).intValue()+minRadius;
		int z = Long.valueOf(Math.round(Math.random() *maxRadius)).intValue()+minRadius;

		if(Math.round(Math.random()*2) == 1) x = x-(x*2);
		if(Math.round(Math.random()*2) == 1) z = z-(z*2);
		while(!found && y > 0) {
			if(pos.getLevel().getBlock(x,y,z).isSolid() && this.safe(pos.getLevel().getBlock(x,y+1,z).getId(),9) && this.safe(pos.getLevel().getBlock(x,y+2,z).getId(),0)) {
			  player.teleport(new Location((double) x + 0.5, (double) y + 1, (double) z + 0.5));
				found = true;
			}
			y--;
			if(y == 0 && !found) {
				this.roll(pos,player);
			}
		}
  }
	public void roll(Position pos, Player player, PlayerRespawnEvent e) {
		boolean found = false;
		int y = 255;
		int x = Long.valueOf(Math.round(Math.random() *maxRadius)).intValue()+minRadius;
		int z = Long.valueOf(Math.round(Math.random() *maxRadius)).intValue()+minRadius;

		if(Math.round(Math.random()*2) == 1) x = x-(x*2);
		if(Math.round(Math.random()*2) == 1) z = z-(z*2);
		while(!found && y > 0) {
			if(pos.getLevel().getBlock(x,y,z).isSolid() && this.safe(pos.getLevel().getBlock(x,y+1,z).getId(),9) && this.safe(pos.getLevel().getBlock(x,y+2,z).getId(),0)) {
			  e.setRespawnPosition(new Position((double) x + 0.5, (double) y + 1, (double) z + 0.5));
				found = true;
			}
			y--;
			if(y == 0 && !found) {
				this.roll(pos,player,e);
			}
		}
  }
  
 
/*  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onJoin(PlayerJoinEvent e) {
		 if(!e.getPlayer().hasPlayedBefore()) {
		 	
		 	 roll(new Position(128,0,128,plugin.getServer().getLevelByName("world")),e.getPlayer());
		 	 }
		}*/
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onRespawn(PlayerRespawnEvent e) {
		Position pos = e.getRespawnPosition();
		Position spos = pos.getLevel().getSpawnLocation();
	    e.getPlayer().setLevel(plugin.getServer().getDefaultLevel());	if(pos.getLevel().getBlock(Long.valueOf(Math.round(pos.getX())).intValue(), Long.valueOf(Math.round(pos.getY())).intValue(), Long.valueOf(Math.round(pos.getZ())).intValue()).getId() != BlockID.BED_BLOCK && e.getPlayer().getSpawn() == pos) roll(pos,e.getPlayer(),e);
	}
}