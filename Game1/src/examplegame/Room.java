package examplegame;

import java.io.Serializable;
import java.util.HashMap;

public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Room[] exits = new Room[6];
	private HashMap<String, Item> items = new HashMap<>();
	private boolean locked = false;

	public Room(String name) {
		this.name = name;
		World.rooms.put(name, this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return Game.roomDescriptions.getOrDefault(name, "No description available.");
	}

	public void setDescription(String description) {
		Game.roomDescriptions.put(name, description);
	}

	public void addExit(Room room, int direction) {
		exits[direction] = room;
	}

	public Room getExit(int direction) {
		return exits[direction];
	}

	public void addItem(Item item) {
		items.put(item.getName().toLowerCase(), item);
	}

	public Item getItem(String name) {
		return items.get(name.toLowerCase());
	}

	public Item removeItem(String name) {
		return items.remove(name.toLowerCase());
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return name + "\n" + getDescription();
	}
}
