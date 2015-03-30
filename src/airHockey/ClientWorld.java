package airHockey;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientWorld extends World {
	private static final long serialVersionUID = 1L;
	private Socket socket;

	public ClientWorld() throws IOException, LineUnavailableException, UnsupportedAudioFileException {

		setLocationRelativeTo(null);
		// client = new Socket("192.168.1.6", 3762);
		socket = new Socket("192.168.2.4", 3769); // port num sent
		new ReadThread(socket, this).start();

		// mallet moves with mouse
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = getLocation();

				table.moveMallet(point);

				try {
					updateMallet2(table.getMallet1Location());
					System.out.println("2 : " + (Table.MIDDLE - table.getMallet1().getMalletY()));
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}

				repaint();
			}
		});

		setVisible(true);
		new GameLoopThread(this).start();
	}

	public void updateMallet2(String location) throws IOException {
		double x = table.getMallet1().getMalletX();
		double y = table.getMallet1().getMalletY();
		double Whalf = Table.WIDTH / 2;
		double Lhalf = Table.HEIGHT / 2;
		// reflection over x and y axis
		double diffx = Math.abs(Whalf - x);
		double diffy = Math.abs(Lhalf - y);
		if (x > Whalf && y > Lhalf) {
			x = Whalf - diffx;
			y = Lhalf - diffy;
		}
		else if (x < Whalf && y < Lhalf) {
			x = Whalf + diffx;
			y = Lhalf + diffy;
		}
		else if (y > Lhalf && x < Whalf) {
			x = Whalf + diffx;
			y = Lhalf - diffy;

		}
		else if (y < Lhalf && x > Whalf) {
			y = Lhalf + diffy;
			x = Whalf - diffx;
		}

		OutputStream out = socket.getOutputStream();

		PrintWriter writer = new PrintWriter(out);
		writer.println(location);
		writer.flush();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			/* LookAndFeel lat = UIManager.getLookAndFeel(); UIDefaults defaults
			 * = lat.getDefaults(); defaults.replace(key, value); for(Object
			 * key: UIManager.getLookAndFeel().getDefaults().keySet()) {
			 * System.out.println(key + " = " + UIManager.get(key)); } */
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		try {
			new ClientWorld();
		}
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {

			e.printStackTrace();
		}
	}
}