package airHockey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReaderThread extends Thread {
	private Socket socket;
	private ReaderListener listener;

	public ReaderThread(Socket socket, ReaderListener listener) {
		this.socket = socket;
		this.listener = listener;
	}

	public void run() {
		try {
			// ObjectInputStream in = (ObjectInputStream) socket.getInputStream();
			InputStream in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				// Command command = (Command) in.readObject();
				// listener.onObjectRead(command);
				listener.onLineRead(line);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		listener.onCloseSocket(socket);
	}
}
