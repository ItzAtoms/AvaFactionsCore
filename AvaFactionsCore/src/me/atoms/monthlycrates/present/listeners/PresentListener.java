package me.atoms.monthlycrates.present.listeners;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.TileState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import me.atoms.Main;
import me.atoms.monthlycrates.present.menu.Menu;
import me.atoms.monthlycrates.present.utils.PresentMaker;

public class PresentListener implements Listener {

    @EventHandler
    public void onPresentPlace(BlockPlaceEvent e) {

        ItemStack itemPlaced = e.getItemInHand();

        if (itemPlaced.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(Main.class), "presents"), PersistentDataType.STRING)) {

            //Bukkit.getServer().getWorld("world").spawnEntity(e.getBlockPlaced().getLocation(), EntityType.FIREBALL);

            Chest c = (Chest) e.getBlockPlaced().getState();
            try {

                ItemStack[] items = PresentMaker.getItemsFromPresent(itemPlaced.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(Main.class), "presents"), PersistentDataType.STRING));
                c.getSnapshotInventory().setContents(items);

                PersistentDataContainer container = c.getPersistentDataContainer();
                container.set(new NamespacedKey(Main.getPlugin(Main.class), "present"), PersistentDataType.INTEGER, 0);

                if (itemPlaced.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(Main.class), "presentMessage"), PersistentDataType.STRING)) {
                    container.set(new NamespacedKey(Main.getPlugin(Main.class), "presentMessage"), PersistentDataType.STRING, itemPlaced.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(Main.class), "presentMessage"), PersistentDataType.STRING));
                }

                if (itemPlaced.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.getPlugin(Main.class), "presentRecipient"), PersistentDataType.STRING)) {
                    container.set(new NamespacedKey(Main.getPlugin(Main.class), "presentRecipient"), PersistentDataType.STRING, itemPlaced.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(Main.class), "presentRecipient"), PersistentDataType.STRING));
                }

                c.update();

            } catch (IOException | ClassNotFoundException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPresentOpen(InventoryOpenEvent e) {

        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest) {

            if (e.getInventory().getHolder() instanceof TileState) {

                Player p = (Player) e.getPlayer();

                TileState state = (TileState) e.getInventory().getHolder();

                PersistentDataContainer container = state.getPersistentDataContainer();

                if (container.has(new NamespacedKey(Main.getPlugin(Main.class), "present"), PersistentDataType.INTEGER)) {

                    if (container.has(new NamespacedKey(Main.getPlugin(Main.class), "presentRecipient"), PersistentDataType.STRING)){

                        if (!container.get(new NamespacedKey(Main.getPlugin(Main.class), "presentRecipient"), PersistentDataType.STRING).equalsIgnoreCase(p.getDisplayName())){

                            e.setCancelled(true);

                            e.getPlayer().sendMessage(format("&c&lError &7» &4You are not the recipient of this present! Keep trying and you will end up on the naughty list."));

                            return;
                        }
                    }

                    Chest c = (Chest) e.getInventory().getHolder();
                    Block chest = state.getBlock();
                    
                   
                    
                    ItemStack[] items = c.getInventory().getContents();
                    e.setCancelled(true);

                    container.remove(new NamespacedKey(Main.getPlugin(Main.class), "present"));
                    state.update();

                    Chest newChest = (Chest) chest.getState();
                    newChest.getSnapshotInventory().clear();
                    for(int i = 0; i < newChest.getInventory().getSize(); i++) {
                    	
                    	if(newChest.getInventory().getItem(i) == null) {
                    		newChest.getInventory().setItem(i, Menu.getFILLER_GLASS());
                    	}
                    	
                    }
                    newChest.setLock(e.getPlayer().getUniqueId().toString());
                    newChest.update();
                    ArmorStand merryChristmas = (ArmorStand) chest.getLocation().getWorld().spawnEntity(chest.getLocation().add(0.5, -0.5, 0.5), EntityType.ARMOR_STAND);
                    merryChristmas.setGravity(false);
                    merryChristmas.setCanPickupItems(false);
                    merryChristmas.setCustomName(format("&e&oOpening present..."));
                    merryChristmas.setCustomNameVisible(true);
                    merryChristmas.setVisible(false);

                    ArmorStand message = (ArmorStand) chest.getLocation().getWorld().spawnEntity(chest.getLocation().add(0.5, -1, 0.5), EntityType.ARMOR_STAND);
                    message.setGravity(false);
                    message.setCanPickupItems(false);
                    if (container.has(new NamespacedKey(Main.getPlugin(Main.class), "presentMessage"), PersistentDataType.STRING)) {
                        message.setCustomName(format("\" &f&o" + container.get(new NamespacedKey(Main.getPlugin(Main.class), "presentMessage"), PersistentDataType.STRING) + "&r\""));
                        message.setCustomNameVisible(true);
                    } else {
                        message.setCustomName(" ");
                        message.setCustomNameVisible(false);
                    }
                    message.setVisible(false);

                    ArmorStand as = (ArmorStand) e.getPlayer().getWorld().spawnEntity(chest.getLocation().add(0.5, -0.5, 0.5), EntityType.ARMOR_STAND);
                    as.setSmall(true);
                    as.setMarker(true);
                    as.setSilent(true);
                    as.setVisible(false);
                    as.setHeadPose(new EulerAngle(0, 0, 0));
                    as.setHelmet(new ItemStack(Material.SPRUCE_SAPLING, 1));


                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            if ((as.getLocation().getY() - chest.getLocation().getY()) >= 3.5){

                                final Firework fw = (Firework) chest.getWorld().spawnEntity(as.getLocation().add(0.5, 0, 0.5), EntityType.FIREWORK);
                                FireworkMeta fm = fw.getFireworkMeta();
                                fm.addEffect(FireworkEffect.builder()
                                        .flicker(true)
                                        .trail(true)
                                        .with(FireworkEffect.Type.BURST)
                                        .withColor(Color.RED, Color.GREEN, Color.LIME)
                                        .build());
                                fm.setPower(0);
                                fw.setFireworkMeta(fm);

                                fw.detonate();

                                as.remove();


                                Bukkit.getServer().getWorld("world").playSound(chest.getLocation(), Sound.BLOCK_BELL_RESONATE, 2.0f, 2.0f);

                                Location location = chest.getLocation().add(0.5, 0, 0.5);
                                for (ItemStack item : items) {
                                    if (item != null) {
                                        chest.getWorld().dropItem(location.add(0, 1, 0), item);
                                    }
                                }

                                this.cancel();
                            }else{
                                as.teleport(as.getLocation().add(0, 0.25, 0));
                                as.setRotation(as.getLocation().getYaw() + 25f, as.getLocation().getPitch() + 25f);
                            }


                        }
                    }.runTaskTimer(Main.getPlugin(Main.class), 0, 5);

                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            merryChristmas.remove();
                            message.remove();
                            newChest.getSnapshotInventory().clear();
                            newChest.update();
                            chest.setType(Material.AIR);

                        }
                    }.runTaskLater(Main.getPlugin(Main.class), 160L);


                }

            }


        }

    }
    
    public static String format(String s) {
    	
    	return ChatColor.translateAlternateColorCodes('&', s);
    }

}
