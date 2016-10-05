public class GridPosition
{
	private int x,y;
	public GridPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public String toString()
	{
		return "("+x+","+y+")";
	}
	public boolean equals(GridPosition pos)
	{
		return (this.x == pos.getX() && this.y == pos.getY());
	}
}