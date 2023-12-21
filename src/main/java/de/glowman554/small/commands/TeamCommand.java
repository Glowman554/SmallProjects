package de.glowman554.small.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.glowman554.small.gui.TeamGUI;

public class TeamCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Player command only!");
			return false;
		}
		Player player = (Player) sender;
		TeamGUI.getGui().openInventoryForPlayer(player);

		return false;
	}

}
