package de.glowman554.small.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.glowman554.small.SmallProjectsMain;

public class AutoResettingArrayList<E> extends ArrayList<E>
{
	private static final long serialVersionUID = 1L;

	public AutoResettingArrayList()
	{
		int day = 20 * 60 * 60 * 24;
		long initial = secondsUntilMidnight() * 20;
		Bukkit.getServer().getScheduler().runTaskTimer(SmallProjectsMain.getInstance(), this::onMidnight, initial, day);
	}

	private long secondsUntilMidnight()
	{
		long current = System.currentTimeMillis();
		long midnight = ((current / 1000 / 60 / 60 / 24) + 1) * 1000 * 60 * 60 * 24;
		return (midnight - current) / 1000;
	}

	private void onMidnight()
	{
		clear();
	}
}
