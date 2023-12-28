package de.glowman554.small;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import de.glowman554.small.utils.PermissionManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.glowman554.small.commands.ExchangeCommand;
import de.glowman554.small.commands.FreeCommand;
import de.glowman554.small.commands.TeamCommand;
import de.glowman554.small.commands.TeamSpawnCommand;
import de.glowman554.small.gui.TeamGUI;
import de.glowman554.small.utils.Coordinate;

public class SmallProjectsMain extends JavaPlugin
{
	private static SmallProjectsMain instance;

	public SmallProjectsMain()
	{
		instance = this;
	}

	private FileConfiguration config = getConfig();

	private File teamsFile;
	private FileConfiguration teams;

	private List<ItemStack> freeItems;
	private List<ItemStack> exchangeItems;
	private List<ItemStack> freeItems2;
	private List<ItemStack> exchangeItems2;

	private PermissionManager permissionManager;

	private void loadTeam(Teams team)
	{
		int x = config.getInt(team.toString() + ".x");
		int y = config.getInt(team.toString() + ".y");
		int z = config.getInt(team.toString() + ".z");
		String world = config.getString(team.toString() + ".world");

		team.setCoordinate(new Coordinate(x, y, z));
		team.setWorld(world);

		getLogger().log(Level.INFO, team.getCoordinate().toString());
	}

	@SuppressWarnings("unchecked")
	private List<ItemStack> loadItems(String section)
	{
		return ((List<String>) config.getList(section)).stream().map(itemString -> {
			String[] itemSplit = itemString.split("\\*");

			Material item = Material.getMaterial(itemSplit[0].toUpperCase());
			if (item == null)
			{
				throw new IllegalArgumentException("Item " + itemSplit[0] + " could not be found!");
			}

			ItemStack items = new ItemStack(item);
			items.setAmount(Integer.parseInt(itemSplit[1]));

			return items;
		}).toList();
	}

	@Override
	public void onLoad()
	{
		config.addDefault("exchange_items", new String[] {"grass_block*64", "stone*32"});
		config.addDefault("free_items", new String[] {"grass_block*64", "diamond_block*32"});
		config.addDefault("exchange_items2", new String[] {"grass_block*64", "stone*32"});
		config.addDefault("free_items2", new String[] {"grass_block*64", "diamond_block*32"});

		config.addDefault("RED.x", 0);
		config.addDefault("RED.y", 100);
		config.addDefault("RED.z", 0);
		config.addDefault("RED.world", "world");

		config.addDefault("BLUE.x", 100);
		config.addDefault("BLUE.y", 100);
		config.addDefault("BLUE.z", 100);
		config.addDefault("BLUE.world", "world");

		config.addDefault("YELLOW.x", 200);
		config.addDefault("YELLOW.y", 100);
		config.addDefault("YELLOW.z", 200);
		config.addDefault("YELLOW.world", "world");

		config.options().copyDefaults(true);
		saveConfig();

		teamsFile = new File(getDataFolder(), "teams.yml");
		teams = YamlConfiguration.loadConfiguration(teamsFile);

		loadTeam(Teams.RED);
		loadTeam(Teams.BLUE);
		loadTeam(Teams.YELLOW);

		freeItems = loadItems("free_items");
		exchangeItems = loadItems("exchange_items");
		freeItems2 = loadItems("free_items2");
		exchangeItems2 = loadItems("exchange_items2");
	}

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(TeamGUI.getGui(), this);

		getCommand("team").setExecutor(new TeamCommand());
		getCommand("tspawn").setExecutor(new TeamSpawnCommand());

		getCommand("free").setExecutor(new FreeCommand(freeItems, "freeCooldown"));
		getCommand("tausch").setExecutor(new ExchangeCommand(exchangeItems, "§c§lᴅᴜ ʙᴇsɪᴛᴢᴛ ɴɪᴄʜᴛ ɢᴇɴᴜɢ ᴇɴᴅsᴛᴏɴᴇ.", new Material[] {Material.END_STONE}));
		getCommand("free2").setExecutor(new FreeCommand(freeItems2, "free2Cooldown"));
		getCommand("tausch2").setExecutor(new ExchangeCommand(exchangeItems2, "§c§lᴅᴜ ʜᴀsᴛ ᴢᴜ-ᴡᴇɴɪɢ ғɪsᴄʜ ᴅᴇʀ ɢʟᴇɪᴄʜᴇɴ ᴀʀᴛ.", new Material[] {Material.COD, Material.SALMON, Material.PUFFERFISH, Material.TROPICAL_FISH}));
	}

	@Override
	public void onDisable()
	{
	}

	public void saveTeams()
	{
		try
		{
			teams.save(teamsFile);
		}
		catch (IOException e)
		{
			throw new IllegalStateException(e);
		}
	}

	public FileConfiguration getTeams()
	{
		return teams;
	}

	public static SmallProjectsMain getInstance()
	{
		return instance;
	}

	public PermissionManager getPermissionManager() {
		if (permissionManager == null) {
			permissionManager = new PermissionManager();
		}
		return permissionManager;
	}
}
