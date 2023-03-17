package GameStates;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * mainMenuScreen with all fields as parameters describe game menu panel
 */
public class MainMenuState extends JPanel implements GameState, ActionListener {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private GameStateManager stateManager = new GameStateManager();
    JFrame frame = new JFrame("Main Menu");
    private JButton startButton;
    private JButton exitButton;
    private Clip startMusicClip;

    /**
     * main menu state initializer
     */
    public void init() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception eButt) {
            eButt.printStackTrace();
        }

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

        try {
            AudioInputStream startMusic = AudioSystem.getAudioInputStream(new File("assets/audio/startmusic.wav"));
            this.startMusicClip = AudioSystem.getClip();
            this.startMusicClip.open(startMusic);
            this.startMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    /**
     * main menu state updater
     */
    public void update() {
        // Update the main menu state.
    }

    /**
     * main menu state render
     */
    public void render() {
        // Render the main menu state.
    }


    /**
     * game state getter
     * @return state manager
     */
    private GameStateManager getStateManager() {
        return stateManager;
    }


    /**
     * DeathScreen menu detect user action
     * @param e player action input
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (stateManager.getCurrentState() != null) {
                try {
                    AudioInputStream buttonSound = AudioSystem.getAudioInputStream(new File("assets/audio/select.wav"));
                    Clip buttonSoundClip = AudioSystem.getClip();
                    buttonSoundClip.open(buttonSound);
                    buttonSoundClip.start();
                } catch (Exception e2) {
                    System.out.println("Error playing sound: " + e2.getMessage());
                }

                startMusicClip.stop();
                
                System.out.println("Closing MenuState");
                frame.dispose();
            }
            getStateManager().setState(new RunningState());
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
