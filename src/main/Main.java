package main;

import hotel.Hotel;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		Hotel hotel = Hotel.getInstance();
		MainFrame main = new MainFrame(hotel);
	}
}
