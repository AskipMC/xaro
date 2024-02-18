package fr.matt.arkadia.event;

import fr.matt.arkadia.manager.ClassementManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ShopEvent implements Listener {

    private ClassementManager classementManager;

    private List<String> invNames = Arrays.asList(
            ChatColor.LIGHT_PURPLE + "Shop " + "agriculteur",
            ChatColor.LIGHT_PURPLE + "Shop " + "mineur",
            ChatColor.LIGHT_PURPLE + "Shop " + "architect",
            ChatColor.LIGHT_PURPLE + "Shop " + "chasseur",
            ChatColor.LIGHT_PURPLE + "Shop " + "bucheron",
            ChatColor.LIGHT_PURPLE + "Shop " + "pecheur",
            ChatColor.LIGHT_PURPLE + "Shop " + "alchimiste",
            ChatColor.LIGHT_PURPLE + "Shop " + "explorateur"
    );
    public ShopEvent(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }

    @EventHandler
    public void onPlayerClickInv(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if(item == null) return;

        String title = event.getView().getTitle();
        if(!invNames.contains(title)) return;

        int counter = 0;
        for (ItemStack invItem : player.getInventory().getContents()) {
            if(invItem == null) continue;
            if(invItem.getType() == item.getType() && invItem.getData().getData() == item.getData().getData()){
                counter += invItem.getAmount();
            }
        }

        if(counter==0) {
            player.sendMessage(ChatColor.RED + "Vous n'avez pas cette item dans votre inventaire.");
            event.setCancelled(true);
            return;
        }

        ItemStack itemStack = new ItemStack(item.getType(),counter); //byte ?? getData().getData()
        player.getInventory().removeItem(itemStack);
        player.updateInventory();

        //voir ce qu'on envoie exactement : counter, material, byte ?, aussi display name pour l'objet venteshop apres

        boolean isSell = this.classementManager.addVenteShop(itemStack, player);
        if(isSell) player.sendMessage(ChatColor.GREEN + "Vous venez de vendre " + counter + " " + item.getItemMeta().getDisplayName());
        else {
            player.sendMessage(ChatColor.RED + "Vous dépassez la limite avec cette vente ou il y a un problème.");
            player.getInventory().addItem(itemStack);
            player.updateInventory();
        }

        event.setCancelled(true);
    }


}
