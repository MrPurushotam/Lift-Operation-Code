import java.util.ArrayList;
import java.util.List;
// Code for lift operation

/**
 * main
 */
public class master {
    public static void main(String[] args) {
        
    }
}

class Lift{
    private int direction;
    private int max=5;
    private List<Integer> requestedFloors;


    public Lift() {
        direction = 0; // Lift starts in stationary position
        requestedFloors = new ArrayList<>();
    }

    public void requestFloor(int floor) {
        requestedFloors.add(floor);
        sortRequestedFloors(); 
    }
    private void determineDirection() {
        if (requestedFloors.isEmpty()) {
            direction = 0; 
        } else {
            int nextFloor = requestedFloors.get(0);
            if (nextFloor > getCurrentFloor()) {
                direction = 1; // Move upwards
            } else if (nextFloor < getCurrentFloor()) {
                direction = -1; // Move downwards
            } else {
                direction = 0; // Lift is already at the requested floor
            }
        }
    }
    private int getCurrentFloor() {
        // Implement logic to get the current floor of the lift
        return 0; // Placeholder
    }
    private void sortRequestedFloors() {
        if (direction == 1) {
            requestedFloors.sort(null); 
        } else if (direction == -1) {
            requestedFloors.sort((a, b) -> b - a); 
        }
    }
        
}