package GameStates;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * deathScreen with all fields as parameters describe game over panel
 */

public class DeathScreenState extends JPanel implements GameState, ActionListener {
    private GameStateManager stateManager = new GameStateManager();

    private JButton restartButton;
    private JButton exitButton;

    private Clip endMusicClip;
    JFrame frame = new JFrame("Game Over");
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
        restartButton = new JButton("Restart Game");
        restartButton.setBounds(width/2 - 300, 200, 200, 50); // x, y, width, height
        restartButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        restartButton.setBackground(Color.GREEN);
        restartButton.setForeground(Color.WHITE);
        restartButton.addActionListener(this);
        panel.add(restartButton);

        // Create the exit button
        exitButton = new JButton("Exit Game");
        exitButton.setBounds(width/2 + 100, 200, 200, 50); // x, y, width, height
        exitButton.setFont(new Font("Arial", Font.BOLD, 20)); // font name, style, size
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        panel.add(exitButton);
    
        // Add the image
        ImageIcon myImageIcon = new ImageIcon("assets/screens/deathscreen.png");
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
            AudioInputStream endMusic = AudioSystem.getAudioInputStream(new File("assets/audio/gamelose.wav"));
            this.endMusicClip = AudioSystem.getClip();
            this.endMusicClip.open(endMusic);
            this.endMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    /**
     * deathScreen updater
     */
    public void update() {
        // Update the death screen state.
    }

    /**
     * deathScree render
     */
    public void render() {
        // Render the death screen state.
    }

    /**
     * DeathScreen menu detect user action
     * @param e player action input
     */
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

    /**
     * return to stateManager
     * @return GameStateManager
     */
    private GameStateManager getStateManager() {
        return stateManager;
    }
}