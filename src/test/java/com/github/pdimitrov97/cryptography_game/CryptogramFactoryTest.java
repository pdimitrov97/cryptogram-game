package com.github.pdimitrov97.cryptography_game;

import static org.junit.Assert.*;
import org.junit.Test;
import com.github.pdimitrov97.cryptography_game.cryptograms.CryptogramFactory;

public class CryptogramFactoryTest
{
	@Test (expected = NullPointerException.class)
	public void testNoCryptogramsLoaded()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.createCryptogram(CryptogramFactory.LETTER_CRYPTOGRAM);	
	}

	@Test (expected = IllegalArgumentException.class)
	public void testInvalidCryptogramTypeUpperBounds()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();
		c.createCryptogram(2);	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidCryptogramTypeLowerBounds()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();
		c.createCryptogram(-1);	
	}
	
	@Test
	public void testLetterCryptogramType()
	{
		String quote = "Some random quote for testing";
		String[] mapping = {"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A"};
		CryptogramFactory c = new CryptogramFactory();
		
		c.loadCryptogram(CryptogramFactory.LETTER_CRYPTOGRAM, quote, mapping);
		assertNotEquals(c.getLetter('A'), 'A');
		assertEquals(quote, c.getQuote());
	}
	
	@Test
	public void testLetterCryptogramMappingNotMatchOnce()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();

		c.createCryptogram(CryptogramFactory.LETTER_CRYPTOGRAM);
		assertNotEquals('A', c.getLetter('A'));
		assertNotEquals('B', c.getLetter('B'));
		assertNotEquals('C', c.getLetter('C'));
		assertNotEquals('D', c.getLetter('D'));
		assertNotEquals('E', c.getLetter('E'));
		assertNotEquals('F', c.getLetter('F'));
		assertNotEquals('G', c.getLetter('G'));
		assertNotEquals('H', c.getLetter('H'));
		assertNotEquals('I', c.getLetter('I'));
		assertNotEquals('J', c.getLetter('J'));
		assertNotEquals('K', c.getLetter('K'));
		assertNotEquals('L', c.getLetter('L'));
		assertNotEquals('M', c.getLetter('M'));
		assertNotEquals('N', c.getLetter('N'));
		assertNotEquals('O', c.getLetter('O'));
		assertNotEquals('P', c.getLetter('P'));
		assertNotEquals('Q', c.getLetter('Q'));
		assertNotEquals('R', c.getLetter('R'));
		assertNotEquals('S', c.getLetter('S'));
		assertNotEquals('T', c.getLetter('T'));
		assertNotEquals('U', c.getLetter('U'));
		assertNotEquals('V', c.getLetter('V'));
		assertNotEquals('W', c.getLetter('W'));
		assertNotEquals('X', c.getLetter('X'));
		assertNotEquals('Y', c.getLetter('Y'));
		assertNotEquals('Z', c.getLetter('Z'));
	}
	
	@Test
	public void testLetterCryptogramMappingNotMatch1000()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();

		for (int i = 0 ; i < 1000 ; i++)
		{
			c.createCryptogram(CryptogramFactory.LETTER_CRYPTOGRAM);
			assertNotEquals('A', c.getLetter('A'));
			assertNotEquals('B', c.getLetter('B'));
			assertNotEquals('C', c.getLetter('C'));
			assertNotEquals('D', c.getLetter('D'));
			assertNotEquals('E', c.getLetter('E'));
			assertNotEquals('F', c.getLetter('F'));
			assertNotEquals('G', c.getLetter('G'));
			assertNotEquals('H', c.getLetter('H'));
			assertNotEquals('I', c.getLetter('I'));
			assertNotEquals('J', c.getLetter('J'));
			assertNotEquals('K', c.getLetter('K'));
			assertNotEquals('L', c.getLetter('L'));
			assertNotEquals('M', c.getLetter('M'));
			assertNotEquals('N', c.getLetter('N'));
			assertNotEquals('O', c.getLetter('O'));
			assertNotEquals('P', c.getLetter('P'));
			assertNotEquals('Q', c.getLetter('Q'));
			assertNotEquals('R', c.getLetter('R'));
			assertNotEquals('S', c.getLetter('S'));
			assertNotEquals('T', c.getLetter('T'));
			assertNotEquals('U', c.getLetter('U'));
			assertNotEquals('V', c.getLetter('V'));
			assertNotEquals('W', c.getLetter('W'));
			assertNotEquals('X', c.getLetter('X'));
			assertNotEquals('Y', c.getLetter('Y'));
			assertNotEquals('Z', c.getLetter('Z'));
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testLetterCryptogramWrongType()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();
		c.createCryptogram(CryptogramFactory.LETTER_CRYPTOGRAM);
		assertNotEquals('A', c.getLetter(1));
	}

	@Test
	public void testNumberCryptogramType()
	{
		String quote = "Some random quote for testing";
		String[] mapping = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "18", "19", "20", "21", "22", "23", "24", "25"};
		CryptogramFactory c = new CryptogramFactory();
		
		c.loadCryptogram(CryptogramFactory.NUMBER_CRYPTOGRAM, quote, mapping);
		assertNotEquals(c.getLetter(1), 'A');
		assertEquals(quote, c.getQuote());
	}
	
	@Test
	public void testNumberCryptogramMappingNotMatchOnce()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();
		
		c.createCryptogram(CryptogramFactory.NUMBER_CRYPTOGRAM);
		assertNotEquals('A', c.getLetter(1));
		assertNotEquals('B', c.getLetter(2));
		assertNotEquals('C', c.getLetter(3));
		assertNotEquals('D', c.getLetter(4));
		assertNotEquals('E', c.getLetter(5));
		assertNotEquals('F', c.getLetter(6));
		assertNotEquals('G', c.getLetter(7));
		assertNotEquals('H', c.getLetter(8));
		assertNotEquals('I', c.getLetter(9));
		assertNotEquals('J', c.getLetter(10));
		assertNotEquals('K', c.getLetter(11));
		assertNotEquals('L', c.getLetter(12));
		assertNotEquals('M', c.getLetter(13));
		assertNotEquals('N', c.getLetter(14));
		assertNotEquals('O', c.getLetter(15));
		assertNotEquals('P', c.getLetter(16));
		assertNotEquals('Q', c.getLetter(17));
		assertNotEquals('R', c.getLetter(18));
		assertNotEquals('S', c.getLetter(19));
		assertNotEquals('T', c.getLetter(20));
		assertNotEquals('U', c.getLetter(21));
		assertNotEquals('V', c.getLetter(22));
		assertNotEquals('W', c.getLetter(23));
		assertNotEquals('X', c.getLetter(24));
		assertNotEquals('Y', c.getLetter(25));
		assertNotEquals('Z', c.getLetter(26));
	}
	
	@Test
	public void testNumberCryptogramMappingNotMatch1000()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();

		for (int i = 0 ; i < 1000 ; i++)
		{
			c.createCryptogram(CryptogramFactory.NUMBER_CRYPTOGRAM);
			assertNotEquals('A', c.getLetter(1));
			assertNotEquals('B', c.getLetter(2));
			assertNotEquals('C', c.getLetter(3));
			assertNotEquals('D', c.getLetter(4));
			assertNotEquals('E', c.getLetter(5));
			assertNotEquals('F', c.getLetter(6));
			assertNotEquals('G', c.getLetter(7));
			assertNotEquals('H', c.getLetter(8));
			assertNotEquals('I', c.getLetter(9));
			assertNotEquals('J', c.getLetter(10));
			assertNotEquals('K', c.getLetter(11));
			assertNotEquals('L', c.getLetter(12));
			assertNotEquals('M', c.getLetter(13));
			assertNotEquals('N', c.getLetter(14));
			assertNotEquals('O', c.getLetter(15));
			assertNotEquals('P', c.getLetter(16));
			assertNotEquals('Q', c.getLetter(17));
			assertNotEquals('R', c.getLetter(18));
			assertNotEquals('S', c.getLetter(19));
			assertNotEquals('T', c.getLetter(20));
			assertNotEquals('U', c.getLetter(21));
			assertNotEquals('V', c.getLetter(22));
			assertNotEquals('W', c.getLetter(23));
			assertNotEquals('X', c.getLetter(24));
			assertNotEquals('Y', c.getLetter(25));
			assertNotEquals('Z', c.getLetter(26));
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNumberCryptogramWrongType()
	{
		CryptogramFactory c = new CryptogramFactory();
		c.loadCryptograms();
		c.createCryptogram(CryptogramFactory.NUMBER_CRYPTOGRAM);
		assertNotEquals('A', c.getLetter('A'));
	}
}
