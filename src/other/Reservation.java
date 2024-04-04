package other;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Extras;
import enums.ReservationStatus;

public class Reservation {
	public ReservationStatus status;
	public LocalDate begin;
	public LocalDate end;
	public double price;
	public ArrayList<Extras> extras;
}
