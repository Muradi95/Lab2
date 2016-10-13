import java.awt.*; 
import java.awt.event.*; 
import java.awt.image.*; 
import java.io.*; 
import java.net.*; 
import javax.imageio.*; 
import javax.swing.*; 
import java.util.ArrayList; 

public class RobotWorld  extends JComponent { 
   public static final Color DARK_COLOR = new Color(153, 114, 81); 
   public static final Color LIGHT_COLOR = new Color(229, 170, 122); 
   private Cell[][] cells; 
   private JFrame frame; 
   private Color lineColor; 

   public RobotWorld(int numRows, int numCols) { 
      init(numRows, numCols); 
   }//constructor 
   
   public RobotWorld(String imageFileName)  { 
      BufferedImage image = loadImage(imageFileName); 
      init(image.getHeight(), image.getWidth()); 
      showImage(image); 
      setTitle(imageFileName); 
   }//constructor 

     public static RobotWorld load(String mapFileName) { 
      FileLoader in = new FileLoader(mapFileName); 
      ArrayList<String> lines = new ArrayList<String>(); 
      while (in.hasMoreLines()) 
         lines.add(in.readLine()); 
      int numRows = lines.size(); 
      int numCols = lines.get(0).length(); 
      RobotWorld world = new RobotWorld(numRows, numCols); 
      world.setTitle(mapFileName); 
      world.setLineColor(new Color(0, 0, 0)); 
      for (int row = 0; row < numRows; row++) { 
         for (int col = 0; col < numCols; col++) { 
            Location loc = new Location(row, col); 
            if (lines.get(row).length() != numCols) 
               throw new RuntimeException("Inconsistent line length in map file \"" + mapFileName + "\""); 
            char ch = lines.get(row).charAt(col); 
               if (ch == 'X') 
               world.setImage(loc, "wall.gif"); 
            else if (ch == '.') 
                  world.setColor(loc, LIGHT_COLOR); 
            else if (ch == ':') 
               world.setColor(loc, DARK_COLOR); 
            else 
               throw new RuntimeException("Invalid character '" + ch + "' in map file \"" + mapFileName + "\""); 
          } 
      } 
      return world; 
   }//load 

   private BufferedImage loadImage(String imageFileName)  { 
      URL url = getClass().getResource(imageFileName); 
      if (url == null) 
         throw new RuntimeException("cannot find file:  " + imageFileName); 
         try  { 
            return ImageIO.read(url); 
         } 
         catch(IOException e) { 
             throw new RuntimeException("unable to read from file:  " + imageFileName); 
      } 
   }//loadImage 

   public int getNumRows()  { 
         return cells.length; 
   }//getNumRows 

   public int getNumCols() { 
      return cells[0].length; 
   }//getNumCols 

   private void init(int numRows, int numCols)  { 
      lineColor = null; 
      cells = new Cell[numRows][numCols]; 
      for (int row = 0; row < numRows; row++)  { 
         for (int col = 0; col < numCols; col++) 
            cells[row][col] = new Cell(); 
      } 
      frame = new JFrame("Grid"); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      int cellSize = Math.max(Math.min(500 / getNumRows(), 500 / getNumCols()), 1); 
      setPreferredSize(new Dimension(cellSize * numCols, cellSize * numRows)); 
      frame.getContentPane().add(this); 
      frame.pack(); 
      frame.setVisible(true); 
   }//init 

   private void showImage(BufferedImage image)  { 
      for (int row = 0; row < getNumRows(); row++)  { 
         for (int col = 0; col < getNumCols(); col++)  { 
            int x = col * image.getWidth() / getNumCols(); 
            int y = row * image.getHeight() / getNumRows(); 
            int c = image.getRGB(x, y); 
              int red = (c & 0x00ff0000) >> 16; 
            int green = (c & 0x0000ff00) >> 8; 
                int blue = c & 0x000000ff; 
            cells[row][col].setColor(new Color(red, green, blue)); 
         } 
      } 
      repaint(); 
   }//showImage

   private int getCellSize()  { 
      int cellWidth = getWidth() / getNumCols(); 
      int cellHeight = getHeight() / getNumRows(); 
      return Math.min(cellWidth, cellHeight); 
   } //getCellSize

   
   public void paintComponent(Graphics g)  { 
      for (int row = 0; row < getNumRows(); row++)   { 
         for (int col = 0; col < getNumCols(); col++)  { 
            Location loc = new Location(row, col); 
            Cell cell = cells[loc.getRow()][loc.getCol()]; 
            Color color = cell.getColor(); 
            g.setColor(color); 
            int cellSize = getCellSize(); 
            int x = col * cellSize; 
            int y = row * cellSize; 
            g.fillRect(x, y, cellSize, cellSize); 
            String imageFileName = cell.getImageFileName(); 
            if (imageFileName != null)  { 
               URL url = getClass().getResource(imageFileName); 
               if (url == null) 
                  System.out.println("File not found:  " + imageFileName); 
               else  { 
                  Image image = new ImageIcon(url).getImage(); 
                  int width = image.getWidth(null); 
                  int height = image.getHeight(null); 
                  if (width > height)  { 
                     int drawHeight = cellSize * height / width; 
                     g.drawImage(image, x, y + (cellSize - drawHeight) / 2, cellSize, drawHeight, null); 
                  } 
                  else  { 
                     int drawWidth = cellSize * width / height; 
                     g.drawImage(image, x + (cellSize - drawWidth) / 2, y, drawWidth, cellSize, null); 
                  } 
               } 
            } 
            if (lineColor != null)  { 
               g.setColor(lineColor); 
               g.drawRect(x, y, cellSize, cellSize); 
            } 
         } 
      } 
   } //paintComponent

   public void setTitle(String title)  { 
      frame.setTitle(title); 
   } //setTitle

   public boolean isValid(Location loc)  { 
      int row = loc.getRow(); 
      int col = loc.getCol(); 
      return 0 <= row  && row < getNumRows() && 0 <= col && col < getNumCols(); 
   } //isValid

   public void setColor(Location loc, Color color)  { 
      if (!isValid(loc)) 
         throw new RuntimeException("cannot set color of invalid location " + loc + " to color " + color); 
      cells[loc.getRow()][loc.getCol()].setColor(color); 
      repaint(); 
   } //setColor

   public Color getColor(Location loc)  { 
      if (!isValid(loc)) 
         throw new RuntimeException("cannot get color from invalid location " + loc); 
      return cells[loc.getRow()][loc.getCol()].getColor(); 
   }//getColor 


   public void setImage(Location loc, String imageFileName)  { 
      if (!isValid(loc)) 
         throw new RuntimeException("cannot set image for invalid location " + loc 
                                     + " to \"" + imageFileName + "\""); 
      cells[loc.getRow()][loc.getCol()].setImageFileName(imageFileName); 
      repaint(); 
   } //setImage

   public String getImage(Location loc)  { 
      if (!isValid(loc)) 
         throw new RuntimeException("cannot get image for invalid location " + loc); 
      return cells[loc.getRow()][loc.getCol()].getImageFileName(); 
   } //getImage

   public static void pause(int milliseconds)  { 
      try  { 
         Thread.sleep(milliseconds); 
      } 
      catch(Exception e)  { 
         //ignore 
      } 
   } //pause

   public void setLineColor(Color color) { 
      lineColor = color; 
      repaint(); 
   } //setLineColor
}//RobotWorld 

