package com.github.pdimitrov97.cryptography_game;

import static org.junit.Assert.*;
import java.sql.Time;
import org.junit.Test;

public class PlayerTest
{
	@Test
	public void testNewPlayer()
	{
		Player p = new Player();

		assertEquals("", p.getName());
		assertEquals(0, p.getNumPlayed());
		assertEquals(0, p.getNumCompleted());
		assertEquals(0, p.getAverageTime().getTime());
		assertEquals(0, p.getBestTime().getTime());
		assertEquals(0.0, p.getAccuracy(), 2);
	}

	@Test
	public void testNewPlayerWithName()
	{
		String name = "Player1";
		Player p = new Player(name);

		assertEquals(name, p.getName());
		assertEquals(0, p.getNumPlayed());
		assertEquals(0, p.getNumCompleted());
		assertEquals(0, p.getAverageTime().getTime());
		assertEquals(0, p.getBestTime().getTime());
		assertEquals(0.0, p.getAccuracy(), 2);
	}

	@Test
	public void testNewPlayerWithValues()
	{
		String name = "Player1";
		int numPlayed = 20;
		int numCompleted = 10;
		Time avgTime = new Time(45678);
		Time bestTime = new Time(12345);
		double accuracy = 0.5;

		Player p = new Player(name, numPlayed, numCompleted, avgTime, bestTime, accuracy);

		assertEquals(name, p.getName());
		assertEquals(numPlayed, p.getNumPlayed());
		assertEquals(numCompleted, p.getNumCompleted());
		assertEquals(avgTime, p.getAverageTime());
		assertEquals(bestTime, p.getBestTime());
		assertEquals(accuracy, p.getAccuracy(), 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewPlayerWithSpaceInName()
	{
		Player p = new Player("Player 1");
	}
}
