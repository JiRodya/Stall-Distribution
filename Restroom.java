/**
 * Title:       RestroomSimulator
 * Semester:    COP3337-Summer2022
 * @author:     Dianelys Rocha
 * 
 * 

/**
   A class that shows how restroom stalls are occupied.
*/

public class Restroom
{
   private boolean [] stalls;
   private int [] occupiedIndexes;
   private int numOccupied;
   private int currentIndex;
   private int minLimit;
   private int maxLimit;
   private int freeStalls;
   private int currentBest;
   private int minIndex;
   private int leaveFree;

   /**
      Constructs a restroom with a given number of stalls.
      @param ns the number of stalls
   */
   public Restroom(int ns)
   {
        stalls=new boolean[ns];
   }

   /*
      Adds an occupant in the middle of the longest sequence of
      unoccupied places.
   */

   public void addOccupant()
   {
       //variables
       numOccupied=currentBest=minIndex=0;
       currentIndex=1;

       //counting to create the array which will hold all the occupied indexes

       for(boolean i : stalls)
       {
         if(i)
            numOccupied++;
       }

       //initializing @occupiedIndexes on +2 because index 0 and final will be added manually
       occupiedIndexes=new int[numOccupied+2];

       occupiedIndexes[0]=0;// adds 0 as first number

       //adding all occupied indexes to the array
       for(int i=0;i<stalls.length;i++)
       {
           if(stalls[i])
           {
               occupiedIndexes[currentIndex]=i;
               currentIndex++;
           }
       }


       occupiedIndexes[currentIndex]=stalls.length-1; // add last Index

       //creating outer loop to run through the @occupiedIndexes
       for(int i=0; i<occupiedIndexes.length-1;i++)
       {
           //setting lower and upper limits to count free stalls, also setting @freeStalls to zero
           minLimit=occupiedIndexes[i];
           maxLimit=occupiedIndexes[i+1];
           freeStalls=0;

           //inner loop to run through stalls within the previous limits
           for(int j=minLimit;j<=maxLimit;j++)
           {
               if(!stalls[j])
                  freeStalls++;
           }

           /**
            * updates @currentBest, to keep track of the "block" with more free Stalls,
            * keeps track of the indexes of the block with more free stalls
            */
           if(freeStalls>currentBest)
           {
               currentBest=freeStalls;
               minIndex=minLimit;
            }
       }


      /**
       * when doing it by hand, I saw that I was counting how many stalls
       * I would leave free at each side of the stall I wanted to occupy,
       * This is just trying to imitate the same logic
       */
       leaveFree=currentBest/2;

       /**Dealing with odd number of free stalls.
        * When I first used this approach, whenever  I would have an odd number of
        * free stalls there would be a problem.
        * When I was dealing with a lower index different from zero, this would yield
        * a wrong number.
        * Adding one to the amount of spaces I needed to leave free, fixed that problem.
        */

       if(currentBest%2!=0 && minIndex!=0)
          leaveFree++;

      while(leaveFree>0)
      {
          minIndex++;
          leaveFree--;
      }
      stalls[minIndex]=true;//setting the needed stall to "occpied"
   }




   /*
      Gets a string describing the current stall occupation
      @return a string with _ for an empty stall and X for an occupied one
   */
   public String getStalls()
   {
       String result="";
      for (boolean i : stalls)
      {
          result+= (i)? "X" : "_";
      }
      return result;
   }
}
