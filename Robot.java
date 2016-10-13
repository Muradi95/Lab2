import java.util.*; 
public class Robot  { 
   private RobotWorld world; 
   private Location robotLoc; 
   private int direction; 
   private int delay = 250; 
   private static final String ROBOT_NORTH_IMAGE = "robotnorth.gif"; 
   private static final String ROBOT_SOUTH_IMAGE = "robotsouth.gif"; 
   private static final String ROBOT_EAST_IMAGE = "roboteast.gif"; 
   private static final String ROBOT_WEST_IMAGE = "robotwest.gif"; 
   public static final int NORTH = 0; 
   public static final int EAST = 1; 
   public static final int SOUTH = 2; 
   public static final int WEST = 3; 

   public Robot(int row, int col, RobotWorld world) { 
      this. world = world; 
      this.direction = NORTH; 
      robotLoc = new Location(row, col); 
      world.setImage(robotLoc, ROBOT_NORTH_IMAGE); 
   } //constructor

   public Robot(int row, int col, int direction, RobotWorld world) { 
      this. world = world; 
      robotLoc = new Location(row, col); 
      if (world.isValid(robotLoc) && world.getImage(robotLoc) != null) 
         throw new RuntimeException("Can't set image, location is occupied!"); 
      setDirection(direction); 
   } 

   private void setDirection(int direction) { 
      this.direction = direction; 
      if (direction  == NORTH) { 
         world.setImage(robotLoc, ROBOT_NORTH_IMAGE); 
      } 
      else if (direction == SOUTH) { 
         world.setImage(robotLoc, ROBOT_SOUTH_IMAGE); 
      } 
      else if (direction == EAST) { 
         world.setImage(robotLoc, ROBOT_EAST_IMAGE); 
      } 
      else if (direction == WEST) { 
          world.setImage(robotLoc, ROBOT_WEST_IMAGE); 
      } 
      else { 
         throw new RuntimeException("Unvalid direction"); 
      } 
   }//setDirection 
   
   public void setDelay(int milliseconds)  { 
      delay = milliseconds; 
      RobotWorld.pause(delay);
    }//setDelay 

   public void move()  { 
      if (world == null) 
         throw new RuntimeException("Map not loaded yet"); 
      int row = robotLoc.getRow(); 
      int col = robotLoc.getCol(); 
      Location newLoc; 
      if (direction ==  NORTH) 
         newLoc = new Location(row - 1, col); 
      else if (direction == SOUTH) 
         newLoc = new Location(row + 1, col); 
      else if (direction == EAST) 
         newLoc = new Location(row, col + 1); 
      else 
         newLoc = new Location(row, col - 1); 
      if (!world.isValid(newLoc)) 
         throw new RuntimeException("Attempt to move robot from " + robotLoc 
                                    + " to invalid location " + newLoc); 
      if (world.getImage(newLoc) != null) 
         throw new RuntimeException("Attempt to move robot from " + robotLoc 
                                    + " to occupied location " + newLoc); 
      String image = world.getImage(robotLoc); 
      world.setImage(robotLoc, null); 
      robotLoc = newLoc; 
      world.setImage(robotLoc, image); 
      RobotWorld.pause(delay); 
   }//move 

   public void turnLeft()  { 
      if ( world == null) 
         throw new RuntimeException("Map not loaded yet"); 
      if (direction == NORTH)  { 
         direction = WEST; 
         world.setImage(robotLoc, ROBOT_WEST_IMAGE); 
      } 
      else if (direction == SOUTH)  { 
         direction = EAST; 
         world.setImage(robotLoc, ROBOT_EAST_IMAGE); 
      } 
      else if (direction == EAST)  { 
         direction = NORTH; 
         world.setImage(robotLoc, ROBOT_NORTH_IMAGE); 
      } 
      else  { 
         direction = SOUTH; 
         world.setImage(robotLoc, ROBOT_SOUTH_IMAGE); 
      } 
      RobotWorld.pause(delay); 
   }//turnLeft 

   public void makeLight()  { 
      if (world == null) 
         throw new RuntimeException("Map not loaded yet"); 
      if (!onDark()) 
         throw new RuntimeException("Location " + robotLoc + " is already light"); 
      world.setColor(robotLoc, RobotWorld.LIGHT_COLOR); 
      RobotWorld.pause(delay); 
   }//makeLight 

   public void makeDark()  { 
      if (world == null) 
         throw new RuntimeException("Map not loaded yet"); 
      if (onDark()) 
         throw new RuntimeException("Location " + robotLoc + " is already dark"); 
      world.setColor(robotLoc, RobotWorld.DARK_COLOR); 
      RobotWorld.pause(delay); 
   }//makeDark 

   public boolean onDark()  { 
      if (world == null) 
         throw new RuntimeException("Map not loaded yet"); 
      return world.getColor(robotLoc).equals(RobotWorld.DARK_COLOR); 
   }//onDark 

   public boolean frontIsClear()  { 
      if ( world == null) 
         throw new RuntimeException("Map not loaded yet"); 
      int row = robotLoc.getRow(); 
      int col = robotLoc.getCol(); 
      Location loc; 
      if (direction == NORTH) 
         loc = new Location(row - 1, col); 
      else if (direction == SOUTH) 
         loc = new Location(row + 1, col); 
      else if (direction == EAST) 
           loc = new Location(row, col + 1); 
      else 
         loc = new Location(row, col - 1); 
      return world.isValid(loc) && world.getImage(loc) == null; 
   }//frontIsClear 

   public boolean atEndOfWorld() { 
      if (direction == NORTH) { 
         return robotLoc.getRow() == 0; 
          } 
      else if (direction == SOUTH) { 
         return robotLoc.getRow() == world.getNumRows() - 1; 
      } 
      else if (direction == EAST) { 
            return robotLoc.getCol() == world.getNumCols() - 1; 
        } 
      else { 
         return robotLoc.getCol() == 0; 
      } 
   }// atEndOfWorld 

   public Location getLocation() { 
      return robotLoc; 
   }//getLocation 

   public int getDirection() {
      return direction;
   }//getDirection

}//Robot 
