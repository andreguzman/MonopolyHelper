package monopoly;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class MonopolyGame {
	private static final String EXIT_GAME_STRING = "exit";
	private static final String EXIT_SETUP_STRING = "quit";
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
				playerMap.put(name, new MonopolyPlayer(name));
				playerNumber++;
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
			case "exit": break;
			default: System.out.println("Invalid Command"); continue;
			}
		}

		quitGame(in);
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