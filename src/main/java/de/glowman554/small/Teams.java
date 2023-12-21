package de.glowman554.small;

import de.glowman554.small.utils.Coordinate;

public enum Teams
{
	BLUE, RED, YELLOW

	;

	private Coordinate coordinate;
	private String world;

	public void setCoordinate(Coordinate coordinate)
	{
		this.coordinate = coordinate;
	}

	public Coordinate getCoordinate()
	{
		return coordinate;
	}
	
	public void setWorld(String world)
	{
		this.world = world;
	}
	
	public String getWorld()
	{
		return world;
	}
}
