package fr.matt.arkadia.command;

import fr.matt.arkadia.manager.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamCommand implements CommandExecutor {

    private TeamManager teamManager;
    private String HELP_MESSAGE = ChatColor.RED + "Liste des commandes disponibles :"
            + "\n - team list"
            + "\n - team create [nom]"
            + "\n - team disband [nom]"
            + "\n - team show [nom]"
            + "\n - team [nom] add [joueur]"
            + "\n - team [nom] remove [joueur]"
            + "\n - team [nom] color [couleur]";

    public TeamCommand(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(!(sender instanceof Player)) return false;

        if(cmd.getName().equalsIgnoreCase("t")) {

            if(args.length == 0) return false;

            Player player = ((Player) sender).getPlayer();
            StringBuilder message = new StringBuilder();

            for(String arg : args) {
                message.append(arg + " ");
            }

            this.teamManager.sendTeamMessage(player, message.toString());
            return false;
        }

        if(cmd.getName().equalsIgnoreCase("spawn")) {
            Player player = ((Player) sender).getPlayer();

            World world = Bukkit.getWorld("world");
            Location spawn = new Location(world, 0, 119,0);

            //METTRE UN DELAY DE 10secondes si dans le nether ou l'end
            player.teleport(spawn);
        }

        if(cmd.getName().equalsIgnoreCase("base")) {
            Player player = ((Player) sender).getPlayer();
            if(!player.getLocation().getWorld().getName().equals("world")) {
                player.sendMessage(ChatColor.RED + "Vous devez etre dans le monde normal.");
                return false;
            }

            this.teamManager.teleportToBase(player);
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("setbase")) {
            Player player = ((Player) sender).getPlayer();
            if(!player.getLocation().getWorld().getName().equals("world")) {
                player.sendMessage(ChatColor.RED + "Vous devez etre dans le monde normal.");
                return false;
            }

            Location location = player.getLocation();
            this.teamManager.setBase(player, location);
            return true;
        }

        if(cmd.getName().equalsIgnoreCase("team")) {
            if(args.length == 0){
                sender.sendMessage(this.HELP_MESSAGE);
            }
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("list")){
                    sender.sendMessage(this.teamManager.teamList());
                }
                else sender.sendMessage(this.HELP_MESSAGE);
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("create")){
                    sender.sendMessage(this.teamManager.createTeam(args[1]));
                }
                else if(args[0].equalsIgnoreCase("disband")){
                    sender.sendMessage(this.teamManager.disbandTeam(args[1]));
                }
                else if(args[0].equalsIgnoreCase("show")){
                    sender.sendMessage(this.teamManager.teamShow(args[1]));
                }
                else sender.sendMessage(this.HELP_MESSAGE);
            }
            if(args.length == 3){
                if(args[1].equalsIgnoreCase("add")) {
                    try {
                        sender.sendMessage(this.teamManager.addTeamPlayer(args[0], args[2]));
                    } catch (Exception e) {
                        throw new RuntimeException(e); //mettre les catchs autre part cf fullstack cours
                    }
                }
                else if(args[1].equalsIgnoreCase("remove")) {
                    try {
                        sender.sendMessage(this.teamManager.removeTeamPlayer(args[0], args[2]));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(args[1].equalsIgnoreCase("color")) {
                    try {
                        sender.sendMessage(this.teamManager.setTeamColor(args[0], args[2]));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else sender.sendMessage(this.HELP_MESSAGE);
            }
        }


        return false;
    }
}
