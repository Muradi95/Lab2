public class MazeFinder {

	private Robot robot;


	public static void main(String[] args) { 
		MazeFinder finder = new MazeFinder(); 
		finder.createEnviroment(); 
		finder.findExit(); 
	}//main 

	public void createEnviroment() { 
		RobotWorld world = RobotWorld.load("src/maze.txt"); 
		robot = new Robot(1, 2, Robot.EAST, world); 
		robot.setDelay(50);
	}//createEnviroment 

	//The robot finds the way through a simply connected maze
	//before: The maze is simply connected.
	//        The robot is at the entrance of the maze.
	//after:  The robot is at the exit of the maze
	public void findExit() {

		while (!robot.atEndOfWorld()) {

			if (checkDirectionLeft())
				robot.move();
			else
				changeDirection();
			}
		}
	//The robot checks if left is clear
	//before: the robot is somewhere in the maze
	//after: the robot has turned left and returned a boolean declaring if it can move forward
	private boolean checkDirectionLeft(){

		robot.turnLeft();
		return robot.frontIsClear();

	}
	//Turn the robot 180 degrees
	private void changeDirection() {

		robot.turnLeft();
		robot.turnLeft();

	}

}//MazeFinder

