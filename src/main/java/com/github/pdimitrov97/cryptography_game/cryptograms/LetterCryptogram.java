package com.github.pdimitrov97.cryptography_game.cryptograms;

import java.util.Random;

public class LetterCryptogram extends Cryptogram
{
	/*
	 * Create a new cryptogram with a given quote.
	 */
	protected LetterCryptogram(String quote)
	{
		super(quote);
		createMapping();
		createInverseMapping();
	}

	/*
	 * Create a new cryptogram with a given quote and mapping.
	 */
	protected LetterCryptogram(String quote, String[] mapping)
	{
		super(quote, mapping);
		createInverseMapping();
	}

	/*
	 * Returns the real letter of a given encrypted letter.
	 */
	protected char getLetter(char cryptogramLetter)
	{
		if (cryptogramLetter < 'A' || cryptogramLetter > 'Z')
			throw new IllegalArgumentException("Argument must be a letter between \'A\' and \'Z\' inclusive!");

		return cryptogramLetters[(int) cryptogramLetter - 'A'];
	}

	/*
	 * Function that creates the random mapping.
	 */
	private void createMapping()
	{
		String[] mapping = new String[26];
		Random rand = new Random();
		String temp;
		int selected = 0;

		for (int i = 0; i < 26; i++)
			mapping[i] = Character.toString((char) (i + 'A'));

		for (int i = 0; i < 25; i++)
		{
			selected = rand.nextInt((25 - (i + 1)) + 1) + (i + 1); // rand((max - min) + 1) + min;

			temp = mapping[i];
			mapping[i] = mapping[selected];
			mapping[selected] = temp;
		}

		cryptogramMapping = mapping;
	}

	/*
	 * Function that creates the inverse mapping (used for getLetter() function).
	 */
	private void createInverseMapping()
	{
		for (int i = 0; i < 26; i++)
		{
			cryptogramLetters[(int) cryptogramMapping[i].charAt(0) - 'A'] = (char) (i + 'A');
		}
	}
}
