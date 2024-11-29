package event.agency.management.system;

public class notification {
    private String message;
    private int notify_Id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNotify_Id() {
        return notify_Id;
    }

    public void setNotify_Id(int notify_Id) {
        this.notify_Id = notify_Id;
    }

    public notification(String message, int notify_Id) {
        this.message = message;
        this.notify_Id = notify_Id;
    }
    
}