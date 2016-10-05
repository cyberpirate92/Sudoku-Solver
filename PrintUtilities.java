public class PrintUtilities
{
	public static void print(int a[])
	{
		for(int i=0;i<a.length;i++)
		{
			System.out.println(a[i]);
		}
	}
	public static void print(int a[][])
	{
		for(int i=0;i<a.length;i++)
		{
			for(int j=0;j<a.length;j++)
			{
				System.out.print("\t"+a[i][j]);
			}
			System.out.println();
		}
	}
}