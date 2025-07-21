import java.util.TreeSet;

public class Elevator {
    private int currentFloor;
    private Direction direction;
    private int totalFloors;

    private TreeSet<Integer> upRequests;
    private TreeSet<Integer> downRequests;
    private TreeSet<Integer> destinationRequests;


    public Elevator(){
        this(5);
    }

    public Elevator(int totalFloors){
        this.currentFloor=1;
        this.direction=IDLE;
        this.totalFloors=totalFloors;
        this.upRequests=new TreeSet<>();
        this.downRequests=new TreeSet<>();
        this.destinationRequests=new TreeSet<>();
        System.out.println("Elevator created with " + totalFloors + " floors.");
    }
    /**
     * Add a floor request to appropriate queue based on current position and direction
     * This is the foundation for smart elevator routing
     */

    public void addRequest(int floor,Direction requestDirection){
        if(floor<1||floor>totalFloors){
            System.out.println("Invalid floor"+floor)
            return;
        }
        if(currentFloor==floor){
            System.out.println("Already in the floor "+floor);
            return;
        }
        if(requestDirection==Direction.UP){
            upRequests.add(floor);
            System.out.println("Added UP request for floor "+ floor);
        }else if(requestDirection==Direction.DOWN){
            downRequests.add(floor);
            System.out.println("Added DOWN request for floor "+floor);
        }
        if(direction==Direction.IDLE){
            determineDirection();
        }
    }
    
    /**
     * Add destination request (passenger inside elevator pressing floor button)
     */
    public void addDestionation(int floor){
        if(floor<1 || floor>totalFloors){
            System.out.println("Invalid floor "+floor);
            return;
        }

        if(floor==currentFloor){
            System.out.println("Already at destionation floor "+floor);
            return;
        }
        destinationRequests.add(floor);
        System.out.println("Added destination request for floor "+floor);

        if(direction==Direction.IDLE){
            determineDirection();
        }
    }

    /**
     * Basic movement implementation - moves one floor at a time
     * This will be enhanced in later phases for realistic timing
     */

     public void moveToFloor(int targetFloor){
        if(targetFloor<1 || targetFloor>totalFloors){
            System.out.println("Invalid floor "+targetFloor);
            return;
        }
        System.out.println("Moving from floor "+currentFloor);

        while(currentFloor!=targetFloor){
            if(currentFloor<targetFloor){
                currentFloor++;
                direction=Direction.UP;
            }else{
                currentFloor--;
                direction=Direction.DOWN;
            }
            System.out.println("Now at floor "+currentFloor);

            checkForPickups();

            try{
                Thread.sleep(500);
            }catch(InterruptedExcpetion e){
                Thread.currentThread().interrupt();
            }
        }
        direction = Direction.IDLE;
        System.out.println("Arrived at floor "+currentFloor);
     }

    /**
     * Determine next direction based on pending requests
     * Core logic for SCAN algorithm implementation
     */

    private void determineDirection(){
        if(!destionationRequests.isEmpty()){
            int nextDest = getNextDestination();
            if(nextDest>currentFloor){
                direction=Direction.UP;
            }else if(nextDest<currentFloor){
                direction=Direction.DOWN;
            }
            return;
        }

        boolean hasUpRequests = !upRequests.isEmpty();
        boolean hasDownRequests = !downRequests.isEmpty();

        if(hasUpRequests && hasDownRequests){
            Integer closestUp = upRequests.ceiling(currentFloor);
            Integer closestDown = downRequests.ceiling(currentFloor);

            if(closestUp==null) closestUp=upRequests.first();
            if(closestDown==null) closestDown=downRequests.first();

            int upDistance = Math.abs(closestUp-currentFloor);
            int downDistance = Math.abs(closestDown-currentFloor);
            direction = (upDistance<=downDistance)? Direction.UP:Direction.DOWN;
        }else if(hasUpRequests){
            direction=Direction.UP;
        }else if(hasDownRequests){
            direction=Direction.down;
        }else{
            direction=Direction.IDLE;
        }
        System.out.println("Direction determined: "+direction);
    }
    /**
     * Get next destination based on current direction and SCAN algorithm
    */
    private int getNextDestination(){
        if(destionationRequests.isEmpty()){
            return currentFloor;
        }
        if(direction==Direction.UP || direction==Direction.DOWN){
            Integer next = destionationRequests.ceiling(currentFloor+1);
            return (next!= null)?next:destionationRequests.first();
        }else{
            Integer next = destionationRequests.ceiling(currentFloor-1);
            return ( next!= null)?next:destionationRequests.last();
        }
    }

    /**
     * Check for pickup opportunities at current floor
     * Implements direction-based pickup logic
    */

    private void checkForPickups(){
        boolean pickup=false;

        if(direction == Direction.UP && upRequests.contains(currentFloor)){
            upRequests.remove(currentFloor);
            System.out.println("Picked up passsenger at floor"+currentFloor+" (going UP)");
            pickedup = true;
        }else if(direction=Direction.DOWN && downRequests.contains(currentFloor)){
            downRequests.remove(currentFloor);
            System.out.println("Picked up passsenger at floor"+currentFloor+" (going DOWN)");
            pickedup=false;
        }

        if(destionationRequests.contains(currentFloor)){
            destionationRequests.remove(currentFloor);
            System.out.println("Passenger alighted at floor " + currentFloor);
        }

        if(pickedup){
            // Simulate passenger pressing destination button
            // In real implementation, this would be user input
            System.out.println("Passenger boarded. Where would you like to go?");
        }
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public Direction getDirection(){
        return this.direction;
    }
}