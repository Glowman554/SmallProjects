package de.glowman554.small.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.glowman554.small.SmallProjectsMain;
import de.glowman554.small.Teams;

public class TeamSpawnCommand implements CommandExecutor
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

		if (SmallProjectsMain.getInstance().getTeams().getString(player.getUniqueId().toString()) == null)
		{
			player.sendMessage("§c§lᴡäʜʟᴇ ᴇʀsᴛ ᴇɪɴ ᴛᴇᴀᴍ ᴀᴜs ᴍɪᴛ /ᴛᴇᴀᴍ.");
		}
		else
		{
			Teams team = Teams.valueOf(SmallProjectsMain.getInstance().getTeams().getString(player.getUniqueId().toString()));
			player.teleport(team.getCoordinate().toLocation(Bukkit.getWorld(team.getWorld())));
		}

		return false;
	}

}
