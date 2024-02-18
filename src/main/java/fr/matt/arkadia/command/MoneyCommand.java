package fr.matt.arkadia.command;

import fr.matt.arkadia.manager.ClassementManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {

    private ClassementManager classementManager;
    public MoneyCommand(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(args.length == 0) sender.sendMessage(this.classementManager.getShoptStat(player.getName()));
        if(args.length == 1 && args[0].equalsIgnoreCase("team")) {
            sender.sendMessage(this.classementManager.getShopTeamStat(player.getName()));
        }

        return true;
    }
}
