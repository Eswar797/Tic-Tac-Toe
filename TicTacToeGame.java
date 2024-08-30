import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TicTacToeGame implements ActionListener {
    private JFrame gameFrame;
    private JPanel gamePanel;
    private JButton[] cellButtons = new JButton[9];
    private boolean isXTurn = true;
    private String playerXName;
    private String playerOName;
    private JLabel turnLabel;

    public TicTacToeGame() {
        // Initialize the game frame
        gameFrame = new JFrame("Tic-Tac-Toe");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        // Input dialog for player names
        getPlayerNames();

        // Create a panel for the game buttons
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize buttons with enhanced UI
        initializeButtons();

        // Create a label to show whose turn it is
        turnLabel = new JLabel(playerXName + "'s turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 24));
        turnLabel.setForeground(Color.BLUE);
        
        // Add components to the frame
        gameFrame.add(turnLabel, BorderLayout.NORTH);
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setSize(400, 400);
        gameFrame.setVisible(true);
    }

    private void getPlayerNames() {
        playerXName = JOptionPane.showInputDialog(gameFrame, "Enter Player X's Name:");
        playerOName = JOptionPane.showInputDialog(gameFrame, "Enter Player O's Name:");
    }

    private void initializeButtons() {
        for (int index = 0; index < 9; index++) {
            cellButtons[index] = new JButton();
            cellButtons[index].setFont(new Font("Arial", Font.BOLD, 60)); // Increased font size for "X" and "O"
            cellButtons[index].setBackground(Color.LIGHT_GRAY);
            cellButtons[index].setForeground(Color.WHITE); // Set darker foreground color for "X" and "O"
            cellButtons[index].setFocusPainted(false);
            cellButtons[index].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
            cellButtons[index].setOpaque(true);
            cellButtons[index].addActionListener(this);
            cellButtons[index].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Add mouse hover effect
            cellButtons[index].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    button.setBackground(Color.GRAY);
                }

                public void mouseExited(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    if (button.getText().isEmpty()) {
                        button.setBackground(Color.LIGHT_GRAY);
                    }
                }
            });

            gamePanel.add(cellButtons[index]);
        }
    }

    public void actionPerformed(ActionEvent event) {
        JButton clickedButton = (JButton) event.getSource();
        clickedButton.setText(isXTurn ? "X" : "O");
        clickedButton.setEnabled(false);
        updateTurnLabel();
        isXTurn = !isXTurn;

        evaluateGameStatus();
    }

    private void updateTurnLabel() {
        turnLabel.setText(isXTurn ? playerOName + "'s turn" : playerXName + "'s turn");
    }

    private void evaluateGameStatus() {
        if (checkForWinner()) {
            return;
        }
        if (checkForTie()) {
            JOptionPane.showMessageDialog(gameFrame, "It's a tie!");
            resetGame();
        }
    }

    private boolean checkForWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (checkLine(cellButtons[i * 3], cellButtons[i * 3 + 1], cellButtons[i * 3 + 2]) ||
                checkLine(cellButtons[i], cellButtons[i + 3], cellButtons[i + 6])) {
                return true;
            }
        }
        // Check diagonals
        if (checkLine(cellButtons[0], cellButtons[4], cellButtons[8]) ||
            checkLine(cellButtons[2], cellButtons[4], cellButtons[6])) {
            return true;
        }
        return false;
    }

    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        if (b1.getText().equals(b2.getText()) && b1.getText().equals(b3.getText()) && !b1.isEnabled()) {
            String winner = b1.getText().equals("X") ? playerXName : playerOName;
            JOptionPane.showMessageDialog(gameFrame, winner + " wins!");
            resetGame();
            return true;
        }
        return false;
    }

    private boolean checkForTie() {
        for (JButton button : cellButtons) {
            if (button.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        for (JButton button : cellButtons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(Color.LIGHT_GRAY); // Reset background color
        }
        isXTurn = true;
        turnLabel.setText(playerXName + "'s turn");
    }

    public static void main(String[] args) {
        new TicTacToeGame();
    }
}