import java.awt.*;
import javax.swing.*;

public class GridTextField extends JTextField
{
	GridPosition position;
	public GridTextField(GridPosition pos)
	{
		super();
		this.position = pos;
	}
	public GridTextField(int x,GridPosition pos)
	{
		super(x);
		this.position = pos;
	}
	public GridTextField(String initialText,int cols,	GridPosition pos)
	{
		super(initialText,cols);
		this.position = pos;
	}
	public GridPosition getGridPosition()
	{
		return position;
	}
}