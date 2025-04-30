import java.awt.Color;
    
    /**
    * Clear the lower (rightmost) two bits in a pixel.
    */
    public static void clearLow( Pixel p )
    {
    /* To be implemented */
    }
      
      public static void testClearLow(*picture parameter*){

    }

    public static void main(String[] args) {
    Picture beach = new Picture ("beach.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore(); 
    }