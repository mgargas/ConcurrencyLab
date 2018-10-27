package lab3.task1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintersMonitor {
    private List<Printer> printersList = new ArrayList<>();
    private Queue<Printer> availablePrinters = new LinkedList<>();
    final Lock lock = new ReentrantLock();
    final Condition noAvailablePrinters  = lock.newCondition();

    public PrintersMonitor(List<Printer> printersList){
        this.printersList = printersList;
        this.availablePrinters.addAll(printersList);
    }

    public Printer reservePrinter(int clientID) {
        this.lock.lock();
        System.out.println("Client# " + clientID + " is reserving the printer");
        try{
            while(this.availablePrinters.size() == 0){
                System.out.println("There's no printers now. Client# " + clientID + " is waiting for the printer");
                this.noAvailablePrinters.await();
            }
            System.out.println("Client# " + clientID + " is getting the printer#" + this.availablePrinters.peek().getId());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            this.lock.unlock();
        }
        return this.availablePrinters.remove();
    }

    public void releasePrinter(int clientID, Printer printer){
        lock.lock();
        System.out.println("Client# " + clientID + " is putting back the printer# " + printer.getId());
        availablePrinters.add(printer);
        this.noAvailablePrinters.signal();
        lock.unlock();
    }
}
