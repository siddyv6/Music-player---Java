package coursework_jukebox;

import java.awt.*;/*Imports the classes of toolkits needed and enables us to use components buttons and text areas.*/
import java.awt.event.*;//Imports classe needed to listen to events that occur i.e. Actionlistener to listen to buttons for when they are pressed
import javax.swing.*;

public class UpdateTracks extends JFrame
        implements ActionListener { //Creats a public classs(non-static) acessable by all called CheckLibary which adds and implements an actionlisten for needed buttons

    JTextField trackNoChoice = new JTextField(4);//Creates a text field where the user can input data, the text field is called trackNo
    JTextField ratingsUpdate = new JTextField(4);//Creates a Text field for user to enter new ratings
    TextArea information2 = new TextArea(10, 70);//This time an text area is created which can be used to display information to the user, this can be by choice edited by the user if wanted
    JButton track = new JButton("Choose track");//Creates a button which enables users to view the details on that one track
    JButton update = new JButton("Update current track ratings");//Adds a button which adds the track to the playlist
    JButton list2 = new JButton("View all tracks");//Creates an Button called list which has got follwing wors displayed inside of the button "List All Tracks".
    JButton reset = new JButton("Reset");//Resets the fields & tracks.
    JButton Add = new JButton("Add or Remove Songs");//

    public UpdateTracks() {// Creates a class called CheckLibrary inside a class Boths these classes are non static
        setLayout(new BorderLayout());// Creates a border layout in which the buttons and everyhting else shall fit in 
        setBounds(100, 100, 800, 300);//Sets the layout to have a size consiting of 400 in width and height of 200 while being placed 100, 100 away from the edge of the screen 
        setTitle("Update Libaray");//Sets the tile of the windows which opens to be called Check Library and appreas at the top.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Closes the window when the X is pressed, but not the whole application
        JPanel top = new JPanel();//Creates a new JPanel with a specifc layout
        JPanel bottom = new JPanel();//
        JPanel middle = new JPanel();//Creats a new JPanel where bottons are specifed and added, here it is south of the window

        top.add(new JLabel("Enter the track number you wish to view: "));//Creates a label inside the window (at the top) next to the text field which says Entr Track Number. 
        top.add(trackNoChoice);//adds the textfield Trackno to the window which shall be located at the top
        top.add(track);//Adds the button Check button inside the window at the top.
        middle.add(new JLabel("Enter new ratings (max of 5)"));//Adds another label next to text field.
        middle.add(ratingsUpdate);//Adds text field where users may enter new ratings
        middle.add(update);//Adds the Update button next to text field
        top.add(list2);//Again adds the button list to the top of the borderlayout.
        bottom.add(reset);//Adds a reset button at the bottom
        bottom.add(Add);

        update.addActionListener(this);//Adds a Action lister to the button list. This "listens" to the button and when it is pressed. It could be used to implement an action to occur after
        track.addActionListener(this);//Adds an actonlisttern to the Add track button. 
        list2.addActionListener(this);//Adds an actionlistener to list2.
        reset.addActionListener(this);//Adds and action listerner to reset button.
        Add.addActionListener(this);//Adds and action listerner to add button.

        add("North", top);//Adds a location being North of the borderlayout. Meaning buutons and other things can be specified to where they want.
        add("South", bottom);//Adds a location being South of the borderlayout, where bottons can be placed
        information2.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());//Gets ready to Set the text from Libary data in the Text area called information
        middle.add(information2);//Adds the information inside the windows specified to the middle
        add("Center", middle);// Creates the location middle being the center where buttons can be added to.
        information2.setEditable(false);

        setResizable(false);//Prevents the resizing of the window
        setVisible(true);//Allows the contents of the to be seen as it is set to true
    }//Closes the class

    public void actionPerformed(ActionEvent e) {//Creates another Public method called action performed. It has an action event which is called e
        Integer ratings;
        try {
            if (e.getSource() == Add) {
                new addOrRemoveTracks();
                information2.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());

            } else if (e.getSource() == list2) {// If the list(List all tracks) button is pressed something shall occur. This is done by using the e.getsource == list as there is an actionlistener on the button it detects if the button gets pressed.
                information2.setText("TrackID - Title - Artists\n" + LibraryDatabase.listAll());//The information from Library data is presented in the Text area called information
            } else {//Something else may happen if the first critiria isn't meet. 
                String key = trackNoChoice.getText();// Creates a string called key which calls the text from trackNo using get tet
                System.out.println(key);
                String name = LibraryDatabase.getName(key);//Creates a string called name in which executes the method getName in LibraryDatabase which gets the track name   
                if (e.getSource() == track && trackNoChoice.getText().isEmpty()) {// Here there is a nested if, where if the name is = null(no real iteam) then another series of actions may occur
                    JOptionPane.showMessageDialog(null, "No such track number");//If the critiria is met then in the text area called information it will dispaly "No such track number".
                } else {//Something else may occur if the first if isn't meet
                    information2.setText(name + " - " + LibraryDatabase.getArtist(key));//In the information text area it sets the text in which brings the string called name which again executes the method getName in LibraryDataOLD and then with a dash it then executes getArtist from library data which gets the artist name              
                    information2.append("\nRating: " + CheckLibrary.stars(LibraryDatabase.getRating(key)));//In the information text area it appends (can take any string value(can be null)) on a new line the "Ratings: " and then using the stars method it will execute from LibraryDataOLD the getRatings method. Using that it will display the ratings 
                    information2.append("\nPlay count: " + LibraryDatabase.getPlayCount(key));//In the information text area it appends (can take any string value(can be null)) on a new line Play count: 
                    if (e.getSource() == update) {
                        ratings = Integer.parseInt(ratingsUpdate.getText());
                        LibraryDatabase.setRating(key, ratings);
                    } //If the update button is pressed then the ratings is used to store the ratings retrived from ratings.update and is then converted
                } //Closes the else 
            }
            if (e.getSource() == reset) {
                trackNoChoice.setText(null);
                ratingsUpdate.setText(null);
                information2.setText(LibraryDatabase.listAll());
            } else {
            }//IF the reset is pressed then the textfield rrackno choice, ratingsupdate is set to null and then the text are is set to view all
  
        } catch (NumberFormatException nfe) {
            information2.setText(LibraryDatabase.listAll());
            JOptionPane.showMessageDialog(null, "Please make sure all the fields aren't left blank and try again!");
        }//Try methos is use to try and catch any exceptions which popup and an error is displayed
    }//Closes the method
}
//Closes the whole class