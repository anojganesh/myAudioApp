//Name: Anoj Ganeshalingam
//Student ID: 501157441

import java.util.*;
import java.io.*;
import java.security.PublicKey;
import java.util.ArrayList;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore 
{
		private ArrayList<AudioContent> contents; //store contents
		private Map<String, Integer> contentHash = new HashMap<String, Integer>(); //hashmap for titles to index
		private Map<String, ArrayList<Integer>> artistsHash = new HashMap<String, ArrayList<Integer>>();// hashmap for artists to indexes
		private Map<String, ArrayList<Integer>> genreHash = new HashMap<String, ArrayList<Integer>>(); // hashmap for genres to indexes
		public AudioContentStore() // constructor, reads file, creates store, creates hashmaps
		{
			contents = new ArrayList<AudioContent>();
			try{
				contents = readStoreFile(); // read store file

			}
			catch (IOException e){
				System.out.println("IOException occured."); // exit if file not found
				System.exit(1);
			}

			for (int i = 0; i < contents.size(); i++){ //fill title hash map
				contentHash.put(contents.get(i).getTitle(), i);
			}

			String creator = "undefined"; // fill artist hash map
			String creator2 = "undefined-2";
			ArrayList<Integer> indexesFound = new ArrayList<Integer>();
			
			for (int i = 0; i < contents.size();i++){
				ArrayList<Integer> artistIndexes = new ArrayList<Integer>();
				if (contents.get(i).getType().equals("SONG")){			// finds 2 creators, compares, forms unique key and values
					creator = ((Song)(contents.get(i))).getArtist();
				}
				else if(contents.get(i).getType().equals("AUDIOBOOK")){
					creator = ((AudioBook)(contents.get(i))).getAuthor();
				}
				for (int j = 0; j< contents.size(); j++){
					if (contents.get(j).getType().equals("SONG")){
						creator2 = ((Song)(contents.get(j))).getArtist();
					}
					else if(contents.get(j).getType().equals("AUDIOBOOK")){
						creator2 = ((AudioBook)(contents.get(j))).getAuthor();
					}

					if (creator.equals(creator2) && !indexesFound.contains(j)){
						indexesFound.add(j);
						artistIndexes.add(j);

					}
				}
				if(artistIndexes.size() >= 1){
				artistsHash.put(creator, artistIndexes);
				}
			}
	
			//making genre hashmap

			
			ArrayList<Integer> popGenres = new ArrayList<Integer>(); //seperate arraylists for each genre, added into seperately
			ArrayList<Integer> rapGenres = new ArrayList<Integer>();
			ArrayList<Integer> rockGenres = new ArrayList<Integer>();
			ArrayList<Integer> jazzGenres = new ArrayList<Integer>();
			ArrayList<Integer> hiphopGenres = new ArrayList<Integer>();
			ArrayList<Integer> classicalGenres = new ArrayList<Integer>();
			for (int i = 0; i < contents.size();i++){
				if (contents.get(i).getType().equals("SONG")){
					Song.Genre genre = ((Song)(contents.get(i))).getGenre();	
					if (genre.equals(Song.Genre.POP)){
						popGenres.add(i);
					}
					else if (genre.equals(Song.Genre.RAP)){
						rapGenres.add(i);
					}
					else if (genre.equals(Song.Genre.ROCK)){
						rockGenres.add(i);
					}
					else if (genre.equals(Song.Genre.JAZZ)){
						jazzGenres.add(i);
					}
					else if (genre.equals(Song.Genre.HIPHOP)){
						hiphopGenres.add(i);
					}
					else if (genre.equals(Song.Genre.CLASSICAL)){
						classicalGenres.add(i);
					}
				}
			}
			genreHash.put("POP", popGenres); // filling appropriate genre index-arraylists into genre Hashmap
			genreHash.put("RAP", rapGenres);
			genreHash.put("ROCK", rockGenres);
			genreHash.put("JAZZ", jazzGenres);
			genreHash.put("HIPHOP", hiphopGenres);
			genreHash.put("CLASSICAL", classicalGenres);

		}

		private ArrayList<AudioContent> readStoreFile() throws IOException{ // reads file, finds appropriate information, returns audiofile contents
			ArrayList<AudioContent> contents = new ArrayList<AudioContent>(); 
			Scanner file = new Scanner(new File("store.txt"));
			String type = "";
			String oldType = "";
			if (file.hasNext()){
				type = file.nextLine();
			}
			while (file.hasNext()){
				if (type.equals("SONG")){ 	
					System.out.println("LOADING SONG");		//song information
					String id = file.nextLine();
					String title = file.nextLine();
					int year = file.nextInt();
					int length = file.nextInt();
					file.nextLine();// clear buffer
					String artist = file.nextLine();
					String composer = file.nextLine();
					String genre = file.nextLine();
					String lines = file.nextLine();
					
					String lyrics = file.nextLine();
					while (true){
						String newline = file.nextLine();
						if (!newline.equals("SONG") && !newline.equals("AUDIOBOOK")){ 
							lyrics = lyrics+ " " + newline;
						}
						else{
							oldType = type; // holds old type
							type = newline;
							break;
						}
					}
					//how I chose to handle different Song.Genre types
					if (genre.equals("POP")){
						contents.add(new Song(title, year, id, oldType, lyrics, length, artist, composer, Song.Genre.POP, lyrics));
					}
					else if(genre.equals("ROCK")){
						contents.add(new Song(title, year, id, oldType, lyrics, length, artist, composer, Song.Genre.ROCK, lyrics));
					}
					else if (genre.equals("RAP")){
						contents.add(new Song(title, year, id, oldType, lyrics, length, artist, composer, Song.Genre.RAP, lyrics));
					}
					else if (genre.equals("JAZZ")){
						contents.add(new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.JAZZ, lyrics));
					}
				
				}
				else if (type.equals("AUDIOBOOK")){ // audiobook information
					System.out.println("LOADING AUDIOBOOK");
					String id = file.nextLine();
					String title = file.nextLine();
					int year = file.nextInt();
					int length = file.nextInt();
					file.nextLine(); // clears buffer
					String author = file.nextLine();
					String narrator = file.nextLine();
					int chapterTLines = file.nextInt();
					file.nextLine(); // clears buffer
					ArrayList<String> chapterTitles = new ArrayList<String>();
					for (int i = 0; i < chapterTLines; i++){
						chapterTitles.add(file.nextLine());
					}

					String text = "";
					ArrayList<String> chapters = new ArrayList<String>();
					
					for (int i = 0; i<chapterTLines; i++){
						int chapterlines = file.nextInt();
						file.nextLine(); // clears buffer
						for (int j=0;j<chapterlines;j++){
							text = text + file.nextLine() + "\n";
						}
						chapters.add(text);
						text = "";
					}
					contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chapterTitles, chapters));

					if (file.hasNext()){
						type = file.nextLine();
					}
				}
			}
			return contents;
		} 
		
		
		public AudioContent getContent(int index) //gets audiofile content
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll() // lists all audiofile contents
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		public int searchStoreByTitle(String title){ //gets index from title-index hashmap

			if (contentHash.get(title) != null){
				return contentHash.get(title);
			}
			else{
				return -1;
			}
		
		}

		public ArrayList<Integer> searchStoreByArtist(String artist){//gets indexes from artist-indexes hashmap
			return artistsHash.get(artist);
		}

		public ArrayList<Integer> searchStoreByGenre(String genre){//gets indexes from genre-indexes hashmap
				return genreHash.get(genre);
		}
		
		private ArrayList<String> makeHPChapterTitles() //unused in file
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters() //unused in file
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles() //unused in file
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters() // unused in file
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles() //unused in file
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters() // unused in file
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}

		
		
		// Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
