package lab3.task1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        int p = 2;
        int c = 10;
        List<Printer> printersList = new ArrayList<>();
        for(int i=0; i<p; i++){
            printersList.add(new Printer(i));
        }
        PrintersMonitor printersMonitor = new PrintersMonitor(printersList);
        List<Client> clientsList = new ArrayList<>();
        for(int i=0; i<c; i++){
            clientsList.add(new Client(i, printersMonitor));
        }
        for(Client client : clientsList) client.run();
        /*for(Client client : clientsList) {
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
