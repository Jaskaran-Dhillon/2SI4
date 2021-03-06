public class HashTableQuad {
    private Integer[] table; //array of null
    private int size; //size of the table
    private int numkeys; //number of keys currently being stored
    private int maxkeys; //max keys that can be stored without breaking the load
    private double load; //lamda value

    public HashTableQuad(int maxNum, double load) {
        this.numkeys = 0;
        this.load = load;
        this.maxkeys = maxNum;

        this.size = get_prime((int) ((maxNum/load) + 1)); //gets the lowest prime number that can satisfy load
        this.table = new Integer[this.size];
    }


    private void rehash() {
        HashTableQuad temp = new HashTableQuad(2 * maxkeys, load); //creates a temp table that is 2x the size of the old table

        for (Integer i=0;i<table.length;i++) {
            Integer x = table[i]; //takes the integer value and adds it to temp if not null
            if (x != null) {
                temp.insert(x);
            }
        }

        this.table = temp.table; //copy over from temp the values and the size, the load factor remains same
        this.size = temp.size;
    }

    public void insert(Integer x) //this is only difference from linear,
    {
        int quadinc = 0;
        if(isIn(x)) //duplicates are not allowed
        {
            return;
        }
        numkeys++;
        if (numkeys>maxkeys) { //if we are beyond maxkeys then the load has been broken and we must rehash
            rehash();
        }

        int i = x % size;
        while(table[i]!=null)
        {
            i= (( x % size)+ (quadinc * quadinc))%size; //quadratic increment until we find a non-null value indicating we have reached a suitable spot to insert


            quadinc++;
        }
        table[i]=x;
    }




    private int get_prime(int x)
    {   int n=x;
        while (!isprime(n)) //checks if n is prime otherwise increment until we get the next prime number
        {
            n+=1;
        }
        return n;
    }

    public boolean isprime(int n) {

        for (int i = 2; i < n; i++)
            if (n % i == 0) {
                return false;
            }

        return true;
    }

    public boolean isIn(int n) { //n

        int i = n%size; //Hash

        while (table[i] != null) {
            if (table[i] == n) {
                return true;
            }
            i++;
            if (i >= (size-1)) { //is incremented past the table, revert back to 0
                i = 0;
            }
            else if (i == n%size) { // if we arrive back at the original index then n is not in the table
                return false;
            }

        }
        return false;
    }
    public void printKeys() {

        for (int i = 0; i < size; i++) {
            if (table[i] != null) { //only print non-null values
                System.out.print(table[i] + ",");
            }
        }
    }

    public void printKeysAndIndexes() {
        for (int i = 0; i < size; i++) {
            Integer x = table[i];
            if (x != null) {
                System.out.println(i + ", " + x);
            }
        }
    }
    public int getNumkeys() {
        return numkeys;
    }

    public int getMax()
    {return maxkeys;
    }

    public int getSize() {
        return size;}

    public Integer[] getTable()
    {return table;}


    public double getLoad()
    {return load;}
}
