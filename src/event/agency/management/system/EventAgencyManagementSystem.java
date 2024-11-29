package event.agency.management.system;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
public class EventAgencyManagementSystem {

    public static void main(String[] args) throws SQLException {
        report report = new report();
        event event = new event();
        eventorganizer o = new eventorganizer();
        //o.addSponsosrToEvent();
        //event.ConfirmEvent(11);
        //event.deleteEvent();
        //event.subscribeToEmail();
        //event.ConfirmEvent(101);

        //report.makeEventReport();

        ReadOnlyEvents readOnlyEvents = new event();

        customer c = new customer(readOnlyEvents);

        c.viewEvents();
        int personid = 0;
        admin a = new admin();
        service s = new service();
        Scanner logininput = new Scanner(System.in);
        System.out.println("1 To Login As Organizer");
        System.out.println("2 To Login/signup As Customer");
        System.out.println("3 To Login As Admin");
        System.out.println("0 for Exit");
        int x = logininput.nextInt();

        while (x != 0) {
            switch (x) {
                case 1: {
                    while (true) {
                        if (o.login()) {

                            Scanner oinput = new Scanner(System.in);
                            System.out.println("1 To update account");
                            System.out.println("2 To add event");
                            System.out.println("3 To edit event");
                            System.out.println("4 To add sponsor");
                            System.out.println("5 To add service");
                            System.out.println("0 for Exit");
                            int y = oinput.nextInt();

                            while (y != 0) {
                                switch (y) {
                                    case 1: {
                                        o.updateAccount();
                                        break;
                                    }
                                    case 2: {
                                        o.addEventDetails();
                                        break;
                                    }
                                    case 3: {
                                        o.editEvent();
                                        break;
                                    }
                                    case 4: {
                                        o.addSponsosrToEvent();
                                        break;
                                    }
                                    case 5: {
                                        s.addService();
                                        break;
                                    }
                                    default: {
                                        System.out.println("Invalid input");
                                        break;
                                    }
                                }
                                System.out.println("1 To update account");
                                System.out.println("2 To add event");
                                System.out.println("3 To edit event");
                                System.out.println("4 To add sponsor");
                                System.out.println("5 To add service");
                                System.out.println("0 for Exit");
                                y = oinput.nextInt();
                            }
                            break;
                        }
                    }
                }
                case 2: {
                    Scanner cx = new Scanner(System.in);
                    System.out.println("1 To Login");
                    System.out.println("2 To Signup");
                    int i = cx.nextInt();
                    if (i == 1) {
                        personid = c.login();
                    } else {
                        c.createAccount();
                    }
                    int z = -1; // initialize z with an invalid value
                    while (z != 0) {
                        Scanner cinput = new Scanner(System.in);
                        System.out.println("1 To update account");
                        System.out.println("2 To book ticket OR Add to balance OR Refund Money");
                        System.out.println("3 To view booking");
                        System.out.println("4 To view events");
                        System.out.println("0 for Exit");
                        z = cinput.nextInt();
                        switch (z) {
                            case 1: {
                                c.updateAccount();
                                break;
                            }
                            case 2: {
                                c.manage(personid);
                                break;
                            }
                            case 3: {
                                c.viewBooking();
                                break;
                            }
                            case 4: {
                                c.viewEvents();
                                break;
                            }
                            case 0: {
                                break;
                            }
                            default: {
                                System.out.println("Invalid input");
                                break;
                            }
                        }
                    }
                    break;
                }

                case 3:
                {
                    a.getInstance("youssef ahmed", "01005428418", "youssefahmed@gmail.com", "20", "23456789", 2, 2, 1);
                    break;
                }
                case 4:
                    break;
            }
            break;
        }

    }

}