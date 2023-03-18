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

    private JButton startButton;
    private JButton exitButton;

    private Clip startMusicClip;
    JFrame frame = new JFrame("Main Menu");
    public void init() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception eButt) {
            eButt.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        frame.setSize(width, height);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.setLayout(null);
       //panel.setLayout(new OverlayLayout(panel));

        // Create the start button
        startButton = new JButton("Start Game");
        startButton.setBounds(width/2 - 300, 200, 200, 50); // x, y, width, height
        startButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        startButton.setBackground(Color.GREEN);
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(this);
        panel.add(startButton);

        // Create the exit button
        exitButton = new JButton("Exit Game");
        exitButton.setBounds(width/2 + 100, 200, 200, 50); // x, y, width, height
        exitButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        panel.add(exitButton);
    
        // Add the image
        ImageIcon myImageIcon = new ImageIcon("assets/screens/menuscreen.png");
        Image myImage = myImageIcon.getImage();
        Image scaledImage = myImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale the image to fit the panel
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledImageIcon);
        label.setBounds(0, 0, width, height);
        panel.add(label);

        // Initialize the main menu state.
        stateManager.setCurrentState(new MainMenuState());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.add(panel); // ADD THE PANEL TO THE FRAME INSTEAD
        frame.setVisible(true);
        frame.setSize(1920, 1080);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

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
