package monopoly;

import static monopoly.MonopolyBank.BANK_NAME;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class MonopolyGame {
    private static final String EXIT_GAME_STRING = "exit";
    private static final String EXIT_SETUP_STRING = "quit";
    private static final Map<String, MonopolyProperty> allProperties = initializeAllProperties();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Map<String, MonopolyPlayer> playerMap = new HashMap<>();
        Map<String, MonopolyProperty> propertyList = new HashMap<>(allProperties);
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
                if (!BANK_NAME.equals(name)) {
                    playerMap.put(name, new MonopolyPlayer(name));
                    playerNumber++;
                } else {
                    System.out.println(String.format("Sorry, your name can't be %s.", BANK_NAME));
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
                case "add": addCommand(actionStrings, playerMap, propertyList); break;
                case "buy": buyCommand(actionStrings, playerMap, propertyList); break;
                case "pay": payCommand(actionStrings, playerMap); break;
                case "trade": tradeCommand(actionStrings, playerMap); break;
                case "passgo": passgoCommand(actionStrings, playerMap); break;
                case "state": displayGameState(playerMap); break;
                case "exit": break;
                default: System.out.println("Invalid Command");  continue;
            }

            displayGameState(playerMap);
        }

        quitGame(in);
    }

    private static void buyCommand(String[] actionStrings, Map<String, MonopolyPlayer> playerMap,
        Map<String, MonopolyProperty> propertyList) {
        if (actionStrings.length != 5) {
            System.out.println("buy command must have 4 arguments.");
        }

        String commandString = actionStrings[1];
        if ("property".equals(commandString)) {
            buyPropertyFromBank(actionStrings[2], actionStrings[3], playerMap, propertyList);
        } else if ("houses".equals(commandString)) {
            buyHouses(actionStrings[2], actionStrings[3], playerMap);
        } else if ("hotel".equals(commandString)) {
            buyHotel(actionStrings[2], actionStrings[3], playerMap);
        } else {
            System.out.println("buy command must be followed by either property, houses, or hotel");
        }
    }

    private static void buyHotel(String numberHotels, String property,
        Map<String, MonopolyPlayer> playerMap) {
        // TODO Auto-generated method stub

    }

    private static void buyHouses(String numberHouses, String property,
        Map<String, MonopolyPlayer> playerMap) {
        // TODO Auto-generated method stub

    }

    private static void buyPropertyFromBank(String property, String player,
        Map<String, MonopolyPlayer> playerMap, Map<String, MonopolyProperty> propertyList) {
        MonopolyPlayer monopolyPlayer = playerMap.get(player);
        if (monopolyPlayer != null) {
            MonopolyProperty monopolyProperty = propertyList.get(property);
            if (monopolyProperty != null) {
                monopolyPlayer.addProperty(monopolyProperty);
                monopolyPlayer.subtractCash(monopolyProperty.getValue());
            } else {
                System.out.println(String.format("%s is not a valid property.", property));
                return;
            }
        } else {
            nonexistentPlayerErrorMessage(player);
            return;
        }
    }

    private static void tradeCommand(String[] actionStrings,
        Map<String, MonopolyPlayer> playerMap) {
        if ("money".equals(actionStrings[1])) {
            tradeMoneyCommand(actionStrings, playerMap);
        } else if ("property".equals(actionStrings[1])) {
            tradePropertyCommand(actionStrings, playerMap);
        } else {
            System.out.println("trade command must be followed by either money or property.");
        }
    }

    private static void addCommand(String[] actionStrings, Map<String, MonopolyPlayer> playerMap,
        Map<String, MonopolyProperty> propertyList) {
        if ("money".equals(actionStrings[1])) {
            addMoneyCommand(actionStrings, playerMap);
        } else if ("property".equals(actionStrings[1])) {
            addPropertyCommand(actionStrings, playerMap, propertyList);
        } else {
            System.out.println("add command must be followed by either money or property.");
        }
    }

    // st.replaceAll("\\s","")
    private static void tradePropertyCommand(String[] actionStrings,
        Map<String, MonopolyPlayer> playerMap) {
        if (actionStrings.length != 5) {
            System.out.println("trade command must have three arguments.");
        }

        tradePropertyBetweenPlayers(actionStrings[2], actionStrings[3], actionStrings[4], playerMap,
            null);
    }

    private static void addPropertyCommand(String[] actionStrings,
        Map<String, MonopolyPlayer> playerMap, Map<String, MonopolyProperty> propertyList) {
        if (actionStrings.length != 4) {
            System.out.println("addProperty command must have two arguments.");
        }

        tradePropertyBetweenPlayers(BANK_NAME, actionStrings[2], actionStrings[3], playerMap,
            propertyList);
    }

    private static void tradePropertyBetweenPlayers(String fromPlayer, String toPlayer,
        String property, Map<String, MonopolyPlayer> playerMap,
        Map<String, MonopolyProperty> propertyList) {
        if (playerMap.containsKey(toPlayer)) {
            MonopolyPlayer toMonopolyPlayer = playerMap.get(toPlayer);
            if (BANK_NAME.equals(fromPlayer)) {
                if (propertyList.containsKey(property.toLowerCase())) {
                    toMonopolyPlayer.addProperty(propertyList.get(property.toLowerCase()));
                } else {
                    if (allProperties.containsKey(property.toLowerCase())) {
                        System.out.println(String.format("%s is owned by someone else.", property));
                    } else {
                        System.out.println(String.format("%s is not a valid property.", property));
                    }
                }
            } else if (playerMap.containsKey(fromPlayer)) {
                MonopolyPlayer fromMonopolyPlayer = playerMap.get(fromPlayer);
                if (fromMonopolyPlayer.checkIfPlayerHasProperty(allProperties.get(property))) {
                    fromMonopolyPlayer.tradeProperty(toMonopolyPlayer, allProperties.get(property));
                } else {
                    System.out.println(String.format("Player %s does not have property %s",
                        fromMonopolyPlayer, property));
                }
            } else {
                nonexistentPlayerErrorMessage(fromPlayer);
            }
        } else {
            nonexistentPlayerErrorMessage(toPlayer);
        }
    }

    private static void tradeMoneyCommand(String[] actionStrings,
        Map<String, MonopolyPlayer> playerMap) {
        if (actionStrings.length != 5) {
            System.out.println("trade command must have three arguments.");
            return;
        }

        tradeMoneyBetweenPlayers(actionStrings[2], actionStrings[3], actionStrings[4], playerMap);
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
            if (BANK_NAME.equals(fromPlayer)) {
                playerMap.get(toPlayer).addCash(addCash);
            } else if (playerMap.containsKey(fromPlayer)) {
                playerMap.get(fromPlayer).tradeCash(playerMap.get(toPlayer), addCash);
            } else {
                nonexistentPlayerErrorMessage(fromPlayer);
            }
        } else {
            nonexistentPlayerErrorMessage(toPlayer);
        }
    }

    private static void passgoCommand(String[] actionStrings,
        Map<String, MonopolyPlayer> playerMap) {
        if (actionStrings.length != 2) {
            System.out.println("passgo command must have one argument.");
        }

        tradeMoneyBetweenPlayers(BANK_NAME, actionStrings[1], "200", playerMap);
    }

    private static void addMoneyCommand(String[] actionStrings,
        Map<String, MonopolyPlayer> playerMap) {
        if (actionStrings.length != 4) {
            System.out.println("add command must have two arguments.");
        }

        tradeMoneyBetweenPlayers(BANK_NAME, actionStrings[2], actionStrings[3], playerMap);
    }

    private static void nonexistentPlayerErrorMessage(String player) {
        System.out.println(String.format("%s is not a player in this game.", player));
    }

    private static void displayGameState(Map<String, MonopolyPlayer> playerMap) {
        for (MonopolyPlayer player : playerMap.values()) {
            System.out.println(System.lineSeparator());
            System.out.println(player.getName());
            System.out.println(String.format("Cash: %d", player.getCash()));
            System.out.println("Properties: ");

            TreeSet<MonopolyProperty> properties = new TreeSet<>();
            properties.addAll(player.getProperties());
            for (MonopolyProperty property : properties) {
                System.out.println(property);
            }
        }
    }

    private static void quitGame(Scanner in) {
        System.out.println("Thanks for playing the game!");
        in.close();
    }

    private static Map<String, MonopolyProperty> initializeAllProperties() {
        Map<String, MonopolyProperty> allProperties = new HashMap<>();

        allProperties.put("mediterraneanavenue", new MonopolyProperty("Mediterranean Avenue", 60, 0,
            MonopolyProperty.ColorGroup.DARK_PURPLE));
        allProperties.put("balticavenue",
            new MonopolyProperty("Baltic Avenue", 80, 0, MonopolyProperty.ColorGroup.DARK_PURPLE));

        allProperties.put("orientalavenue", new MonopolyProperty("Oriental Avenue", 100, 0,
            MonopolyProperty.ColorGroup.LIGHT_BLUE));
        allProperties.put("vermontavenue",
            new MonopolyProperty("Vermont Avenue", 100, 0, MonopolyProperty.ColorGroup.LIGHT_BLUE));
        allProperties.put("connecticutavenue", new MonopolyProperty("Connecticut Avenue", 120, 0,
            MonopolyProperty.ColorGroup.LIGHT_BLUE));

        allProperties.put("st.charlesplace",
            new MonopolyProperty("St. Charles Place", 140, 0, MonopolyProperty.ColorGroup.PURPLE));
        allProperties.put("vermontavenue",
            new MonopolyProperty("States Avenue", 140, 0, MonopolyProperty.ColorGroup.PURPLE));
        allProperties.put("virginiaavenue",
            new MonopolyProperty("Virginia Avenue", 160, 0, MonopolyProperty.ColorGroup.PURPLE));

        allProperties.put("st.jamesplace",
            new MonopolyProperty("St. James Place", 180, 0, MonopolyProperty.ColorGroup.ORANGE));
        allProperties.put("tennesseeavenue",
            new MonopolyProperty("Tennessee Avenue", 180, 0, MonopolyProperty.ColorGroup.ORANGE));
        allProperties.put("newyorkavenue",
            new MonopolyProperty("New York Avenue", 200, 0, MonopolyProperty.ColorGroup.ORANGE));

        allProperties.put("kentuckyavenue",
            new MonopolyProperty("Kentucky Avenue", 220, 0, MonopolyProperty.ColorGroup.RED));
        allProperties.put("indianaavenue",
            new MonopolyProperty("Indiana Avenue", 220, 0, MonopolyProperty.ColorGroup.RED));
        allProperties.put("illinoisavenue",
            new MonopolyProperty("Illinois Avenue", 240, 0, MonopolyProperty.ColorGroup.RED));

        allProperties.put("atlanticavenue",
            new MonopolyProperty("Atlantic Avenue", 260, 0, MonopolyProperty.ColorGroup.YELLOW));
        allProperties.put("ventnoravenue",
            new MonopolyProperty("Ventnor Avenue", 260, 0, MonopolyProperty.ColorGroup.YELLOW));
        allProperties.put("marvingardens",
            new MonopolyProperty("Marvin Gardens", 280, 0, MonopolyProperty.ColorGroup.YELLOW));

        allProperties.put("pacificavenue",
            new MonopolyProperty("Pacific Avenue", 300, 0, MonopolyProperty.ColorGroup.GREEN));
        allProperties.put("northcarolinaavenue", new MonopolyProperty("North Carolina Avenue", 300,
            0, MonopolyProperty.ColorGroup.GREEN));
        allProperties.put("pennsylvaniaavenue",
            new MonopolyProperty("Pennsylvania Avenue", 320, 0, MonopolyProperty.ColorGroup.GREEN));

        allProperties.put("parkplace",
            new MonopolyProperty("Park Place", 350, 0, MonopolyProperty.ColorGroup.DARK_BLUE));
        allProperties.put("boardwalk",
            new MonopolyProperty("Boardwalk", 400, 0, MonopolyProperty.ColorGroup.DARK_BLUE));

        return allProperties;
    }
}