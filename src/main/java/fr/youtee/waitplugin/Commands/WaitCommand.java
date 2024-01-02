package fr.youtee.waitplugin.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.TabExecutor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static fr.youtee.waitplugin.WaitPlugin.plugin;

public class WaitCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Vérifiez si la commande a été correctement utilisée
        if (args.length < 2) {
            sender.sendMessage("Utilisation : /wait <secondes> <commande>");
            return true;
        }

        try {
            int seconds = Integer.parseInt(args[0]);

            // Prend chaque argument après le premier et les joint en une seule chaîne
            String commandToRun = String.join(" ", args).substring(args[0].length() + 1);

            // Créez une tâche BukkitRunnable pour retarder l'exécution de la commande
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Exécutez la commande spécifiée après le délai
                    sender.getServer().dispatchCommand(sender, commandToRun);
                }
            }.runTaskLater(plugin, seconds * 20L); // 20 ticks par seconde

            sender.sendMessage("La commande sera exécutée après " + seconds + " secondes.");
        } catch (NumberFormatException e) {
            sender.sendMessage("Veuillez spécifier un nombre valide de secondes.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Pour l'instant tu peux juste mettre null et tu pourras le compléter après vu que c'est optionnel
        return null;
    }
}
