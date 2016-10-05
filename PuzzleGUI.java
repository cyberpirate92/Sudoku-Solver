import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class PuzzleGUI extends JFrame implements ActionListener
{
	JPanel buttonPanel,titlePanel;
	JLabel statusLabel;
	NumberGrid gridPanel;
	JButton btnEvaluate,btnCheck,btnSolve,btnClear,btnSave,btnLoad;
	private final String CURRENT_DIRECTORY = "C:\\Users\\intex\\Desktop\\ai";

	public PuzzleGUI()
	{
		super("Sudoku Helper");
		initVar();
		initLayout();
		initFonts();
		initListeners();
		finalizeGUI();
	}

	public void initVar()
	{
		gridPanel = new NumberGrid(9,9,1,1);
		buttonPanel = new JPanel();
		titlePanel = new JPanel();
		statusLabel = new JLabel(" ");
		btnEvaluate = new JButton("Evaluate Matrix");
		btnCheck = new JButton("Validate");
		btnSolve = new JButton("Solve");
		btnClear = new JButton("Clear");
		btnSave = new JButton("Save");
		btnLoad = new JButton("Load");
	}
	public void initLayout()
	{
		JLabel titleLabel = new JLabel("Sudoku Helper");
		titleLabel.setFont(new Font("Tahoma",Font.BOLD,15));
		titleLabel.setBackground(Color.WHITE);

		// for the title panel
		titlePanel.add(titleLabel);

		// for the button panel
		buttonPanel.setLayout(new GridLayout(6,2,5,5));
		buttonPanel.add(btnEvaluate);
		buttonPanel.add(btnCheck);
		buttonPanel.add(btnSolve);
		buttonPanel.add(btnClear);
		buttonPanel.add(btnSave);
		buttonPanel.add(btnLoad);
		buttonPanel.setPreferredSize(new Dimension(100,this.getHeight()));

		//border for the status label
		//Border border = BorderFactory.createLineBorder(Color.BLACK,1,true);
		//statusLabel.setBorder(border);
		statusLabel.setHorizontalAlignment(JLabel.CENTER);

		// for the final frame render
		this.setLayout(new BorderLayout());
		this.getContentPane().add(gridPanel,BorderLayout.CENTER);
		this.getContentPane().add(titlePanel,BorderLayout.NORTH);
		this.getContentPane().add(buttonPanel,BorderLayout.WEST);
		this.getContentPane().add(statusLabel,BorderLayout.SOUTH);

	}
	public void initFonts()
	{
		Font f = new Font("Tahoma",Font.PLAIN,11);
		statusLabel.setFont(f);
	}
	public void initListeners()
	{
		btnEvaluate.addActionListener(this);
		btnCheck.addActionListener(this);
		btnClear.addActionListener(this);
		btnSolve.addActionListener(this);
		btnSave.addActionListener(this);
		btnLoad.addActionListener(this);
	}
	public void finalizeGUI()
	{
		this.setSize(450,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void setStatus(String msg)
	{
		statusLabel.setText(msg);
	}
	public void setStatus(String msg,boolean error)
	{
		if(error)
			statusLabel.setForeground(Color.RED);
		else
			statusLabel.setForeground(Color.BLACK);
		setStatus(msg);
	}
	public String getStatus(String msg)
	{
		return statusLabel.getText();
	}
	public void clearStatus()
	{
		setStatus(" ",false);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnEvaluate)
		{
			/*if(gridPanel.containsEmpty())
			{
				setStatus("Please Fill all the boxes first");
				return;
			}*/
			System.out.println("Evaluating Matrix ......");
			PrintUtilities.print(gridPanel.getIncompleteMatrix());

			// Printing out the possible numbers in vacant slots
			SudokuSolver.printPossibleNumbers(gridPanel.getIncompleteMatrix());
			int[][] solvedMatrix = SudokuSolver.solve2(gridPanel.getIncompleteMatrix());
			if(SudokuSolver.isValidSolution(solvedMatrix))
			{
				gridPanel.setMatrix(solvedMatrix);
				setStatus("Solved. :)");
			}
			else
				setStatus("unSolved, sorry :(",true);
		}
		else if(e.getSource() == btnCheck)
		{
			if(gridPanel.containsEmpty())
			{
				setStatus("Please Fill all the boxes first");
				return;
			}
			try
			{
				if(SudokuSolver.isValidSolution(gridPanel.getMatrix()))
				{
					System.out.println("[+] Sudoku Solution is valid.");
					this.setStatus("Solution Accepted");
				}
				else
				{
					System.out.println("[+] Not a valid Solution.");
					this.setStatus("Solution Not Accepted.");
				}
			}
			catch(NumberFormatException nfe)
			{
				setStatus(nfe.getLocalizedMessage());
			}
		}
		else if(e.getSource() == btnSolve)
		{
			int[][] solution = SudokuSolver.solve(gridPanel.getIncompleteMatrix());
			gridPanel.setMatrix(solution);
		}
		else if(e.getSource() == btnClear)
		{
			gridPanel.clearAll();
			setStatus("All Cleared.");
		}
		else if(e.getSource() == btnSave)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(CURRENT_DIRECTORY));
			int action = chooser.showSaveDialog(null);
			if(action == JFileChooser.APPROVE_OPTION)
			{
				gridPanel.saveFile(chooser.getSelectedFile());
				setStatus("Solution saved to file sucessfully.");
			}
			else
				setStatus("Solution not saved.",true);
		}
		else if(e.getSource() == btnLoad)
		{
			try
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(CURRENT_DIRECTORY));
				int action = chooser.showOpenDialog(null);
				if(action == JFileChooser.APPROVE_OPTION)
				{
					setStatus("Loading File ",false);
					gridPanel.loadFile(chooser.getSelectedFile());
					setStatus("File loaded succesfully.",false);
				}
				else
					setStatus("Solution not loaded.",true);
			}
			catch(FileNotFoundException fnfe)
			{
				fnfe.printStackTrace();
				setStatus("File Invalid.",true);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				setStatus("Cannot write to File.",true);
			}
		}
	}
}