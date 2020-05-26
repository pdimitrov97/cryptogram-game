package com.github.pdimitrov97.cryptography_game;

import static org.junit.Assert.*;
import org.junit.Test;
import java.sql.Time;
import java.util.List;

public class PlayersTest
{
	@Test (expected = NullPointerException.class)
	public void testAddPlayer()
	{
		Players playersDB = new Players();
		Player p = null;
		
		playersDB.addPlayer(p);
	}
	
	@Test
	public void testAddPlayerSame()
	{
		Players playersDB = new Players();
		Player p1 = new Player("Player1");
		
		assertTrue(playersDB.addPlayer(p1));
		assertFalse(playersDB.addPlayer(p1));
	}

	@Test
	public void testRemovePlayer()
	{
		Players playersDB = new Players();
		Player p1 = new Player("Player1");
		Player p2 = new Player("Player2");
		
		playersDB.addPlayer(p1);
		playersDB.addPlayer(p2);
		
		assertTrue(playersDB.removePlayer(p1));
		assertFalse(playersDB.removePlayer(p1));
	}

	@Test
	public void testUpdatePlayer()
	{
		Players playersDB = new Players();
		Player p1 = new Player("Player1", 20, 10, new Time(45678), new Time(12345), 0.5);
		
		playersDB.addPlayer(p1);
		p1.setNumPlayed(30);
		p1.setNumCompleted(15);
		p1.setAverageTime(new Time(34567));
		p1.setBestTime(new Time(1234));
		p1.setAccuracy(0.6);
		
		playersDB.updatePlayer(p1);
		
		Player tempPlayer = playersDB.getPlayer(playersDB.findPlayer(p1.getName()));
		
		assertEquals(p1.getNumPlayed(), tempPlayer.getNumPlayed());
		assertEquals(p1.getNumCompleted(), tempPlayer.getNumCompleted());
		assertEquals(p1.getAverageTime().getTime(), tempPlayer.getAverageTime().getTime());
		assertEquals(p1.getBestTime().getTime(), tempPlayer.getBestTime().getTime());
		assertEquals(p1.getAccuracy(), tempPlayer.getAccuracy(), 2);
		
		//p1.setAccuracy(0.2);
	}

	@Test
	public void testFindPlayerPlayer()
	{
		Players playersDB = new Players();
		Player p1 = new Player("Player1");
		Player p2 = new Player("Player2");
		Player p3 = new Player("Player3");

		playersDB.addPlayer(p1);
		playersDB.addPlayer(p2);
		playersDB.addPlayer(p3);
		
		assertNotEquals(-1, playersDB.findPlayer(p1));
		assertNotEquals(-1, playersDB.findPlayer(p2));
		assertNotEquals(-1, playersDB.findPlayer(p3));
	}

	@Test
	public void testFindPlayerString()
	{
		Players playersDB = new Players();
		Player p1 = new Player("Player1");
		Player p2 = new Player("Player2");
		Player p3 = new Player("Player3");

		playersDB.addPlayer(p1);
		playersDB.addPlayer(p2);
		playersDB.addPlayer(p3);
		
		assertNotEquals(-1, playersDB.findPlayer(p1.getName()));
		assertNotEquals(-1, playersDB.findPlayer(p2.getName()));
		assertNotEquals(-1, playersDB.findPlayer(p3.getName()));
	}

	@Test
	public void testGetPlayer()
	{
		Players playersDB = new Players();
		Player p1 = new Player("Player1");
		Player p2 = new Player("Player2");
		Player p3 = new Player("Player3");

		playersDB.addPlayer(p1);
		playersDB.addPlayer(p2);
		playersDB.addPlayer(p3);
		
		Player tempPlayer;
		int index = playersDB.findPlayer(p1.getName());
		tempPlayer = playersDB.getPlayer(index);
		assertEquals(p1, tempPlayer);
		
		index = playersDB.findPlayer(p2.getName());
		tempPlayer = playersDB.getPlayer(index);
		assertEquals(p2, tempPlayer);
		
		index = playersDB.findPlayer(p3.getName());
		tempPlayer = playersDB.getPlayer(index);
		assertEquals(p3, tempPlayer);
	}

	@Test
	public void testGetAllPlayersCryptogramsPlayed()
	{
		int[] playedCryptograms = {10, 20, 30, 40, 50};
		Players playersDB = new Players();
		Player tempPlayer;
		
		for (int i = 0 ; i < playedCryptograms.length ; i++)
		{
			tempPlayer = new Player("Player" + (i + 1), playedCryptograms[i], 0, new Time(0), new Time(0), 0.0);
			playersDB.addPlayer(tempPlayer);
		}
		
		List<Integer> allPlayedCryptograms = playersDB.getAllPlayersCryptogramsPlayed();
		
		for (int i = 0 ; i < playedCryptograms.length ; i++)
		{
			assertEquals((Integer)playedCryptograms[i], allPlayedCryptograms.get(i));		
		}
	}

	@Test
	public void testGetAllPlayersCryptogramsCompleted()
	{
		int[] completedCryptograms = {10, 20, 30, 40, 50};
		Players playersDB = new Players();
		Player tempPlayer;
		
		for (int i = 0 ; i < completedCryptograms.length ; i++)
		{
			tempPlayer = new Player("Player" + (i + 1), 0, completedCryptograms[i], new Time(0), new Time(0), 0.0);
			playersDB.addPlayer(tempPlayer);
		}
		
		List<Integer> allAvgTimes = playersDB.getAllPlayersCryptogramsCompleted();
		
		for (int i = 0 ; i < completedCryptograms.length ; i++)
		{
			assertEquals((Integer)completedCryptograms[i], allAvgTimes.get(i));		
		}
	}

	@Test
	public void testGetAllPlayersAvgTimes()
	{
		int[] avgTimes = {10, 20, 30, 40, 50};
		Players playersDB = new Players();
		Player tempPlayer;
		
		for (int i = 0 ; i < avgTimes.length ; i++)
		{
			tempPlayer = new Player("Player" + (i + 1), 0, 0, new Time(avgTimes[i]), new Time(0), 0.0);
			playersDB.addPlayer(tempPlayer);
		}
		
		List<Time> allAvgTimes = playersDB.getAllPlayersAvgTimes();
		
		for (int i = 0 ; i < avgTimes.length ; i++)
		{
			assertEquals(avgTimes[i], allAvgTimes.get(i).getTime());		
		}
	}
	
	@Test
	public void testGetAllPlayersBestTimes()
	{
		int[] bestTimes = {10, 20, 30, 40, 50};
		Players playersDB = new Players();
		Player tempPlayer;
		
		for (int i = 0 ; i < bestTimes.length ; i++)
		{
			tempPlayer = new Player("Player" + (i + 1), 0, 0, new Time(0), new Time(bestTimes[i]), 0.0);
			playersDB.addPlayer(tempPlayer);
		}
		
		List<Time> allBestTimes = playersDB.getAllPlayersBestTimes();
		
		for (int i = 0 ; i < bestTimes.length ; i++)
		{
			assertEquals(bestTimes[i], allBestTimes.get(i).getTime());		
		}
	}
	
	@Test
	public void testGetAllPlayersAccuracies()
	{
		double[] accuracies = {0.1, 0.2, 0.3, 0.4, 0.5};
		Players playersDB = new Players();
		Player tempPlayer;
		
		for (int i = 0 ; i < accuracies.length ; i++)
		{
			tempPlayer = new Player("Player" + (i + 1), 0, 0, new Time(0), new Time(0), accuracies[i]);
			playersDB.addPlayer(tempPlayer);
		}
		
		List<Double> allAccuracies = playersDB.getAllPlayersAccuracies();
		
		for (int i = 0 ; i < accuracies.length ; i++)
		{
			assertEquals(accuracies[i], allAccuracies.get(i), 2);		
		}
	}

	@Test
	public void testGetTop10()
	{
		int[] playedCryptograms = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 0};
		int[] completedCryptograms = {5, 10, 15, 14, 8, 31, 22, 0, 50, 70, 0};
		int[] avgTimes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 0};
		int[] bestTimes = {35, 20, 80, 40, 27, 87, 31, 21, 83, 93, 0};
		double[] accuracies = {0.1, 0.2, 0.3, 0.4, 0.5, 0.8, 0.7, 0.9, 0.6, 1.0, 0.0};
		
		int[] sortedPlayedCryptograms = {100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 0};
		int[] sortedCompletedCryptograms = {70, 50, 31, 22, 15, 14, 10, 8, 5, 0, 0};
		int[] sortedAvgTimes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 0};
		int[] sortedBestTimes = {20, 21, 27, 31, 35, 40, 80, 83, 87, 93, 0};
		double[] sortedAccuracies = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0};

		Players playersDB = new Players();
		Player tempPlayer;

		for (int i = 0 ; i < bestTimes.length ; i++)
		{
			tempPlayer = new Player("Player" + (i + 1), playedCryptograms[i], completedCryptograms[i], new Time(avgTimes[i]), new Time(bestTimes[i]), accuracies[i]);
			playersDB.addPlayer(tempPlayer);
		}
		
		List<Player> top10 = playersDB.getTop10(Players.CRITERIA_NUM_PLAYED);
		
		for(int i = 0 ; i < top10.size() ; i++)
		{
			assertEquals(sortedPlayedCryptograms[i], top10.get(i).getNumPlayed());
		}

		top10 = playersDB.getTop10(Players.CRITERIA_NUM_COMPLETED);

		for(int i = 0 ; i < top10.size() ; i++)
		{
			assertEquals(sortedCompletedCryptograms[i], top10.get(i).getNumCompleted());
		}
		
		top10 = playersDB.getTop10(Players.CRITERIA_AVERAGE_TIME);

		for(int i = 0 ; i < top10.size() ; i++)
		{
			assertEquals(sortedAvgTimes[i], top10.get(i).getAverageTime().getTime());
		}
		
		top10 = playersDB.getTop10(Players.CRITERIA_BEST_TIME);

		for(int i = 0 ; i < top10.size() ; i++)
		{
			assertEquals(sortedBestTimes[i], top10.get(i).getBestTime().getTime());
		}
		
		top10 = playersDB.getTop10(Players.CRITERIA_ACCURACY);

		for(int i = 0 ; i < top10.size() ; i++)
		{
			assertEquals(sortedAccuracies[i], top10.get(i).getAccuracy(), 2);
		}
	}
}
