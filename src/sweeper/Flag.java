package sweeper;

public class Flag {

    private Matrix flagMap;



    private int totalFlaged;
    private int totalClosed;


    void start(){
        flagMap=new Matrix(Box.CLOSED);
        totalFlaged=0;
        totalClosed=Ranges.getSquare();

    }
    Box get(Coord coord){
        return flagMap.get(coord);

    }

     void setOpenedtoBox(Coord coord) {
        flagMap.set(coord,Box.OPENED);
        totalClosed--;
    }

   private  void setFlagedtoBox(Coord coord) {

        flagMap.set(coord,Box.FLAGED);
        totalFlaged++;
    }
    private void setClosedtoBox(Coord coord) {

        flagMap.set(coord,Box.CLOSED);
        totalFlaged--;
    }

    public void toggleFlagedtoBox(Coord coord) {

        switch(flagMap.get(coord)){

            case FLAGED:setClosedtoBox(coord);break;
            case CLOSED:setFlagedtoBox(coord);break;
    }}
        public int getTotalFlaged() {
            return totalFlaged;
        }

        public int getTotalClosed() {
            return totalClosed;
        }

        void setFlagedToLastClosedBoxes() {
        for(Coord coord : Ranges.getAllCoords())
            if(Box.CLOSED==flagMap.get(coord))
                setFlagedtoBox(coord);
    }

     void setBobmbedToBox(Coord coord) {
        flagMap.set(coord,Box.BOMBED);

    }

     void setOpenedToClosedBox(Coord coord) {
    if(Box.CLOSED==flagMap.get(coord))
        flagMap.set(coord,Box.OPENED);

    }

     void setNobombToFlagedBox(Coord coord) {

        if(Box.FLAGED==flagMap.get(coord))
            flagMap.set(coord,Box.NOBOMB);

    }

    int getCountofFlagedBoxesAround(Coord coord) {
        int count =0;
        for(Coord around : Ranges.getCoordAround(coord))
            if(flagMap.get(around)==Box.FLAGED)
                count++;
            return count;
    }
}
