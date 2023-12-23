package de.glowman554.small.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExchangeCommand implements CommandExecutor
{
	private Random random = new Random();
	private final List<ItemStack> items;
	private final String message;
	private final Material[] allowedMaterials;

	public ExchangeCommand(List<ItemStack> items, String message, Material[] allowedMaterials)
	{
		this.items = items;
		this.message = message;
		this.allowedMaterials = allowedMaterials;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Player command only!");
			return false;
		}
		Player player = (Player) sender;

		for (Material material : allowedMaterials)
		{

			if (player.getInventory().contains(material, 64))
			{
				ItemStack item = items.get(random.nextInt(items.size())).clone();
				removeFromInventory(player, material, 64);
				player.getInventory().addItem(item);

				return false;
			}
		}
		player.sendMessage(message);

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
					stack.setAmount(0);
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
