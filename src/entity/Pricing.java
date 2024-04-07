package entity;

import java.time.LocalDate;
import java.util.HashMap;
import enums.RoomType;
import enums.Extras;

public class Pricing {
	public LocalDate creationDate;
	public LocalDate expirationDate;
	public HashMap<RoomType, Double> roomPrices;
	public HashMap<Extras, Double> extrasPrices;
}
