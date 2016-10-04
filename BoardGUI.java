/**
    BoardGUI.java
    @author: cyberpirate92
    The GUI for the solver program
**/

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

class BoardGUI extends JFrame implements ActionListener {
    JPanel topPanel, centerPanel, bottomPanel;
    JTextField grid[][];
    JButton loadBtn, saveBtn, solveBtn;

    public BoardGUI() {
        topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();

        grid = new JTextField[9][9];
        for(int i=0; i<9; i++)
            for(int j=0; j<9; j++)
                grid[i][j] = new JTextField(1);

        loadBtn = new JButton("Load");
        saveBtn = new JButton("Save");
        solveBtn = new JButton("Solve");

        initLayout();
        registerEventListeners();
    }

    private void initLayout() {
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(new JLabel("Sudoku Solver"));

        centerPanel.setLayout(new GridLayout(9,9,2,2));
        for(int i=0; i<9; i++)
            for(int j=0; j<9; j++)
                centerPanel.add(grid[i][j]);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(loadBtn);
        bottomPanel.add(saveBtn);
        bottomPanel.add(solveBtn);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        this.setSize(500,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void registerEventListeners() {
        loadBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        solveBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if(btn == loadBtn) {

        }
        else if(btn == saveBtn) {

        }
        else if(btn == solveBtn) {

        }
    }
}
