package src;

public class Tonnel {
    Planet from;
    Planet to;
    int cost;

    public Tonnel(Planet from, Planet to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "T{" +
                "" + from +
                " => " + to +
                ", cost=" + cost +
                '}';
    }
}
