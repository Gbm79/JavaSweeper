package sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;


    private GameState state;

    public Game(int cols, int rows, int bombs) {

        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        state = GameState.PLAY;

    }

    public Box getBox(Coord coord) {
        if (Box.OPENED == flag.get(coord))
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (isGameOver()) return;
        openBox(coord);
        checkWinner();


    }

    public void pressRightButton(Coord coord) {
        if (isGameOver()) return;
        flag.toggleFlagedtoBox(coord);
    }

    public GameState getState() {
        return state;
    }

    public int getTotalBombs() {
        return bomb.getTotalBombs();
    }

    public int getTotalFlaged() {
        return flag.getTotalFlaged();
    }

    private boolean isGameOver() {
        if (GameState.PLAY != state) {
            start();
            return true;
        }
        return false;
    }

    private void checkWinner() {
        if (GameState.PLAY == state)
            if (flag.getTotalClosed() == bomb.getTotalBombs()) {
                state = GameState.WON;
                flag.setFlagedToLastClosedBoxes();
            }

    }


    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxAroundNumbers(coord);
                break;
            case FLAGED:
                break;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAroundZero(coord);
                        break;

                    case BOMB:
                        openBombes(coord);
                        break;
                    default:
                        flag.setOpenedtoBox(coord);
                        break;
                }
        }
    }

    private void setOpenedToClosedBoxAroundNumbers(Coord coord) {
        if (Box.BOMB != bomb.get(coord))
            if (bomb.get(coord).getNumber()==flag.getCountofFlagedBoxesAround(coord))
        for(Coord around : Ranges.getCoordAround(coord))
            if(flag.get(around)==Box.CLOSED)
                openBox(around);
    }

    private void openBombes(Coord bombedCoord) {
        flag.setBobmbedToBox(bombedCoord);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToClosedBox(coord);
            else
                flag.setNobombToFlagedBox(coord);
        state = GameState.IS_BOMBED;
    }

    private void openBoxesAroundZero(Coord coord) {
        flag.setOpenedtoBox(coord);
        for (Coord around : Ranges.getCoordAround(coord))
            openBox(around);

    }

}
