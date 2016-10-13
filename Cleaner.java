import java.util.Objects;

public class Cleaner {
        private Robot robot;
        public static void main(String[] args) {
                Cleaner cleaner = new Cleaner();
                cleaner.createEnviroment();
                cleaner.cleanCorridors();
        } //main

        private void createEnviroment() {
                RobotWorld world = RobotWorld.load("src/square.txt");
                robot = new Robot(1, world.getNumCols() - 2, Robot.WEST, world);
                robot.setDelay(250);
        }//createEnviroment 



        // The robot cleans the corridor by making the colour of each cell light
        // before: The room has four corridors, forming a square
        //         The robot is located in beginning of one of the corridors, facing
        //           the corridor in counter-clockwise direction.
        //         All cells in the corridors are dark.
        // after:  The robot has the same location and facing the same direction.
        private void cleanCorridors()
        {
               for(int i = 0; i < 4; i++)                  //Clean 4 corridors
               {
                       int b = 0;
                       while (b < 4)
                       {                                  //clean four cells
                               robot.makeLight();
                               robot.move();
                               b++;

                       }
                       robot.turnLeft();                   //when in the end of a corridor turn left
                       b = 0;                              //reset loop
               }



        }//cleanCorridors
}//Cleaner 

