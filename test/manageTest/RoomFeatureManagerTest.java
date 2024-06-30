package manageTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Room;
import entity.RoomFeature;
import entity.RoomFeatureLink;
import enums.RoomStatus;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;
import manage.RoomFeatureManager;

public class RoomFeatureManagerTest {

    public static RoomFeatureManager test = new RoomFeatureManager();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        test.createRoomFeature("Klima", 15);
        test.createRoomFeature("Pušačka soba", 10);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testCreateRoomFeature() {
        test.createRoomFeature("Balkon", 10.0);
        RoomFeature feature = test.readRoomFeature("Balkon");
        assertNotNull(feature);
        assertEquals("Balkon", feature.getName());
        assertTrue(feature.price == 10);
        assertTrue(test.roomFeatures.contains(feature));
    }

    @Test(expected = DuplicateIDException.class)
    public void testCreateDuplicateRoomFeature() {
        test.createRoomFeature("Klima", 15);
    }

    @Test
    public void testReadRoomFeature() {
        RoomFeature feature = test.readRoomFeature("Klima");
        assertNotNull(feature);
    }

    @Test(expected = NonexistentEntityException.class)
    public void testReadRoomFeatureException() {
        test.readRoomFeature("Sauna");
    }

    @Test
    public void testUpdateRoomFeature() {
        test.updateRoomFeature("Klima", 12.0);
        RoomFeature feature = test.readRoomFeature("Klima");
        assertTrue(feature.price == 12);
    }

    @Test
    public void testDeleteRoomFeature() {
        RoomFeature r = test.readRoomFeature("Pušačka soba");
        test.deleteRoomFeature("Pušačka soba");
        assertTrue(test.roomFeatures.contains(r) == false);
    }

    @Test
    public void testAddFeatureLink() {
    	boolean exists = false;
        Room room = new Room("101", "Jednokrevetna", RoomStatus.SLOBODNA, null);
        RoomFeature feature = test.readRoomFeature("Klima");
        test.addFeatureLink(room, feature);
        RoomFeatureLink link = new RoomFeatureLink(room, feature);
        for(RoomFeatureLink i:test.roomFeatureLinks) {
        	if(i.equals(link)) {
        		exists = true;
        	}
        }
        assertTrue(exists);
    }

    @Test
    public void testDeleteFeatureLink() {
    	boolean exists = false;
        Room room = new Room("102", "Jednokrevetna", RoomStatus.SLOBODNA, null);
        RoomFeature feature = test.readRoomFeature("Klima");
        test.addFeatureLink(room, feature);
        test.deleteFeatureLink(room, feature);
        RoomFeatureLink link = new RoomFeatureLink(room, feature);
        for(RoomFeatureLink i:test.roomFeatureLinks) {
        	if(i.equals(link)) {
        		exists = true;
        	}
        }
        assertFalse(exists);
    }
}
