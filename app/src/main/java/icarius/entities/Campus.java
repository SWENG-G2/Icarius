package icarius.entities;

public class Campus {
    public long id;
    public String name;

    public Campus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nId: " + id + "\t\tCampus: " + name;
    }
}
