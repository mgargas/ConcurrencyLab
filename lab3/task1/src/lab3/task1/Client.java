package lab3.task1;

import org.Customer;

public class Client extends Thread{
    private int id;
    private PrintersMonitor printersMonitor;

    @Override
    public void run() {
        Printer printer = this.printersMonitor.reservePrinter(this.id);
        System.out.println("Printing message: " + "Client# " + this.id + "Printer#" + printer.getId());
        this.printersMonitor.releasePrinter(this.id, printer);
    }

    public Client(int id, PrintersMonitor printersMonitor){
        this.id = id;
        this.printersMonitor = printersMonitor;
    }
}
