package examplegame;

import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea output = new JTextArea();
	private JTextField input = new JTextField();

	public GameGUI() {
		super("Example Game - Text Adventure");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(740, 530);
		setLocationRelativeTo(null);

		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		output.setBackground(new Color(25, 30, 40));
		output.setForeground(Color.WHITE);

		input.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

		JPanel bottom = new JPanel(new BorderLayout(8, 0));
		bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JButton send = new JButton("Enter");

		bottom.add(input, BorderLayout.CENTER);
		bottom.add(send, BorderLayout.EAST);

		add(new JScrollPane(output), BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		Runnable submit = () -> {
			String command = input.getText();
			input.setText("");
			appendText("> " + command);
			Game.runGameGUI(command);
		};

		input.addActionListener(e -> submit.run());
		send.addActionListener(e -> submit.run());
	}

	public void appendText(String text) {
		output.append(text + "\n");
		output.setCaretPosition(output.getDocument().getLength());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> Game.startGUI());
	}
}
