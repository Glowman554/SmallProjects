package de.glowman554.small.commands;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.glowman554.small.SmallProjectsMain;
import de.glowman554.small.utils.AutoResettingArrayList;

public class FreeCommand implements CommandExecutor
{
	private AutoResettingArrayList<UUID> cooldown = new AutoResettingArrayList<UUID>();
	private Random random = new Random();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Player command only!");
			return false;
		}
		Player player = (Player) sender;

		if (cooldown.contains(player.getUniqueId()))
		{
			player.sendMessage("§c§lʙɪᴛᴛᴇ ᴡᴀʀᴛᴇ ʙɪs ᴅᴜ ᴡɪᴇᴅᴇʀ ᴋᴏsᴛᴇɴʟᴏs ᴇɪɴ ɪᴛᴇᴍ ᴀʙʜᴏʟᴇɴ ᴋᴀɴɴsᴛ.");
		}
		else
		{
			cooldown.add(player.getUniqueId());

			List<ItemStack> items = SmallProjectsMain.getInstance().getFreeItems();
			ItemStack item = items.get(random.nextInt(items.size())).clone();

			player.getInventory().addItem(item);
		}

		return false;
	}

}
