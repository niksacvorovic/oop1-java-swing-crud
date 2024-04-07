package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import enums.Extras;
import enums.ReservationStatus;
import enums.RoomType;

public class Reservation {
	public ReservationStatus status;
	public RoomType type;
	public LocalDate begin;
	public LocalDate end;
	public double price;
	public ArrayList<Extras> extras;
}
