package coursework_jukebox;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Jukebox extends JFrame
        implements ActionListener {

    JButton check = new JButton("Check Library");
    JButton playlist = new JButton("Create Playlist");
    JButton update = new JButton("Update Library");
    JButton quit = new JButton("Exit");

    public static void main(String[] args) {
        new Jukebox();
    }

    public Jukebox() {

        setLayout(new BorderLayout());
        setSize(450, 100);
        setTitle("Jukebox");

        // close application only by clicking the quit button
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel top = new JPanel();
        top.add(new JLabel("Select an option by clicking one of the buttons below"));
        add("North", top);

        JPanel bottom = new JPanel();
        bottom.add(check);
        check.addActionListener(this);
        bottom.add(playlist);
        playlist.addActionListener(this);
        bottom.add(update);
        update.addActionListener(this);
        bottom.add(quit);
        quit.addActionListener(this);
        add("South", bottom);

        setResizable(false);
        setVisible(true);
    }//Here is where the GUI is set up and added into the window, its starts of with setting a basic layout 
//and setting p the buttons for the jukebux. Finally the buttons are adding to there own jpoanels. 
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == check) {
            new CheckLibrary();
        } else if (e.getSource() == playlist) {
            new CreatePlaylist();
        } else if (e.getSource() == update) {
            new UpdateTracks();
        } else if (e.getSource() == quit) {
            LibraryDatabase.close();
            System.exit(0);
        }
    }//If staements set, so if one of the buttons is pressed, one of the buttons will open 
}