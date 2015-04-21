package airHockey;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

import commands.PositionCommand;

public class Mallet extends Positionable {
	protected static final int MALLETRADIUS = 20;
	private Image image;

	public Mallet(int sideCenter) throws IOException {
		posX = World.GAMEWIDTH / 2;
		posY = sideCenter;
		image = ImageIO.read(getClass().getResource("pics/mallet.jpg"));
		command = new PositionCommand(posX, posY, 'm');
	}

	public void drawMallet(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(image, (int) posX - MALLETRADIUS, (int) posY - MALLETRADIUS, MALLETRADIUS * 2, MALLETRADIUS * 2, null);
	}

	public void setMalletXY(Point location) {
		double locx = location.getX();
		double locy = location.getY();
		Point point = MouseInfo.getPointerInfo().getLocation();
		if (point.getY() - locy - (MALLETRADIUS * 2) >= World.FRAMEHEIGHT / 2) {
			posX = point.getX() - locx;
			posY = point.getY() - locy - MALLETRADIUS * 2;
		}
	}

	public double getMalletX() {
		return posX;
	}

	public double getMalletY() {
		return posY;
	}

	@Override
	public void updateCoordinates(double x, double y, Table table) {
		Point location = new Point((int) x, (int) y);
		table.updateCheckHit();
		posX = (int) location.getX();
		posY = (int) location.getY();
	}
}
