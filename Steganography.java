import java.awt.Color; 
    /**
    * Clear the lower (rightmost) two bits in a pixel.
    */
    public static void clearLow( Pixel p ){ 
      p.setRed(p.getRed() & 0b11111100);
        p.setGreen(p.getGreen() & 0b11111100);
        p.setBlue(p.getBlue() & 0b11111100);
    }
      public static Picture testClearLow(Picture fromPic){
        return new Picture();
    }
    public class Steganography{ 
    public static void main(String[]args) {
    Picture beach = new Picture ("beach.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore(); 
    }
  }