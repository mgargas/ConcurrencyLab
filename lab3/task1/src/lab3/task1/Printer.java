package lab3.task1;

public class Printer {
    private int id;

    public Printer(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PRINTER#" + this.id;
    }
}
