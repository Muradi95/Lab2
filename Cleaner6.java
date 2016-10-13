public class Cleaner6 {
    private Robot robot;
    public static void main(String[] args) {
        Cleaner6 cleaner = new Cleaner6();
        cleaner.createEnviroment();
        cleaner.cleanCorridors();
    } //main

    private void createEnviroment()
    {
        RobotWorld world = RobotWorld.load("src/loop.txt");
        robot = new Robot(1, world.getNumCols() - 6, Robot.SOUTH, world);
        robot.setDelay(250);
    }//createEnviroment

    //before: robot is in staring position, facing any direction
    //after: robot is back in starting position, all cells are now light
    private void cleanCorridors()
    {
        Location startp = robot.getLocation();

        firstMove();

        Location currentp = robot.getLocation();
        while(!currentp.equals(startp))
        {
            while (robot.frontIsClear() && !currentp.equals(startp))
            {
                cleaningRoutine();
                currentp = robot.getLocation();
            }
            if(!currentp.equals(startp) && !scoutLeft())
                turnFront();
        }
    }//cleanCorridors

    //before: robot checks if current cell is dark
    //after: cell is light, the robot has moved one cell and checks for wall
    private void cleaningRoutine()
    {
        if (robot.onDark())
            robot.makeLight();
        robot.move();
        robot.frontIsClear();
    }

    //before: robot is facing in some direction
    //after: robot has turned left and and returned a boolean declaring whether or not the front is clear
    private boolean scoutLeft()
    {
        robot.turnLeft();
        return robot.frontIsClear();
    }//scoutLeft

    //before: robot is facing in some direction
    //after: robot has turned 180degrees
    private void turnFront()
    {
        robot.turnLeft();
        robot.turnLeft();
    }//turnFront

    //before: robot is in starting position
    //after: robot has turned first cell light if it was dark, robot has started moving if front was clear,
    //robot has turned and started moving if it was facing a wall
    private void firstMove()
    {
        if(robot.onDark())
            robot.makeLight();
        if(robot.frontIsClear() )
            robot.move();
        else if (!scoutLeft()) {
            robot.turnLeft();
            robot.move();
        }
        else
        {
            if (robot.onDark())
                robot.makeLight();
            robot.move();
        }
    }
}//Cleaner
