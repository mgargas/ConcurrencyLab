package lab4.task2;

public interface IBuffer {
    void put(int amount, int producerId);

    void take(int amount, int consumerId);

    int getSize();
}
