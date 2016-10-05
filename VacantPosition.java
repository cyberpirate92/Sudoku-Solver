import java.util.ArrayList;

public class VacantPosition
{
	private GridPosition gridPosition;
	private ArrayList<Integer> possibleNumbers;

	public VacantPosition(GridPosition pos,int[][] matrix)
	{
		this.gridPosition = pos;
		this.possibleNumbers = obtainPossibleNumbers(matrix);
	}
	private ArrayList<Integer> obtainPossibleNumbers(int[][] matrix)
	{
		ArrayList<Integer> possibleList = new ArrayList<Integer>();
		int[][] subMatrix = new int[3][3];
		int x = this.gridPosition.getX() , y = this.gridPosition.getY();
		GridPosition xPoint,yPoint;
		
		if( x >= 0 && x <=2 ) // possible subgrids are (1,2,3)
		{
			xPoint = new GridPosition(0,2);
			if(y >= 0 && y <= 2) // sub-grid : 1
				yPoint = new GridPosition(0,2);
			else if(y >= 3 && y <= 5) // sub-grid : 2
				yPoint = new GridPosition(3,5);
			else // sub-grid : 3
				yPoint = new GridPosition(6,8);
		}
		else if( x >= 3 && x <=5 ) // possible subgrids are (4,5,6)
		{
			xPoint = new GridPosition(3,5);
			if(y >= 0 && y <= 2) // sub-grid : 4
				yPoint = new GridPosition(0,2);
			else if(y >= 3 && y <= 5) // sub-grid : 5
				yPoint = new GridPosition(3,5);
			else // sub-grid : 6
				yPoint = new GridPosition(6,8);
		}
		else
		{
			xPoint = new GridPosition(6,8);
			if(y >= 0 && y <= 2) // sub-grid : 4
				yPoint = new GridPosition(0,2);
			else if(y >= 3 && y <= 5) // sub-grid : 5
				yPoint = new GridPosition(3,5);
			else // sub-grid : 6
				yPoint = new GridPosition(6,8);	
		}
		subMatrix = getSubMatrix(matrix,xPoint,yPoint);
		
		/* DEBUG ----- |
		System.out.println("Sub-Matrix for grid : "+gridPosition);
		PrintUtilities.print(subMatrix);
		|----- DEBUG */
		
		//populating array with 1-9.
		for(int i=1;i<=9;i++)
			possibleList.add(new Integer(i));

		// 2 arraylists for row numbers and column numbers.
		ArrayList<Integer> rowList = new ArrayList<Integer>(),columnList = new ArrayList<Integer>();
		
		for(int i=0;i<matrix.length;i++)
		{
			rowList.add(new Integer(matrix[gridPosition.getX()][i]));
			columnList.add(new Integer(matrix[i][gridPosition.getY()]));
		}
		
		// removing the row data from possible list
		possibleList.removeAll(rowList);
		// removing the col data from possible list
		possibleList.removeAll(columnList);

		//populate the sub-grid list
		ArrayList<Integer> subGridList = new ArrayList<Integer>();
		for(int i=0;i<subMatrix.length;i++)
			for(int j=0;j<subMatrix[i].length;j++)
				subGridList.add(new Integer(subMatrix[i][j]));

		// remove the sub-grid data from possible list
		possibleList.removeAll(subGridList);
		return possibleList;
	}
	private int[][] getSubMatrix(int[][] matrix,GridPosition xPos,GridPosition yPos)
	{
		int[][] subMatrix = new int[3][3];
		int i,j,k,l;
		for(k = 0 , i = xPos.getX() ; i <= xPos.getY() ; k++ , i++)
			for(l = 0 , j = yPos.getX() ; j <= yPos.getY() ; l++ , j++)
				subMatrix[k][l] = matrix[i][j];
		return subMatrix;
	}
	public void printPossibleNumbers()
	{
		System.out.println("Grid Position : "+gridPosition);
		if(this.possibleNumbers.size() > 0)
		{
			System.out.println("Possible Numbers for this position :");
			for(Integer integer : possibleNumbers)
				System.out.print("\t"+integer);
			System.out.println();
		}
		else  // if this condition is reached, there is some error in the provided input or there is no solution.
			System.out.println("[!] No possible Numbers for this Grid position.[Mostly ERROR]");
	}
	public int possibleNumbersCount()
	{
		return possibleNumbers.size();
	}
	public GridPosition getGridPosition()
	{
		return gridPosition;
	}
	public int getOnlyPossibleNumber()
	{
		return possibleNumbers.get(0).intValue();
	}
	public boolean hasOnlyOnePossibleNumber()
	{
		return (possibleNumbers.size() == 1);
	}
}