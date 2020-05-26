package com.github.pdimitrov97.cryptography_game;

import java.sql.Time;

public class Player
{
	// Variables
	private String name;
	private int numPlayed;
	private int numCompleted;
	private Time averageTime;
	private Time bestTime;
	private double accuracy;

	/*
	 * Default empty constructor
	 */
	public Player()
	{
		this.name = "";
		this.numPlayed = 0;
		this.numCompleted = 0;
		this.averageTime = new Time(0);
		this.bestTime = new Time(0);
		this.accuracy = 0;
	}

	/*
	 * Constructor only with a given name
	 */
	public Player(String name)
	{
		if (name.contains(" "))
			throw new IllegalArgumentException("Name cannot contain a space character!");

		this.name = name;
		this.numPlayed = 0;
		this.numCompleted = 0;
		this.averageTime = new Time(0);
		this.bestTime = new Time(0);
		this.accuracy = 0;
	}

	/*
	 * Constructor with all fields
	 */
	public Player(String name, int numPlayed, int numCompleted, Time averageTime, Time bestTime, double accuracy)
	{
		if (name.contains(" "))
			throw new IllegalArgumentException("Name cannot contain a space character!");

		this.name = name;
		this.numPlayed = numPlayed;
		this.numCompleted = numCompleted;
		this.averageTime = averageTime;
		this.bestTime = bestTime;
		this.accuracy = accuracy;
	}

	/*
	 * Get name
	 */
	public String getName()
	{
		return name;
	}

	/*
	 * Set name
	 */
	public void setName(String name)
	{
		if (name.contains(" "))
			throw new IllegalArgumentException("Name cannot contain a space character!");

		this.name = name;
	}

	/*
	 * Get number of played cryptograms
	 */
	public int getNumPlayed()
	{
		return numPlayed;
	}

	/*
	 * Set number of played cryptograms
	 */
	public void setNumPlayed(int numPlayed)
	{
		this.numPlayed = numPlayed;
	}

	/*
	 * Get number of completed cryptograms
	 */
	public int getNumCompleted()
	{
		return numCompleted;
	}

	/*
	 * Set number of completed cryptograms
	 */
	public void setNumCompleted(int numCompleted)
	{
		this.numCompleted = numCompleted;
	}

	/*
	 * Get average time
	 */
	public Time getAverageTime()
	{
		return averageTime;
	}

	/*
	 * Set average time
	 */
	public void setAverageTime(Time averageTime)
	{
		this.averageTime = averageTime;
	}

	/*
	 * Get best time
	 */
	public Time getBestTime()
	{
		return bestTime;
	}

	/*
	 * Set best time
	 */
	public void setBestTime(Time bestTime)
	{
		this.bestTime = bestTime;
	}

	/*
	 * Get accuracy
	 */
	public double getAccuracy()
	{
		return accuracy;
	}

	/*
	 * Set accuracy
	 */
	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}
}
