package coursework_jukebox;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class addOrRemoveTracks extends JFrame
        implements ActionListener {
    JTextField Title = new JTextField(10);
    JTextField Genre = new JTextField(10);
    JTextField Album = new JTextField(10);
    JTextField Artist = new JTextField(10);
   //4 text fields are decleared and made  
    JButton add = new JButton("Add song");
    JButton remove = new JButton("Remove Song");
//Buttons are declared and made
    
    public addOrRemoveTracks() {
        setLayout(new BorderLayout());
        setBounds(100, 100, 400, 300);
        setTitle("Add or Remove Songs");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();//
        JPanel middle = new JPanel();
        //The GUI properties are set, the layout size,title,exit button and jpanels to add the buttons and text field too.
                top.add(new JLabel("Enter Track details to remove or add songs:"));
        middle.add(new JLabel("Title:"));
        middle.add(Title);
        middle.add(new JLabel("Genre:"));
        middle.add(Genre);
        middle.add(new JLabel("                    Artist:"));
        middle.add(Artist);
        middle.add(new JLabel("Album:"));
        middle.add(Album);
        //Add the  textfield into the various jpanels and also added 4 labels 3 to the meddle and one at the top.
        bottom.add(add);
        bottom.add(remove);
       // Add the buttons to the bottom
        
        
        add.addActionListener(this);
        remove.addActionListener(this);
        add("North", top);
        add("South", bottom);
        add("Center", middle);
        //Add actionlistener to the butons and add positions to the jpanel
        
        setResizable(false);//Prevents the resizing of the window
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        try {//A try is used to try catch the exeptions which may occur
      if (e.getSource() == add) {
          if (!Title.getText().isEmpty() && !Artist.getText().isEmpty()) {
          String Titletxt =Title.getText(); 
          String Genretxt =Genre.getText();
          String Albumtxt =Album.getText();
          String Artisttxt =Artist.getText();
        
          LibraryDatabase.addSong(Titletxt,Genretxt,Albumtxt,Artisttxt);
          JOptionPane.showMessageDialog(null, "Song had been added!");//
           Title.setText(null); 
          Genre.setText(null);
          Album.setText(null);
          Artist.setText(null);
          } else {
              JOptionPane.showMessageDialog(null, "Error, please check Title and Artist fields & Try again!");//
          }//If the add button pressed then the nested if check that the title and artist textfield isnt empty then the field text are retrive and saved as a string
          //Then the methodto add a song is executed with the values and a message is showed when a message which say the song is added 
          //Then the textfield are set to null 
          //If the nested if isn't meet then an error is displayed
      } else if (e.getSource() == remove)  {
          if (!Title.getText().isEmpty() && !Artist.getText().isEmpty()) {
              String Titletxt =Title.getText(); 
          String Albumtxt =Album.getText();
          String Artisttxt =Artist.getText();
                  LibraryDatabase.deleteSong(Titletxt,Albumtxt,Artisttxt);
                               JOptionPane.showMessageDialog(null, "Song has been deleted");//
          Title.setText(null); 
          Genre.setText(null);
          Album.setText(null);
          Artist.setText(null);
          } else {
                            JOptionPane.showMessageDialog(null, "Error, please check Title, Artist & Album fields & Try again!");//
//If the remove button is presed the nested if check if title and artist text field isnt empy then there are string made from the getting the text from the text fields 
//Then using the strings the emthos deletesong from the librarydatabase is used to delete the song and a messsage is displayed saying the song is deleted.                            
//Then the textfields are set to null if the nested if failed ta messagedialog is showed up which says theres is and error.                            
          }
      }
    }
        catch (Exception f) {
            System.out.println(e);
        }
    //if there is and exception 
}
}
