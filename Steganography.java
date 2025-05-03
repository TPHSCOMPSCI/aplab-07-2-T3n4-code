  import java.awt.Color;

     public class Steganography{ 

    public static void main(String[] args) {
    Picture beach = new Picture ("beach.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore(); 

    Picture beach2 = new Picture ("beach.jpg"); 
    beach2.explore(); 
    Picture copy2 = testSetLow(beach2, Color.PINK); 
    copy2.explore();

    Picture copy3 = revealPicture(copy2); 
    copy3.explore();

    if (canHide(copy2, copy3)) {
      Picture combined = hidePicture(copy2, copy3);
      combined.explore();
  
      Picture revealed = revealPicture(combined);
      revealed.explore();
      
  }

    }
    public static void clearLow( Pixel p ){ 
        p.setRed(p.getRed() & 0b11111100);
        p.setGreen(p.getGreen() & 0b11111100);
        p.setBlue(p.getBlue() & 0b11111100);
    }

    public static Picture testClearLow(Picture fromPic){
      return new Picture();
    }

  public static void setLow (Pixel p, Color c){
        p.setRed(c.getRed() & 0b11111111);
        p.setGreen(c.getGreen() & 0b11111111);
        p.setBlue(c.getBlue() & 0b11111111);
  }

    public static Picture testSetLow(Picture fromPic, Color c){
      return new Picture();
    }

    public static Picture revealPicture(Picture hidden){
      Picture copy = new Picture(hidden);
      Pixel[][] pixels = copy.getPixels2D();
      Pixel[][] source = hidden.getPixels2D();
      for (int r = 0; r < pixels.length; r++){
        for (int c = 0; c < pixels[0].length; c++){
          Color col = source[r][c].getColor(); 
          Pixel p = pixels[r][c];
          p.setColor(new Color(col.getRed()%4*64, col.getGreen()%4*64, col.getBlue()%4*64));
        }
      }
      return copy;
    }

    public static boolean canHide(Picture source, Picture secret){
      if(source.getPictureWithHeight() == secret.getPictureWithHeight() && source.getPictureWithHeight() == source.getPictureWithWidth(0))
      return true;
      
      else
      return false;
      
    }

    public static Picture hidePicture(Picture source, Picture secret){

    Picture combined = new Picture(source);
    Pixel[][] sourcePixels = source.getPixels2D();
    Pixel[][] secretPixels = secret.getPixels2D();
    Pixel[][] combinedPixels = combined.getPixels2D();

    for (int r = 0; r < sourcePixels.length; r++) {
        for (int c = 0; c < sourcePixels[0].length; c++) {
            Pixel sourcePixel = sourcePixels[r][c];
            Pixel secretPixel = secretPixels[r][c];
            Pixel combinedPixel = combinedPixels[r][c];

            
            int red = (sourcePixel.getRed() & 0b11111100) | (secretPixel.getRed() >> 6);
            int green = (sourcePixel.getGreen() & 0b11111100) | (secretPixel.getGreen() >> 6);
            int blue = (sourcePixel.getBlue() & 0b11111100) | (secretPixel.getBlue() >> 6);

            combinedPixel.setColor(new Color(red, green, blue));
        }
    }

    return combined;

  }
}

  //** * Clear the lower (rightmost) two bits in a pixel. */
  /** * Set the lower 2 bits in a pixel to the highest 2 bits in c */ 
  /** o * Sets the highest two bits of each pixel’s colors * to the lowest two bits of each pixel’s color s */ 
  