package examplegame;

import java.util.HashMap;

public class World {

	public static HashMap<String, Room> rooms = new HashMap<>();

	public static Room buildWorld() {
		rooms.clear();

		Room livingRoom = new Room("Living Room");
		Room office = new Room("Office");
		Room kitchen = new Room("Kitchen");
		Room bedroom = new Room("Bedroom");
		Room attic = new Room("Attic");
		Room garage = new Room("Garage");

// Living Room exits
		livingRoom.addExit(kitchen, 0);
		livingRoom.addExit(office, 1);
		livingRoom.addExit(bedroom, 2);

// Office exits
		office.addExit(livingRoom, 0);
		office.addExit(garage, 3);

// Kitchen exits
		kitchen.addExit(livingRoom, 1);
		kitchen.addExit(garage, 3);

// Bedroom exits
		bedroom.addExit(livingRoom, 3);
		bedroom.addExit(attic, 4);

// Attic exits
		attic.addExit(bedroom, 5);

// Garage exits
		garage.addExit(kitchen, 2);
		garage.addExit(office, 4);

// Locked rooms
		office.setLocked(true);
		attic.setLocked(true);
		garage.setLocked(true);

// Living Room items
		livingRoom.addItem(new Tool());
		livingRoom.addItem(new Item("musicbox"));
		livingRoom.addItem(new Item("couch", true));

// Kitchen items
		kitchen.addItem(new Item("flashlight"));
		kitchen.addItem(new Item("knife"));
		kitchen.addItem(new Item("orb"));

// Bedroom items
		bedroom.addItem(new OfficeKey());
		bedroom.addItem(new Item("bed", true));

// Attic item
		attic.addItem(new Safe());

// Office item
		office.addItem(new Item("computer", true));

		return livingRoom;
	}
}