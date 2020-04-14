package fr.peaceandcube.pacbirthday;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.peaceandcube.pacbirthday.command.BirthdayCommand;
import fr.peaceandcube.pacbirthday.command.BirthdaysCommand;
import fr.peaceandcube.pacbirthday.command.SetBirthdayCommand;
import fr.peaceandcube.pacbirthday.data.BirthdayData;
import fr.peaceandcube.pacbirthday.event.PlayerJoin;

public class PACBirthday extends JavaPlugin {
	public FileConfiguration config = this.getConfig();
	
	@Override
	public void onEnable() {
		this.getCommand("setbirthday").setExecutor(new SetBirthdayCommand());
		this.getCommand("birthday").setExecutor(new BirthdayCommand());
		this.getCommand("birthdays").setExecutor(new BirthdaysCommand());
		
		this.saveDefaultConfig();
		this.reloadConfig();
		
		BirthdayData.loadFile(this);
		
		this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
}
