package coursework_jukebox;

import java.sql.*; // DB handling package
import javax.swing.JOptionPane;
class LibraryDatabase {

    // Declare the JDBC objects.
    private static Connection con = null;
    private static Statement stmt = null;

    static {
        // standard code to open a connection and statement to SQL Server database
        try {
            // Create a variable for the connection string.
            String connectionUrl = "jdbc:sqlserver://SQL-SERVER;"
                    + "databaseName=vs317;integratedSecurity=true;";
// ************  For userName user your user ID  ***************

            // Establish the connection.
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection(connectionUrl);

        } // Handle any errors that may have occurred.
        catch (SQLException sqle) {
            System.out.println("Sql Exception :" + sqle.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found Exception :" + e.getMessage());
        }
    }

    public static String listAll() {
        String output = "";
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT Tracks.TrackID, Tracks.Title, Artists.ArtistName FROM Artists, Tracks Where (Artists.ArtistID=Tracks.ArtistID) ");            
            while (res.next()) { // there is a result
                // the name field is the thrid one in the ResultSet
                // Note that with  ResultSet we count the fields starting from 1
                output +=  res.getString(1) + " - " + res.getString(2) +  " - " + res.getString(3) + "\n";
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return output;
    }

    public static String getName(String key) {
        try {
      /*     NOTE: you cannot use key as a column name, it is a reserved word
             with SQL Server. Instead ID will be used
             Need single quote marks ' around the key field in SQL. 
             This is easy to get wrong! For instance if key was "04" the 
             SELECT statement would be:
             SELECT * FROM LibraryTable WHERE key = '04'      */
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT Title From Tracks WHERE TrackID = '" + key + "'");
            if (res.next()) { // there is a result
                // the name field is the second one in the ResultSet
                // Note that with  ResultSet we count the fields starting from 1
                return res.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
                             JOptionPane.showMessageDialog(null,"Please make sure all the fields are correctly filled & try again!");
            System.out.println(e);
            return null;
        }
    }
//Try and catch used to executre and catch any exceptions.
    //SQL statement is executed and if res.next then it returns the the values by using getstring. If an exception is caught a messagedialogue is showed 
    public static String getArtist(String key) {
   try {
          stmt = con.createStatement();
            ResultSet ArtistID = stmt.executeQuery("SELECT ArtistID FROM Tracks WHERE TrackID = '" + key + "'");
            String Artist_key = "empty";
            if (ArtistID.next()) { 
                Artist_key = ArtistID.getString(1);
            } 
            
            ResultSet res = stmt.executeQuery("SELECT ArtistName FROM Artists WHERE ArtistID = '" + Artist_key + "'");
            if (res.next()) { 
                return res.getString(1);
            } else {
                return null;
            }
        } catch (Exception e) {
                                         JOptionPane.showMessageDialog(null,"Please make sure all the fields are correctly filled & try again!");

            System.out.println(e);
        return null;
    }
    }//An method is create called getArtist which returns a string A ResultSet is create called ArtistID which executes the sql statement, the sql statemnt
    //Selects the Artist ID from the table tracks where the track id is equal to the entered trackID Then the if the if statemen is meet then the string is made by storing the returned sql queireis 
    //Then a new ResultSet is made called res which executes a queur which selects the artist name from the artists where the artistId is equaled to the returned string previously
    //else if the if statement is meet then the results are reurned else a null is returned 
    //The try and catch is there to catch an exception if so then a error message is displayed
    //and a null is returned

    public static int getRating(String key) {
        try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT TrackRatings FROM Tracks WHERE TrackID = '" + key + "'");
             if (res.next()) {
                return res.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
    //A method called getRating is made which will retun a int and needs a key, a try and catch is used to try and catch an exception  and if so a the exception is printed and -1 is returned
    //A ResulSet called res is created which excutes a sql query which select the track ratings from tracks where the trackID is qual the inputed string
    //If the if statement is returned then ir returns a int else it returns a 0 
    
    public static int getPlayCount(String key) {      
       try {
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT TrackHits FROM Tracks WHERE  TrackID = '" + key + "'");
            if (res.next()) { 
                return res.getInt(1);
           } else {
                return -1;
            }
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
//A method called getplaycount is created 
    //A ResulSet called res is created which excutes a sql query which select the track ratings from tracks where the trackID is qual the inputed string
    //If the if statement is returned then ir returns a int else it returns a 0 
    
    public static void setRating(String key, int rating) {
        /* SQL UPDATE statement required. For instance if rating is 5 and key is "04" then updateStr is
         UPDATE Libary SET rating = 5 WHERE ID = '04'*/
        String updateStr = null;
        if (rating <= 5) {
        updateStr = "UPDATE Tracks SET TrackRatings = " + rating + " WHERE TrackID = '" + key + "'";
        System.out.println(updateStr);
    } else { 
    JOptionPane.showMessageDialog(null, "Please make sure the rating aren't greater than 5!");
        }
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//A setRating method is created which doesnt return anything but uses a string and and int. 
    //a string called updatestr is made and if the int rating is less or equal to 5 then updateStr store the sql statement, which upates track the track so it sets the ratings for that specific track by getting the trackid
    //else a error is diisplayed, then a try and catch is implemented, it trys o execture the sql state before and if a exception is found it will pring it
     public static void incrementPlayCount(String key) {
         int getPlayCount = getPlayCount(key);
         ++getPlayCount;
        String updateStr = "UPDATE Tracks SET TrackHits =" + getPlayCount + " WHERE TrackID = '" + key + "'";
        System.out.println(updateStr);
        try {
            stmt.executeUpdate(updateStr);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//A method is created which doesn't return anythign and is called incrementPlaycount which retrives a string 
    //a string called updatestr is made with which updates the track hits of a track by using the trackID and incrementing he playcount 
     //It then prints that sql statemet and then a try and catch is implelemted which trys to execture the sql statement upfateStr and if an exception is found it shall pringt
    
     public static void createPlaylist(String playlistName) {
                 String updateStr = "INSERT into Playlist (PlaylistName) Values ('" + playlistName + "')";
         try {
            stmt.executeUpdate(updateStr);
            System.out.println(updateStr);
        } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Check Playlist Name and try again!");
            System.out.println(e);
        }
     }// A method called create playlist is made whic doesn't return anything and uses a string called playlistname, firstly a string called updateStr is made and it is essentially a sql statement which creates a playlist
     //Then a try and catc is implemented and it trys to exectue the updateStr and then println it. if it catches an exception it would show an error messagedialoge.
     

     public static String songToPlaylist(String playlistName,String key ) {
try {      
            stmt = con.createStatement();
            ResultSet playlistID = stmt.executeQuery("SELECT PlaylistID FROM Playlist where PlaylistName= '" + playlistName + "'");
            String playlistid2 = "empty";
            if (playlistID.next()) { 
                playlistid2 = playlistID.getString(1);
                System.out.println(playlistid2);
            } 
            String updateStr = "INSERT into Queues (PlaylistID,TrackID) values (" + playlistid2 + "," + key + ")";    
            stmt.executeUpdate(updateStr);
            System.out.println(updateStr);
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"Please Try again!");
            System.out.println(e);
        }
        return null;
     }
     //A method caleed SongtoPlaylist is made which returns a string, it was a try and catch which trys the code and if it catches a exception 
     //A resultset called playlistid is create and it exxectue an sql statement which selects the plalistID from playlist where playlistname is inputted by useed then a string playlistid is made 
     //if the if statement is meet, playlist2 is used to save the string which is returned and then it prints after that a new string is made called updatestr which instes into ques table and adds the playlist id and trackid
     //it then exectures it and if an exception is caught then an error pop up dialloge is showed and the method returns a 
     public static String removeSongsPlaylist(String playlistName,String key ) {
try {
           
            stmt = con.createStatement();
            ResultSet playlistID = stmt.executeQuery("SELECT PlaylistID FROM Playlist where PlaylistName= '" + playlistName + "'");
            String playlistid2 = "empty";
            if (playlistID.next()) { 
                playlistid2 = playlistID.getString(1);
                System.out.println(playlistid2);
            } 
            String updateStr = "DELETE FROM Queues WHERE PlaylistID=" + playlistid2 + " AND TrackID=" + key + "";    
            stmt.executeUpdate(updateStr);

            System.out.println(updateStr);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Please Try again!");
            System.out.println(e);
        }
        return null;
     }//A method called removeSongPlaylist is created hich uses 2 strings ad returns a string, again a try and caths is first used if an exception is found with the try it will use a message dialoge with a error message
     //A resultSet called playlistID is created and it executes a sql statement which selects the playlitid from the paylist table where playlist name is used to locate the id
     //if the if statement is meet then a string is used to store the returned PlaylistID and it prints it, Then an string updateStr is made which is used to store the sql statement which deletes from the Queue table where the playlist id is used to select the p;laylist and then using the string key 
     //then the string is executed and then updateStr is printed 
       
          public static String viewPlaylist (String playlistName) {
          String output = "";
try {
            
            stmt = con.createStatement();
            ResultSet playlistID = stmt.executeQuery("SELECT PlaylistID FROM Playlist where PlaylistName= '" + playlistName + "'");
            String playlistid2 = "empty";
            if (playlistID.next()) { 
                playlistid2 = playlistID.getString(1);
                System.out.println(playlistid2);
            } 
            ResultSet res = stmt.executeQuery("SELECT Queues.PlaylistID, Tracks.Title, Tracks.ArtistID FROM Queues, Tracks Where (Queues.TrackID=Tracks.TrackID) AND ( Queues.PlaylistID='" + playlistid2 + "')");
            while (res.next()) { 
               output += res.getString(1) + " " + res.getString(2) +  " " + "\n";
            }
    
     } catch (Exception e) {
            System.out.println(e);
        }
        return output;
     }
      //A method called view playlist which first uses resultSet platlistID to execute an sql statemener to select the playlistid  then if the if statemenr is meet the the returned string is save and afterwards 
          //a result set is used to execture an sql which uses another type of inner join to view the playlist, then a while statement is used to ouput the result if an exception is caught it prints it else it will return outpu
       
      public static String viewoldplaylist (String playlistName) {
          String output = "";
try {
            
            stmt = con.createStatement();
            ResultSet playlistID = stmt.executeQuery("SELECT PlaylistID FROM Playlist where PlaylistName= '" + playlistName + "'");
            String playlistid2 = "empty";
            if (playlistID.next()) { 
                playlistid2 = playlistID.getString(1);
                System.out.println(playlistid2);
            } 
            ResultSet res = stmt.executeQuery("SELECT Queues.PlaylistID, Tracks.Title, Tracks.ArtistID FROM Queues, Tracks Where (Queues.TrackID=Tracks.TrackID) AND ( Queues.PlaylistID='" + playlistid2 + "')");
            while (res.next()) { 
               output += res.getString(1) + " " + res.getString(2) +  " " + "\n";
            }
    
     } catch (Exception e) {
            System.out.println(e);
        }
        return output;
     }
      
            
      public static String viewALLPlaylists () {
          String output = "";
try {
            
            stmt = con.createStatement();
           
            ResultSet res = stmt.executeQuery("Select * from playlist");
            while (res.next()) { 
               output += res.getString(1) + " " + res.getString(2) +  " " + "\n";
            }
    
     } catch (Exception e) {
            System.out.println(e);
        }
        return output;
     }
      //A View all playlist method is made and a try and catch is used to try and catch any exceptions if exception is found then printed else a resultset is used to execute the sql suery used to view all the playlists
      //A while is used to output. Then it returns the output
      //
      
           public static void deleteSong (String Title,String Album,String Artist) {
       try {    
         
         stmt = con.createStatement();
       
                    ResultSet getAlbumID = stmt.executeQuery("Select TrackID From Tracks Where (Title= '" + Title + "')");
            String TrackID = "empty";
            if (getAlbumID.next()) { 
                TrackID = getAlbumID.getString(1);
                System.out.println(TrackID);
            } 
            
            String deleteQueue = "DELETE FROM Queues WHERE TrackID= '" + TrackID +"'";
        System.out.println(deleteQueue);
            stmt.executeUpdate(deleteQueue);
            
            String deletetrack = "delete from tracks where title='" + Title +"'";
        System.out.println(deletetrack);
            stmt.executeUpdate(deletetrack);
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Please Try again!");
            System.out.println(e);
        }
     }
           //A deketesnong methd which uses 3 strins to delete the song
           //A resultSe called getalbum to execture an sql query to view trackid from the track table where the title = one of the string 
           //If the if statement is meet then the trackid string is used to store the trackid returned form the sql statement then a new string called delete queue is used to delete from the queue qwere the trackid from before is used
           //Then the deletequeue string is exectuted, after a new string called delete track which delte the track from the table track uusing the early title string then if the catches and exception it will use a message dialoge to show an error message
     public static void addSong (String Title,String Genre,String Album,String Artist) {
       try {    
         
         stmt = con.createStatement();
       
            String Arts = "INSERT INTO Artists(ArtistName) VALUES ('" + Artist + "');";
            stmt.executeUpdate(Arts);
            
                   ResultSet getArtistID = stmt.executeQuery("Select ArtistID From Artists Where (ArtistName= '" + Artist + "')");
            String ArtistID = "empty";
            if (getArtistID.next()) { 
                ArtistID = getArtistID.getString(1);
                System.out.println(ArtistID);
            } 
            
            
            String Album2 =  "INSERT INTO Albums(AlbumTitle,ArtistID) VALUES ('" + Album + "'," + ArtistID + ");";
            stmt.executeUpdate(Album2);
                    ResultSet getAlbumID = stmt.executeQuery("Select AlbumID From Albums Where (AlbumTitle= '" + Album + "')");
            String AlbumID = "empty";
            if (getAlbumID.next()) { 
                AlbumID = getAlbumID.getString(1);
                System.out.println(AlbumID);
            } 
         
         
          String updateStr = "INSERT INTO Tracks (Title,Genre,ArtistID,AlbumID) VALUES ('" + Title + "','" + Genre + "'," + ArtistID + "," + AlbumID +")";
        System.out.println(updateStr);
            stmt.executeUpdate(updateStr);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Please Try again!");
            System.out.println(e);
        }
     }//An add song method is made to allow users toi add song, then a try and catch is made to tr and catch any exceptions from the try. 
     //Then a string arts is saved to save an sql statement which inserts into artists, the artists name with the value of artist, Then the arts string is executed
     //Resultset is made to get the artisID by exectuing an sql statement to get artistID from artis by using the artist name. If the if statement is meet then the artistid2 is used to store the artistid
     //and then it is printlned, Then a new string albulm2 is used to insert into albulm the album title and ID withb the values and then its executed.
     //ReusltsetId is used to execute a sql query which selects the AlbumID where from albulm table using the ablum title. 
     //if the if statement is meet then the albulm id string is used to store the albulmid and then printlned. 
     //After a new string updateStr is used to save sql statement which insters into track all the values for title genre artistID and ALbumID 
     
     
      public static String selectArtist (String ArtistName) {
          String output = "";
try {
            
            stmt = con.createStatement();
            ResultSet ArtistID = stmt.executeQuery("SELECT ArtistID FROM Artists where ArtistName= '" + ArtistName + "'");
            String ArtistID2 = "empty";
            if (ArtistID.next()) { 
                ArtistID2 = ArtistID.getString(1);
                System.out.println(ArtistID2);
            } 
            ResultSet res = stmt.executeQuery("SELECT Title FROM Tracks Where ArtistID='" + ArtistID2 + "'");
            while (res.next()) { 
               output += res.getString(1) + "\n";
            }
    
     } catch (Exception e) {
            System.out.println(e);
                        JOptionPane.showMessageDialog(null,"Please Try again!");

        }
        return output;
     }
      //SelectArtist method is made and then a try and catch to try and catch any exceptions which apear from the try. A resultset is used to exectue sql query which selects the artistID
      //If the if statement is meet, then the artistID2 string is used to store the artistid and then the artisid2 is printed, then another Resultset is used to select tiels from track using the artistid2
      //and it returns the outpus
     
       public static String selectAlbum (String AlbumTitle) {
          String output = "";
try {
            
            stmt = con.createStatement();
            ResultSet AlbumsID = stmt.executeQuery("SELECT AlbumID FROM Albums where AlbumTitle= '" + AlbumTitle + "'");
            String AlbumsID2 = "empty";
            if (AlbumsID.next()) { 
                AlbumsID2 = AlbumsID.getString(1);
                System.out.println(AlbumsID2);
            } 
            ResultSet res = stmt.executeQuery("SELECT TrackID,Title FROM Tracks Where AlbumID='" + AlbumsID2 + "'");
            while (res.next()) { 
               output += res.getString(1) + " " + res.getString(2) +  " " + "\n";
            }
     } catch (Exception e) {
                 JOptionPane.showMessageDialog(null,"Please make sure all the fields are correctly filled & try again!");
            System.out.println(e);
        }
        return output;
     }
      //A method called selectAlbulm is made which will return string, A try and catch is used to try and catch any exceptions from the try. Resultset albulID is used to execute an sql query which selects the albulmID
       //if the if statement is meet, then the albulmID2 is used to store the albulmID then using another resultset is used to exectute a sql queryy which selects te track id and title from that albulmid and then its is outputed
       public static String selectTitle (String Title) {
          String output = "";
try {
            ResultSet res = stmt.executeQuery("SELECT TrackID FROM Tracks where Title= '" + Title + "'");
            while (res.next()) { 
               output += res.getString(1);
            }
     } catch (Exception e) {
                 JOptionPane.showMessageDialog(null,"Please make sure all the fields are correctly filled & try again!");
            System.out.println(e);
        }
        return output;
       }
   //Try and catch used to executre and catch any exceptions.
    //SQL statement is executed and if the if is meet res.next then it returns the the values by using getstring. If an exception is caught a messagedialogue is showed 
  // The resulset executes a query to select the trackID 
      
         public static String SortRatings (String key) {
          String output = "";
try {
            
        stmt = con.createStatement();
      
      
                 ResultSet res = stmt.executeQuery("SELECT TrackID,Title,TrackRatings From Tracks ORDER BY TrackRatings DESC");
            while (res.next()) { 
               output += res.getString(1) + " - " + res.getString(2) + " - " + res.getString(3) + " " + "\n";
            }
            
     } catch (Exception e) {
            System.out.println(e);
        }
return output;
     }
         //A method called sort ratings ia made and the a try and catch is used to catch exceptions and when an exceptions is caught it will println it
         ///A resultset is made to execute a sql query which selects the  trackid,title,trackratings from tracks and orders it in descending order
      public static String SortPlayHits (String key) {
          String output = "";
try {
            
        stmt = con.createStatement();
      
      
                 ResultSet res = stmt.executeQuery("SELECT TrackID,Title,TrackHits From Tracks ORDER BY TrackHits DESC");
            while (res.next()) { 
               output += res.getString(1) + " - " + res.getString(2) + " - " + res.getString(3) + " " + "\n";
            }
            
     } catch (Exception e) {
            System.out.println(e);
        }
return output;
     }//Sort playlist returns a string a try and catch is used to try and catch an exception which will then print the error 
      //A resultset is created which then executes an sql query which select the trackid and title with track hits from tracks and orders them by track hits
      //a while statement is used to store the string values into output and then the output is retuened
      
      
    // close the database
    public static void close() {
        try {
            con.close();
        } catch (Exception e) {
            // this shouldn't happen
            System.out.println(e);
        }
    }
}

