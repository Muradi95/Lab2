import java.awt.Color;
public class Cell  { 
   private Color color; 
   private String imageFileName; 

   public Cell()  { 
      color = new Color(0, 0, 0); 
      imageFileName = null; 
   } //constructor

   public void setColor(Color color)  { 
      this.color = color; 
   } //setColor

   public Color getColor()  { 
      return color; 
   } //getColor

   public String getImageFileName() { 
      return imageFileName; 
   } //getImageFileName

   public void setImageFileName(String fileName)  { 
      imageFileName = fileName; 
   } //setImageFileName
}//Cell
