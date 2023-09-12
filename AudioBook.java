//Name: Anoj Ganeshalingam
//Student ID: 501157441

import java.util.ArrayList;

/*
 * An AudioBook is a type of AudioContent.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 * 
 */
public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author; 
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length, //Constructor 
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional AudioBook instance variables. 
		super(title, year, id, type, audioFile, length);
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;
		
	}
	
	public String getType() // returns type
	{
		return TYPENAME;
	}

  // Print information about the audiobook. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print author and narrator
	// see the video
	public void printInfo()
	{
		super.printInfo();
		System.out.print(" Author: " + this.author + " Narrated by: " + this.narrator + "\n");

	}
	
  // Play the audiobook by setting the audioFile to the current chapter title (from chapterTitles array list) 
	// followed by the current chapter (from chapters array list)
	// Then make use of the the play() method of the superclassselectchapter
	public void play()

	{
		setAudioFile(chapterTitles.get(currentChapter) + "\n" + chapters.get(currentChapter)); 
		super.play();
	}
	
	public void playAllChapters(){ // to play all chapters from playlist play (included in README.txt)
		setAudioFile(chapterTitles.get(0) + "\n" + chapters.get(0));
		super.play();
		for (int i = 1; i < chapterTitles.size(); i ++){
		setAudioFile(chapterTitles.get(i) + "\n" + chapters.get(i));
		super.play2(); // play without printing info
		}
	}
	
	// Print the table of contents of the book - i.e. the list of chapter titles
	// See the video
	public void printTOC()
	{
		for (int i = 0; i < this.chapterTitles.size();i++){
			System.out.println("Chapter " + i+1 + ". " + chapterTitles.get(i) + "\n");
		}
	}

	// Select a specific chapter to play - nothing to do here
	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	
	//Two AudioBooks are equal if their AudioContent information is equal and both the author and narrators are equal
	public boolean equals(Object other)
	{ 
		AudioBook other2 = (AudioBook) other;
		if ((this.author.equals(other2.getAuthor())) && (this.narrator.equals(other2.getNarrator()))){
			return true;
		}
		else{
		return false;
		}
	}
	
	// below methods self-explanatory
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}

}
