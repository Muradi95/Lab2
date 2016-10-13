public class Location { 
   private int row; 
   private int col; 

   public Location(int row, int col) { 
      this.row = row; 
      this.col = col; 
   } //constructor

   public int getRow() { 
      return row; 
   } //getRow

   public int getCol() { 
      return col; 
   } //getCol

   public boolean equals(Location otherLoc) { 
      return row == otherLoc.getRow() && col == otherLoc.getCol(); 
   } //equals

   public String toString() { 
      return "(" + row + ", " + col + ")"; 
   } //toString
} //Location
