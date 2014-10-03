package coursework_jukebox;

import java.awt.*;/*Imports the classes of toolkits needed and enables us to use components buttons and text areas.*/
import java.awt.event.*;//Imports classe needed to listen to events that occur i.e. Actionlistener to listen to buttons for when they are pressed
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.UIManager.*;

public class CreatePlaylist extends JFrame
        implements ActionListener { //Creats a public classs(non-static) acessable by all called CheckLibary which adds and implements an actionlisten for needed buttons

    JTextField trackNoChoice = new JTextField(3);//Creates a text field where the user can input data, the text field is called trackNo
    JTextField PlaylistName = new JTextField(10);//Crreates a Txtfeld where the user can enter the playlist name
    TextArea information2 = new TextArea(6, 45);//This time an text area is created which can be used to display information to the user, this can be by choice edited by the user if wanted
    TextArea information = new TextArea(6, 45);//textarea displays all tracks
    JButton add = new JButton("Add to playlist");//Adds a button which adds the track to the playlist
    JButton remove = new JButton("Remove from Playlist");//Adds a button which removes track from playlists
    JButton createPlaylist = new JButton("Create Playlist");//Adds a button which creates a playlists
    JButton openPlaylist = new JButton("Open an exsiting Playlist");//Adds a button which opens an exsiting playlists
    JButton viewPlaylists = new JButton("View all Playlist");//Adds a button which views all playlists
    JButton reset = new JButton("Reset the current playlist");//Resets the playlist and text area
    ImageIcon nextImg = new ImageIcon("src/coursework_jukebox/control_double_right.png");//Creates an icon 
    JButton next = new JButton(nextImg);//Adds a button which skips to the next song
    ImageIcon previousImg = new ImageIcon("src/coursework_jukebox/control_double_left.png");
    JButton previous = new JButton(previousImg);//Adds a buttons which goes back to the last song.
    ImageIcon playImg = new ImageIcon("src/coursework_jukebox/play.png");
    JButton play = new JButton(playImg);//

    public CreatePlaylist() {// Creates a class called CreatePlaylist inside a class Boths these classes are non static
        setLayout(new BorderLayout());// Creates a border layout in which the buttons and everyhting else shall fit in 
        setBounds(100, 100, 780, 400);//Sets the layout to have a size consiting of 400 in width and height of 200 while being placed 100, 100 away from the edge of the screen 
        setTitle("Create playlist");//Sets the tile of the windows which opens to be called Check Library and appreas at the top.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Closes the window when the X is pressed, but not the whole application
        JPanel top = new JPanel();//Creates a new JPanel with a specifc layout
        JPanel bottom = new JPanel();//
        JPanel right = new JPanel();

        bottom.add(previous);//Add the previous button at the bottom of the borderlayout.
        bottom.add(play);//Adds the play button on the top.
        bottom.add(next);//Adds the next button to the bottom, which skips to the next track.

        createPlaylist.addActionListener(this);
        openPlaylist.addActionListener(this);
        add.addActionListener(this);
        reset.addActionListener(this);
        play.addActionListener(this);
        next.addActionListener(this);//
        previous.addActionListener(this);//
        remove.addActionListener(this);
        viewPlaylists.addActionListener(this);
//All the butons which were made have had an actionlistenrer added to them so when the button is pressed it is registered,

        add("North", top);
        add("South", bottom);
        add("East", right);
        JPanel middle = new JPanel();
 //New JPanels are made to add on various elements on to in
        information2.setText("TrackID - Title - Artist\n" + LibraryDatabase.listAll());
        information2.setEditable(false);
        information.setText("PlaylistID - Playlist name: \n" + LibraryDatabase.viewALLPlaylists());
        information.setEditable(false);
     
        top.add(new JLabel("Enter new Playlist name or Choose an existing one:"));
        top.add(PlaylistName);
        top.add(createPlaylist);
        top.add(openPlaylist);
// A new JLabel is added to the north JPanel. After a new textfield and then 2 buttons 
        
        middle.add(new JLabel("Enter TrackID to save to/remove from playlist:"));//Creates a label inside the window (at the top) next to the text field which says Entr Track Number. 
        middle.add(add);
        middle.add(trackNoChoice);
        middle.add(remove);
        middle.add(information2);
        middle.add(information);//
        middle.add(viewPlaylists);
//Again a new JLabel iss added to the middle jpanel alongside a 3 buttons and one textfield to enter he track no. There are also 2 text fields added. 
       
        add("Center", middle);
        
        setResizable(false);//Prevents the resizing of the window
        setVisible(true);//Allows the contents of the to be seen as it is set to true
    }//Closes the class

    public void actionPerformed(ActionEvent e) {//Creates another Public class called action performed. It has an action event which is called e

        String key = trackNoChoice.getText();
         if (e.getSource() == createPlaylist) {
            if (!PlaylistName.getText().isEmpty()) {
                String playlistName = PlaylistName.getText();
                LibraryDatabase.createPlaylist(playlistName);
                JOptionPane.showMessageDialog(null, "Playlist Created! Now add someSongs!");

            } else {
                JOptionPane.showMessageDialog(null, "Please Enter a playlist name and try again!");
            } 
            // If the create playist is pressed, the neted if check if that the playlist name isn't empty, it will sctreat a string callled playl
            //name which gets the text from the textfield playlistname. Then the method is retrived from LibraryDatabase.createplalist. From which a playlist is made and then a message popup is showed.
        } else if (e.getSource() == add) {
             if (!PlaylistName.getText().isEmpty()) {
            String playlistName = PlaylistName.getText();
            LibraryDatabase.songToPlaylist(playlistName, key);
            information.setText(LibraryDatabase.viewPlaylist(playlistName));
            } else {  JOptionPane.showMessageDialog(null, "Please Reopen the playlist and try again!");
             } 
        } else if (e.getSource() == play) {
                LibraryDatabase.incrementPlayCount(key);
            //If the add button is pressed it firstly it reset the textfield where the trackno is added, to prevent confusion
            //Then a string is made called playlistname and the text is retrived from the textfield which the the playlist name is entered.
            //Then the method is triggered and uses the key string and playlist name string  and adds the song to the playlist and then the playlist is viewed by retriving a method and setting the text for the text area
        } else if (e.getSource() == reset) {
            information.setText(null);
            trackNoChoice.setText(null);
            //If rest is pressed, the information text area is set to null and so is the text field where track number is entered.
        } else if (e.getSource() == remove) {
            if (!PlaylistName.getText().isEmpty()) {
            String playlistName = PlaylistName.getText();
            LibraryDatabase.removeSongsPlaylist(playlistName, key);
            information.setText(LibraryDatabase.viewPlaylist(playlistName));
            } else {  JOptionPane.showMessageDialog(null, "Please Reopen the playlist and try again!");
            } 
            //if te remove button is pressed a string playlist is created from the playlist txtfield and the removesong from playlist methos is retrive and executed using the key string and playlist string
            // And then the text area information is set to view the playlist by using a method 
        }else if (e.getSource()== viewPlaylists) { 
            information.setText("PlaylistID - Playlist name \n" + LibraryDatabase.viewALLPlaylists());
            //When the viewplaylist button is pressed the text area information is set to a certain string and then the ViewALLplaylist is executeted from library database and the returned strins are added.
        } else if (e.getSource() == openPlaylist) {
            if (!PlaylistName.getText().isEmpty()) {
            trackNoChoice.setText(null);
            String playlistName = PlaylistName.getText();
            information.setText(LibraryDatabase.viewoldplaylist(playlistName));
            } else { 
                                JOptionPane.showMessageDialog(null, "Please Enter a playlist name and try again!");

            }//If the open playlist button is pressed the nested if then checks that the playlist name text field isn't empty then the textfield where track ischossed is set to null 
            //and another String playlist name is made with the text in the text field where the playlist name is added USing that the viewoldplaylist from library database is viewed executed and thhen the returned strings will be used to set the txt area.
            //else if the playlist name text field isempty then as popup message is showed
        }
    }
}

