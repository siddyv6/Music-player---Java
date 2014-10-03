package coursework_jukebox;

import java.awt.*;/*Imports the classes of toolkits needed and enables us to use components buttons and text areas.*/
import java.awt.event.*;//Imports classe needed to listen to events that occur i.e. Actionlistener to listen to buttons for when they are pressed
import javax.swing.*;

public class CheckLibrary extends JFrame
                  implements ActionListener { 
    JTextField trackNo = new JTextField(10);//Creates a text field where the user can input data, the text field is called trackNo
    TextArea information = new TextArea(10, 65);//This time an text area is created which can be used to display information to the user, this can be by choice edited by the user if wanted
    JButton list = new JButton("List All Tracks");//Creates an Button called list which has got follwing wors displayed inside of the button "List All Tracks".
    JButton check = new JButton("Check Track");//Again creates another buttons called check and has "check Track" written inside of the button
   JButton ratings = new JButton("Ratings");//A button is created called ratings
   JButton Mostplayed = new JButton("Most played");//A button is created which says mostplayed
    JComboBox select = new JComboBox();//A combobox is screated called select
    
    public CheckLibrary() {// Creates a method called CheckLibrary inside a method Boths these methods are non static
        setLayout(new BorderLayout());// Creates a border layout in which the buttons and everyhting else shall fit in 
        setBounds(100, 100, 650, 250);//Sets the layout to have a size consiting of 400 in width and height of 200 while being placed 100, 100 away from the edge of the screen 
        setTitle("Check Library");//Sets the tile of the windows which opens to be called Check Library and appreas at the top.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Closes the window when the X is pressed, but not the whole application
        JPanel top = new JPanel();//Creates a new JPanel with a specifc layout
        top.add(new JLabel("Enter & Choose Title/Album/Artist"));//Creates a label inside the window (at the top) next to the text field which says Entr Track Number. 
        top.add(trackNo);//adds the button Trackno to the window which shall be located at the top
        top.add(select);//Adds the button select button inside the window at the top.
        top.add(check);//Adds the button select button inside the window at the top.
        top.add(list);//Again adds the button list to the top in the windows
        list.addActionListener(this);
        select.addActionListener(this);
        trackNo.addActionListener(this);
        check.addActionListener(this);
        ratings.addActionListener(this);
        Mostplayed.addActionListener(this);
        //A actionlistener is added to each of these buttons so thst when the button is pressed the actions is registered.
        add("North", top);//Adds a location being North. Meaning buutons and other things can be specified to where they want.
        JPanel middle = new JPanel();//Creates a new Jpanel called JMiddle
       JPanel bottom = new JPanel();//
       
        information.setText( "TrackID - Title - Artists\n" + LibraryDatabase.listAll());//Gets ready to Set the text from Libary data in the Text area called information
        middle.add(information);//Adds the information inside the windows specified to the middle
        bottom.add(new JLabel("Sort by:"));//Creates a label inside the window (at the top) next to the text field which says Entr Track Number. 
        bottom.add(ratings);
        bottom.add(Mostplayed);
        //Most od thw 
        add("Center", middle);// Creates the location middle being the center where buttons can be added to.
        information.setEditable(false);
            add("South", bottom);//Adds a location being South of the borderlayout, where bottons can be placed

        setResizable(false);//Prevents the resizing of the window
        setVisible(true);//Allows the contents of the to be seen as it is set to true
        
        
        String[] IDS = { "Titles", "Artist", "Album"};
        for( int i = 0; i < IDS.length; i++ )
		    select.addItem( IDS[i] );
        
    }//Closes the method

    public void actionPerformed(ActionEvent e) {//Creates another Public method called action performed. It has an action event which is called e
        information.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());//
        if (e.getSource() == list) {// If the list(List all tracks) button is pressed something shall occur. This is done by using the e.getsource == list as there is an actionlistener on the button it detects if the button gets pressed.
            information.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());//The information from Library data is presented in the Text area called information
        } else {//Something else may happen if the first critiria isn't meet.
            String key2 = LibraryDatabase.selectTitle(trackNo.getText());//
            int artist2 = LibraryDatabase.getPlayCount(key2);
            //String key2 is used to select the the tile ofthe song by using the trackId
            //then the int artist2 is used to store the playcount
             if (select.getSelectedIndex() == 0 && e.getSource() == check) {
                 if (!trackNo.getText().isEmpty() && artist2 != -1) {
                 String key = LibraryDatabase.selectTitle(trackNo.getText());//
                 String name = LibraryDatabase.getName(key);//
                trackNo.setText(null);
                information.setText(name + "-" + LibraryDatabase.getArtist(key));
                information.append("\nRating: " + stars(LibraryDatabase.getRating(key)));
                information.append("\nPlay count: " + LibraryDatabase.getPlayCount(key)); 
                    } else {
                        JOptionPane.showMessageDialog(null,"Please make sure you have entered the correct title & try again!");
                    }
                 //IF the first combo box is selects the check button is pressed the nested if then it checks that trackno isn;t empty and that the playcount isnt = -1
                 //Then a string key is created which is the executed method selecttile which select the title. Then the string name is made which gets the name by using the string key
                 //the text field is the set to null and then the text area is used to set the name of the and the return string form the method get artist to get the artis of the song
                 //the textarea, is then appended so the rating is displayed ad then it is appned so the playcount is views
                 //Else an eeror message is pop uped
            } else if (select.getSelectedIndex() == 1 && e.getSource() == check) {
                if (!trackNo.getText().isEmpty()) {
                    String key = trackNo.getText();
                    trackNo.setText(null);
                    information.setText(LibraryDatabase.selectArtist(key));
                        } else {                 
                         JOptionPane.showMessageDialog(null,"Please make sure you have entered the correct artist & try again!");
                               information.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());//The information from Library data is presented in the Text area called information
//If the 2 combo box is selected and the check button is pressed the nested if is then executed or the error message is poped up
//The nested if checks if that the track choice isnt empty then the string key is made with the text retrived from the trackno.
//Then teh text field is set to null and the infration text area is set to the the select artists
                }
                } else if (select.getSelectedIndex() == 2 && e.getSource() == check) {
                    if (!trackNo.getText().isEmpty()) {
                        String key = trackNo.getText();//
                        information.setText(LibraryDatabase.selectAlbum(key));            
                     } else { JOptionPane.showMessageDialog(null,"Please make sure you have entered the correct album & try again!");                
                                        information.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());//The information from Library data is presented in the Text area called information
                    }//If the 3 combo box is selected and the check button is pressed then the nested if is executed or else the error pop upo is displayed
//if the track no isnt empty then the sting key is made with the track no and then the information text area is set to select album where the albulm is schose and viewed
                    //else the information text area is set to view all track in the library and then the error is showed up 
                } else if (e.getSource()==ratings) {
                 String key = trackNo.getText();
                 information.setText("TrackID - Title - Ratings" + "\n" + LibraryDatabase.SortRatings(key));
                 //if the ratings button is placed the stringkey is set to trackno.getTExt and then the text area info is set to sort by ratings
            } else if (e.getSource()==Mostplayed) {
                String key = trackNo.getText();
                information.setText("TrackID - Title - Play count" + "\n" + LibraryDatabase.SortPlayHits(key));
                //Else if the most played button is played and the strinkg key is made with the track ~ID and then the sort playlist method is executed. 
            } 
        }//Closes the nested if
    }//Closes the method

    public static String stars(int rating) {//Creats a private method which is only accessible here, called stars with an int called rating
        String stars = "";//Creates a string called stars with no real characters.
        for (int i = 0; i < rating; ++i) {//For i=0 and i is less than the int rating you add 1 to i
            stars += "*";//Stars variable is = stars + * = Stars
        }//Closes for statement
        return stars;//Returns the stars obtained
    }//Closes the method
}//Closes the whole class