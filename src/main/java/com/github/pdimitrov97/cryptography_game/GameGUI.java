package com.github.pdimitrov97.cryptography_game;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.github.pdimitrov97.cryptography_game.cryptograms.CryptogramFactory;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import javax.swing.event.ChangeEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class GameGUI
{
	// Constants for screen titles
	private static final String TITLE = "Cryptogram Game";
	private static final String SELECT_SUBTITLE = ": Select Game Type";
	private static final String REPLACE_SUBTITLE = ": Replace letter";
	private static final String ENTER_SUBTITLE = ": Enter letter";
	private static final String HINT_SUBTITLE = ": Hint";
	private static final String SAVE_SUBTITLE = ": Save Game";
	private static final String LOAD_SUBTITLE = ": Load Game";
	// Constants for file names
	private static final String DEFAULT_SAVE_FILE = "saved_cryptogram_game.txt";
	// Constants for screen dimensions
	private static final int WINDOW_ACCOUNT_WIDTH = 265;
	private static final int WINDOW_ACCOUNT_HEIGHT = 145;
	private static final int WINDOW_ACCOUNT_LOGGED_WIDTH = 265;
	private static final int WINDOW_ACCOUNT_LOGGED_HEIGHT = 275;
	private static final int WINDOW_GAME_DEFAULT_WIDTH = 770;
	private static final int WINDOW_GAME_DEFAULT_HEIGHT = 100;
	private static final int WINDOW_GAME_WIDTH = 770;
	private static final int WINDOW_GAME_HEIGHT = 125;
	private static final int WINDOW_NO_GAME_WIDTH = 630; // 770;
	private static final int WINDOW_NO_GAME_HEIGHT = 521; // 455;
	private static final int WINDOW_SCOREBOARD_WIDTH = 700;
	private static final int WINDOW_SCOREBOARD_HEIGHT = 300;
	private static final int WINDOW_HELP_WIDTH = 670;
	private static final int WINDOW_HELP_HEIGHT = 508;
	// Constants for GUI elements
	private static final int TEXTBOX_MARGIN_X = 10;
	private static final int TEXTBOX_MARGIN_Y = 60;
	private static final int TEXTBOX_WIDTH = 22;
	private static final int TEXTBOX_HEIGHT = 26;
	private static final int LABEL_CHARACTER_WIDTH = 6;
	private static final int LABEL_CHARACTER_HEIGHT = 26;
	private static final int LABEL_LETTER_WIDTH = 22;
	private static final int LABEL_LETTER_HEIGHT = 26;
	private static final int LABEL_FREQUENCY_WIDTH = 22;
	private static final int LABEL_FREQUENCY_HEIGHT = 26;
	private static final int TEXTBOX_LABEL_OFFSET_Y = 22;
	private static final int LABEL_FREQUENCY_OFFSET_Y = TEXTBOX_LABEL_OFFSET_Y + 18;
	private static final int LETTER_WIDTH = 25;
	private static final int SPACE_WIDTH = 20;
	private static final int PUNCTUATION_MARK_WIDTH = 6;
	private static final int NEW_LINE_MARGIN = 80;
	private static final int GAME_BUTTONS_DEFAULT_Y = 45;
	private static final int GAME_BUTTONS_MARGIN_Y = 65;
	private static final int VIEW_FREQ_OFFSET = 30;
	private static final int FRAME_MARGIN = 10;
	private static final Color unmarked = new Color(255, 255, 255);
	private static final Color marked = new Color(255, 255, 125);

	// Variables for the game
	private CryptogramFactory currentCryptogram = null;
	private Players playersDatabase = null;
	private Player currentPlayer;
	private String[] currentMapping;
	private String cryptogramOnlyLetters;
	private long gameTimestamp;
	private int gameEnteredLetters;
	private int gameGuessedLetters;
	private String fileTyped;
	private boolean loggedIn;
	private boolean gameGenerated;
	private boolean gameRunning;
	private int selectedGameType;
	private JTextField[] fields;
	private JLabel[] mappingLabels;
	private JLabel[] frequencyLabels;
	private int windowWidth = WINDOW_GAME_DEFAULT_WIDTH;
	private int windowHeight = WINDOW_GAME_DEFAULT_HEIGHT;
	private Insets insets;

	// GUI variables
	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	// Account panel
	private JPanel panelAccount;
	private JLabel nameLabel;
	private JTextField name;
	private JButton loginBtn;
	private JButton regBtn;
	private JLabel loggedInAs;
	private JLabel numPlayed;
	private JLabel numCompleted;
	private JLabel avgTime;
	private JLabel bestTime;
	private JLabel accuracy;
	// Game panel
	private JPanel panelGame;
	private JButton btnStartGame;
	private JButton btnLoadGame;
	private JLabel gameImgLabel;
	private JCheckBox chckbxShowFreq;
	private JButton btnHint;
	private JButton btnSkip;
	private JButton btnGiveUp;
	private JButton btnSaveGame;
	// Scoreboard panel
	private JPanel panelScoreboard;
	private JRadioButton criteriaNumCompleted;
	private JRadioButton criteriaAvgTime;
	private JRadioButton criteriaBestTime;
	private JRadioButton criteriaAccuracy;
	private JTable scoreboardTable;
	// Help panel
	private JPanel panelHelp;

	/*
	 * Main function that creates the Game class.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GameGUI window = new GameGUI();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Initialize main variables and create the main window.
	 */
	public GameGUI()
	{
		currentCryptogram = new CryptogramFactory();
		currentCryptogram.loadCryptograms();
		playersDatabase = new Players();
		playersDatabase.loadPlayers();
		currentPlayer = null;
		currentMapping = new String[26];
		cryptogramOnlyLetters = "";
		gameTimestamp = 0;
		gameEnteredLetters = 0;
		gameGuessedLetters = 0;
		fileTyped = "";
		loggedIn = false;
		gameGenerated = false;
		gameRunning = false;
		selectedGameType = -1;
		fields = null;
		mappingLabels = null;
		frequencyLabels = null;

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				if (playersDatabase != null)
					playersDatabase.savePlayers();
			}
		});

		initMainWindow();
	}

	/*
	 * Initialize the main window.
	 */
	private void initMainWindow()
	{
		// Create frame
		frame = new JFrame(TITLE);
		frame.setSize(WINDOW_ACCOUNT_WIDTH, WINDOW_ACCOUNT_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowStateListener(new RestoreWindow());
		frame.setResizable(false);

		// Tabbed pane that will hold the 4 panels
		insets = frame.getInsets();
		tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
		tabbedPane.addChangeListener(new TabChangeAction());
		frame.getContentPane().add(tabbedPane);

		// Account panel
		panelAccount = new JPanel();
		tabbedPane.addTab("Account", null, panelAccount, null);
		panelAccount.setLayout(null);

		// Name label
		nameLabel = new JLabel();
		nameLabel.setBounds(10, 10, 100, 20);
		nameLabel.setText("Enter username:");
		panelAccount.add(nameLabel);

		// Name textbox
		name = new JTextField();
		name.setBounds(10, 30, 225, 20);
		panelAccount.add(name);

		// Login button
		loginBtn = new JButton();
		loginBtn.setBounds(10, 60, 100, 20);
		loginBtn.setText("Login");
		loginBtn.addActionListener(new LoginButtonAction());
		panelAccount.add(loginBtn);

		// Register button
		regBtn = new JButton();
		regBtn.setBounds(134, 60, 100, 20);
		regBtn.setText("Register");
		regBtn.addActionListener(new RegisterButtonAction());
		panelAccount.add(regBtn);

		// Logged In As label
		loggedInAs = new JLabel();
		loggedInAs.setText("Logged in as: ");
		loggedInAs.setBounds(10, 90, 240, 20);
		loggedInAs.setVisible(false);
		panelAccount.add(loggedInAs);

		// Played Cryptograms label
		numPlayed = new JLabel();
		numPlayed.setText("Played cryptograms: ");
		numPlayed.setBounds(10, 110, 240, 20);
		numPlayed.setVisible(false);
		panelAccount.add(numPlayed);

		// Completed Cryptograms label
		numCompleted = new JLabel();
		numCompleted.setText("Completed cryptograms: ");
		numCompleted.setBounds(10, 130, 240, 20);
		numCompleted.setVisible(false);
		panelAccount.add(numCompleted);

		// Average Time label
		avgTime = new JLabel();
		avgTime.setText("Average time: ");
		avgTime.setBounds(10, 150, 240, 20);
		avgTime.setVisible(false);
		panelAccount.add(avgTime);

		// Best Time label
		bestTime = new JLabel();
		bestTime.setText("Best time: ");
		bestTime.setBounds(10, 170, 240, 20);
		bestTime.setVisible(false);
		panelAccount.add(bestTime);

		// Accuracy label
		accuracy = new JLabel();
		accuracy.setText("Accuracy: ");
		accuracy.setBounds(10, 190, 240, 20);
		panelAccount.add(accuracy);
		accuracy.setVisible(false);

		// Game Panel
		panelGame = new JPanel();
		tabbedPane.addTab("Game", null, panelGame, null);
		panelGame.setLayout(null);

		// Label for image
		ImageIcon gameImage = new ImageIcon("game.png");
		gameImgLabel = new JLabel(gameImage);
		gameImgLabel.setBounds(10, 50, gameImage.getIconWidth(), gameImage.getIconHeight());
		panelGame.add(gameImgLabel);

		// Start game button
		btnStartGame = new JButton("Start New Game");
		btnStartGame.addActionListener(new StartGameButtonAction());
		btnStartGame.setBounds(10, 11, 130, 23);
		panelGame.add(btnStartGame);

		// Load game button
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new LoadGameButtonAction());
		btnLoadGame.setBounds(150, 11, 100, 23);
		panelGame.add(btnLoadGame);

		// View Frequencies label
		chckbxShowFreq = new JCheckBox("View Frequencies");
		chckbxShowFreq.addChangeListener(new ViewFreqAction());
		chckbxShowFreq.setBounds(10, GAME_BUTTONS_DEFAULT_Y, 150, 23);
		chckbxShowFreq.setVisible(false);
		chckbxShowFreq.setRolloverEnabled(false);
		panelGame.add(chckbxShowFreq);

		// Skip button
		btnSkip = new JButton("Skip Game");
		btnSkip.addActionListener(new SkipButtonAction());
		btnSkip.setBounds(208, GAME_BUTTONS_DEFAULT_Y, 100, 23);
		btnSkip.setVisible(false);
		panelGame.add(btnSkip);

		// Give up button
		btnGiveUp = new JButton("Give up");
		btnGiveUp.addActionListener(new GiveUpButtonAction());
		btnGiveUp.setBounds(318, GAME_BUTTONS_DEFAULT_Y, 89, 23);
		btnGiveUp.setVisible(false);
		panelGame.add(btnGiveUp);

		// Hint button
		btnHint = new JButton("Hint");
		btnHint.addActionListener(new HintButtonAction());
		btnHint.setBounds(10, GAME_BUTTONS_DEFAULT_Y, 89, 23);
		btnHint.setVisible(false);
		panelGame.add(btnHint);

		// Save Game button
		btnSaveGame = new JButton("Save Game");
		btnSaveGame.addActionListener(new SaveGameButtonAction());
		btnSaveGame.setBounds(417, GAME_BUTTONS_DEFAULT_Y, 100, 23);
		btnSaveGame.setVisible(false);
		panelGame.add(btnSaveGame);

		// Scoreboard panel
		panelScoreboard = new JPanel();
		tabbedPane.addTab("Scoreboard", null, panelScoreboard, null);
		panelScoreboard.setLayout(null);

		// Scoreboard criteria label
		JLabel criteriaLabel = new JLabel();
		criteriaLabel.setBounds(10, 10, 100, 20);
		criteriaLabel.setText("Select criteria: ");
		panelScoreboard.add(criteriaLabel);

		// Num completed cryptograms radio button
		criteriaNumCompleted = new JRadioButton();
		criteriaNumCompleted.setText("Completed cryptograms");
		criteriaNumCompleted.setBounds(170, 10, 170, 20);
		criteriaNumCompleted.addActionListener(new CriteriaChangeAction());
		panelScoreboard.add(criteriaNumCompleted);

		// Average time radio button
		criteriaAvgTime = new JRadioButton();
		criteriaAvgTime.setText("Average Time");
		criteriaAvgTime.setBounds(345, 10, 110, 20);
		criteriaAvgTime.addActionListener(new CriteriaChangeAction());
		panelScoreboard.add(criteriaAvgTime);

		// Besttime radio button
		criteriaBestTime = new JRadioButton();
		criteriaBestTime.setText("Best Time");
		criteriaBestTime.setBounds(470, 10, 90, 20);
		criteriaBestTime.addActionListener(new CriteriaChangeAction());
		panelScoreboard.add(criteriaBestTime);

		// Accuracy radio button
		criteriaAccuracy = new JRadioButton();
		criteriaAccuracy.setText("Accuracy");
		criteriaAccuracy.setBounds(580, 10, 80, 20);
		criteriaAccuracy.addActionListener(new CriteriaChangeAction());
		panelScoreboard.add(criteriaAccuracy);

		// Radio buttons group
		ButtonGroup group = new ButtonGroup();
		group.add(criteriaNumCompleted);
		group.add(criteriaBestTime);
		group.add(criteriaAvgTime);
		group.add(criteriaAccuracy);

		// Table and headers
		String[] headers = { "Place", "Name", "Games played", "Games completed", "Average time", "Best time", "Accuracy" };
		scoreboardTable = new JTable(10, 7);
		scoreboardTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scoreboardTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		scoreboardTable.getColumnModel().getColumn(1).setPreferredWidth(96);
		scoreboardTable.getColumnModel().getColumn(2).setPreferredWidth(101);
		scoreboardTable.getColumnModel().getColumn(3).setPreferredWidth(130);
		scoreboardTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		scoreboardTable.getColumnModel().getColumn(5).setPreferredWidth(90);
		scoreboardTable.getColumnModel().getColumn(6).setPreferredWidth(90);

		for (int i = 0; i < scoreboardTable.getColumnCount(); i++)
		{
			TableColumn column = scoreboardTable.getTableHeader().getColumnModel().getColumn(i);
			column.setHeaderValue(headers[i]);
		}

		// Pane for scoreboard table
		JScrollPane scrollPane = new JScrollPane(scoreboardTable);
		scrollPane.setBounds(10, 40, 670, 183);
		panelScoreboard.add(scrollPane);

		// Help panel
		panelHelp = new JPanel();
		panelHelp.setLayout(null);
		tabbedPane.addTab("Help", null, panelHelp, null);
		// More

		ImageIcon helpImage = new ImageIcon("help.png");
		JLabel helpImgLabel = new JLabel(helpImage);
		helpImgLabel.setBounds(5, 5, helpImage.getIconWidth(), helpImage.getIconHeight());
		panelHelp.add(helpImgLabel);
	}

	/*
	 * Clears all textboxes and labels from a game and hides game buttons
	 */
	private void clearWindow()
	{
		for (JTextField t : fields)
		{
			panelGame.remove(t);
		}

		for (JLabel l : mappingLabels)
		{
			panelGame.remove(l);
		}

		for (JLabel l : frequencyLabels)
		{
			panelGame.remove(l);
		}

		chckbxShowFreq.setBounds(chckbxShowFreq.getX(), GAME_BUTTONS_DEFAULT_Y - 10, chckbxShowFreq.getWidth(), chckbxShowFreq.getHeight());
		btnHint.setBounds(btnHint.getX(), GAME_BUTTONS_DEFAULT_Y, btnHint.getWidth(), btnHint.getHeight());
		btnGiveUp.setBounds(btnGiveUp.getX(), GAME_BUTTONS_DEFAULT_Y, btnGiveUp.getWidth(), btnGiveUp.getHeight());
		btnSkip.setBounds(btnSkip.getX(), GAME_BUTTONS_DEFAULT_Y, btnSkip.getWidth(), btnSkip.getHeight());
		btnSaveGame.setBounds(btnSaveGame.getX(), GAME_BUTTONS_DEFAULT_Y, btnSaveGame.getWidth(), btnSaveGame.getHeight());
		chckbxShowFreq.setVisible(false);
		btnHint.setVisible(false);
		btnGiveUp.setVisible(false);
		btnSkip.setVisible(false);
		btnSaveGame.setVisible(false);

		frame.setSize(WINDOW_GAME_DEFAULT_WIDTH, WINDOW_GAME_DEFAULT_HEIGHT);
		insets = frame.getInsets();
		tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
	}

	/*
	 * Generates the game textboxes and labels and show game buttons
	 */
	private void generateCryptogram()
	{
		boolean foundNextWord = false;
		char checkCharNext;
		char checkChar;
		int numFields = 0;
		int numMappingLabels = 0;
		int numFrequencyLabels = 0;
		int posFields = 0;
		int posMappingLabels = 0;
		int posFrequencyLabels = 0;
		int xNext = TEXTBOX_MARGIN_X;
		int x = TEXTBOX_MARGIN_X;
		int y = TEXTBOX_MARGIN_Y;
		int j = 0;
		JTextField tempText;
		JLabel tempLabel;

		frame.setSize(WINDOW_GAME_WIDTH, WINDOW_GAME_HEIGHT);
		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		String quote = currentCryptogram.getQuote();
		quote = quote.toUpperCase();

		// Get number of textboxes and number of labels.
		for (int i = 0; i < quote.length(); i++)
		{
			checkChar = quote.charAt(i);

			if ((checkChar >= 'a' && checkChar <= 'z') || (checkChar >= 'A' && checkChar <= 'Z'))
			{
				numFields++;
				numFrequencyLabels++;
			}

			numMappingLabels++;
		}

		fields = new JTextField[numFields];
		mappingLabels = new JLabel[numMappingLabels];
		frequencyLabels = new JLabel[numFrequencyLabels];

		// Initialize textboxes.
		for (int i = 0; i < numFields; i++)
			fields[i] = new JTextField();

		// Initialize mapping labels.
		for (int i = 0; i < numMappingLabels; i++)
			mappingLabels[i] = new JLabel();

		// Initialize frequency labels.
		for (int i = 0; i < numFrequencyLabels; i++)
			frequencyLabels[i] = new JLabel();

		// Setting up textboxes and labels.
		for (int i = 0; i < quote.length(); i++)
		{
			checkChar = quote.charAt(i);
			xNext = x;

			// Check whether a word will fill in the window.
			while (j < quote.length() && !foundNextWord)
			{
				checkCharNext = quote.charAt(j);

				// Check if it is not a letter.
				if (!(checkCharNext >= 'a' && checkCharNext <= 'z') && !(checkCharNext >= 'A' && checkCharNext <= 'Z'))
				{
					// Check if it is a space character.
					if (checkCharNext != ' ')
						xNext = xNext + PUNCTUATION_MARK_WIDTH;

					// Does it go out of the window? If yes, reset X and go to a new line.
					if (xNext >= (WINDOW_GAME_WIDTH - TEXTBOX_MARGIN_X))
					{
						xNext = TEXTBOX_MARGIN_X;
						x = TEXTBOX_MARGIN_X;
						y += NEW_LINE_MARGIN;

						frame.setSize(WINDOW_GAME_WIDTH, frame.getHeight() + NEW_LINE_MARGIN);
						insets = frame.getInsets();
						tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
						windowWidth = frame.getWidth();
						windowHeight = frame.getHeight();
					}

					foundNextWord = true;
					j++;
					break;
				}
				else // It is a letter.
				{
					xNext = xNext + LETTER_WIDTH;

					// Does it go out of the window? If yes, reset X and go to a new line.
					if (xNext >= (WINDOW_GAME_WIDTH - TEXTBOX_MARGIN_X))
					{
						xNext = TEXTBOX_MARGIN_X;
						x = TEXTBOX_MARGIN_X;
						y += NEW_LINE_MARGIN;

						frame.setSize(WINDOW_GAME_WIDTH, frame.getHeight() + NEW_LINE_MARGIN);
						insets = frame.getInsets();
						tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
						windowWidth = frame.getWidth();
						windowHeight = frame.getHeight();
						foundNextWord = true;

						// Find the end of the current word so that next time we start searching, we are
						// at the right place.
						while (j < quote.length())
						{
							checkCharNext = quote.charAt(j++);

							if (!(checkCharNext >= 'a' && checkCharNext <= 'z') && !(checkCharNext >= 'A' && checkCharNext <= 'Z'))
								break;
						}

						break;
					}
				}

				j++;
			}

			// If it is a label - set it up.
			if (!(checkChar >= 'a' && checkChar <= 'z') && !(checkChar >= 'A' && checkChar <= 'Z'))
			{
				tempLabel = mappingLabels[posMappingLabels];

				tempLabel.setBounds(x, y, LABEL_CHARACTER_WIDTH, LABEL_CHARACTER_HEIGHT);
				tempLabel.setText(Character.toString(checkChar));
				tempLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
				panelGame.add(tempLabel);

				posMappingLabels++;
				x += (checkChar == ' ' ? SPACE_WIDTH : PUNCTUATION_MARK_WIDTH);
				foundNextWord = false;
			}
			else // If it is textbox - set it up as well as its corresponding mapping label.
			{
				tempText = fields[posFields];
				tempText.setBounds(x, y, TEXTBOX_WIDTH, TEXTBOX_HEIGHT);
				tempText.setHorizontalAlignment(SwingConstants.CENTER);
				tempText.setFont(new Font("Georgia", Font.PLAIN, 18));
				tempText.setDocument(new JTextFieldLimit(1));
				tempText.setTransferHandler(null);
				tempText.setDropTarget(null);
				tempText.setName(Character.toString(checkChar));
				tempText.addKeyListener(new KeyAdapter()
				{
					// Handle keyTyped event - check for invalid keys, modify textboxes and move
					// focus
					public void keyTyped(KeyEvent e)
					{
						textfieldOnKeyTyped(e);
					}
				});
				tempText.addFocusListener(new FocusAdapter()
				{
					@Override
					// Handle focus gain
					public void focusGained(FocusEvent e)
					{
						textfieldOnFocusGained(e);
					}
				});
				tempText.addFocusListener(new FocusAdapter()
				{
					@Override
					// Handle focus lost
					public void focusLost(FocusEvent e)
					{
						textfieldOnFocusLost(e);
					}
				});
				panelGame.add(tempText);
				posFields++;

				tempLabel = mappingLabels[posMappingLabels];
				tempLabel.setBounds(x, y + TEXTBOX_LABEL_OFFSET_Y, LABEL_LETTER_WIDTH, LABEL_LETTER_HEIGHT);
				tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
				tempLabel.setText(currentMapping[(int) checkChar - 'A']);
				tempLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
				panelGame.add(tempLabel);
				posMappingLabels++;

				tempLabel = frequencyLabels[posFrequencyLabels];
				tempLabel.setName("Frequency");
				tempLabel.setVisible(false);
				tempLabel.setBounds(x, y + LABEL_FREQUENCY_OFFSET_Y, LABEL_FREQUENCY_WIDTH, LABEL_FREQUENCY_HEIGHT);
				tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
				tempLabel.setText(Integer.toString(currentCryptogram.getFrequency(checkChar)));
				tempLabel.setFont(new Font("Verdana", Font.PLAIN, 10));
				panelGame.add(tempLabel);
				posFrequencyLabels++;

				x += LETTER_WIDTH;
			}
		}

		// Position game buttons and show them
		chckbxShowFreq.setBounds(chckbxShowFreq.getX(), chckbxShowFreq.getY() + y + GAME_BUTTONS_MARGIN_Y - VIEW_FREQ_OFFSET, chckbxShowFreq.getWidth(), chckbxShowFreq.getHeight());
		btnHint.setBounds(btnHint.getX(), btnHint.getY() + y + GAME_BUTTONS_MARGIN_Y, btnHint.getWidth(), btnHint.getHeight());
		btnGiveUp.setBounds(btnGiveUp.getX(), btnGiveUp.getY() + y + GAME_BUTTONS_MARGIN_Y, btnGiveUp.getWidth(), btnGiveUp.getHeight());
		btnSkip.setBounds(btnSkip.getX(), btnSkip.getY() + y + GAME_BUTTONS_MARGIN_Y, btnSkip.getWidth(), btnSkip.getHeight());
		btnSaveGame.setBounds(btnSaveGame.getX(), btnSaveGame.getY() + y + GAME_BUTTONS_MARGIN_Y, btnSaveGame.getWidth(), btnSaveGame.getHeight());
		chckbxShowFreq.setVisible(true);
		btnHint.setVisible(true);
		btnGiveUp.setVisible(true);
		btnSkip.setVisible(true);
		btnSaveGame.setVisible(true);

		// Set frame and tabbed pane size
		frame.setSize(frame.getWidth(), frame.getHeight() + TEXTBOX_MARGIN_Y + GAME_BUTTONS_MARGIN_Y + FRAME_MARGIN);
		insets = frame.getInsets();
		tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));

		windowWidth = frame.getWidth();
		windowHeight = frame.getHeight();
		gameGenerated = true;
		gameRunning = true;
	}

	/*
	 * Handle keyTyped event - check for invalid keys, modify textboxes and move
	 * focus.
	 */
	private void textfieldOnKeyTyped(KeyEvent e)
	{
		JTextField textField = (JTextField) e.getSource();
		char tempChar = e.getKeyChar();

		if (!gameRunning)
		{
			JOptionPane.showMessageDialog(null, "Game has already finished!", TITLE, JOptionPane.DEFAULT_OPTION);
			e.consume();
			return;
		}

		if ((tempChar >= 'a' && tempChar <= 'z') || (tempChar >= 'A' && tempChar <= 'Z') || tempChar == KeyEvent.VK_BACK_SPACE || tempChar == KeyEvent.VK_DELETE)
		{
			// If the character is a letter between 'a' and 'z' or between 'A' and 'Z'
			// Replace if exists
			if (tempChar != KeyEvent.VK_BACK_SPACE && tempChar != KeyEvent.VK_DELETE) // if ((tempChar >= 'a' && tempChar <= 'z') || (tempChar >= 'A' && tempChar <=
																						// 'Z'))
			{
				for (JTextField t : fields)
				{
					if (t != textField && t.getText().equals(Character.toString(tempChar).toUpperCase()))
					{
						// Do you want to change the letter?
						JPanel replaceLetter = new JPanel();
						replaceLetter.add(new JLabel("Do you want to replace the letter \"" + Character.toString(tempChar).toUpperCase() + "\"?"));

						int result = JOptionPane.showConfirmDialog(null, replaceLetter, TITLE + REPLACE_SUBTITLE, JOptionPane.YES_NO_OPTION); // JOptionPane.YES_OPTION;

						if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION)
						{
							e.consume();
							return;
						}
						else
						{
							for (JTextField t1 : fields)
							{
								if (t1 != textField && t1.getText().equals(Character.toString(tempChar).toUpperCase()))
									t1.setText("");
							}
						}
					}
				}

				// Put the same letter everywhere
				for (JTextField t : fields)
				{
					if (t.getName().equals(textField.getName()))/// *t != textField &&
						t.setText(Character.toString(tempChar).toUpperCase());
				}

				// Move focus
				int textboxIndex = 0;
				boolean foundEmpty = false;

				// Search for the next empty field
				for (int i = 0; i < fields.length; i++)
				{
					if (fields[i] == textField)
					{
						textboxIndex = i;

						for (int j = i + 1; j < fields.length; j++)
						{
							if (fields[j].getText().isEmpty())
							{
								fields[j].requestFocusInWindow();
								foundEmpty = true;
								break;
							}
						}

						break;
					}
				}

				// if an empty field is not found, search from the begging
				if (!foundEmpty)
				{
					for (int i = 0; i < textboxIndex; i++)
					{
						if (fields[i].getText().isEmpty())
						{
							fields[i].requestFocusInWindow();
							break;
						}
					}
				}

				// Accuracy change
				if (textField.getText().equals(textField.getName()))
					gameGuessedLetters++;

				gameEnteredLetters++;
				checkCryptogram();
			}
			else
			{
				// If it is a delete or backspace, delete all text fields that match the current
				// one
				for (JTextField t : fields)
				{
					if (t.getName().equals(textField.getName()))
						t.setText("");
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Only letters are allowed!", TITLE + ENTER_SUBTITLE, JOptionPane.DEFAULT_OPTION);
			return;
		}

		e.consume();
		return;
	}

	/*
	 * Handle focus gained to a textfield.
	 */
	private void textfieldOnFocusGained(FocusEvent e)
	{
		JTextField textField = (JTextField) e.getSource();

		for (JTextField t : fields)
		{
			if (t.getName().equals(textField.getName()))
				t.setBackground(marked);
		}
	}

	/*
	 * Handle focus lost from a textfield.
	 */
	private void textfieldOnFocusLost(FocusEvent e)
	{
		for (JTextField t : fields)
		{
			JTextField textField = (JTextField) e.getSource();

			if (t.getName().equals(textField.getName()))
				t.setBackground(unmarked);
		}
	}

	/*
	 * Helper function used to get only the letters of a cryptogram. Used for game
	 * checks.
	 */
	private void makeOnlyLetters()
	{
		String quote = currentCryptogram.getQuote();
		StringBuilder onlyLetters = new StringBuilder();
		char tempChar;

		for (int i = 0; i < quote.length(); i++)
		{
			tempChar = quote.charAt(i);

			if ((tempChar >= 'a' && tempChar <= 'z') || (tempChar >= 'A' && tempChar <= 'Z'))
				onlyLetters.append(tempChar);
		}

		cryptogramOnlyLetters = onlyLetters.toString();
		cryptogramOnlyLetters = cryptogramOnlyLetters.toUpperCase();
	}

	/*
	 * Used to check whether the currently typed letters match the original
	 * sentence. Ends the game and updates statistics if the cryptogram is correct.
	 */
	private void checkCryptogram()
	{
		boolean solutionCorrect = true;
		JTextField t;

		for (int i = 0; i < fields.length; i++)
		{
			t = fields[i];

			// If there is an empty textifield, ignore checking
			if (t.getText().length() <= 0)
				return;

			// If there is an incorrect textfield, solution is not correct
			if (t.getText().charAt(0) != cryptogramOnlyLetters.charAt(i))
				solutionCorrect = false;
		}

		// Solution is correct, end game, update statistics
		if (solutionCorrect)
		{
			JOptionPane.showMessageDialog(null, "Congratulations, your solution is correct!", TITLE, JOptionPane.DEFAULT_OPTION);
			gameRunning = false;

			long tempTimestamp = (System.nanoTime() / 1000000) - gameTimestamp; // System.currentTimeMillis() - gameTimestamp;
			long tempAvgTime = currentPlayer.getAverageTime().getTime() * currentPlayer.getNumCompleted();
			long tempBestTime = currentPlayer.getBestTime().getTime();
			double tempAccuracy = currentPlayer.getAccuracy() * currentPlayer.getNumCompleted();

			currentPlayer.setNumCompleted(currentPlayer.getNumCompleted() + 1);
			tempAvgTime = (tempAvgTime + tempTimestamp) / currentPlayer.getNumCompleted();
			currentPlayer.setAverageTime(new Time(tempAvgTime));

			if (tempBestTime == 0 || tempBestTime > tempTimestamp)
			{
				currentPlayer.setBestTime(new Time(tempTimestamp));

				long timeMs = currentPlayer.getBestTime().getTime();
				long second = (timeMs / 1000) % 60;
				long minute = (timeMs / (1000 * 60)) % 60;
				long hour = (timeMs / (1000 * 60 * 60)) % 24;
				String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				JOptionPane.showMessageDialog(null, "Well played, your new best time is: " + time, TITLE, JOptionPane.DEFAULT_OPTION);
			}

			tempAccuracy = (tempAccuracy + ((double) gameGuessedLetters / (double) gameEnteredLetters)) / currentPlayer.getNumCompleted();
			currentPlayer.setAccuracy(tempAccuracy);
			playersDatabase.updatePlayer(currentPlayer);
			return;
		}
		else
		{
			// Solution is not correct
			JOptionPane.showMessageDialog(null, "Your solution is not correct. Keep trying!", TITLE, JOptionPane.DEFAULT_OPTION);
			return;
		}
	}

	/*
	 * Used to save the current game to a file.
	 */
	private void saveGame()
	{
		JFileChooser fileChooser = new JFileChooser()
		{
			// If file exists, ask the user whether they want to replace it
			@Override
			public void approveSelection()
			{
				File file = getSelectedFile();

				if (file.exists() && getDialogType() == SAVE_DIALOG)
				{
					int result = JOptionPane.showConfirmDialog(this, "This file already exists! Do you want to overwrite it?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);

					if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION)
						return;
					else if (result == JOptionPane.CANCEL_OPTION)
					{
						cancelSelection();
						return;
					}
					else if (result == JOptionPane.YES_OPTION)
					{
						super.approveSelection();
						return;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Cannot save game!", TITLE + SAVE_SUBTITLE, JOptionPane.DEFAULT_OPTION);
						return;
					}
				}

				super.approveSelection();
			}
		};

		fileChooser.setSelectedFile(new File(DEFAULT_SAVE_FILE));

		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();

			// What to save dialog
			Object[] options = { "Both", "Cryptogram only", "Cancel" };
			JPanel saveCryptogram = new JPanel();
			saveCryptogram.add(new JLabel("Do you want to save both the cryptogram and the state of play?"));

			int result = JOptionPane.showOptionDialog(null, saveCryptogram, TITLE + SAVE_SUBTITLE, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

			if (result == JOptionPane.CANCEL_OPTION)
				return;

			// Create or overwrite file
			try
			{
				if (file.exists())
					file.delete();

				file.createNewFile();
			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null, "Cannot save game!", TITLE + SAVE_SUBTITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			FileWriter fw = null;
			BufferedWriter writer = null;
			String[] mapping = currentCryptogram.getMapping();
			String text;

			// Fill in the content of the file
			try
			{
				fw = new FileWriter(file);
				writer = new BufferedWriter(fw);

				writer.write(Integer.toString(selectedGameType));
				writer.newLine();
				writer.write(currentCryptogram.getQuote());
				writer.newLine();

				for (int i = 0; i < 26; i++)
				{
					writer.write(mapping[i]);

					if (i != 25)
						writer.write(" ");
				}

				writer.newLine();

				for (JTextField f : fields)
				{
					text = f.getText();

					if (text.isEmpty() || result == JOptionPane.NO_OPTION)
						writer.write("-");
					else
						writer.write(text);
				}

				writer.close();
			}
			catch (IOException e2)
			{
				JOptionPane.showMessageDialog(null, "Cannot save game!", TITLE + SAVE_SUBTITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
	}

	/*
	 * Used to load a game from a file.
	 */
	private boolean loadGame()
	{
		JFileChooser fileChooser = new JFileChooser();

		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			String quote = "";
			String mapping[] = new String[26];

			// Load selected file
			try
			{
				FileReader fr = new FileReader(file);
				BufferedReader reader = new BufferedReader(fr);
				String typeString = "";
				String map = "";
				Scanner scan = null;
				char tempChar;
				int fieldsNum = 0;
				boolean mappingEntered[] = new boolean[26];

				typeString = reader.readLine();

				// Check what is the type of the loaded cryptogram
				if (typeString.length() != 1)
				{
					JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
					return false;
				}

				// Check type bounds
				if (typeString.charAt(0) < '0' || typeString.charAt(0) > '1')
				{
					JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
					return false;
				}

				selectedGameType = Integer.parseInt(typeString);
				quote = reader.readLine();
				map = reader.readLine();

				// Check type of cryptogram
				if (selectedGameType == CryptogramFactory.LETTER_CRYPTOGRAM)
				{
					// If it is a letter cryptogram
					// Check length of mapping
					if (map.length() != (26 + 25)) // 26 letters + 25 spaces between them
					{
						JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
						return false;
					}

					scan = new Scanner(map);

					// Try to load a valid mapping
					for (int i = 0; i < 26; i++)
					{
						tempChar = scan.next().charAt(0);

						if (tempChar < 'A' || tempChar > 'Z' || mappingEntered[(int) tempChar - 'A'])
						{
							JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
							return false;
						}

						mapping[i] = Character.toString(tempChar);
						mappingEntered[(int) tempChar - 'A'] = true;
					}
				}
				else if (selectedGameType == CryptogramFactory.NUMBER_CRYPTOGRAM)
				{
					// If it is a number cryptogram
					// Check length if mapping
					if (map.length() != (43 + 25)) // 43 is the total size of the numbers 1 to 26 inclusive + 25 spaces between
													// them
					{
						JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
						return false;
					}

					scan = new Scanner(map);
					int tempNumber = -1;

					// Try to load a valid mapping
					for (int i = 0; i < 26; i++)
					{
						tempNumber = scan.nextInt();

						if (tempNumber <= 0 || tempNumber > 26 || mappingEntered[tempNumber - 1])
						{
							JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
							return false;
						}

						mapping[i] = Integer.toString(tempNumber);
						mappingEntered[tempNumber - 1] = true;
					}
				}
				else
				{
					// Invalid game type
					JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
					return false;
				}

				// Try to load a valid game state
				fileTyped = reader.readLine();

				for (int i = 0; i < quote.length(); i++)
				{
					tempChar = quote.charAt(i);

					if (!(tempChar >= 'a' && tempChar <= 'z') && !(tempChar >= 'A' && tempChar <= 'Z'))
						continue;

					fieldsNum++;
				}

				// Check length of quote and lenght of game state
				if (fileTyped.length() != fieldsNum)
				{
					JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
					return false;
				}

				// Check for invalid characters
				for (int i = 0; i < fileTyped.length(); i++)
				{
					tempChar = fileTyped.charAt(i);

					if ((tempChar < 'A' || tempChar > 'Z') && tempChar != '-')
					{
						JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
						return false;
					}
				}

				// Check if there is nothing else in the file
				if (reader.readLine() != null)
				{
					JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
					return false;
				}

				reader.close();
			}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "File not found!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
				return false;
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "File is corupted!", TITLE + LOAD_SUBTITLE, JOptionPane.DEFAULT_OPTION);
				return false;
			}

			// Load the game into the variables
			currentCryptogram.loadCryptogram(selectedGameType, quote, mapping);
			currentMapping = currentCryptogram.getMapping();
			return true;
		}

		return false;
	}

	/*
	 * Additional function to loadGame() function used to fill letters if there is
	 * state of play.
	 */
	private void enterLoadedLetters()
	{
		JTextField tempField;
		char tempChar;

		for (int i = 0; i < fileTyped.length(); i++)
		{
			tempChar = fileTyped.charAt(i);

			if (tempChar == '-')
				continue;

			tempField = fields[i];
			tempField.setText(Character.toString(tempChar));
		}
	}

	/*
	 * Used to update the scoreboard table depending on the criteria provided.
	 */
	private void updateScoreboard(int criteria)
	{
		List<Player> top10 = new ArrayList<Player>();
		Player tempPlayer;

		// Clear scoreboard
		for (int r = 0; r < scoreboardTable.getRowCount(); r++)
		{
			for (int c = 0; c < scoreboardTable.getColumnCount(); c++)
			{
				scoreboardTable.setValueAt("", r, c);
			}
		}

		// If a valid criteria provided, update the scoreboard
		if (criteria == Players.CRITERIA_NUM_COMPLETED || criteria == Players.CRITERIA_AVERAGE_TIME || criteria == Players.CRITERIA_BEST_TIME || criteria == Players.CRITERIA_ACCURACY)
		{
			top10 = playersDatabase.getTop10(criteria);
			long timeMs;
			long second;
			long minute;
			long hour;
			String time;

			for (int r = 0; r < scoreboardTable.getRowCount() && r < top10.size(); r++)
			{
				tempPlayer = top10.get(r);
				scoreboardTable.setValueAt((r == 0 ? "1st" : (r == 1 ? "2nd" : (r == 2 ? "3rd" : (r + 1) + "th"))) + " place", r, 0);
				scoreboardTable.setValueAt(tempPlayer.getName(), r, 1);
				scoreboardTable.setValueAt(tempPlayer.getNumPlayed(), r, 2);
				scoreboardTable.setValueAt(tempPlayer.getNumCompleted(), r, 3);

				timeMs = tempPlayer.getAverageTime().getTime();
				second = (timeMs / 1000) % 60;
				minute = (timeMs / (1000 * 60)) % 60;
				hour = (timeMs / (1000 * 60 * 60)) % 24;
				time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				scoreboardTable.setValueAt(time, r, 4);

				timeMs = tempPlayer.getBestTime().getTime();
				second = (timeMs / 1000) % 60;
				minute = (timeMs / (1000 * 60)) % 60;
				hour = (timeMs / (1000 * 60 * 60)) % 24;
				time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				scoreboardTable.setValueAt(time, r, 5);

				scoreboardTable.setValueAt(String.format("%.2f", tempPlayer.getAccuracy()), r, 6);
			}
		}
	}

	/*
	 * Used to restore the window to its current size if maximized.
	 */
	private class RestoreWindow implements WindowStateListener
	{
		public void windowStateChanged(WindowEvent e)
		{
			if ((e.getNewState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH)
			{
				// JFrame thisFrame = (JFrame) e.getSource();
				// thisFrame.setSize(windowWidth, windowHeight);
				// dim = Toolkit.getDefaultToolkit().getScreenSize();

				int index = tabbedPane.getSelectedIndex();

				if (index == 0)
				{
					if (loggedIn)
					{
						frame.setBounds(frame.getX(), frame.getY(), WINDOW_ACCOUNT_LOGGED_WIDTH, WINDOW_ACCOUNT_LOGGED_HEIGHT);
						insets = frame.getInsets();
						tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
					}
					else
					{
						frame.setBounds(frame.getX(), frame.getY(), WINDOW_ACCOUNT_WIDTH, WINDOW_ACCOUNT_HEIGHT);
						insets = frame.getInsets();
						tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
					}
				}
				else if (index == 1)
				{
					if (gameGenerated)
					{
						frame.setBounds(frame.getX(), frame.getY(), WINDOW_GAME_WIDTH, windowHeight);
						insets = frame.getInsets();
						tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
					}
					else
					{
						frame.setBounds(frame.getX(), frame.getY(), WINDOW_NO_GAME_WIDTH, WINDOW_NO_GAME_HEIGHT);
						insets = frame.getInsets();
						tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
					}
				}
				else if (index == 2)
				{
					frame.setBounds(frame.getX(), frame.getY(), WINDOW_SCOREBOARD_WIDTH, WINDOW_SCOREBOARD_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}
			}
		}
	}

	/*
	 * Used to update the screen and tabbed pane sizes according to the selected
	 * tab.
	 */
	private class TabChangeAction implements ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			int index = tabbedPane.getSelectedIndex();

			if (index == 0)
			{
				if (loggedIn)
				{
					frame.setBounds(frame.getX(), frame.getY(), WINDOW_ACCOUNT_LOGGED_WIDTH, WINDOW_ACCOUNT_LOGGED_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));

					loggedInAs.setText("Logged in as: " + currentPlayer.getName());
					numPlayed.setText("Played cryptograms: " + currentPlayer.getNumPlayed());
					numCompleted.setText("Completed cryptograms: " + currentPlayer.getNumCompleted());

					long timeMs = currentPlayer.getAverageTime().getTime();
					long second = (timeMs / 1000) % 60;
					long minute = (timeMs / (1000 * 60)) % 60;
					long hour = (timeMs / (1000 * 60 * 60)) % 24;
					String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
					avgTime.setText("Average time: " + time);

					timeMs = currentPlayer.getBestTime().getTime();
					second = (timeMs / 1000) % 60;
					minute = (timeMs / (1000 * 60)) % 60;
					hour = (timeMs / (1000 * 60 * 60)) % 24;
					time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
					bestTime.setText("Best time: " + time);
					accuracy.setText(String.format("Accuracy: %.2f", currentPlayer.getAccuracy()));
				}
				else
				{
					frame.setBounds(frame.getX(), frame.getY(), WINDOW_ACCOUNT_WIDTH, WINDOW_ACCOUNT_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}
			}
			else if (index == 1)
			{
				if (gameGenerated)
				{
					frame.setBounds(frame.getX(), frame.getY(), WINDOW_GAME_WIDTH, windowHeight);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}
				else
				{
					frame.setBounds(frame.getX(), frame.getY(), WINDOW_NO_GAME_WIDTH, WINDOW_NO_GAME_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}
			}
			else if (index == 2)
			{
				frame.setBounds(frame.getX(), frame.getY(), WINDOW_SCOREBOARD_WIDTH, WINDOW_SCOREBOARD_HEIGHT);
				insets = frame.getInsets();
				tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));

				if (criteriaNumCompleted.isSelected())
					updateScoreboard(Players.CRITERIA_NUM_COMPLETED);
				else if (criteriaAvgTime.isSelected())
					updateScoreboard(Players.CRITERIA_AVERAGE_TIME);
				else if (criteriaBestTime.isSelected())
					updateScoreboard(Players.CRITERIA_BEST_TIME);
				else if (criteriaAccuracy.isSelected())
					updateScoreboard(Players.CRITERIA_ACCURACY);
				else
					updateScoreboard(-1);
			}
			else if (index == 3)
			{
				frame.setBounds(frame.getX(), frame.getY(), WINDOW_HELP_WIDTH, WINDOW_HELP_HEIGHT);
				insets = frame.getInsets();
				tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
			}
		}
	}

	/*
	 * Login button actions.
	 */
	private class LoginButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if (name.getText().isEmpty())
			{
				// Check if textfield is empty
				JOptionPane.showMessageDialog(null, "You should enter a username!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}
			else if (name.getText().contains(" "))
			{
				// Check if there is a space character in the name
				JOptionPane.showMessageDialog(null, "Username cannot contain a space character!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			int result = playersDatabase.findPlayer(name.getText());

			// Check if a player exists
			if (result != -1)
			{
				// Player does not exist
				currentPlayer = playersDatabase.getPlayer(result);

				loggedInAs.setVisible(true);
				numPlayed.setVisible(true);
				numCompleted.setVisible(true);
				avgTime.setVisible(true);
				bestTime.setVisible(true);
				accuracy.setVisible(true);

				loggedInAs.setText("Logged in as: " + currentPlayer.getName());
				numPlayed.setText("Played cryptograms: " + currentPlayer.getNumPlayed());
				numCompleted.setText("Completed cryptograms: " + currentPlayer.getNumCompleted());

				long timeMs = currentPlayer.getAverageTime().getTime();
				long second = (timeMs / 1000) % 60;
				long minute = (timeMs / (1000 * 60)) % 60;
				long hour = (timeMs / (1000 * 60 * 60)) % 24;
				String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				avgTime.setText("Average time: " + time);

				timeMs = currentPlayer.getBestTime().getTime();
				second = (timeMs / 1000) % 60;
				minute = (timeMs / (1000 * 60)) % 60;
				hour = (timeMs / (1000 * 60 * 60)) % 24;
				time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				bestTime.setText("Best time: " + time);
				accuracy.setText(String.format("Accuracy: %.2f", currentPlayer.getAccuracy()));

				if (!loggedIn)
				{
					frame.setSize(WINDOW_ACCOUNT_LOGGED_WIDTH, WINDOW_ACCOUNT_LOGGED_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}
				else if (gameGenerated)
				{
					clearWindow();

					frame.setSize(WINDOW_ACCOUNT_LOGGED_WIDTH, WINDOW_ACCOUNT_LOGGED_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}

				gameImgLabel.setVisible(true);
				name.setText("");
				loggedIn = true;
				gameRunning = false;
				gameGenerated = false;
			}
			else
			{
				// Player exists
				JOptionPane.showMessageDialog(null, "The username you entered does not match any account.\nRegister for an account.", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
	}

	/*
	 * Register button actions.
	 */
	private class RegisterButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if (name.getText().isEmpty())
			{
				// Check if the textfield is empty
				JOptionPane.showMessageDialog(null, "You should enter a username!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}
			else if (name.getText().contains(" "))
			{
				// Check if the username contains space characters
				JOptionPane.showMessageDialog(null, "Username cannot contain a space character!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			int result = playersDatabase.findPlayer(name.getText());

			// Check if user exists
			if (result == -1)
			{
				// If user does not exist
				Player newPlayer = new Player(name.getText(), 0, 0, new Time(0), new Time(0), 0.0);
				playersDatabase.addPlayer(newPlayer);
				currentPlayer = newPlayer;

				loggedInAs.setVisible(true);
				numPlayed.setVisible(true);
				numCompleted.setVisible(true);
				avgTime.setVisible(true);
				bestTime.setVisible(true);
				accuracy.setVisible(true);

				loggedInAs.setText("Logged in as: " + currentPlayer.getName());
				numPlayed.setText("Played cryptograms: " + currentPlayer.getNumPlayed());
				numCompleted.setText("Completed cryptograms: " + currentPlayer.getNumCompleted());

				long timeMs = currentPlayer.getAverageTime().getTime();
				long second = (timeMs / 1000) % 60;
				long minute = (timeMs / (1000 * 60)) % 60;
				long hour = (timeMs / (1000 * 60 * 60)) % 24;
				String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				avgTime.setText("Average time: " + time);

				timeMs = currentPlayer.getBestTime().getTime();
				second = (timeMs / 1000) % 60;
				minute = (timeMs / (1000 * 60)) % 60;
				hour = (timeMs / (1000 * 60 * 60)) % 24;
				time = String.format("%02d:%02d:%02d.%d", hour, minute, second, (timeMs % 1000) / 10);
				bestTime.setText("Best time: " + time);
				accuracy.setText(String.format("Accuracy: %.2f", currentPlayer.getAccuracy()));

				if (!loggedIn)
				{
					frame.setSize(WINDOW_ACCOUNT_LOGGED_WIDTH, WINDOW_ACCOUNT_LOGGED_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}
				else if (gameGenerated)
				{
					clearWindow();

					frame.setSize(WINDOW_ACCOUNT_LOGGED_WIDTH, WINDOW_ACCOUNT_LOGGED_HEIGHT);
					insets = frame.getInsets();
					tabbedPane.setBounds(0, 0, frame.getWidth(), frame.getHeight() - (insets.top + insets.bottom));
				}

				gameImgLabel.setVisible(true);
				name.setText("");
				loggedIn = true;
				gameRunning = false;
				gameGenerated = false;
			}
			else
			{
				// If the user exist
				JOptionPane.showMessageDialog(null, "User with this name already exists!\nPlease use a different username.", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}
		}
	}

	/*
	 * Start Game button actions.
	 */
	private class StartGameButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Check if user has logged in
			if (!loggedIn)
			{
				JOptionPane.showMessageDialog(null, "You must be logged in to start a game!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			// Ask the user how they want the mapping
			Object[] options = { "Letter Cryptogram", "Number Cryptogram", "Cancel" };
			JPanel selectCryptogram = new JPanel();
			selectCryptogram.add(new JLabel("What type of cryptogram do you want to play?"));

			int result = JOptionPane.showOptionDialog(null, selectCryptogram, TITLE + SELECT_SUBTITLE, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

			if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION)
				return;
			else if (result == JOptionPane.YES_OPTION)
				selectedGameType = CryptogramFactory.LETTER_CRYPTOGRAM;
			else
				selectedGameType = CryptogramFactory.NUMBER_CRYPTOGRAM;

			if (gameGenerated)
				clearWindow();
			else
				gameImgLabel.setVisible(false);

			// Get a cryptogram and start game
			currentCryptogram.createCryptogram(selectedGameType);
			currentMapping = currentCryptogram.getMapping();

			makeOnlyLetters();
			generateCryptogram();

			currentPlayer.setNumPlayed(currentPlayer.getNumPlayed() + 1);
			playersDatabase.updatePlayer(currentPlayer);
			gameTimestamp = (System.nanoTime() / 1000000);// System.currentTimeMillis();
			gameEnteredLetters = 0;
			gameGuessedLetters = 0;
		}
	}

	/*
	 * Load Game button actions.
	 */
	private class LoadGameButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Check if user has logged in
			if (!loggedIn)
			{
				JOptionPane.showMessageDialog(null, "You must be logged in to start a game!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			// Check if the game has been loaded successfully
			if (loadGame())
			{
				// Start the games
				if (gameGenerated)
					clearWindow();
				else
					gameImgLabel.setVisible(false);

				makeOnlyLetters();
				generateCryptogram();
				enterLoadedLetters();

				currentPlayer.setNumPlayed(currentPlayer.getNumPlayed() + 1);
				playersDatabase.updatePlayer(currentPlayer);
				gameTimestamp = (System.nanoTime() / 1000000); // System.currentTimeMillis();
				gameEnteredLetters = 0;
				gameGuessedLetters = 0;
			}
		}
	}

	/*
	 * View Frequencies checkbox action.
	 */
	private class ViewFreqAction implements ChangeListener
	{
		public void stateChanged(ChangeEvent arg0)
		{
			JCheckBox check = (JCheckBox) arg0.getSource();

			if (check.isSelected())
			{
				for (JLabel l : frequencyLabels)
				{
					l.setVisible(true);
				}
			}
			else
			{
				for (JLabel l : frequencyLabels)
				{
					l.setVisible(false);
				}
			}
		}
	}

	/*
	 * Hint button actions.
	 */
	private class HintButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Check if a game is running
			if (!gameRunning)
			{
				JOptionPane.showMessageDialog(null, "Game has already finished!", TITLE + HINT_SUBTITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			JTextField t = null;
			char tempChar = 0;
			int posSelected = -1;

			// First select a unfilled vowel that is not used
			for (int i = 0; i < cryptogramOnlyLetters.length(); i++)
			{
				tempChar = cryptogramOnlyLetters.charAt(i);

				// Check if it should be a vowel
				if (tempChar == 'A' || tempChar == 'E' || tempChar == 'I' || tempChar == 'O' || tempChar == 'U')
				{
					t = fields[i];

					// Check if it is not filled already in the current place
					if (t.getText().equals(Character.toString(tempChar)))
						continue;

					// Check if is an empty textfield
					if (t.getText().isEmpty())
					{
						JTextField t1;
						boolean found = true;

						// Search whether this letter has been used somewhere else
						for (int j = 0; j < cryptogramOnlyLetters.length(); j++)
						{
							t1 = fields[j];

							if (t1.getText().isEmpty())
								continue;

							if (tempChar == t1.getText().charAt(0))
							{
								found = false;
								break;
							}
						}

						// If it is not used, mark it as selected
						if (found)
						{
							posSelected = i;
							break;
						}
					}
				}
			}

			// If a unfilled vowel is found, place it
			if (posSelected != -1)
			{
				for (JTextField t1 : fields)
				{
					if (t.getName().equals(t1.getName()))
						t1.setText(Character.toString(tempChar).toUpperCase());
				}

				gameEnteredLetters++;
				checkCryptogram();
				return;
			}
			else
			{
				// Search for a unfilled consonant that is not used
				for (int i = 0; i < cryptogramOnlyLetters.length(); i++)
				{
					tempChar = cryptogramOnlyLetters.charAt(i);

					// Check if it is a consonant
					if (tempChar != 'A' && tempChar != 'E' && tempChar != 'I' && tempChar != 'O' && tempChar != 'U')
					{
						t = fields[i];

						// Check if it is not filled already in the current place
						if (t.getText().equals(Character.toString(tempChar)))
							continue;

						// Check if it is an empty field
						if (t.getText().isEmpty())
						{
							JTextField t1;
							boolean found = true;

							// Search whether this letter has been used somewhere else
							for (int j = 0; j < cryptogramOnlyLetters.length(); j++)
							{
								t1 = fields[j];

								if (t1.getText().isEmpty())
									continue;

								if (tempChar == t1.getText().charAt(0))
								{
									found = false;
									break;
								}
							}

							// If it is not used, mark it as sellected
							if (found)
							{
								posSelected = i;
								break;
							}
						}
					}
				}

				// If an unfilled consonat found, place it
				if (posSelected != -1)
				{
					for (JTextField t1 : fields)
					{
						if (t.getName().equals(t1.getName()))
							t1.setText(Character.toString(tempChar).toUpperCase());
					}

					gameEnteredLetters++;
					checkCryptogram();
					return;
				}
				else
				{
					// Cannot find a hint to be given.
					JOptionPane.showMessageDialog(null, "Cannot give a hint because all letters from the cryptogram are used.\nRemove an invalid mapping to get a hint!", TITLE + HINT_SUBTITLE, JOptionPane.DEFAULT_OPTION);
					return;
				}
			}
		}
	}

	/*
	 * Skip button actions.
	 */
	private class SkipButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Check if a game is running
			if (!gameRunning)
			{
				JOptionPane.showMessageDialog(null, "Game has already finished!", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			// Start a new game with the same parameters
			if (gameGenerated)
				clearWindow();

			gameRunning = false;
			currentCryptogram.createCryptogram(selectedGameType);
			currentMapping = currentCryptogram.getMapping();
			makeOnlyLetters();
			generateCryptogram();
		}
	}

	/*
	 * Give Up button actions.
	 */
	private class GiveUpButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Check if game is generated
			if (gameGenerated)
			{
				// Check if the game is not running
				if (!gameRunning)
				{
					JOptionPane.showMessageDialog(null, "Game has already finished!", TITLE, JOptionPane.DEFAULT_OPTION);
					return;
				}

				// Fill all text fields
				JTextField t;

				for (int i = 0; i < cryptogramOnlyLetters.length(); i++)
				{
					t = fields[i];
					t.setText(Character.toString(cryptogramOnlyLetters.charAt(i)));
				}

				gameRunning = false;
			}
		}
	}

	/*
	 * Save Game button actions.
	 */
	private class SaveGameButtonAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Check if game is running
			if (!gameRunning)
			{
				JOptionPane.showMessageDialog(null, "Game cannot be saved because it has already finished.", TITLE, JOptionPane.DEFAULT_OPTION);
				return;
			}

			// Save the game
			saveGame();
		}
	}

	/*
	 * Used to catch writing in textboxes and limit the size of the input.
	 */
	private class JTextFieldLimit extends PlainDocument
	{
		private int limit;

		JTextFieldLimit(int limit)
		{
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper)
		{
			super();
			this.limit = limit;
		}

		@Override
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
		{
			if (str == null)
				return;

			if (str.length() <= limit)
			{
				char currentChar = 0;

				for (int i = 0; i < str.length(); i++)
				{
					currentChar = str.charAt(i);

					if (!(currentChar >= 'a' && currentChar <= 'z') && !(currentChar >= 'A' && currentChar <= 'Z'))
						return;
				}

				super.remove(0, getLength());
				super.insertString(0, str.toUpperCase(), attr);
			}
		}
	}

	/*
	 * Updates the scoreboard table depending on the criteria selected.
	 */
	private class CriteriaChangeAction implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if (criteriaNumCompleted.isSelected())
				updateScoreboard(Players.CRITERIA_NUM_COMPLETED);
			else if (criteriaAvgTime.isSelected())
				updateScoreboard(Players.CRITERIA_AVERAGE_TIME);
			else if (criteriaBestTime.isSelected())
				updateScoreboard(Players.CRITERIA_BEST_TIME);
			else if (criteriaAccuracy.isSelected())
				updateScoreboard(Players.CRITERIA_ACCURACY);
			else
				updateScoreboard(-1);
		}
	}
}