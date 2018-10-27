package lab3.task2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Guest guest0 = new Guest(0, 3, 0);
        Guest guest1 = new Guest(1, 4, 1);
        Guest guest2 = new Guest(2, 5, 2);
        Guest guest3 = new Guest(3, 0, 0);
        Guest guest4 = new Guest(4, 1, 1);
        Guest guest5 = new Guest(5, 2, 2);

        HashMap<Integer,Guest> guestsMap = new HashMap<>();

        guestsMap.put(0, guest0);
        guestsMap.put(1, guest1);
        guestsMap.put(2, guest2);
        guestsMap.put(3, guest3);
        guestsMap.put(4, guest4);
        guestsMap.put(5, guest5);

        Server server = new Server(guestsMap, 6);

        guest0.setServer(server);
        guest1.setServer(server);
        guest2.setServer(server);
        guest3.setServer(server);
        guest4.setServer(server);
        guest5.setServer(server);

        guest0.start();
        guest1.start();
        guest2.start();
        guest3.start();
        guest4.start();
        guest5.start();

        /*Guest guest0 = new Guest(0, 1, 0);
        Guest guest1 = new Guest(1, 0, 0);

        HashMap<Integer,Guest> guestsMap = new HashMap<>();

        guestsMap.put(0, guest0);
        guestsMap.put(1, guest1);

        Server server = new Server(guestsMap, 2);

        guest0.setServer(server);
        guest1.setServer(server);


        guest0.start();
        guest1.start();
*/


    }
}
