import javax.swing.SwingUtilities;
class Test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BoardGUI();
            }
        });
    }
}
