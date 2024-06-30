package manageTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Pricing;
import exceptions.NonexistentEntityException;
import manage.PricingManager;

import java.time.LocalDate;
import static org.junit.Assert.*;

public class PricingManagerTest {

    private static PricingManager test = new PricingManager();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	PricingManager.setPricingID("0");
        test.roomTypes.add("Jednokrevetna");
        test.roomTypes.add("Dvokrevetna");
        test.services.add("Dorucak");
        test.services.add("Vecera");
        test.createPricing(LocalDate.parse("2024-06-01"), LocalDate.parse("2024-09-01"), 20, 30, 8, 10);
        test.createPricing(LocalDate.parse("2024-04-01"), LocalDate.parse("2024-05-01"), 20, 30, 8, 10);
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testCreatePricing() {
        test.createPricing(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-02-01"), 15, 20, 5, 8);
        Pricing p = test.readPricing("3");
        assertEquals("3", p.getID());
        assertEquals(LocalDate.parse("2024-01-01"), p.startDate);
        assertEquals(LocalDate.parse("2024-02-01"), p.endDate);
        assertTrue(p.servicePrices.get("Jednokrevetna") == 15.0);
        assertTrue(p.servicePrices.get("Dvokrevetna") == 20.0);
        assertTrue(p.servicePrices.get("Dorucak") == 5.0);
        assertTrue(p.servicePrices.get("Vecera") == 8.0);
    }

    @Test
    public void testUpdatePricing() {
    	Pricing p = test.readPricing("1");
    	test.updatePricing("1", p.startDate, LocalDate.parse("2024-08-01"), p.servicePrices);
    	assertEquals(LocalDate.parse("2024-08-01"), p.endDate);
    }
    
    @Test
    public void testUpdateOnePrice() {
    	Pricing p = test.readPricing("1");
    	test.updateOnePrice("1", "Vecera", 12);
    	assertTrue(p.servicePrices.get("Vecera") == 12.0);
    }
    
    @Test(expected = RuntimeException.class)
    public void testCreatePricingException() {
        test.createPricing(LocalDate.parse("2024-11-01"), LocalDate.parse("2024-10-01"), 10, 10, 10, 10);
    }

    @Test
    public void testReadPricing() {
        Pricing p = test.readPricing("1");
        assertTrue(p != null);
    }

    @Test(expected = NonexistentEntityException.class)
    public void testReadPricingException() {
        test.readPricing("25");
    }

    @Test
    public void testDeletePricing() {
    	Pricing p = test.readPricing("2");
    	test.deletePricing("2");
    	assertTrue(test.pricings.contains(p) == false);
    }
}
