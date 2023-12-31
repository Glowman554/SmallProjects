package de.glowman554.small.gui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.glowman554.small.SmallProjectsMain;
import de.glowman554.small.Teams;
import de.glowman554.small.utils.AutoResettingArrayList;

public class TeamGUI implements Listener
{
	private HashMap<HumanEntity, Inventory> inventoryInstances = new HashMap<>();
	private AutoResettingArrayList<UUID> cooldown = new AutoResettingArrayList<>("teamsCooldown", UUID::fromString);

	private static TeamGUI gui = new TeamGUI();

	public void openInventoryForPlayer(HumanEntity player)
	{
		if (cooldown.contains(player.getUniqueId()))
		{
			player.sendMessage("§c§lʙɪᴛᴛᴇ ᴡᴀʀᴛᴇ ʙɪsᴛ ᴅᴜ ᴡɪᴇᴅᴇʀ ᴅᴇɪɴ ᴛᴇᴀᴍ ᴡᴇᴄʜsᴇʟɴ ᴋᴀɴɴsᴛ.");
		}
		else
		{
			Inventory inventory = Bukkit.createInventory(null, 9 * 3, "§c§lᴛᴇᴀᴍ ᴀᴜssᴜᴄʜᴇɴ");

			populateInventory(inventory, player);

			inventoryInstances.put(player, inventory);
			player.openInventory(inventory);
		}
	}

	private void populateInventory(Inventory inventory, HumanEntity player)
	{
		List<String> lore = Arrays.asList(new String[] {"§c§lᴡäʜʟᴇ ᴅɪᴇsᴇs ᴛᴇᴀᴍ ᴜɴᴅ ᴛᴇʟᴇᴘᴏʀᴛɪᴇʀᴇ ᴅɪᴄʜ ᴢᴜ ᴅᴇɪɴᴇʀ ᴛᴇᴀᴍ-ʙᴀsᴇ."});

		ItemStack teamBlue = new ItemStack(Material.BLUE_SHULKER_BOX);
		ItemMeta metaBlue = teamBlue.getItemMeta();
		metaBlue.setDisplayName("§b§lʙʟᴀᴜ");
		metaBlue.setLore(lore);
		teamBlue.setItemMeta(metaBlue);
		inventory.setItem(11, teamBlue);

		ItemStack teamRed = new ItemStack(Material.RED_SHULKER_BOX);
		ItemMeta metaRed = teamRed.getItemMeta();
		metaRed.setDisplayName("§c§lʀᴏᴛ");
		metaRed.setLore(lore);
		teamRed.setItemMeta(metaRed);
		inventory.setItem(13, teamRed);

		ItemStack teamYellow = new ItemStack(Material.YELLOW_SHULKER_BOX);
		ItemMeta metaYellow = teamYellow.getItemMeta();
		metaYellow.setDisplayName("§e§lɢᴇʟʙ");
		metaYellow.setLore(lore);
		teamYellow.setItemMeta(metaYellow);
		inventory.setItem(15, teamYellow);
	}

	@EventHandler
	public void onPlayerInventoryClick(InventoryClickEvent e)
	{
		if (e.getInventory().equals(inventoryInstances.get(e.getWhoClicked())))
		{
			e.setCancelled(true);

			int rawSlot = e.getRawSlot();

			String oldTeam = SmallProjectsMain.getInstance().getTeams().getString(e.getWhoClicked().getUniqueId().toString());

			if (oldTeam != null) {
				SmallProjectsMain.getInstance().getPermissionManager().removePermission(e.getWhoClicked(), "team." + oldTeam);
			}

			Teams newTeam = switch (rawSlot) {
                case 11 -> Teams.BLUE;
                case 13 -> Teams.RED;
                case 15 -> Teams.YELLOW;
                default -> null;
            };

            if (newTeam != null) {
				SmallProjectsMain.getInstance().getTeams().set(e.getWhoClicked().getUniqueId().toString(), newTeam.toString());
				SmallProjectsMain.getInstance().saveTeams();
				SmallProjectsMain.getInstance().getPermissionManager().addPermission(e.getWhoClicked(), "team." + newTeam.toString());
				e.getWhoClicked().closeInventory();
			}
		}
	}

	@EventHandler
	public void onPlayerInventoryDragEvent(InventoryDragEvent e)
	{
		if (e.getInventory().equals(inventoryInstances.get(e.getWhoClicked())))
		{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInventoryClose(InventoryCloseEvent e)
	{
		if (e.getInventory().equals(inventoryInstances.get(e.getPlayer())))
		{
			cooldown.add(e.getPlayer().getUniqueId());
			cooldown.save();
			inventoryInstances.remove(e.getPlayer());
		}
	}

	public static TeamGUI getGui()
	{
		return gui;
	}
}
