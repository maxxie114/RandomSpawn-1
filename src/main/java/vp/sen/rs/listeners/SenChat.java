package vp.sen.rs.listeners;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import vp.sen.rs.utility.SenConfig;
import vp.sen.rs.Main;
import java.lang.Math;

public class SenChat implements Listener {
	public final Main plugin;
	public final SenConfig conf;
	
	public SenChat(Main plugin) {
		this.plugin = plugin;
		this.conf = new SenConfig(plugin);
	}
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onChat(PlayerChatEvent e) {
		 if(e.getPlayer().isOp() && e.getMessage().startsWith("~")) {
		 	switch(e.getMessage().toLowerCase().replace("~","")) {
		 	 	case "test":
		 	 	 int y = conf.getInt("maxY");
	 		   int x = new Long(Math.round(Math.random()*conf.getInt("maxRadius"))).intValue()+conf.getInt("minRadius");
	 		   int z = new Long(Math.round(Math.random()*conf.getInt("maxRadius"))).intValue()+conf.getInt("minRadius");
	 		   if(Math.round(Math.random()*2) == 1) x = x-(x*2);
	 	     if(Math.round(Math.random()*2) == 1) z = z-(z*2);
	 		   e.getPlayer().sendChat("§b[§dRS§b]§e " + e.getPlayer().getLevel().getBlock(x,y,z).toString());
		 	 	break;
		 	 	default:
		 	 	 e.getPlayer().sendChat("§b[§dRS§b]§e No command specified.");
		 	 	break;
		 	 }
		 	e.setCancelled(true);
		}
	}
}