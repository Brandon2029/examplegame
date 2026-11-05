package examplegame;

public class Tool extends Item {

	private static final long serialVersionUID = 1L;

	public Tool() {
		super("tool");
	}

	@Override
	public void use() {
		if (!Game.getCurrentRoom().getName().equals("Bedroom")) {
			Game.print("You don't see any way to use the tool here.");
			return;
		}

		Room attic = Game.getCurrentRoom().getExit(4);

		if (!attic.isLocked()) {
			Game.print("You already opened the attic door!");
			return;
		}

		attic.setLocked(false);
		Game.print("You pull open the attic hatch with the tool. " + "A ladder falls down!");
	}
}