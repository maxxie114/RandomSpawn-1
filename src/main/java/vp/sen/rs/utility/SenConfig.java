package vp.sen.rs.utility;

import cn.nukkit.utils.Config;
import vp.sen.rs.Main;

import java.io.File;

public class SenConfig {
	public final Main plugin;
	public final Config conf;
	
	public SenConfig(Main plugin) {
		this.plugin = plugin;
		this.conf = new Config(new File(plugin.getDataFolder(), "config.yml"));
	}

	public boolean getBool(String str) {
		return conf.get(str, true);
	}

	public int getInt(String str) {
		return Integer.parseInt(conf.get(str, "0"));
	}
	
	public String getString(String str) {
		return conf.get(str,"");
 	}
}