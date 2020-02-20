import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/* BONUS WORK:
 * Added a JComboBox for instrument selection.
 * Added a JTextArea for tempo.
 * Added a help window.
 * Made the repeat song a JTextArea so the user is not limited to 10 repeats.
 * Added the black keys.
 * Added a custom color.*/

/** Constructs the GUI and handles user interaction.
 * @author Meredith Myers
 */
public class Piano implements ActionListener {

    /** GUI frame.*/
    private JFrame frame;
    /** Allows user to type in notes to play.*/
    private JTextArea entryBox;
    /** Allows selection from various instruments.*/
    private JComboBox instrument;
    /** Allows selection from various instruments.*/
    private JComboBox style;
    /** Allows user to enter desired tempo.*/
    private JTextArea tempo;
    /** Allows user to change key **/
    private JComboBox key;
    /** The total number of notes.*/
    public static final int NUM_KEYS = 7;
    /** How many octaves should be created.*/
    public static final int NUM_OCTAVES = 3;
    /** Holds the possible notes.*/
    private String[] notes = {"C","D","E","F","G","A","B"};
    /** Holds the possible sharps.*/
    private String[] sharps = {"C#","D#","F#","G#","A#"};
    /** Holds the octave numbers.*/
    private String[] octave = {"4","5","6"};
    /** Holds the possible instruments*/
    private String[] instruments = {"Piano", "Voice", "Horn","Violin"};
    /** Holds possible keys **/
    private String[] keys = {"C", "C#", "D", "D#/Eb", "E", "F", "F#", "G", "G#/Ab", "A", "Bb", "B"};
    /** Holds the possible styles*/
    private String[] styles = {"Classical", "Minor Classical", "Jazz", "Blues"};
    /** Holds which instrument is currently selected.*/
    private String instrumentType = "I[Piano]";
    /** Custom color for GUI entry fields*/
    private Color customColor = new Color(170,180,254);
    /** Border for entry fields*/
    private Border border = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

    static boolean entered;
    static public int[] notesEntered = new int[8];
    //public int[] notesEntered = {1, 3, 2, 5, 3, 6, 4, 1};
    int numNotes = 0, transpositionKey = 0;
    final JFXPanel fxPanel = new JFXPanel();
    static int timeVar = 120;

    String styleInput = "Classical", instrumentInput = "Piano", keyInput = "C";

    /** Constructs the GUI */
    public Piano(){
        clearSound();

        // ------------ Create GUI -----------
        frame = new JFrame("Piano GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the mainPanel
        Container mainPanel = frame.getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setForeground(Color.WHITE);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

        // ---- Instrument and tempo panel ----
        JPanel iTpanel = new JPanel();
        iTpanel.setLayout(new BoxLayout(iTpanel,BoxLayout.X_AXIS));
        iTpanel.setForeground(Color.WHITE);
        iTpanel.setBackground(Color.BLACK);
        iTpanel.add(Box.createRigidArea(new Dimension(180,0)));

        // Instrument label
        JLabel instrumentLabel = new JLabel("Instrument:");
        instrumentLabel.setForeground(Color.WHITE);
        instrumentLabel.setBackground(Color.BLACK);
        iTpanel.add(instrumentLabel);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Instrument combo box
        instrument = new JComboBox(instruments);
        instrument.setName("instrument");
        instrument.addActionListener(this);
        instrument.setForeground(Color.WHITE);
        instrument.setBackground(Color.BLACK);
        iTpanel.add(instrument);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Style label
        JLabel styleLabel = new JLabel("Style:");
        styleLabel.setForeground(Color.WHITE);
        styleLabel.setBackground(Color.BLACK);
        iTpanel.add(styleLabel);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Style combo box
        style = new JComboBox(styles);
        style.setName("style");
        style.addActionListener(this);
        style.setForeground(Color.WHITE);
        style.setBackground(Color.BLACK);
        iTpanel.add(style);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Tempo label
        JLabel tempoLabel = new JLabel("Tempo:");
        tempoLabel.setForeground(Color.WHITE);
        tempoLabel.setBackground(Color.BLACK);
        iTpanel.add(tempoLabel);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Tempo text area
        tempo = new JTextArea();
        tempo.setName("tempo");
        tempo.setText("120");
        tempo.setFont(new Font("Ariel", Font.BOLD, 14));
        tempo.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //nothing
            }

            @Override
            public void focusLost(FocusEvent e) {
                timeVar = Integer.parseInt(tempo.getText());
            }
        });
        tempo.setBorder(border);
        tempo.setForeground(Color.BLACK);
        tempo.setBackground(customColor);
        iTpanel.add(tempo);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Key label
        JLabel keyLabel = new JLabel("Key:");
        styleLabel.setForeground(Color.WHITE);
        styleLabel.setBackground(Color.BLACK);
        iTpanel.add(keyLabel);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Key combo box
        key = new JComboBox(keys);
        style.setName("key");
        style.addActionListener(this);
        style.setForeground(Color.WHITE);
        style.setBackground(Color.BLACK);
        iTpanel.add(key);
        iTpanel.add(Box.createRigidArea(new Dimension(20,0)));

        // Add iTpanel to mainPanel
        mainPanel.add(iTpanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));

        // -------- piano keys panel --------

        // Call the make keys method
        JLayeredPane pianoKeyPanel = makeKeys();
        // Add to main panel
        mainPanel.add(pianoKeyPanel);

        // ---------- Notes Panel -----------

        // Create the notes panel
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel,BoxLayout.X_AXIS));
        notesPanel.setForeground(Color.WHITE);
        notesPanel.setBackground(Color.BLACK);
        notesPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        // Make notes label
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setForeground(Color.WHITE);
        notesLabel.setBackground(Color.BLACK);
        notesPanel.add(notesLabel);
        notesPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Create entry box
        entryBox = new JTextArea();
        entryBox.setBorder(border);
        entryBox.setFont(new Font("Ariel", Font.BOLD, 14));
        entryBox.setForeground(Color.BLACK);
        entryBox.setBackground(customColor);
        notesPanel.add(entryBox);
        notesPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        // Add the top panel to the main panel
        mainPanel.add(Box.createRigidArea(new Dimension(0,50)));
        mainPanel.add(notesPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Create the reset button
        JButton playButton = new JButton("Reset");
        playButton.setName("reset");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(Color.BLACK);

        // Add the action listener
        playButton.addActionListener(this);

        // Add Reset button to the mainPanel
        mainPanel.add(playButton);
        mainPanel.add(Box.createRigidArea(new Dimension(50, 20)));

        // Show the window
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(900,320);

    }
    /** Creates the panel containing all of the piano keys.
     * @return the panel containing the keys. */
    public JLayeredPane makeKeys(){
        // Initialize
        String name = "";
        int x = 320;
        int y = 0;

        // Create layerPane
        JLayeredPane keyBoard = new JLayeredPane();
        keyBoard.setPreferredSize(new Dimension(900,162));
        keyBoard.add(Box.createRigidArea(new Dimension(x, 0)));

        // Add the white key buttons
            for(int j=0; j<7; j++) {
                switch(j){
                    case 0:
                        name = "A4";
                        break;
                    case 1:
                        name = "B4";
                        break;
                    case 2:
                        name = "C5";
                        break;
                    case 3:
                        name = "D5";
                        break;
                    case 4:
                        name = "E5";
                        break;
                    case 5:
                        name = "F5";
                        break;
                    case 6:
                        name = "G5";
                        break;
                }
                ImageIcon img = new ImageIcon("images/" + notes[j] + ".png");
                JButton jb = new JButton(img);
                jb.setName(name);
                jb.setActionCommand(name);
                jb.addActionListener(this);
                jb.setBounds(x, y, 35, 162);
                keyBoard.add(jb, new Integer(1));
                keyBoard.add(Box.createRigidArea(new Dimension(2, 0)));
                x += 37;
            }

        // Reinitialize
        x = 320;

        // Add the black keys

            ImageIcon img = new ImageIcon("images/blackKey.png");

            // Make 5 "keys"

            JButton jb0 = new JButton(img);
            jb0.setName("A-4");
            jb0.setActionCommand("A-4");
            jb0.addActionListener(this);

            JButton jb1 = new JButton(img);
            jb1.setName("C-5");
            jb1.setActionCommand("C-5");
            jb1.addActionListener(this);

            JButton jb2 = new JButton(img);
            jb2.setName("D-5");
            jb2.setActionCommand("D-5");
            jb2.addActionListener(this);

            JButton jb3 = new JButton(img);
            jb3.setName("F-5");
            jb3.setActionCommand("F-5");
            jb3.addActionListener(this);

            JButton jb4 = new JButton(img);
            jb4.setName("G-5");
            jb4.setActionCommand("G-5");
            jb4.addActionListener(this);

            // Place the 5 keys
            jb0.setBounds(20+(x),y,25, 65);
            keyBoard.add(jb0,new Integer(2));

            jb2.setBounds(130+(x),y,25,65);
            keyBoard.add(jb1,new Integer(2));

            jb3.setBounds(200+(x),y,25,65);
            keyBoard.add(jb2,new Integer(2));

            jb1.setBounds(90+(x),y,25,65);
            keyBoard.add(jb3,new Integer(2));

            jb4.setBounds(240+(x),y,25,65);
            keyBoard.add(jb4,new Integer(2));
        // Return the keyboard
        return keyBoard;
    }

    /** Creates a piano object. */
    public static void main(String[] args) {
        new Piano();
    }

    public void playSong(){
        for(int i = 0; i < 8; i++){
            System.out.print(notesEntered[i] + ", ");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Initialize
        String command = "";
        JButton jb = null;
        String name = "";

        // Get which object was clicked
        Object obj = e.getSource();

        // If the object was a JComboBox
        if (obj instanceof JComboBox){
            styleInput = (String)style.getSelectedItem();
            if(styleInput.equals("Minor Classical")){
                styleInput = "ClassicalMinor";
            }
            instrumentInput = (String)instrument.getSelectedItem();
            keyInput = (String)key.getSelectedItem();
            transpositionKey = findTranspositionKey(keyInput);
        }
        // Else the object is a JButton
        else{
            jb = (JButton)obj;
            name = jb.getName();
        }

        // If the JButton is the reset button:
        if (name.equals("reset")){
            entered = false;
            numNotes = 0;
            entryBox.setText("");
        }
        else if(obj instanceof JComboBox){
            // Do nothing
        }
        // Else a key was clicked
        else{
            // Get the action command
            command = jb.getActionCommand();
            // Add that string to the text field
            entryBox.append(command+" ");
            if(numNotes < 8) {
                switch (command) {
                    case ("C5"):
                        notesEntered[numNotes] = 1;
                        playFile("c5");
                        numNotes++;
                        break;
                    case ("C-5"):
                        playFile("c-5");
                        break;
                    case ("D5"):
                        notesEntered[numNotes] = 2;
                        playFile("d5");
                        numNotes++;
                        break;
                    case ("D-5"):
                        playFile("d-5");
                        break;
                    case ("E5"):
                        notesEntered[numNotes] = 3;
                        playFile("e5");
                        numNotes++;
                        break;
                    case ("F5"):
                        notesEntered[numNotes] = 4;
                        playFile("f5");
                        numNotes++;
                        break;
                    case ("F-5"):
                        playFile("f-5");
                        break;
                    case ("G5"):
                        notesEntered[numNotes] = 5;
                        playFile("g5");
                        numNotes++;
                        break;
                    case ("G-5"):
                        playFile("g-5");
                        break;
                    case ("A4"):
                        notesEntered[numNotes] = 6;
                        playFile("a5");
                        numNotes++;
                        break;
                    case ("A-4"):
                        playFile("a-5");
                        break;
                    case ("B4"):
                        notesEntered[numNotes] = 7;
                        playFile("b5");
                        numNotes++;
                        break;
                    case ("B-4"):
                        playFile("b-5");
                        break;
                    default:
                        break;
                }
            } if(numNotes == 8){
                System.out.println("Entered: ");
                for(int i = 0; i < 8; i++){
                    System.out.print(notesEntered[i] + " ");
                }
                entered = true;
            }
        }

    }

    void playFile(String note){
        try {
            String bip = note + ".mp3";
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearSound(){
        try {
            String bip = "a4.mp3";
            Media hit = new Media(new File(bip).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.setVolume(0);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getKey(){
        return transpositionKey;
    }
    public String getStyle(){
        return styleInput;
    }

    public int findTranspositionKey(String note){
        int returnValue = 0;
        switch(note){
            case "C":
                returnValue = 0;
                break;
            case "C#":
                returnValue = 1;
                break;
            case "D":
                returnValue = 2;
                break;
            case "D#/Eb":
                returnValue = 3;
                break;
            case "E":
                returnValue = 4;
                break;
            case "F":
                returnValue = 5;
                break;
            case "F#":
                returnValue = 6;
                break;
            case "G":
                returnValue = 7;
                break;
            case "G#/Ab":
                returnValue = 8;
                break;
            case "A":
                returnValue = 9;
                break;
            case "Bb":
                returnValue = 10;
                break;
            case "B":
                returnValue = 11;
                break;
        }
        return returnValue;
    }
}