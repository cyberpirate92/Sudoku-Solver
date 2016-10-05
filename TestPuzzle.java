import javax.swing.SwingUtilities;

class TestPuzzle
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					new PuzzleGUI();
				}
			}
		);
	}
}