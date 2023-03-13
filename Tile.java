package test;


import java.util.Objects;
import java.util.Random;

public class Tile
{
   /**premission**/
    public final char letter;
    public final int score;

    public Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }



    //Interior class
    public static  class Bag
    {
        final int[] lettersAtFirst={9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        int[] lettersCount={9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        Tile[] tiles;

        private static Bag b = null;
        int capacity;

        private Bag()
        {
             tiles= new Tile[]{new Tile('A',1), new Tile('B',3),
                    new Tile('C',3),new Tile('D',2),new Tile('E',1),new Tile('F',4),
                    new Tile('G',2),new Tile('H',4),new Tile('I',1),new Tile('J',8),
                    new Tile('K',5),new Tile('L',1),new Tile('M',3),new Tile('N',1),
                    new Tile('O',1),new Tile('P',3),new Tile('Q',10),new Tile('R',1),
                    new Tile('S',1),new Tile('T',1),new Tile('U',1),new Tile('V',4),
                    new Tile('W',4),new Tile('X',8),new Tile('Y',4),new Tile('Z',10)};

             capacity=98;
        }



        public Tile getRand()
        {
            if (capacity==0)
                return null;

            Random r= new Random();
            int cell = Math.abs(r.nextInt(26));
            while(lettersCount[cell]==0)
            {
                cell=(cell+1)%26;
            }
            lettersCount[cell]-=1;

            Tile t=tiles[cell];
            capacity--;
            return t;
        }



        public Tile getTile(char letter)
        {
            int i;
            for( i=0; i<26; i++) {
                if (tiles[i].letter == letter) {
                    if (lettersCount[i] == 0)
                        return null;
                    else {
                        lettersCount[i] -= 1;
                        capacity--;
                        return tiles[i];
                    }
                }
            }
            return null;
        }



        public void put(Tile t)
        {
            char l=t.letter;
            int i;
            if (capacity==98)
                return;
            for(i=0; i<26;i++)
            {
                if (tiles[i].letter==l)
                {
                    if(lettersCount[i]+1>lettersAtFirst[i])
                        return;
                    else {
                        lettersCount[i] += 1;
                        capacity++;
                    }
                }
            }
        }

        public int size()
        {
           return capacity;
        }

        //TODO
        //CONTINUE
        public int[] getQuantities()
        {
            int [] quantities = (int[])  lettersCount.clone();
            return quantities;
        }

        public static Bag getBag()
        {
            if (b==null)
            {
                b=new Bag();
                return b;
            }

                return b;

        }
    }

}
