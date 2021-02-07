package me.atoms;

import java.util.logging.Level;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.atoms.monthlycrates.present.command.CreatePresent;
import me.atoms.monthlycrates.present.listeners.MenuListener;
import me.atoms.monthlycrates.present.listeners.PresentListener;

public class Main extends JavaPlugin{
	PluginManager pm = this.getServer().getPluginManager();
	public static Main plugin;
	
	@Override
	public void onEnable() {
		monthlyCrates();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
	
	public void monthlyCrates() {
		
		try {
			getCommand("present").setExecutor(new CreatePresent());
		}catch(Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.SEVERE, "Could not load monthly crate command!");
		}
		
		try {
		pm.registerEvents(new MenuListener(), this);
		pm.registerEvents(new PresentListener(), this);
		}catch(Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.SEVERE, "Could not load monthly crate events!");
		}
		
	}
	
}
