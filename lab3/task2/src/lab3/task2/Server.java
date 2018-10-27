package lab3.task2;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    HashMap<Integer, Guest> guests = new HashMap<>();
    private Boolean[] presentGuests;
    private Boolean[] pairHavingTable;
    final Lock lock = new ReentrantLock();
    final Condition table  = lock.newCondition();
    final Condition currentlyWaiting = lock.newCondition();
    HashMap<Integer, Lock> pairLocks = new HashMap<>();
    HashMap<Integer, Condition> pairMissingSomeone = new HashMap<>();
    private boolean isReserved = false;
    private int guestsAmount;

    public Server(HashMap<Integer, Guest> guests, int guestsAmount){
        this.guests = guests;
        this.guestsAmount = guestsAmount;
        this.presentGuests = new Boolean[guestsAmount];
        this.pairHavingTable = new Boolean[guestsAmount/2];
        for(int i=0; i<guestsAmount; i++) this.presentGuests[i] = false;
        for(int i=0; i<guestsAmount/2; i++){
            Lock pair_lock = new ReentrantLock();
            Condition pair_condition = pair_lock.newCondition();
            pairLocks.put(i, pair_lock);
            pairMissingSomeone.put(i, pair_condition);
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
            Lock pairLock = pairLocks.get(pairID);
            Condition pairCondition = pairMissingSomeone.get(pairID);
            lock.unlock();
            pairLock.lock();
            pairCondition.await();
            lock.lock();
            System.out.println("Guest#" + guestID + " has just found out that Guest#" + anotherGuestID + " has come too");
            System.out.println("Guest#" + guestID + " is waiting for the table");
            while (isReserved){
                table.await();
            }
            isReserved = true;
            pairHavingTable[pairID] = true;
            System.out.println("Guest#" + guestID + " has reserved the table");
            currentlyWaiting.signal();
            lock.unlock();

        }else {
            Lock pairLock = pairLocks.get(pairID);
            pairLock.lock();
            pairMissingSomeone.get(pairID).signal();
            pairLock.unlock();
            while(!pairHavingTable[pairID]){
                currentlyWaiting.await();
            }
            System.out.println("Guest#" + guestID + " has just found out that Guest#" + anotherGuestID + " has reserved the table.");
            lock.unlock();
        }

    }

    public void releaseTable(Integer guestID) {
        lock.lock();
        System.out.println("Guest#" + guestID + " is leaving the table");
        Integer anotherGuestID = guests.get(guestID).getAnotherGuestID();
        Integer pairID = guests.get(guestID).getPairID();
        if(!presentGuests[anotherGuestID]){
            isReserved = false;
            System.out.println("Guest#" + guestID + " is releasing the table");
            table.signal();
            presentGuests[guestID] = false;
            System.out.println("Guest#" + guestID + " is leaving the restaurant");
            lock.unlock();
        }else{
            presentGuests[guestID] = false;
            System.out.println("Guest#" + guestID + " is leaving the restaurant and Guest#" + anotherGuestID);
            lock.unlock();
        }
    }
}
