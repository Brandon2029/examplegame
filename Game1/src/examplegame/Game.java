package examplegame;

import java.util.*;
import java.io.*;

public class Game {

	private static Room currentRoom = World.buildWorld();
	private static ArrayList<Item> inventory = new ArrayList<>();
	public static HashMap<String, String> roomDescriptions = new HashMap<>();
	private static GameGUI gui;

	public static Room getCurrentRoom() {
		return currentRoom;
	}

	public static void print(Object obj) {
		String message = String.valueOf(obj);
		if (gui != null) {
			gui.appendText(message);
		} else {
			System.out.println(message);
		}
	}

	public static void loadRoomDescriptions() {
		roomDescriptions.clear();

		try (Scanner scan = new Scanner(new File("roomDescriptions.txt"))) {
			while (scan.hasNextLine()) {
				String name = scan.nextLine().trim();

				if (name.isEmpty()) {
					continue;
				}

				StringBuilder description = new StringBuilder();

				while (scan.hasNextLine()) {
					String line = scan.nextLine();

					if (line.trim().equals("#")) {
						break;
					}

					description.append(line).append("\n");
				}

				roomDescriptions.put(name, description.toString().trim());
			}
		} catch (FileNotFoundException e) {
			print("Cannot find roomDescriptions.txt. Put it in: " + System.getProperty("user.dir"));
		}
	}

	public static void addInventoryItem(Item item) {
		if (!hasInventoryItem(item.getName())) {
			inventory.add(item);
		}
	}

	public static boolean hasInventoryItem(String name) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	private static Item inventoryItem(String name) {
		for (Item item : inventory) {
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}
		return null;
	}

	public static void move(String direction) {
		int index;

		switch (direction) {
		case "east":
			index = 0;
			break;
		case "west":
			index = 1;
			break;
		case "north":
			index = 2;
			break;
		case "south":
			index = 3;
			break;
		case "up":
			index = 4;
			break;
		case "down":
			index = 5;
			break;
		default:
			print("That is not a valid direction.");
			return;
		}

		Room next = currentRoom.getExit(index);

		if (next == null) {
			print("You can't go that way.");
			return;
		}

		if (next.isLocked()) {
			print("That room is locked.");
			return;
		}

		currentRoom = next;
		print(currentRoom);
	}

	public static void takeItem(String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			print("There is no " + name + " here.");
			return;
		}

		if (item.isHeavy()) {
			print("You can't lift the " + item.getName() + "!");
			return;
		}

		addInventoryItem(item);
		currentRoom.removeItem(name);
		print("You take the " + name + " and carry it with you!");
	}

	public static void printInventory() {
		if (inventory.isEmpty()) {
			print("You are carrying nothing.");
			return;
		}

		print("You are carrying:");

		for (Item item : inventory) {
			print(item);
		}
	}

	public static void useItem(String name) {
		Item item = inventoryItem(name);

		if (item == null) {
			print("You must have the " + name + " in your inventory to use it.");
			return;
		}

		item.use();
	}

	public static void interact(String verb, String name) {
		Item item = currentRoom.getItem(name);

		if (item == null) {
			item = inventoryItem(name);
		}

		if (item == null) {
			print("You can't find the " + name + " here or in your inventory.");
			return;
		}

		if (verb.equals("open")) {
			item.open();
		} else if (verb.equals("close")) {
			item.close();
		} else if (verb.equals("read")) {
			item.read();
		}
	}

	public static void saveGame() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("save0.sav"))) {

			out.writeObject(currentRoom);
			out.writeObject(inventory);
			out.writeObject(World.rooms);

			print("Game saved successfully!");

		} catch (IOException e) {
			print("Could not save game: " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static void loadGame() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("save0.sav"))) {

			Room room = (Room) in.readObject();
			ArrayList<Item> items = (ArrayList<Item>) in.readObject();
			HashMap<String, Room> rooms = (HashMap<String, Room>) in.readObject();

			currentRoom = room;
			inventory = items;
			World.rooms = rooms;

			print("Game loaded successfully!");
			print(currentRoom);

		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			print("Could not load game: " + e.getMessage());
		}
	}

	public static boolean processCommand(String command) {
		command = command.toLowerCase().replaceAll("\\b(the|to)\\b", "").replaceAll("\\s+", " ").trim();

		if (command.isEmpty()) {
			return true;
		}

		String[] words = command.split(" ");
		String action = words[0];

		switch (action) {
		case "east":
		case "west":
		case "north":
		case "south":
		case "up":
		case "down":
			move(action);
			break;

		case "go":
			if (words.length < 2) {
				print("Go where?");
			} else {
				move(words[1]);
			}
			break;

		case "take":
			if (words.length < 2) {
				print("Take what?");
			} else {
				takeItem(words[1]);
			}
			break;

		case "use":
			if (words.length < 2) {
				print("Use what?");
			} else {
				useItem(words[1]);
			}
			break;

		case "open":
		case "close":
		case "read":
			if (words.length < 2) {
				print(action + " what?");
			} else {
				interact(action, words[1]);
			}
			break;

		case "i":
		case "inventory":
			printInventory();
			break;

		case "look":
			print(currentRoom);
			break;

		case "save":
			saveGame();
			break;

		case "load":
			loadGame();
			break;

		case "help":
			print("Commands: go [direction], take [item], " + "use [item], open [item], close [item], "
					+ "read [item], i, look, save, load, exit");
			break;

		case "exit":
			print("Thanks for playing!");
			return false;

		default:
			print("That is not a valid command. Type help for commands.");
		}

		return true;
	}

	public static void runGame() {
		loadRoomDescriptions();

		Scanner input = new Scanner(System.in);
		print(currentRoom);

		while (true) {
			print("Enter a command:");

			if (!input.hasNextLine()) {
				break;
			}

			if (!processCommand(input.nextLine())) {
				break;
			}
		}
	}

	public static void runGameGUI(String command) {
		if (!processCommand(command) && gui != null) {
			gui.appendText("Game ended. Close the window to quit.");
		}
	}

	public static void startGUI() {
		loadRoomDescriptions();

		gui = new GameGUI();
		gui.setVisible(true);

		print(currentRoom);
		print("Type help for commands.");
	}

	public static void main(String[] args) {
		runGame();
	}
}