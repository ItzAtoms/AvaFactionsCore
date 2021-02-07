package me.atoms.monthlycrates.present.menu.menus;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.atoms.monthlycrates.present.menu.Menu;
import me.atoms.monthlycrates.present.menu.PlayerMenuUtility;
import me.atoms.monthlycrates.present.utils.PresentMaker;

public class CreatePresentMenu extends Menu{

	public CreatePresentMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMenuName() {
		return "Create new present";
	}

	@Override
	public int getSlots() {
		return 54;
	}

	@Override
    public void handleMenu(InventoryClickEvent e) {

        Player p = playerMenuUtility.getOwner();

        if(e.getView().getTitle().equalsIgnoreCase("Create new present")) {
	        if (e.getSlot() == 0){
	            e.setCancelled(true);
	        }else if (e.getSlot() == 40){
	
	            //get the items in the top 3 rows
	            ArrayList<ItemStack> items = new ArrayList<>();
	            ItemStack present;
	
	            try {
	
	                if (e.getInventory().getItem(0) != null){
	                    for (int i = 1; i <= 26; i++){
	                        if (e.getInventory().getItem(i) != null){
	                            items.add(e.getInventory().getItem(i));
	                        }
	                    }
	
	                    present = PresentMaker.createPresent(items, p.getDisplayName(), playerMenuUtility.getRecipient(), playerMenuUtility.getPresentMessage());
	                }else{
	                    for (int i = 0; i <= 26; i++){
	                        if (e.getInventory().getItem(i) != null){
	                            items.add(e.getInventory().getItem(i));
	                        }
	                    }
	                    present = PresentMaker.createPresent(items, p.getDisplayName(), null, null);
	                }
	
	                p.closeInventory();
	                p.getInventory().addItem(present);
	                p.sendMessage(format("&a&lFactory &7» &cPresent wrapped and ready to go!"));
	            } catch (IOException ioException) {
	                p.sendMessage(format("&c&lError &7» &4Unable to create present."));
	            }
	
	        }else if (e.getSlot() >= 27 && e.getSlot() < 54) {
	            e.setCancelled(true);
	
	        }

        }
	}
	@Override
	public void setMenuItems() {

        for (int i = 27; i < 54; i++){
            inventory.setItem(i, FILLER_GLASS);
        }

        ItemStack info = makeItem(Material.PAPER, format("&e&lInfo"), format("&aPut the items you"),
                format("&awant in the present"), format("&ain the box above."));

        ItemStack create = makeItem(Material.BELL, format("&c&lC&a&lr&c&le&a&la&c&lt&a&le &c&lP&f&lr&c&le&f&ls&c&le&f&ln&c&lt"), format("&aClick this item and the &c&lElves&a..."),
                format("&awill wrap your &f&l&nPRESENT"));

        inventory.setItem(39, info);
        inventory.setItem(40, create);

        if (playerMenuUtility.getPresentMessage() != null || playerMenuUtility.getRecipient() != null){
            ItemStack messageItem = new ItemStack(Material.NAME_TAG, 1);
            ItemMeta messageMeta = messageItem.getItemMeta();

            if (playerMenuUtility.getRecipient() != null){

                System.out.println("recipient: " + playerMenuUtility.getRecipient());
                messageMeta.setDisplayName(format("&e&lTo: &f" + playerMenuUtility.getRecipient()));

            }else{
                messageMeta.setDisplayName(format("&e&lMessage &7: "));
            }

            ArrayList<String> messageLore = new ArrayList<>();
            if (playerMenuUtility.getPresentMessage() != null){
                messageLore.add(format("&e&lMessage &7: " + playerMenuUtility.getPresentMessage()));
                messageMeta.setLore(messageLore);
            }else{
                messageLore.add("");
            }

            messageItem.setItemMeta(messageMeta);

            inventory.addItem(messageItem);
        }

    }

}
