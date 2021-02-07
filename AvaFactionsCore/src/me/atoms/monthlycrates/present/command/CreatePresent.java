package me.atoms.monthlycrates.present.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.atoms.monthlycrates.present.Present;
import me.atoms.monthlycrates.present.menu.PlayerMenuUtility;
import me.atoms.monthlycrates.present.menu.menus.CreatePresentMenu;

public class CreatePresent implements CommandExecutor{

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
			
			if(!(p.hasPermission("ava.commands.createpresent"))) {
				p.sendMessage(format("&c&lError &7» &4You do not have sufficient permission to use this command."));
			}
			
			if(args.length >= 1 ) {
				
				String to = args[0];
				Player recipient = Bukkit.getPlayer(to);
				
				if(recipient == null) {
					p.sendMessage(format("&c&lError &7» &4Could not find player."));
				}else {
					PlayerMenuUtility playerMenuUtility = Present.getPlayerMenuUtility(p);
					
					if(args.length == 1) {
						playerMenuUtility.setRecipient(recipient.getDisplayName());
					}else if(args.length > 1) {
					
						playerMenuUtility.setRecipient(recipient.getDisplayName());
						
						StringBuilder message = new StringBuilder();
						
						for(int i = 1; i < args.length; i++) {
							message.append(args[i] + " ");
						}
					
						playerMenuUtility.setPresentMessage(message.toString());
					}
					
					new CreatePresentMenu(Present.getPlayerMenuUtility(p)).open();
				}
			}else {
				new CreatePresentMenu(Present.getPlayerMenuUtility(p)).open();
			}
			
		}
		
		return true;
	}
	
	
	public static String format(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
}
