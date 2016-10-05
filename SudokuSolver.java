import java.util.ArrayList;

public class SudokuSolver
{
	public static boolean isValidSolution(int[][] solution)
	{
		for(int i=0;i<solution.length;i++)
		{
			for(int j=0;j<solution[i].length;j++)
			{
				int number = solution[i][j];
				if(number >= 1 && number <= 9)
				{

				}
				else return false;				// [ERROR] contains a value not in the range 1-9
			}
		}
		// [CHECKPOINT] values are valid (may be repeated too) in the range 1-9, safe to proceed.
		// [NOW] checking rows for duplicity...
		for(int i=0;i<solution.length;i++)
		{
			if(hasDuplicates(solution[i]))
			{
				System.out.println("[!] Row "+(i+1)+" has duplicate values");
				return false;
			}
		}
		//[CHECKPOINT] all the rows are duplicates-free.
		//[NOW] checking columns for duplicity...
		for(int i=0;i<solution.length;i++)
		{
			if(hasDuplicates(getColumn(solution,i)))
			{
				System.out.println("[!] Column "+(i+1)+" has duplicate values");
				return false;
			}
		}
		//[CHECKPOINT] all the columns are duplicates-free.
		//[NOW] the provided matrix represents a correct sudoku solution.
		return true;
	}
	private static boolean hasDuplicates(int a[])
	{
		boolean[] flag = new boolean[a.length];
		int number;
		for(int i=0;i<a.length;i++)
		{
			number = a[i];
			if(!flag[number-1])
			{
				flag[number-1] = true;
			}
			else return true;
		}
		return false;
	}
	private static int[] getColumn(int[][] matrix,int colNumber)
	{
		int[] columnArray = new int[matrix.length];
		for(int i=0;i<matrix.length;i++)
			columnArray[i] = matrix[i][colNumber];
		return columnArray;
	}
	public static int[][] solve(int[][] sudokuMatrix)
	{
	/*	//count number of zeroes (vacancies.)
		ArrayList<GridPosition> vacantPositions = new ArrayList<GridPosition>();
		int vacantCounter = 0;
		for(int i=0;i<sudokuMatrix.length;i++)
		{
			for(int j=0;j<sudokuMatrix[i].length;j++)
			{
				if(sudokuMatrix[i][j] == 0)
				{
					GridPosition vacantPosition = new GridPosition(i,j);
					vacantPositions.add(vacantPosition);
					vacantCounter++;
				}
			}
		}
		System.out.println("Total Vacant Positions : "+vacantCounter);
		if(vacantCounter == 0)
		{
			System.out.println("There is nothing to solve.");
			return sudokuMatrix;
		}
		else if(vacantCounter == sudokuMatrix.length*sudokuMatrix.length)
		{
			System.out.println("Please fill atleast half.");
			return sudokuMatrix;
		}
		else
		{
			printVacanciesPerNumber(sudokuMatrix); // debug Function
			for(GridPosition vacantPosition : vacantPositions)
			{
				System.out.println("Currently analyzing position : "+vacantPosition);
			}
			return sudokuMatrix;
		}*/
		return sudokuMatrix;
	}
	public static int countVacant(int a[])
	{
		int counter = 0;
		for(int i=0;i<a.length;i++)
		{
			if(a[i] == 0)
				counter++;
		}
		return counter;
	}
	private static int[] countVacanciesPerNumber(int[][] matrix)
	{
		int[] n = new int[matrix.length]; // contains how many times each number appeared in the solution matrix
		for(int i=0;i<matrix.length;i++)
		{
			for(int j=0;j<matrix[i].length;j++)
			{
				if(matrix[i][j] != 0)
				{
					n[matrix[i][j] - 1]++;
				}
			}
		}
		return n;
	}
	private static void printVacanciesPerNumber(int[][] matrix)
	{
		int[] n = countVacanciesPerNumber(matrix);
		System.out.println("Vacancies Per Number ....");
		for(int i=0;i<n.length;i++)
			System.out.println((i+1)+" -> "+n[i]);
	}

	public static ArrayList<GridPosition> getVacantGridPositions(int[][] matrix)
	{
		ArrayList<GridPosition> vacantPositions = new ArrayList<GridPosition>();

		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[i].length;j++)
				if(matrix[i][j] == 0)
					vacantPositions.add(new GridPosition(i,j));
		return vacantPositions;
	}

	public static void printPossibleNumbers(int[][] matrix)
	{
		ArrayList<GridPosition> positions = getVacantGridPositions(matrix);
		for(GridPosition temp : positions)
		{
			VacantPosition x = new VacantPosition(temp,matrix);
			x.printPossibleNumbers();
		}
	}
	public static int[][] solve2(int[][] matrix)
	{
		ArrayList<GridPosition> vacantPositions = new ArrayList<GridPosition>();
		ArrayList<VacantPosition> vacancyDetails = new ArrayList<VacantPosition>();

		do
		{
			vacancyDetails.clear();
			vacantPositions.clear();
			vacantPositions = getVacantGridPositions(matrix);
			for(GridPosition temp : vacantPositions)
			{
				VacantPosition x = new VacantPosition(temp,matrix);
				vacancyDetails.add(x);
			}
			for(VacantPosition position : vacancyDetails)
			{
				if(position.hasOnlyOnePossibleNumber())
				{
					GridPosition gridPos = position.getGridPosition();
					System.out.println("Solving Position : "+gridPos);
					int value = position.getOnlyPossibleNumber();
					System.out.println("Only Possible Value : "+value);
					matrix[gridPos.getX()][gridPos.getY()] = value;
					PrintUtilities.print(matrix);
					break;
				}
			}

		}while(vacancyDetails.size() > 0);
		return matrix;
	}
}