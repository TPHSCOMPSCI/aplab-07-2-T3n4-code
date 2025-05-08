  import java.awt.Color;
  import java.util.ArrayList;

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

      hideText(revealed, null);

      revealText(revealed);
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
<<<<<<< HEAD
<<<<<<< HEAD
      if(source.getPictureFrame().equals(secret.getPictureFrame()))
      return true;

      else
      return false;
=======
=======
>>>>>>> a1e6dc4961e45438af25514f760cbfabadc20b7c
      if(source.getPictureWithHeight() == secret.getPictureWithHeight() && source.getPictureWithHeight() == source.getPictureWithWidth(0))
      return true;
      
      else
      return false;
      
<<<<<<< HEAD
>>>>>>> a1e6dc4961e45438af25514f760cbfabadc20b7c
=======
>>>>>>> a1e6dc4961e45438af25514f760cbfabadc20b7c
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
          /**
         * Takes a string consisting of letters and spaces and
        * encodes the string into an arraylist of integers.
        * The integers are 1-26 for A-Z, 27 for space, and 0 for end of
        * string. The arraylist of integers is returned.
        * @param s string consisting of letters and spaces
        * @return ArrayList containing integer encoding of uppercase
        * version of s
        */
        public static ArrayList<Integer> encodeString(String s) {
          s = s.toUpperCase();
          String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
          ArrayList<Integer> result = new ArrayList<Integer>();
          for (int i = 0; i < s.length(); i++) {
              if (s.substring(i, i + 1).equals(" ")) {
                  result.add(27);  // Space is encoded as 27
              } else {
                  result.add(alpha.indexOf(s.substring(i, i + 1)) + 1);  // Letters A-Z are encoded as 1-26
              }
          }
          result.add(0);  // 0 represents the end of the string
          return result;
      }
                  /**
          * Returns the string represented by the codes arraylist.
          * 1-26 = A-Z, 27 = space
          * @param codes encoded string
          * @return decoded string
          */
          public static String decodeString(ArrayList<Integer> codes) {
            String result = "";
            String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < codes.size(); i++) {
                if (codes.get(i) == 27) {
                    result = result + " ";  // 27 represents a space
                } else {
                    result = result + alpha.substring(codes.get(i) - 1, codes.get(i));  // 1-26 for A-Z
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
            private static int[] getBitPairs(int num) {
              int[] bits = new int[3];
              int code = num;
              for (int i = 0; i < 3; i++) {
                  bits[i] = code % 4;  // Extracting the 2-bit pairs
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
        public static void hideText(Picture source, String s) {
          ArrayList<Integer> encodedMessage = encodeString(s);
          Pixel[][] pixels = source.getPixels2D();
      
          int messageIndex = 0;
      
          for (int row = 0; row < pixels.length; row++) {
              for (int col = 0; col < pixels[row].length; col++) {
                  if (messageIndex >= encodedMessage.size()) return;
      
                  Pixel pixel = pixels[row][col];
                  int currentNumber = encodedMessage.get(messageIndex);
                  int[] bitPairs = getBitPairs(currentNumber);
      
                  
                  pixel.setRed((pixel.getRed() & 0xFC) | bitPairs[0]);
                  pixel.setGreen((pixel.getGreen() & 0xFC) | bitPairs[1]);
                  pixel.setBlue((pixel.getBlue() & 0xFC) | bitPairs[2]);
      
                  messageIndex++;
              }
          }
      }
              public static String revealText(Picture source) {
            String result = "";
            Pixel[][] pixels = source.getPixels2D(); 
            ArrayList<Integer> encodedMessage = new ArrayList<>(); 

            for (int row = 0; row < pixels.length; row++) {
                for (int col = 0; col < pixels[row].length; col++) {
                    Pixel pixel = pixels[row][col];

                    
                    int red = pixel.getRed() & 0x03;
                    int green = pixel.getGreen() & 0x03;
                    int blue = pixel.getBlue() & 0x03;

                   
                    int encodedNumber = (red << 4) | (green << 2) | blue;
                    encodedMessage.add(encodedNumber);

                    if (encodedNumber == 0) return decodeString(encodedMessage);
                }
            }

            return decodeString(encodedMessage);
        }
     }


  //** * Clear the lower (rightmost) two bits in a pixel. */
  /** * Set the lower 2 bits in a pixel to the highest 2 bits in c */ 
  /** o * Sets the highest two bits of each pixel’s colors * to the lowest two bits of each pixel’s color s */ 
  