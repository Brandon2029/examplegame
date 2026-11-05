package examplegame;

public class Safe extends Item {

	private static final long serialVersionUID = 1L;

	private boolean opened = false;
	private boolean discovered = false;

	public Safe() {
		super("safe", true);
	}

	@Override
	public void open() {
		if (opened) {
			Game.print("The safe is already open.");
			return;
		}

		opened = true;
		Game.print("You open the safe.");

		if (!discovered) {
			Game.getCurrentRoom().addItem(new Notebook());
			discovered = true;
			Game.print("You discover a notebook inside the safe!");
		}
	}

	@Override
	public void close() {
		if (!opened) {
			Game.print("The safe is already closed.");
			return;
		}

		opened = false;
		Game.print("You close the safe.");
	}
}