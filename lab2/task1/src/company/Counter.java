package company;

public class Counter {
    private int value;
    public void increment(){
        this.value++;
    }
    public void decrement(){
        this.value--;
    }
    public int getValue(){
        return this.value;
    }

    public Counter(int value){
        this.value = value;
    }
}
