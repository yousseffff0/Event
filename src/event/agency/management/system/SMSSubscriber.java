package event.agency.management.system;

import java.util.ArrayList;

public class SMSSubscriber implements Observer {

    public ArrayList<notification> messages = new ArrayList<notification>();
    private String name;
    private String mobileNo;

    public SMSSubscriber(String name, String mobileNo) {
        this.name = name;
        this.mobileNo = mobileNo;
    }

    public SMSSubscriber() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public void Update(String message) {
        database db = new database();
        ArrayList<String> numbers = new ArrayList<String>();

            System.out.println(
                    "received a SMS that says: "
                            + message);

    }

}
