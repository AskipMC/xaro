package fr.matt.arkadia.command;

import fr.matt.arkadia.manager.ClassementManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClassementCommand implements CommandExecutor {

    private ClassementManager classementManager;

    private String HELP_MESSAGE = ChatColor.RED + "Liste des commandes disponibles :"
            + "\n - classement create [nom]"
            + "\n - classement stop";
    public ClassementCommand(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(!(sender instanceof Player)) return false;

        if(cmd.getName().equalsIgnoreCase("classement")) {

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("stop")) {
                    Player player = ((Player) sender).getPlayer();
                    player.sendMessage(this.classementManager.stopClassement());
                }
            }
            if(args.length == 2) {
                if(args[0].equalsIgnoreCase("start")) {
                    Player player = ((Player) sender).getPlayer();
                    player.sendMessage(this.classementManager.startClassement(args[1]));
                }
            }
        }

        return false;

    }
}
