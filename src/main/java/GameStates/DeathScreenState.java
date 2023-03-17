package GameStates;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DeathScreenState extends JPanel implements GameState, ActionListener {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("Game Over");
    private JButton restartButton;
    private JButton exitButton;

    private Clip endMusicClip;

    public void init() {
        try {
            AudioInputStream endMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamelose.wav"));
            this.endMusicClip = AudioSystem.getClip();
            this.endMusicClip.open(endMusic);
            this.endMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }

        // Initialize the death screen state.
        // Create the start button
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(100, 100, 100, 50); // x, y, width, height
        restartButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        restartButton.setBackground(Color.GREEN);
        restartButton.setForeground(Color.WHITE);
        restartButton.addActionListener(this);
        restartButton.setOpaque(true);
        restartButton.setBorderPainted(false);
        add(restartButton);

        // Create the exit button
        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(this);
        exitButton.setBounds(250, 100, 100, 50); // x, y, width, height
        exitButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        add(exitButton);

        // Initialize the main menu state.
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        stateManager.setCurrentState(new DeathScreenState());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
    }

    public void update() {
        // Update the death screen state.
    }

    public void render() {
        // Render the death screen state.
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            if (stateManager.getCurrentState() != null) {

                try {
                    AudioInputStream buttonSound = AudioSystem.getAudioInputStream(new File("assets/audio/select.wav"));
                    Clip buttonSoundClip = AudioSystem.getClip();
                    buttonSoundClip.open(buttonSound);
                    buttonSoundClip.start();
                } catch (Exception e2) {
                    System.out.println("Error playing music: " + e2.getMessage());
                }

                this.endMusicClip.stop();
                
                stateManager.setCurrentState(new RunningState());
                System.out.println("Restarting game");
                frame.dispose();
            }
            getStateManager().setState(new RunningState());
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }

    }

    private GameStateManager getStateManager() {
        return stateManager;
    }
}