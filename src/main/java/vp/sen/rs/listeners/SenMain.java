package vp.sen.rs.listeners;

import cn.nukkit.Player;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.level.Position;
import vp.sen.rs.Main;
import vp.sen.rs.utility.SenConfig;

public class SenMain implements Listener {
	public final Main plugin;
	public final SenConfig conf;
    public final int minRadius;
    public final int maxRadius;
	
	public SenMain(Main plugin, SenConfig conf) {
		this.plugin = plugin;
		this.conf = conf;
        this.minRadius = conf.getInt("minRadius");
        this.maxRadius = conf.getInt("maxRadius");
	}

	public void roll(Position pos, Player player, PlayerRespawnEvent e) {
		boolean found = false;
		int y = 255;
		int x = Long.valueOf(Math.round(Math.random() *maxRadius)).intValue()+minRadius;
		int z = Long.valueOf(Math.round(Math.random() *maxRadius)).intValue()+minRadius;

		if(Math.round(Math.random()*2) == 1) x = x-(x*2);
		if(Math.round(Math.random()*2) == 1) z = z-(z*2);
		while(!found && y > 0) {
			if(pos.getLevel().getBlock(x,y,z).isSolid() && pos.getLevel().getBlock(x,y+1,z).getId() == 0 && pos.getLevel().getBlock(x,y+2,z).getId() == 0) {
				e.setRespawnPosition(new Position((double) x, (double) (y + 1), (double) z));
				found = true;
			}
			y--;
			if(y == 0 && !found) {
				this.roll(pos,player,e);
			}
		}
  	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onRespawn(PlayerRespawnEvent e) {
		Position pos = e.getRespawnPosition();
		Position spos = pos.getLevel().getSpawnLocation();
		if(pos.getLevel().getBlock(Long.valueOf(Math.round(pos.getX())).intValue(), Long.valueOf(Math.round(pos.getY())).intValue(), Long.valueOf(Math.round(pos.getZ())).intValue()).getId() != BlockID.BED_BLOCK && e.getPlayer().getSpawn() == pos) roll(pos,e.getPlayer(),e);
	}
}