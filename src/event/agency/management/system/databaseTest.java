package event.agency.management.system;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class databaseTest {

    @Test
    public void testgetId(){

        database db = new database();

        assertEquals(4,db.IncrementId());
    }
}