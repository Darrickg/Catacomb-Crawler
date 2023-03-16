package GameStates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenuState extends JPanel implements GameState, ActionListener {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("Main Menu");
    private JButton startButton;
    private JButton exitButton;
    public void init() {
        // Create the start button
        startButton = new JButton("Start Game");
        startButton.setBounds(100, 100, 100, 50); // x, y, width, height
        startButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
        add(startButton);

        // Create the exit button
        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(this);
        exitButton.setBounds(250, 100, 100, 50); // x, y, width, height
        exitButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        add(exitButton);

        // Initialize the main menu state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        stateManager.setCurrentState(new MainMenuState());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
    }

    public void update() {
        // Update the main menu state.
    }

    public void render() {
        // Render the main menu state.
    }



    private GameStateManager getStateManager() {
        return stateManager;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (stateManager.getCurrentState() != null) {
                System.out.println("Closing MenuState");
                frame.dispose();
            }
            getStateManager().setState(new RunningState());
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
