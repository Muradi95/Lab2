import java.util.Objects;

public class Cleaner2 {
    private Robot robot;
    public static void main(String[] args) {
        Cleaner2 cleaner = new Cleaner2();
        cleaner.createEnviroment();
        cleaner.cleanCorridors();
    } //main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/square2.txt");
        robot = new Robot(1, world.getNumCols() - 2, Robot.WEST, world);
        robot.setDelay(250);
    }//createEnviroment


    // The robot cleans the corridor by making the colour of each cell light
    // before: The room has four corridors, forming a square
    //         The robot is located in beginning of one of the corridors, facing
    //           the corridor in counter-clockwise direction.
    // after:  The robot has the same location and facing the same direction.
    private void cleanCorridors()
    {
        for(int i = 0; i < 4; i++)                              //cleaning 4 corridors
        {

            while (robot.frontIsClear())                       //if the robot can move forward do the following
            {
                if(robot.onDark())                              // if the robot is on a dark spot
                    robot.makeLight();                          // clean
                robot.move();                                   //move the next cell

            }

            robot.turnLeft();                                   //end of corridor -- turn left


        }



    }//cleanCorridors
}//Cleaner
