package com.github.pdimitrov97.cryptography_game.cryptograms;

public abstract class Cryptogram
{
	// Variables
	protected String quote = null;
	protected char[] cryptogramLetters = null;
	protected String[] cryptogramMapping = null;
	protected int[] letterFrequencies = null;

	/*
	 * Constructor with only a quote.
	 */
	protected Cryptogram(String quote)
	{
		this.quote = quote;
		cryptogramLetters = new char[26];
		cryptogramMapping = new String[26];
		letterFrequencies = new int[26];
		createFrequencies();
	}

	/*
	 * Constructor with a quote and a mapping
	 */
	protected Cryptogram(String quote, String[] mapping)
	{
		this.quote = quote;
		this.cryptogramMapping = mapping;
		cryptogramLetters = new char[26];
		letterFrequencies = new int[26];
		createFrequencies();
	}

	/*
	 * Returns the quote.
	 */
	protected String getQuote()
	{
		if (quote == null)
			throw new NullPointerException("No cryptogram created or quote is empty!");

		return quote;
	}

	/*
	 * Returns the mapping.
	 */
	protected String[] getMapping()
	{
		if (cryptogramMapping == null)
			throw new NullPointerException("Mapping is not created yet!");

		return cryptogramMapping;
	}

	/*
	 * Returns the frequency of a specific letter.
	 */
	protected int getFrequency(char letter)
	{
		if (letter >= 'a' && letter <= 'z')
			return letterFrequencies[(int)letter - 'a'];
		else if (letter >= 'A' && letter <= 'Z')
			return letterFrequencies[(int)letter - 'A'];

		throw new IllegalArgumentException("Argument must be a letter between \'a\' and \'z\' or \'A\' and \'Z\' inclusive!");
	}

	/*
	 * Function used to calculate the frequencies when a cryptogram is created.
	 */
	private void createFrequencies()
	{
		char tempChar;

		for (int i = 0 ; i < quote.length() ; i++)
		{
			tempChar = quote.charAt(i);

			if (tempChar >= 'a' && tempChar <= 'z')
				letterFrequencies[(int)tempChar - 'a']++;
			else if (tempChar >= 'A' && tempChar <= 'Z')
				letterFrequencies[(int)tempChar - 'A']++;
		}
	}
}
