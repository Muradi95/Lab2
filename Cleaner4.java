import java.util.Objects;

public class Cleaner4 {
    private Robot robot;

    public static void main(String[] args) {
        Cleaner4 cleaner = new Cleaner4();
        cleaner.createEnviroment();
        cleaner.cleanCorridors();

    } //main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/square3.txt");
        robot = new Robot(1, world.getNumCols() - 2, Robot.NORTH, world);
        robot.setDelay(250);
    }//createEnviroment

    // The robot cleans the corridor by making the colour of each cell light
    // before: The room has four corridors, forming a rectangle
    //         The robot is located in beginning of one of the corridors
    //         All cells in the corridors are dark.
    // after:  The robot has the same location and facing the same direction.
    private void cleanCorridors() {
        int direction = robot.getDirection();
        System.out.print(direction);
        if (direction == 2 || direction == 1) //facing South or East
            moveClockwise();
        else
            moveAntiClockwise();
    }//cleanCorridors

    //Before: Robot is somewhere in the room
    //After: Robot has turned right
    public void turnRight() {
        robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
    }//turnRight

    //Before: Same as "Cleancorridors"
    //        Except robot is facing either North or West
    // After: The robot has cleaner four corridors and has the startlocation and start direction
    public void moveClockwise() {
        if(!robot.frontIsClear())
            turnRight();
        for (int i = 0; i < 4; i++) {
            while (robot.frontIsClear()) {
                if (robot.onDark())
                    robot.makeLight();
                robot.move();
            }
            turnRight();
        }
    }//moveClockwise

    //Before: Same as "Cleancorridors"
    //        Except robot is facing either North or West
    // After: The robot has cleaner four corridors and has the startlocation and start direction
    public void moveAntiClockwise() {

        if(!robot.frontIsClear())
            robot.turnLeft();
        for (int i = 0; i < 4; i++) {
            while (robot.frontIsClear()) {
                if (robot.onDark())
                    robot.makeLight();
                robot.move();
            }
            robot.turnLeft();
        }
    }//MoveAntiClockwise
}

