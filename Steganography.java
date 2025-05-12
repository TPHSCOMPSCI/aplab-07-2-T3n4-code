  import java.awt.Color;
  import java.util.ArrayList;
  import java.awt.Point;
  import java.awt.Graphics;
  
     public class Steganography{ 

    public static void main(String[] args) {
    Picture beach = new Picture ("beach.jpg");
    Picture robot = new Picture("robot.jpg");
    Picture flower1 = new Picture("flower1.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore(); 

    Picture beach2 = new Picture ("beach.jpg"); 
    beach2.explore(); 
    Picture copy2 = testSetLow(beach2, Color.PINK); 
    copy2.explore();

    Picture copy3 = revealPicture(copy2); 
    copy3.explore();

      System.out.println(canHide(beach, robot));
        if (canHide(beach, robot)) {
            Picture hidden1 = hidePicture(beach, robot, 65, 208);
            Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
            hidden2.explore();
            Picture revealed = revealPicture(hidden2);
            revealed.explore();
        }

            Picture swan1 = new Picture("swan.jpg");
            Picture swan2 = new Picture("swan.jpg");
            System.out.println("Swan and swan2 are the same: " +
            isSame(swan1, swan2));
            swan1 = testClearLow(swan1);
            System.out.println("Swan and swan2 are the same (after clearLow run on swan): "
            + isSame(swan1, swan2));

          Picture arch = new Picture("arch.jpg");
           Picture arch2 = new Picture("arch.jpg");
          Picture koala = new Picture("koala.jpg") ;
          Picture robot1 = new Picture("robot.jpg");
          ArrayList<Point> pointList = findDifferences(arch, arch2);
          System.out.println("PointList after comparing two identical s pictures " +
          "has a size of " + pointList.size());
          pointList = findDifferences(arch, koala);
          System.out.println("PointList after comparing two different sized pictur t es " +
          "has a size of " + pointList.size());
          arch2 = hidePicture(arch, robot1, 65, 102);
          pointList = findDifferences(arch, arch2);
          System.out.println("Pointlist after hiding a picture has a siz m e of"
          + pointList.size());
          arch.show();
          arch2.show();

          Picture hall = new Picture("femaleLionAndHall.jpg");
          Picture robot2 = new Picture("robot.jpg");
          Picture flower2 = new Picture("flower1.jpg");
          // hide pictures
          Picture hall2 = hidePicture(hall, robot2, 50, 300);
          Picture hall3 = hidePicture(hall2, flower2, 115, 275);
          hall3.explore();
          if(!isSame(hall, hall3))
          {
         Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
          hall4.show();
          Picture unhiddenHall3 = revealPicture(hall3);
          unhiddenHall3.show();
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
      if(source.getWidth() >= secret.getWidth() && source.getHeight() >= secret.getHeight())
      return true;

      else
      return false;  
    }
      
     public static Picture hidePicture(Picture source, Picture secret, int row, int column){
        Picture CombinedPicture = new Picture(source);
        Pixel[][] CombinedPixels = CombinedPicture.getPixels2D();
        Pixel[][] secretPixels = secret.getPixels2D();
        for(int r = 0; r < secretPixels.length; r++){
            for(int c = 0; c < secretPixels[r].length; c++){
                Pixel CombinedPixel = CombinedPixels[r + row][c + column];
                Color secretColor = secretPixels[r][c].getColor();
                setLow(CombinedPixel, secretColor);
            }
        }
        return CombinedPicture;
    }

     public static boolean isSame(Picture Picture1, Picture Picture2) {
        if ( Picture1.getWidth() != Picture2.getWidth() ||  Picture1.getHeight() != Picture2.getHeight()) {
            return false;
        }
        Pixel[][] Pic1 =  Picture1.getPixels2D();
        Pixel[][] Pic2 =  Picture1.getPixels2D();
        for (int r = 0; r < Pic1.length; r++) {
            for (int c = 0; c < Pic1[0].length; c++) {
                if (!Pic1[r][c].getColor().equals(Pic2[r][c].getColor())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<Point> findDifferences(Picture Picture1, Picture Picture2) {
        ArrayList<Point> diffPoints = new ArrayList<>();
        if (Picture1.getWidth() != Picture2.getWidth() || Picture1.getHeight() != Picture1.getHeight()) {
            return diffPoints;
        }
        Pixel[][] Pic1 = Picture1.getPixels2D();
        Pixel[][] Pic2 = Picture1.getPixels2D();
        for (int r = 0; r < Pic1.length; r++) {
            for (int c = 0; c < Pic1[0].length; c++) {
                if (!Pic1[r][c].getColor().equals(Pic2[r][c].getColor())) {
                    diffPoints.add(new Point(c, r));
                }
            }
        }
        return diffPoints;
    }

    public static Picture showDifferentArea(Picture Picture1, ArrayList<Point> differentPoints){
        Picture modifiedPicture = new Picture(Picture1);
        for(Point p : differentPoints){
            int x = (int) p.getX();
            int y = (int) p.getY();
            Pixel pix = modifiedPicture.getPixel(x, y);
            pix.setColor(new Color(175,175,175));
        }
        return modifiedPicture;
    }

          /**
       * Takes a string consisting of letters and spaces and
      * encodes the string into an arraylist of integers.
      * The integers are 1-26 for A-Z, 27 for space, and 0 for end of
      * string. The arraylist of integers is returned.
      * @param s string consisting of letters and spaces
      * @return ArrayList containing integer encoding of uppercase
      * version of s
      */
      public static ArrayList<Integer> encodeString(String s)
      {
      s = s.toUpperCase();
      String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      ArrayList<Integer> result = new ArrayList<Integer>();
      for (int i = 0; i < s.length(); i++)
      {
      if (s.substring(i,i+1).equals(" "))
      {
      result.add(27);
      }
      else
      {
      result.add(alpha.indexOf(s.substring(i,i+1))+1);
      }
      }
      result.add(0);
      return result;
      } 

      public static String decodeString(ArrayList<Integer> codes) {
        String result = "";
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < codes.size(); i++) {
            if (codes.get(i) == 27) {
                result = result + " ";
            } else {
                result = result
                        + alpha.substring(codes.get(i) - 1, codes.get(i));
            }
        }
        return result;
    }

            /**
      * Given a number from 0 to 63, creates and returns a 3-element
      * int array consisting of the integers representing the
      * pairs of bits in the number from right to left.
      * @param num number to be broken up
      * @return bit pairs in number
      */
      private static int[] getBitPairs(int num)
      {
      int[] bits = new int[3];
      int code = num;
      for (int i = 0; i < 3; i++)
      {
      bits[i] = code % 4;
      code = code / 4;
      }
      return bits;
      }

            /**
       * Hide a string (must be only capital letters and spaces) in a
      * picture.
      * The string always starts in the upper left corner.
      * @param source picture to hide string in
      * @param s string to hide
      * @return picture with hidden string
      */
      public static Picture hideText(Picture source, String s){
        Picture hidden = new Picture(source);
        Pixel[] hiddenPixels = hidden.getPixels();
        ArrayList<Integer> codedMessage = encodeString(s);
        for(int j = 0; j < codedMessage.size(); j++){
            int[] splitBits = getBitPairs(codedMessage.get(j));
            Pixel p =  hiddenPixels[j];
            clearLow(p);
            p.setRed(p.getRed() + splitBits[0]);
            p.setGreen(p.getGreen() + splitBits[1]);
            p.setBlue(p.getBlue() + splitBits[2]);
        }
        return hidden;
    }

    /**
     * Returns a string hidden in the picture
     * @param source picture with hidden string
     * @return revealed string
     */
    public static String revealText(Picture source){
        ArrayList<Integer> codes = new ArrayList<Integer>();
        Pixel[] pixels = source.getPixels();
        for(int i = 0; i < source.getPixels().length; i++){
                Pixel p = pixels[i];
                int highestBinary = p.getBlue() % 4 * 16;
                int middleBinary = p.getGreen() % 4 * 4;
                int lowestBinary = p.getRed() % 4;
                int code = highestBinary + middleBinary + lowestBinary;
                if(code == 0){
                    i = source.getPixels().length; //I forgot how to do a while loop
                } else {
                    codes.add(code);
                }
            }
        String result = decodeString(codes);
        return result;
    }
  }

  //** * Clear the lower (rightmost) two bits in a pixel. */
  /** * Set the lower 2 bits in a pixel to the highest 2 bits in c */ 
  /** o * Sets the highest two bits of each pixel’s colors * to the lowest two bits of each pixel’s color s */ 
  