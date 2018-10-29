package lab3.task2;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    HashMap<Integer, Guest> guests = new HashMap<>();
    private Boolean[] presentGuests;
    private Boolean[] pairHavingTable;
    private final Lock lock = new ReentrantLock();
    private final Condition table  = lock.newCondition();
    private HashMap<Integer, Condition> pairMissingTable = new HashMap<>();
    private HashMap<Integer, Condition> pairMissingSomeone = new HashMap<>();
    private boolean isReserved = false;

    public Server(HashMap<Integer, Guest> guests, int guestsAmount){
        this.guests = guests;
        this.presentGuests = new Boolean[guestsAmount];
        this.pairHavingTable = new Boolean[guestsAmount/2];
        for(int i=0; i<guestsAmount; i++) this.presentGuests[i] = false;
        for(int i=0; i<guestsAmount/2; i++){
            pairMissingSomeone.put(i, lock.newCondition());
            pairMissingTable.put(i, lock.newCondition());
            pairHavingTable[i] = false;
        }
    }

    public void reserveTable(Integer guestID) throws InterruptedException {
        lock.lock();
        presentGuests[guestID] = true;
        System.out.println("Guest#" + guestID + " has just come to the restaurant");
        Integer anotherGuestID = guests.get(guestID).getAnotherGuestID();
        Integer pairID = guests.get(guestID).getPairID();
        if(!presentGuests[anotherGuestID]){
            System.out.println("Guest#" + guestID + " is waiting for Guest#" + anotherGuestID);
            pairMissingSomeone.get(pairID).await();
            System.out.println("Guest#" + guestID + " has just found out that Guest#" + anotherGuestID + " has come too");
            System.out.println("Guest#" + guestID + " is checking if the table is available.");
            while (isReserved){
                System.out.println("Guest#" + guestID + " is waiting for the table.");
                table.await();
            }
            isReserved = true;
            pairHavingTable[pairID] = true;
            System.out.println("Guest#" + guestID + " has reserved the table");
            pairMissingTable.get(pairID).signal();
            pairMissingSomeone.get(pairID).await();

        }else {
            pairMissingSomeone.get(pairID).signal();
            while(!pairHavingTable[pairID]){
                pairMissingTable.get(pairID).await();
            }
            System.out.println("Guest#" + guestID + " has just found out that Guest#" + anotherGuestID + " has reserved the table.");
            pairMissingSomeone.get(pairID).signal();
        }
        lock.unlock();

    }

    public void releaseTable(Integer guestID) {
        lock.lock();
        System.out.println("Guest#" + guestID + " is leaving the table");
        Integer anotherGuestID = guests.get(guestID).getAnotherGuestID();
        if(!presentGuests[anotherGuestID]){
            isReserved = false;
            System.out.println("Guest#" + guestID + " is releasing the table");
            table.signal();
            presentGuests[guestID] = false;
            System.out.println("Guest#" + guestID + " is leaving the restaurant");
        }else{
            presentGuests[guestID] = false;
            System.out.println("Guest#" + guestID + " is leaving the restaurant and Guest#" + anotherGuestID);
        }
        lock.unlock();
    }
}
