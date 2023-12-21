package de.glowman554.small.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.glowman554.small.SmallProjectsMain;

public class ExchangeCommand implements CommandExecutor
{
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

		if (!player.getInventory().contains(Material.END_STONE, 64))
		{
			player.sendMessage("§c§lᴅᴜ ʙᴇsɪᴛᴢᴛ ɴɪᴄʜᴛ ɢᴇɴᴜɢ ᴇɴᴅsᴛᴏɴᴇ.");
		}
		else
		{
			List<ItemStack> items = SmallProjectsMain.getInstance().getExchangeItems();
			ItemStack item = items.get(random.nextInt(items.size())).clone();

			removeFromInventory(player, Material.END_STONE, 64);

			player.getInventory().addItem(item);
		}

		return false;
	}

	private int removeFromInventory(Player player, Material material, int quantity)
	{
		int removed = 0;

		for (ItemStack stack : player.getInventory().getContents())
		{
			if (stack != null && stack.getType() == material)
			{
				int amount = stack.getAmount();
				if (amount <= quantity)
				{
					player.getInventory().remove(stack);
					removed += amount;
					quantity -= amount;
				}
				else
				{
					stack.setAmount(amount - quantity);
					removed += quantity;
					quantity = 0;
				}

				if (quantity <= 0)
				{
					break;
				}
			}
		}

		return removed;
	}
}
