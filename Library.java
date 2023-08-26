//Name: Anoj Ganeshalingam
//Student ID: 501157441

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.sound.sampled.AudioFileFormat;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultStyledDocument.ElementSpec;
import javax.xml.transform.ErrorListener;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
	
 	//  private ArrayList<Podcast> podcasts;
	


	// In assignment 2 replaced errors with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage() // returns error message
	{
		return errorMsg;
	}

	public String setErrorMessage(String input){ //sets error message
		errorMsg = input;
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	   // podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) throws AudioContentNotFoundException  // adds audiobook content to library, checks if already downloaded
	{
		
		if (content.getType().equals("AUDIOBOOK")){      
			if (!audiobooks.contains((AudioBook)content)){
				audiobooks.add((AudioBook)content);
				return;
			}
			else{
				setErrorMessage("AudioBook " + content.getTitle() + " already downloaded");
				throw new AudioContentNotFoundException(errorMsg);
			}
		}
		else if (content.getType().equals("SONG")){
			
			if (!songs.contains((Song)content)){
				songs.add((Song)content);
				return;
			}
			else{
				setErrorMessage("Song " + content.getTitle() + " already downloaded");
				throw new AudioContentNotFoundException(errorMsg);
			}
		}
		else{
			setErrorMessage("Content not found");
			throw new AudioContentNotFoundException(errorMsg);
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		/* 
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();	
		}
		*/
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			//playlists.get(i).printContents();
			System.out.print(playlists.get(i).getTitle());	
			System.out.println("");
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> artists = new ArrayList<String>();
		for (int i=0; i < songs.size(); i++){
			if(!artists.contains(songs.get(i).getArtist())){
				artists.add(songs.get(i).getArtist());
				System.out.println(i+1 + ". " + songs.get(i).getArtist());
			} 
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) throws AudioContentNotFoundException
	{
		if (index > 0 && index <= songs.size()){
			songs.remove(index-1);
			
		}
		else{
			errorMsg = "Song Not Found";
			throw new AudioContentNotFoundException("Song Not Found");
		}
		
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			return a.getYear()-b.getYear();
		}	
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 	Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			return a.getLength()-b.getLength();
		}	
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws AudioContentNotFoundException
	{
		if (index < 1 || index > songs.size())
		{
			errorMsg = "Song Not Found";
			throw new AudioContentNotFoundException(errorMsg);
		}
		else{
		songs.get(index-1).play();
		}

	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) throws AudioContentNotFoundException
	{
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("Book Not Found");
			
		}
		else{
			if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters()){
				throw new AudioContentNotFoundException("Chapter Not Found");
				
			}
			else{
				audiobooks.get(index-1).selectChapter(chapter);
				audiobooks.get(index-1).play();
			}
		}
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{	
		if (index < 1 || index > audiobooks.size())
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		else{
			for (int i = 0; i < audiobooks.get(index-1).getNumberOfChapters();i++){
				System.out.println("Chapter " + (i+1) + ". " + audiobooks.get(index-1).getChapterTitles().get(i) + "\n");
			}
		}
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) throws AudioContentNotFoundException
	{	
		Playlist p = new Playlist(title);
		if (!playlists.contains(p)){
			playlists.add(p);
		}
		else{
			errorMsg = "Playlist " + title + " already exists";
			throw new AudioContentNotFoundException(errorMsg);
		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws AudioContentNotFoundException
	{
		for(int i = 0; i < playlists.size(); i ++){
			if (playlists.get(i).getTitle().equals(title)){
					playlists.get(i).printContents();
					return;
			}
		}
		errorMsg = "Playlist Not Found";
		throw new AudioContentNotFoundException(errorMsg);
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws AudioContentNotFoundException
	{
		for(int i = 0; i < playlists.size(); i ++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				playlists.get(i).playAll();
				return;
			}
		}
		errorMsg = "Playlist Not Found";
		throw new AudioContentNotFoundException(errorMsg);
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws AudioContentNotFoundException
	{
		for(int i = 0; i < playlists.size(); i ++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){
				if (indexInPL > 0 && indexInPL <= playlists.get(i).getContent().size()){
					playlists.get(i).getContent().get(indexInPL-1).playAll();
					return;
				}
				else{
					errorMsg = "Invalid Index";
					throw new AudioContentNotFoundException(errorMsg);
				}

			}
		}
		errorMsg = "Playlist Not Found"; 
		throw new AudioContentNotFoundException(errorMsg);

	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws AudioContentNotFoundException
	{



		for (int i = 0; i < playlists.size(); i++){
			if (playlists.get(i).getTitle().equals(playlistTitle)){ // found playlist
				if (type.equalsIgnoreCase("AUDIOBOOK")){
					if (index > 0 && index <= audiobooks.size()){
						playlists.get(i).addContent(audiobooks.get(index-1));
						return;
					}
					else{
						errorMsg = "AudioBook not found";
						throw new AudioContentNotFoundException(errorMsg);
					}
				}

				else if (type.equalsIgnoreCase("SONG")){
					if (index > 0 && index <= songs.size()){
						playlists.get(i).addContent(songs.get(index-1));
						return;
					}
					else{
						errorMsg = "Song not found";
						throw new AudioContentNotFoundException(errorMsg);
					}
				}
				
				else{
					errorMsg = "Type not found";
					throw new AudioContentNotFoundException(errorMsg);
				}
				


			}
		}
		errorMsg = "Playlist not found";
		throw new AudioContentNotFoundException(errorMsg);
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws AudioContentNotFoundException
	{
			for (int i = 0; i < playlists.size(); i++){
				if (playlists.get(i).getTitle().equals(title)){ // found playlist
					if (index >= 0 && index < playlists.get(i).getContent().size()){ // passes in true index
						playlists.get(i).deleteContent(index+1); // because deletecontent subtracts 1.
						return;
					} 
					else{
						throw new AudioContentNotFoundException("Index not valid");
						
					}
				}
			}
			throw new AudioContentNotFoundException("playlist not found");
	}
	
	//below methods self-explanatory
	public ArrayList<Song> getSongs(){
		return songs;
	}

	public ArrayList<AudioBook> getAudiobooks(){
		return audiobooks;
	}

	public ArrayList<Playlist> getPlaylists(){
		return playlists;
	}
}

class AudioContentNotFoundException extends RuntimeException{ // AudioContentNotFound exception class
	public AudioContentNotFoundException(){}
	public AudioContentNotFoundException(String message){
		super(message);
	}
}

