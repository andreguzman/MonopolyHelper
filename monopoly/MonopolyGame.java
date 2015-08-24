package monopoly;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class MonopolyGame {
	private static final String EXIT_GAME_STRING = "exit";
	private static final String EXIT_SETUP_STRING = "quit";
	private static final String BANK_STRING = "bank";
	private static final Map<String, MonopolyProperty> allProperties = initializeAllProperties();

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		Map<String, MonopolyPlayer> playerMap = new HashMap<>();
		Stack<String> actionStack = new Stack<String>();

		String input = "";
		int playerNumber = 1;
		while (!EXIT_SETUP_STRING.equals(input)) { 
			System.out.println(String.format("Enter player %d name :", playerNumber));
			String name = input = in.next();

			if (EXIT_GAME_STRING.equals(name)) {
				quitGame(in);
				System.exit(0);
			}

			if (!EXIT_SETUP_STRING.equals(name)) {
				if (!BANK_STRING.equals(name)) {
					playerMap.put(name, new MonopolyPlayer(name));
					playerNumber++;
				} else {
					System.out.println(String.format("Sorry, your name can't be %s.", BANK_STRING));
				}
			} else {
				if (playerMap.size() < 2) {
					System.out.println("Please enter at least two players.");
					input = "";
					continue;
				}
			}
		}

		String commandString = "";
		while (!commandString.equals(EXIT_GAME_STRING)) {
			commandString = in.nextLine();
			commandString = commandString.trim();

			if (commandString == null || commandString.isEmpty()) {
				continue;
			}

			String[] actionStrings = commandString.split(" ");

			switch (actionStrings[0]) {
				case "state": displayGameState(playerMap); break;
				case "add": addMoneyToPlayerFromBank(actionStrings, playerMap); break;
				case "passgo": passgoCommand(actionStrings, playerMap); break;
				case "pay": payCommand(actionStrings, playerMap); break;
				case "trade": tradeCommand(actionStrings, playerMap); break;
				case "exit": break;
				default: System.out.println("Invalid Command"); continue;
			}

			displayGameState(playerMap);
		}

		quitGame(in);
	}

	private static void tradeCommand(String[] actionStrings,
			Map<String, MonopolyPlayer> playerMap) {
		if (actionStrings.length != 4) {
			System.out.println("trade command must have three arguments.");
			return;
		}

		tradeMoneyBetweenPlayers(actionStrings[1], actionStrings[2], actionStrings[3], playerMap);
	}

	private static void payCommand(String[] actionStrings, Map<String, MonopolyPlayer> playerMap) {
		if (actionStrings.length != 4) {
			System.out.println("pay command must have three arguments.");
			return;
		}

		tradeMoneyBetweenPlayers(actionStrings[2], actionStrings[1], actionStrings[3], playerMap);
	}

	private static void tradeMoneyBetweenPlayers(String fromPlayer, String toPlayer, String amount,
			Map<String, MonopolyPlayer> playerMap) {
		int addCash = 0;

		try {
			addCash = Integer.parseInt(amount);
		} catch (NumberFormatException e) {
			System.out.println(String.format("%s is not a valid amount of money.", addCash));
			return;
		}

		if (playerMap.containsKey(toPlayer)) {
			if (BANK_STRING.equals(fromPlayer)) {
				playerMap.get(toPlayer).addCash(addCash);
			} else if (playerMap.containsKey(fromPlayer)) {
				playerMap.get(fromPlayer).tradeCash(playerMap.get(toPlayer), addCash);
			} else {
				System.out.println(String.format("%s is not a player in this game", fromPlayer));
			}
		} else {
			System.out.println(String.format("%s is not a player in this game", toPlayer));
		}
	}

	private static void passgoCommand(String[] actionStrings,
			Map<String, MonopolyPlayer> playerMap) {
		if (actionStrings.length != 2) {
			System.out.println("passgo command must have one argument.");
		}

		tradeMoneyBetweenPlayers(BANK_STRING, actionStrings[1], "200", playerMap);
	}

	private static void addMoneyToPlayerFromBank(String[] actionStrings,
			Map<String, MonopolyPlayer> playerMap) {
		if (actionStrings.length != 3) {
			System.out.println("add command must have two arguments.");
		}

		tradeMoneyBetweenPlayers(BANK_STRING, actionStrings[1], actionStrings[2], playerMap);
	}

	private static void displayGameState(Map<String, MonopolyPlayer> playerMap) {
		for(MonopolyPlayer player : playerMap.values()) {
			System.out.println(System.lineSeparator());
			System.out.println(player.getName());
			System.out.println(String.format("Cash: %d", player.getCash()));
			System.out.println("Properties: ");

			TreeSet<MonopolyProperty> properties = new TreeSet<>();
			properties.addAll(player.getProperties());
			for(MonopolyProperty property : properties) {
				System.out.println(property);
			}
		}
	}
	
	private static void quitGame(Scanner in) {
		System.out.println("Thanks for playing the game!");
		in.close();
	}
	
	private static Map<String, MonopolyProperty> initializeAllProperties() {
		return null;
	}
}