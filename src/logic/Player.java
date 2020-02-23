package logic;

public class Player {
    private Spot location;

    public Player(Spot _location) {
        location = _location;
    }

    public int getCol() {
        return location.getCol();
    }

    public int getRow() {
        return location.getRow();
    }

    public Spot getSpot() {
        return location;
    }

    public void setSpot(Spot spot) {
        location = spot;
    }

}
