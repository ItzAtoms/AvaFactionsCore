package me.atoms.monthlycrates.present;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.atoms.monthlycrates.present.menu.PlayerMenuUtility;

public class Present {
	private static HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<Player, PlayerMenuUtility>();

	public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
		PlayerMenuUtility playerMenuUtility;
		
		if(!(playerMenuUtilityMap.containsKey(p))) {
			playerMenuUtility = new PlayerMenuUtility(p);
			playerMenuUtilityMap.put(p, playerMenuUtility);
			
			return playerMenuUtility;
		}else {
			return playerMenuUtilityMap.get(p);
		}
	}
}
