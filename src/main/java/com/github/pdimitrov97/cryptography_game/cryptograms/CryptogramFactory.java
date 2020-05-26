package com.github.pdimitrov97.cryptography_game.cryptograms;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

public class CryptogramFactory
{
	// Constants for files
	private static final String CRYPTOGRAMS_FILE = "cryptograms.txt";
	// Constants for types of cryptograms
	public static final int LETTER_CRYPTOGRAM = 0;
	public static final int NUMBER_CRYPTOGRAM = 1;
	// Variables
	private Cryptogram cryptogram;
	private List<String> quotes;

	/*
	 * Initialize variables.
	 */
	public CryptogramFactory()
	{
		cryptogram = null;
		quotes = new ArrayList<String>();
	}

	/*
	 * Creates a new cryptogram of a specified type.
	 */
	public void createCryptogram(int type)
	{
		if (quotes.size() <= 0)
			throw new NullPointerException("No cryptograms loaded!");

		Random rand = new Random();
		
		if (type == LETTER_CRYPTOGRAM)
			cryptogram = new LetterCryptogram(quotes.get(rand.nextInt(quotes.size())));
		else if (type == NUMBER_CRYPTOGRAM)
			cryptogram = new NumberCryptogram(quotes.get(rand.nextInt(quotes.size())));
		else
			throw new IllegalArgumentException("Cryptogram types are " + LETTER_CRYPTOGRAM + " for Letter cryptogram and " + NUMBER_CRYPTOGRAM + " for Number cryptogram!");
	}

	/*
	 * Creates a new cryptogram with a given, type, quote and mapping.
	 */
	public void loadCryptogram(int type, String quote, String[] mapping)
	{
		if (type == LETTER_CRYPTOGRAM)
			cryptogram = new LetterCryptogram(quote, mapping);
		else if (type == NUMBER_CRYPTOGRAM)
			cryptogram = new NumberCryptogram(quote, mapping);
		else
			throw new IllegalArgumentException("Cryptogram types are " + LETTER_CRYPTOGRAM + " for Letter cryptogram and " + NUMBER_CRYPTOGRAM + " for Number cryptogram!");
	}

	/*
	 * Returns the quote of the current cryptogram.
	 */
	public String getQuote()
	{
		return cryptogram.getQuote();
	}

	/*
	 * Returns the mapping of the current cryptogram.
	 */
	public String[] getMapping()
	{
		return cryptogram.getMapping();
	}

	/*
	 * Returns the frequency of a specified letter of the current cryptogram.
	 */
	public int getFrequency(char letter)
	{
		return cryptogram.getFrequency(letter);
	}

	/*
	 * Returns the real letter of a given encrypted letter (if a Letter Cryptogram).
	 */
	public char getLetter(char cryptogramLetter)
	{
		if(!(cryptogram instanceof LetterCryptogram))
			throw new IllegalArgumentException("Current cryptogram is NOT Letter cryptogram!");
		else
			return ((LetterCryptogram)cryptogram).getLetter(cryptogramLetter);
	}

	/*
	 * Returns the real letter of a given encrypted number (if a Number Cryptogram).
	 */
	public char getLetter(int cryptogramInt)
	{
		if(!(cryptogram instanceof NumberCryptogram))
			throw new IllegalArgumentException("Current cryptogram is NOT Number cryptogram!");
		else
			return ((NumberCryptogram)cryptogram).getLetter(cryptogramInt);
	}

	/*
	 * Loads the file with cryptograms.
	 */
	public void loadCryptograms()
	{
		try
		{
			FileReader fr = new FileReader(CRYPTOGRAMS_FILE);
			BufferedReader reader = new BufferedReader(fr);
			
			String quote = "";	
			String line = reader.readLine();
			int cryptogramsCount = 0;
			
			while (line != null)
			{
				line = line.trim();
				
				if (!line.equals(""))
				{
					quote = line;
					quotes.add(quote);
					cryptogramsCount++;
				}

				line = reader.readLine();
			}
			
			reader.close();
			
			if (cryptogramsCount == 0)
			{
				JOptionPane.showMessageDialog(null, "No cryptograms loaded!");
				System.exit(0);
			}
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Cannot find " + CRYPTOGRAMS_FILE + "!");
			System.exit(0);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Cannot load cryptograms from " + CRYPTOGRAMS_FILE + "!");
			System.exit(0);
		}
	}
}
