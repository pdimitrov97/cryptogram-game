package com.github.pdimitrov97.cryptography_game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Players
{
	// Constants for files
	private static final String PLAYERS_FILE = "players.txt";
	// Constants for criteria
	public static final int CRITERIA_NUM_PLAYED = 0;
	public static final int CRITERIA_NUM_COMPLETED = 1;
	public static final int CRITERIA_BEST_TIME = 3;
	public static final int CRITERIA_AVERAGE_TIME = 2;
	public static final int CRITERIA_ACCURACY = 4;
	// Variables
	private List<Player> players;

	/*
	 * Initialize variables.
	 */
	public Players()
	{
		players = new ArrayList<Player>();
	}

	/*
	 * Save players to file.
	 */
	public void savePlayers()
	{
		FileWriter fw = null;
		BufferedWriter writer = null;
		Player tempPlayer;

		try
		{
			fw = new FileWriter(PLAYERS_FILE);
			writer = new BufferedWriter(fw);

			for (int i = 0; i < players.size(); i++)
			{
				tempPlayer = players.get(i);

				writer.write(tempPlayer.getName() + " " + tempPlayer.getNumPlayed() + " " + tempPlayer.getNumCompleted() + " " + tempPlayer.getAverageTime().getTime() + " " + tempPlayer.getBestTime().getTime() + " " + tempPlayer.getAccuracy());

				if (i != players.size() - 1)
					writer.newLine();
			}

			writer.close();
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "File " + PLAYERS_FILE + " not found!");
			System.exit(0);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Cannot save players to " + PLAYERS_FILE + " file!");
			System.exit(0);
		}
	}

	/*
	 * Load players from file.
	 */
	public void loadPlayers()
	{
		try
		{
			FileReader fr = new FileReader(PLAYERS_FILE);
			BufferedReader reader = new BufferedReader(fr);

			String name = "";
			double accuracy = 0;
			long averageTime = 0;
			int numPlayed = 0;
			int numCompleted = 0;
			long bestTime = 0;
			Player p;

			Scanner scan = null;
			String line = reader.readLine();

			while (line != null)
			{
				scan = new Scanner(line).useLocale(Locale.UK);

				name = scan.next();
				numPlayed = scan.nextInt();
				numCompleted = scan.nextInt();
				averageTime = scan.nextLong();
				bestTime = scan.nextLong();
				accuracy = scan.nextDouble();

				p = new Player(name, numPlayed, numCompleted, new Time(averageTime), new Time(bestTime), accuracy);

				if (findPlayer(p) == -1)
					players.add(p);

				line = reader.readLine();
			}

			reader.close();
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "File " + PLAYERS_FILE + " not found!");
			System.exit(0);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Cannot load players from " + PLAYERS_FILE + " file!");
			System.exit(0);
		}
	}

	/*
	 * Add a player if they do not exist.
	 */
	public boolean addPlayer(Player p)
	{
		if (p == null)
			throw new NullPointerException("Player cannot be null!");

		if (findPlayer(p) != -1)
			return false;

		players.add(p);
		return true;
	}

	/*
	 * Remove player if they exist.
	 */
	public boolean removePlayer(Player p)
	{
		if (p == null)
			throw new NullPointerException("Player cannot be null!");

		int tempPlayer = findPlayer(p);

		if (tempPlayer != -1)
		{
			players.remove(tempPlayer);
			return true;
		}

		return false;
	}

	/*
	 * Update a player if they exist.
	 */
	public void updatePlayer(Player p)
	{
		if (p == null)
			throw new NullPointerException("Player cannot be null!");

		int tempPlayer = findPlayer(p);

		if (tempPlayer != -1)
			players.set(tempPlayer, p);
		else
			throw new IllegalArgumentException("Player does not exist!");
	}

	/*
	 * Find a player by passing a Player parameter and return the index of their
	 * position.
	 */
	public int findPlayer(Player p)
	{
		if (p == null)
			throw new NullPointerException("Player cannot be null!");

		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getName().equals(p.getName()))
				return i;
		}

		return -1;
	}

	/*
	 * Find a player by passing a Name parameter and return the index of their
	 * position.
	 */
	public int findPlayer(String name)
	{
		if (name == null)
			throw new NullPointerException("Player name cannot be null!");

		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getName().equals(name))
				return i;
		}

		return -1;
	}

	/*
	 * Returns a Player by an index.
	 */
	public Player getPlayer(int playerIndex)
	{
		if (playerIndex < 0 || playerIndex >= players.size())
			throw new IllegalArgumentException("Player index is out of bounds!");

		return players.get(playerIndex);
	}

	/*
	 * Returns a list of all players' played cryptograms.
	 */
	public List<Integer> getAllPlayersCryptogramsPlayed()
	{
		List<Integer> played = new ArrayList<Integer>();

		for (Player p : players)
		{
			played.add(p.getNumPlayed());
		}

		return played;
	}

	/*
	 * Returns a list of all players' completed cryptograms.
	 */
	public List<Integer> getAllPlayersCryptogramsCompleted()
	{
		List<Integer> completed = new ArrayList<Integer>();

		for (Player p : players)
		{
			completed.add(p.getNumCompleted());
		}

		return completed;
	}

	/*
	 * Returns a list of all players' average times.
	 */
	public List<Time> getAllPlayersAvgTimes()
	{
		List<Time> times = new ArrayList<Time>();

		for (Player p : players)
		{
			times.add(p.getAverageTime());
		}

		return times;
	}

	/*
	 * Returns a list of all players' best times.
	 */
	public List<Time> getAllPlayersBestTimes()
	{
		List<Time> times = new ArrayList<Time>();

		for (Player p : players)
		{
			times.add(p.getBestTime());
		}

		return times;
	}

	/*
	 * Returns a list of all players' accuracies.
	 */
	public List<Double> getAllPlayersAccuracies()
	{
		List<Double> accuracies = new ArrayList<Double>();

		for (Player p : players)
		{
			accuracies.add(p.getAccuracy());
		}

		return accuracies;
	}

	/*
	 * Returns a list of the top 10 players by a specified criteria
	 */
	public List<Player> getTop10(int criteria)
	{
		if (criteria != CRITERIA_ACCURACY && criteria != CRITERIA_AVERAGE_TIME && criteria != CRITERIA_NUM_PLAYED && criteria != CRITERIA_NUM_COMPLETED && criteria != CRITERIA_BEST_TIME)
			throw new IllegalArgumentException("Invalid criteria argument!");

		List<Player> topPlayers = new ArrayList<Player>();

		if (criteria == CRITERIA_NUM_PLAYED)
		{
			Collections.sort(players, new Comparator<Player>()
			{
				public int compare(Player p1, Player p2)
				{
					if (p1.getNumPlayed() < p2.getNumPlayed())
						return -1;
					else if (p1.getNumPlayed() == p2.getNumPlayed())
						return 0;
					else
						return 1;
				}
			});
		}
		else if (criteria == CRITERIA_NUM_COMPLETED)
		{
			Collections.sort(players, new Comparator<Player>()
			{
				public int compare(Player p1, Player p2)
				{
					if (p1.getNumCompleted() < p2.getNumCompleted())
						return -1;
					else if (p1.getNumCompleted() == p2.getNumCompleted())
						return 0;
					else
						return 1;
				}
			});
		}
		else if (criteria == CRITERIA_AVERAGE_TIME)
		{
			Collections.sort(players, new Comparator<Player>()
			{
				public int compare(Player p1, Player p2)
				{
					if (p1.getAverageTime().getTime() == 0 || p2.getAverageTime().getTime() == 0)
						return 1;

					if (p1.getAverageTime().getTime() > p2.getAverageTime().getTime())
						return -1;
					else if (p1.getAverageTime().getTime() == p2.getAverageTime().getTime())
						return 0;
					else
						return 1;
				}
			});
		}
		else if (criteria == CRITERIA_BEST_TIME)
		{
			Collections.sort(players, new Comparator<Player>()
			{
				public int compare(Player p1, Player p2)
				{
					if (p1.getBestTime().getTime() == 0 || p2.getBestTime().getTime() == 0)
						return 1;

					if (p1.getBestTime().getTime() > p2.getBestTime().getTime())
						return -1;
					else if (p1.getBestTime().getTime() == p2.getBestTime().getTime())
						return 0;
					else
						return 1;
				}
			});
		}
		else if (criteria == CRITERIA_ACCURACY)
		{
			Collections.sort(players, new Comparator<Player>()
			{
				public int compare(Player p1, Player p2)
				{
					if (p1.getAccuracy() < p2.getAccuracy())
						return -1;
					else if (p1.getAccuracy() == p2.getAccuracy())
						return 0;
					else
						return 1;
				}
			});
		}

		for (int i = players.size() - 1; i >= players.size() - 10 && i >= 0; i--)
			topPlayers.add(players.get(i));

		return topPlayers;
	}
}