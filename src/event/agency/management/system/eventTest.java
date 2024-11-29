package event.agency.management.system;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class eventTest {
    @Test

    public void testConfirmEvent(){
        ArrayList<event> expectedEventList = new ArrayList<event>();
        event event1;
        event event = new event();
        database db = new database();

        event.ConfirmEvent(100);
        event1 = event.returnEventInfo(100);


        List<event> actualEventList = db.returnEvents();
        int actualLength = actualEventList.size();
        assertEquals(event1.getCost(), db.getEventCost(100));
    }


}