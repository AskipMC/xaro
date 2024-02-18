package fr.matt.arkadia.command;

import fr.matt.arkadia.manager.ClassementManager;
import fr.matt.arkadia.model.Classement;
import fr.matt.arkadia.model.Economie;
import fr.matt.arkadia.model.EconomieItem;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ShopCommand implements CommandExecutor {

    private ClassementManager classementManager;
    private String HELP_MESSAGE = ChatColor.RED + "Liste des commandes disponibles :"
            + "\n - shop agriculteur"
            + "\n - shop mineur"
            + "\n - shop architect"
            + "\n - shop chasseur"
            + "\n - shop bucheron"
            + "\n - shop pecheur"
            + "\n - shop explorateur";

    public ShopCommand(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = ((Player) sender).getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();

        if(args.length != 1) {
            player.sendMessage(HELP_MESSAGE);
            return false;
        }

        Economie economie = this.classementManager.getEconomie();
        if(economie==null){
            player.sendMessage(ChatColor.RED + "Aucun classement est en cours, il en faut un pour le shop.");
            return false;
        }

        if(args[0].equalsIgnoreCase("agriculteur")){
            this.buildInventory(player, economie, "agriculteur");
            return false;
        }
        else if(args[0].equalsIgnoreCase("mineur")){
            this.buildInventory(player, economie, "mineur");
            return false;
        }
        else if(args[0].equalsIgnoreCase("architect")){
            this.buildInventory(player, economie, "architect");
            return false;
        }
        else if(args[0].equalsIgnoreCase("chasseur")){
            this.buildInventory(player, economie, "chasseur");
            return false;
        }
        else if(args[0].equalsIgnoreCase("bucheron")){
            this.buildInventory(player, economie, "bucheron");
            return false;
        }
        else if(args[0].equalsIgnoreCase("pecheur")){
            this.buildInventory(player, economie, "pecheur");
            return false;
        }
        else if(args[0].equalsIgnoreCase("explorateur")){
            this.buildInventory(player, economie, "explorateur");
            return false;
        }
        else player.sendMessage(HELP_MESSAGE);

        return false;

    }

    private void buildInventory(Player player, Economie economie, String category) {
        List<EconomieItem> items = economie.getItems().stream()
                                        .filter(i -> i.getCategory().equalsIgnoreCase(category))
                                        .toList();

        Inventory inv = Bukkit.createInventory(null , 54, ChatColor.LIGHT_PURPLE + "Shop " + category);
        for(int i=0; i<items.size() ;i++) {
            inv.addItem(this.buildItemMeta(items.get(i)));
        }

        player.openInventory(inv);
        player.updateInventory();

    }

    private ItemStack buildItemMeta(EconomieItem item) {

        ItemStack itemStack;
        if(item.getByteMaterial() == 0){
            if(item.getMaterial().equals(Material.POTION)) itemStack = new ItemStack(item.getMaterial(),1, (short) 0); //pour la potion d'eau
            else itemStack = new ItemStack(item.getMaterial(),1);
        }
        else itemStack = new ItemStack(item.getMaterial(),1, (short) item.getByteMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Prix : " + ChatColor.GREEN +item.getValue(),
                ChatColor.GRAY + "Limite : " + ChatColor.RED +item.getMax()
        ));
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
