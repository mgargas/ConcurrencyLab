package lab3.task2;

public class Guest extends Thread{
    private Integer guestID;
    private Integer anotherGuestID;
    private Integer pairID;
    private Server server;

    public Integer getAnotherGuestID() {
        return anotherGuestID;
    }

    public Integer getPairID() {
        return pairID;
    }

    public Integer getGuestID() {
        return guestID;
    }

    @Override
    public void run() {
        System.out.println("Guest#" + this.guestID + " is doing some stuff");
        try {
            server.reserveTable(this.guestID);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Guest#" + guestID + " is eating");
        server.releaseTable(this.guestID);
    }

    public Guest(Integer guestID, Integer anotherGuestID, Integer pairID){
        this.guestID = guestID;
        this.anotherGuestID = anotherGuestID;
        this.pairID = pairID;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
