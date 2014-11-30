package graph;

public class Miasto {
    private int id;
    private String nazwa;

    public Wierzcholek getW() {
        return w;
    }

    private Wierzcholek w;

    public Miasto(int id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
        w = new Wierzcholek(this);
    }

    @Override
    public String toString() {
        return nazwa.toString();
    }
}
