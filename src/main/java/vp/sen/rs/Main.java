package vp.sen.rs;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import vp.sen.rs.listeners.SenMain;
import vp.sen.rs.utility.SenConfig;

import java.io.File;

public class Main extends PluginBase {
	public SenConfig conf;

	@Override
    public void onEnable() {
        this.getLogger().info(TextFormat.AQUA + "Random Spawn by " + TextFormat.LIGHT_PURPLE + "NameDoesCode.");
        if(!new File(this.getDataFolder(), "config.yml").exists()) this.saveResource("config.yml");
    this.conf = new SenConfig(this);
    this.getServer().getPluginManager().registerEvents(new SenMain(this,conf), this);
    }

    @Override
    public void onDisable() { this.getLogger().info(TextFormat.RED + "Plugin Disabled!"); }
}