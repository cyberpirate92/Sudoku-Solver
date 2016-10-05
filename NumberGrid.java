import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.color.*;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class NumberGrid extends JPanel implements FocusListener
{
	GridTextField grid[][];
	int rows,cols;
	public NumberGrid(int rows,int cols,int vGap,int hGap)
	{
		Border border = BorderFactory.createLineBorder(Color.MAGENTA,2,true);
		Font numberFont = new Font("Garamond",Font.BOLD,20);
		this.rows = rows;
		this.cols = cols;
		grid = new GridTextField[rows][cols];
		this.setLayout(new GridLayout(rows,cols,vGap,hGap));
		this.setBackground(Color.WHITE);
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				grid[i][j] = new GridTextField("0",1,new GridPosition(i,j));
				grid[i][j].setDocument(new JTextFieldLimit(1));
				grid[i][j].setHorizontalAlignment(JTextField.CENTER);
				grid[i][j].setBorder(border);
				grid[i][j].setFont(numberFont);
				grid[i][j].addFocusListener(this);
				this.add(grid[i][j]);
			}
		}
		border = BorderFactory.createLineBorder(Color.BLUE,2,true);
		for(int i=0;i<3;i++)
		{
			for(int j=3;j<6;j++)
			{
				grid[i][j].setBorder(border);
				grid[j][i].setBorder(border);
			}
		}
		for(int i=3;i<6;i++)
		{
			for(int j=6;j<9;j++)
			{
				grid[i][j].setBorder(border);
				grid[j][i].setBorder(border);
			}
		}
	}
	public int get(int i,int j)
	{
		return Integer.parseInt(grid[i][j].getText());
	}
	public void set(int i,int j,int value)
	{
		grid[i][j].setText(value+"");
	}
	public int[][] getMatrix()
	{
		int[][] matrix = new int[rows][cols];
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
				matrix[i][j] = Integer.parseInt(grid[i][j].getText());
		return matrix;
	}
	public int[][] getIncompleteMatrix()
	{
		int[][] matrix = new int[rows][cols];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				String digit = grid[i][j].getText().trim();
				if(digit.length() == 0 || !(digit.charAt(0) >='1' && digit.charAt(0) <='9'))
				{
					matrix[i][j] = 0;
					grid[i][j].setForeground(Color.RED);
				}
				else
					matrix[i][j] = Integer.parseInt(digit);
			}
		}
		return matrix;
	}
	public int[] getRow(int rowNumber) // 0 <= rowNumber <= rows-1 
	{
		int[] requestedRow = new int[cols];
		for(int i=0;i<cols;i++)
			requestedRow[i] = Integer.parseInt(grid[rowNumber][i].getText());
		return requestedRow;
	}
	public int[] getColumn(int colNumber) // 0 <= columnNumber <= cols-1 
	{
		int[] requestedColumn = new int[rows];
		for(int i=0;i<rows;i++)
			requestedColumn[i] = Integer.parseInt(grid[i][colNumber].getText());
		return requestedColumn;
	}
	public void clearAll()
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				this.grid[i][j].setText("");
				this.grid[i][j].setBackground(Color.WHITE);
			}
		}
	}
	public boolean containsEmpty()
	{
		boolean flag = false;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(this.grid[i][j].getText().trim().length() == 0)
				{
					this.grid[i][j].setBackground(Color.RED);
					flag = true;
				}
			}
		}
		return flag;
	}
	public void setMatrix(int[][] solution)
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(solution[i][j] != 0)
				{
					grid[i][j].setText(solution[i][j]+"");
				}
			}
		}
	}
	public void saveFile(File selectedFile)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<cols;j++)
				{
					if(grid[i][j].getText().trim().length() !=0 && grid[i][j].getText().charAt(0)>='1' && grid[i][j].getText().charAt(0) <='9')
						writer.write(grid[i][j].getText().charAt(0));
					else
						writer.write("0");
					
					if(j!=cols-1)
					{
							writer.write(",");
					}
				}
				writer.write("\r\n");
			}
			writer.flush();
			writer.close();
		}
		catch(Exception e)
		{
			System.out.println("[!] "+e);
		}
	}
	private void clearAllColor()
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				grid[i][j].setBackground(Color.WHITE);
			}
		}
	}
	public void loadFile(File fileToLoad) throws IOException,FileNotFoundException
	{
		this.clearAll();
		String line;
		int rowCount = 0;
		BufferedReader br = new BufferedReader(new FileReader(fileToLoad));
		while((line = br.readLine()) != null)
		{
			String[] row = line.split(",");
			for(int i=0;i<row.length;i++)
			{
				if(!row[i].equals("0"))
					grid[rowCount][i].setText(row[i]);
			}
			rowCount++;
		}
	}
	public void focusGained(FocusEvent event)
	{
		clearAllColor();
		GridTextField textField = (GridTextField)event.getSource();
		GridPosition eventPosition = textField.getGridPosition();
		System.out.println("Currently Selected Position : "+eventPosition);
		if(textField.getText().length() > 0)
		{
			textField.setSelectionStart(0);
			textField.setSelectionEnd(1);
			char temp = textField.getSelectedText().charAt(0);
			if(temp >= '1' && temp <= '9')
			{
				int value = Integer.parseInt(temp+"");
				int count = colorByValue(value,Color.ORANGE);
				System.out.println("Number of '"+value+"' in solution : "+count);
			}
		}
		for(int i=0;i<rows;i++)
			grid[eventPosition.getX()][i].setBackground(Color.GREEN);
		for(int j=0;j<cols;j++)
			grid[j][eventPosition.getY()].setBackground(Color.GREEN);
		textField.setBackground(Color.ORANGE);
	}
	private int colorByValue(int value,Color highlightColor)
	{
		int[][] matrix = this.getIncompleteMatrix();
		int count = 0;
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(matrix[i][j] == value)
				{
					grid[i][j].setBackground(highlightColor);
					count++;
				}
			}
		}
		return count;
	}
	public void focusLost(FocusEvent event)
	{
		GridTextField textField = (GridTextField)event.getSource();
		GridPosition eventPosition = textField.getGridPosition();
		for(int i=0;i<rows;i++)
			grid[eventPosition.getX()][i].setBackground(Color.WHITE);
		for(int j=0;j<cols;j++)
			grid[j][eventPosition.getY()].setBackground(Color.WHITE);
	}
}