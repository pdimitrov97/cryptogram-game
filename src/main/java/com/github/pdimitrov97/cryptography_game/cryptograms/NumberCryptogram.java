package com.github.pdimitrov97.cryptography_game.cryptograms;

import java.util.Random;

public class NumberCryptogram extends Cryptogram
{
	/*
	 * Create a new cryptogram with a given quote.
	 */
	protected NumberCryptogram(String phrase)
	{
		super(phrase);
		createMapping();
		createInverseMapping();
	}

	/*
	 * Create a new cryptogram with a given quote and mapping.
	 */
	protected NumberCryptogram(String phrase, String[] mapping)
	{
		super(phrase, mapping);
		createInverseMapping();
	}

	/*
	 * Returns the real letter of a given encrypted number.
	 */
	protected char getLetter(int cryptogramInt)
	{
		if (cryptogramInt < 1 || cryptogramInt > 26)
			throw new IllegalArgumentException("Argument must be an integer between 1 and 26 inclusive!");

		return cryptogramLetters[cryptogramInt - 1];
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

		for (int i = 1; i <= 26; i++)
			mapping[i - 1] = Integer.toString(i);

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
			cryptogramLetters[Integer.parseInt(cryptogramMapping[i]) - 1] = (char) (i + 'A');
		}
	}
}
