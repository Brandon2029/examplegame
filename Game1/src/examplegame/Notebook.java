package examplegame;

public class Notebook extends Item {

	private static final long serialVersionUID = 1L;

	private boolean readBefore = false;

	public Notebook() {
		super("notebook");
	}

	@Override
	public void read() {
		if (!Game.hasInventoryItem("notebook")) {
			Game.print("You must take the notebook before reading it.");
			return;
		}

		Game.print("The notebook reveals the password " + "to your father's computer.");

		if (!readBefore) {
			Game.addInventoryItem(new Item("password"));
			readBefore = true;
			Game.print("Password added to your inventory!");
		}
	}
}