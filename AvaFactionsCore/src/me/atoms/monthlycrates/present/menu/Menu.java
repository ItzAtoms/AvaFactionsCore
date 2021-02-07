package me.atoms.monthlycrates.present.menu;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Menu implements InventoryHolder{

	protected PlayerMenuUtility playerMenuUtility;
	
	protected Inventory inventory;
	
	protected static ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE, "&8&l?");
	
	public Menu(PlayerMenuUtility playerMenuUtility) {
		this.playerMenuUtility = playerMenuUtility;
	}
	
	public abstract String getMenuName();
	
	public abstract int getSlots();
	
	public abstract void handleMenu(InventoryClickEvent e);
	
	public abstract void setMenuItems();
	
	public void open() {
		inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
		
		this.setMenuItems();
		
		playerMenuUtility.getOwner().openInventory(inventory);
	}
	
	@Override
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setFillerGlass() {
		for(int i = 0; i < getSlots(); i++) {
			if(inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}
			
	public static ItemStack makeItem(Material material, String displayName, String...lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(format(displayName));
		
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public static ItemStack getFILLER_GLASS() {
		return FILLER_GLASS;
	}
	public static String format(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
