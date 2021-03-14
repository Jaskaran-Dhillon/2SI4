import java.util.Random;
public class HugeInteger {
    public int size;
    public String num;
    public boolean negative;

    public HugeInteger(String val) throws IllegalArgumentException {
        String check = val;
        int i = 0;
        size = check.length();      // Stores the size of the created number
        while (i < check.length())   // The loop checks whether or not every char in the string is either an int from 0-9 or a negative sign.
        {
            if ((check.charAt(i) == 45 && i == 0) || (check.charAt(i) >= 48 && check.charAt(i) <= 57)) //Notice that only the first char may be a negative
            {
                i++;
            } else {
                throw new IllegalArgumentException("Argument does not meet specifications of the constructor");
            }
        }

        if (size == 0 || (check.charAt(0) == 45 && size == 1)) //Deal with cases such as "" and "-"
        {
            throw new IllegalArgumentException("Illegal input, empty string ");
        }

        boolean allzero = true; //deals with case "00000" and "0"
        i = 0;
        if (check.charAt(0) == 48 || check.charAt(0) == 45 && check.charAt(1) == 48) {
            while (allzero) {
                if (check.charAt(i) != 48) //if we reach a number that isn't 0 we can leave this check
                {
                    break;
                }
                if (i == (size - 1)) // If we reach the end of the string and its all 0's, we let the num = 0 and the size is therefore = 1
                {
                    negative = false;
                    num = "0";
                    size = 1;
                    return; // The number has been created and the constructor can end
                }
                i++;

            }
        }
        if (check.charAt(0) == 45 && check.charAt(1) == 48) //deals with case "-00000" and "-0"
        {
            i = 1;
            while (allzero) {
                if (check.charAt(i) != 48) //if we reach a number that isn't 0 we can leave this check
                {
                    break;
                }
                if (i == (size - 1)) // If we reach the end of the string and its all 0's, we let the num = 0 and the size is therefore = 1
                {
                    negative = false;
                    num = "0";
                    size = 1;
                    return; // The number has been created and the constructor can end
                }
                i++;

            }
        }
        int start = 0;
        int end = 0;
        //The following will deal with cases like -0005365 and -2345235235
        if (check.charAt(0) == 45)//checks if first char is a negative and next char is 0
        {
            num = "";
            negative = true;
            end = 1;
            while (check.charAt(end) == 48) {

                end++;

            }
            for (start = size - 1; start >= end; start--) //starts at the end of string and copies
            {
                num += check.charAt(start);
            }
            size = num.length();
            return;
        }
        start = 0;
        end = 0;
        //The following will deal with cases like 0005365 and 4634636
        if (check.charAt(0) >= 48)//checks if first char is a negative and next char is 0
        {
            num = "";
            negative = false;
            end = 0;
            while (check.charAt(end) == 48) {

                end++;

            }
            for (start = size - 1; start >= end; start--) //starts at the end of string and copies
            {
                num += check.charAt(start);
            }
            size = num.length();
            return;
        }
    }


    public HugeInteger(int n) throws IllegalArgumentException {


        if (n >= 1) {
            int i;
            int random;
            Random rand = new Random();
            num = "";

            for (i = 0; i < n - 1; i++) {

                random = rand.nextInt(9); //used to deal with function outputting 0

                num += random;
            }
            num += rand.nextInt(8) + 1;
        } else {
            throw new IllegalArgumentException("n must be >=1");
        }
        size = num.length();
    }

    public HugeInteger add(HugeInteger h) {
        int i = 0;
        String add = "";
        int maxsize;
        int smallsize;
        int temp_add = 0;
        HugeInteger abscopy_this = new HugeInteger(this.toString());
        abscopy_this.negative = false;
        HugeInteger abscopy_h = new HugeInteger(h.toString());
        abscopy_h.negative = false;

        if (this.size > h.size) {
            maxsize = this.size;
            smallsize = h.size;

        } else if (this.size < h.size) {
            maxsize = h.size;
            smallsize = this.size;

        } else {
            maxsize = this.size;
            smallsize = this.size;
        }

        if (this.negative == false && h.negative == true) {
            HugeInteger plus_add_minus = this.subtract(abscopy_h);
            return  plus_add_minus;
            }
        if (this.negative == true && h.negative == false) {
            HugeInteger minus_add_plus = h.subtract(abscopy_this);
            return  minus_add_plus;
        }
        // The following code only adds 2 pos nums
            int carry = 0;
            for (i = 0; i < smallsize; i++) //addition up to size of small int
            {
                temp_add = (this.num.charAt(i) - 48) + (h.num.charAt(i) - 48);
                if (carry > 0) {
                    temp_add += carry;
                    carry = 0;
                }

                if (temp_add >= 10) {
                    carry = 1;
                    temp_add = temp_add % 10;
                }
                add += temp_add;
            }


            for (i = smallsize; i < maxsize; i++)//addition up to size of big int
            {
                if (this.size > h.size) {


                    temp_add = this.num.charAt(i) - 48;

                    if (carry > 0) {
                        temp_add += carry;
                        carry = 0;
                    }
                    if (temp_add >= 10) {
                        carry = 1;
                        temp_add = temp_add % 10;
                    }

                    add += temp_add;
                }
                if (this.size < h.size) {

                    temp_add = h.num.charAt(i) - 48;
                    if (carry > 0) {
                        temp_add += carry;
                        carry = 0;
                    }

                    if (temp_add >= 10) {
                        carry = 1;
                        temp_add = temp_add % 10;
                    }
                    add += temp_add;
                }
            }

            add += carry;

            String revstring = "";
            for (i = add.length() - 1; i >= 0; i--) {
                revstring += add.charAt(i);
            }

            HugeInteger addition = new HugeInteger(revstring);
            if (h.negative==true && this.negative==true)
            {
                addition.negative=true;
            }

            return addition;
        }


    public HugeInteger subtract(HugeInteger h) {
        int i = 0;
        String difference = "";
        int maxsize;
        int smallsize;
        int temp_sub = 0;
        int borrow = 0;
        HugeInteger abscopy_this = new HugeInteger(this.toString());
        abscopy_this.negative = false;
        HugeInteger abscopy_h = new HugeInteger(h.toString());
        abscopy_h.negative = false;
        if (this.size > h.size) {
            maxsize = this.size;
            smallsize = h.size;

        } else if (this.size < h.size) {
            maxsize = h.size;
            smallsize = this.size;

        } else {
            maxsize = this.size;
            smallsize = this.size;
        }



        if (this.compareTo(h) == 0) // If they are the same number, the difference is 0.
        {
            HugeInteger subtraction = new HugeInteger("0");
            return subtraction;
        }
        if (this.negative==false && h.negative==true)
        {
            HugeInteger plus_sub_minus = this.add(abscopy_h);
            return plus_sub_minus;
        }

        if (this.negative==true && h.negative==false)
        {
            HugeInteger minus_sub_plus = abscopy_this.add(h);
            minus_sub_plus.negative=true;
            return minus_sub_plus;
        }

        // The following code Deals with postive number subtraction and also neg - neg ops

        if (abscopy_this.compareTo(abscopy_h) == 1) {
            for (i = 0; i < smallsize; i++) //subtraction up to size of small int
            {
                temp_sub = (this.num.charAt(i) - 48) - (h.num.charAt(i) - 48);
                if (borrow > 0) {
                    temp_sub -= 1;
                    borrow = 0;
                }

                if (temp_sub < 0) {
                    borrow = 1;
                    temp_sub += 10;
                }
                difference += temp_sub;
            }
            for (i = smallsize; i < maxsize; i++)//addition up to size of big int
            {

                temp_sub = this.num.charAt(i) - 48;
                if (borrow > 0) {
                    temp_sub -= 1;
                    borrow = 0;
                }
                if (temp_sub < 0) {
                    borrow = 1;
                    temp_sub += 10;
                }
                difference += temp_sub;

            }
        }
        if (abscopy_this.compareTo(abscopy_h) == (-1)) {
            for (i = 0; i < smallsize; i++) //subtraction up to size of small int
            {
                temp_sub = (h.num.charAt(i) - 48) - (this.num.charAt(i) - 48);
                if (borrow > 0) {
                    temp_sub -= 1;
                    borrow = 0;
                }

                if (temp_sub < 0) {
                    borrow = 1;
                    temp_sub += 10;
                }
                difference += temp_sub;
            }
            for (i = smallsize; i < maxsize; i++)//addition up to size of big int
            {
                temp_sub = h.num.charAt(i) - 48;
                if (borrow > 0) {
                    temp_sub -= 1;
                    borrow = 0;
                }
                if (temp_sub < 0) {
                    borrow = 1;
                    temp_sub += 10;
                }
                difference += temp_sub;

            }
        }


        String revstring = "";
        for (i = difference.length() - 1; i >= 0; i--) {
            revstring += difference.charAt(i);
        }

        HugeInteger subtraction = new HugeInteger(revstring);
        if (this.compareTo(h)==1)
        {
            subtraction.negative=false;
        }
        if (this.compareTo(h)==-1)
        {
            subtraction.negative=true;
        }
        return subtraction;
    }

    public HugeInteger multiply(HugeInteger h)
    {
        String num_1 = "";
        String num_2="";
        int i=0;
        for (i = this.num.length() - 1; i >= 0; i--) {  //Reverses both numbers to be used in algorithm, any possible negatives are exlsuded
            num_1 += this.num.charAt(i);
        }
        for (i = h.num.length() - 1; i >= 0; i--) {
            num_2 += h.num.charAt(i);
        }

        if(this.num=="0"||h.num=="0")
        {
            HugeInteger prod = new HugeInteger("0");
            return prod;
        }


        int[] product = new int[this.num.length()+h.num.length()];
        for( i=num_1.length()-1;i>=0;i--){
            for(int j=num_2.length()-1;j>=0;j--){
                product[i+j+1] += (num_1.charAt(i)- 48) *(num_2.charAt(j) - 48) ;    //The indices will take care of the need to shift the sum by 1 every row.

            }
        }

        int carry = 0;
        for(i=product.length-1;i>=0;i--){ //They array is likely storing numbers greater than 10, need to move backwards through array to calculate the carry and add wherever appropriate
            int reduced_num = (product[i]+carry) % 10; //Modulus is used to find remainder and leave it in the slot
            carry = (product[i]+carry)/10; // Any tens digit is placed into carry and added to next slot
            product[i] = reduced_num;
        }



        String number = ""; //Number in array is converted into string
        for (i = 0; i <product.length; i++) {
           number += product[i];
        }

        HugeInteger prod = new HugeInteger(number);
        if (this.negative!=h.negative) //Adds a - if the signs do not match
        {
            prod.negative=true;
        }
        return prod;
    }


    public  int compareTo(HugeInteger h) {
        int i;
        if (this.num.equals(h.num) && (this.negative == h.negative)) //If the numbers are equal and both their signs are equal, they are they same.
        {
            return 0;
        }
        //will take care of nums with same num and sign

        if (this.negative == false && h.negative == true) //If this is positive, and h is negative, then this > h
        {
            return 1;
        }

        if (this.negative == true && h.negative == false) //If this is negative, and h is positive, then this < h
        {
            return -1;
        }

        // take care of diff signs

        //past this point assumes both are of same size
        if (this.negative == false && h.negative == false) //if both nums are positive and of different size, bigger num can be found
        {
            if (this.size > h.size) {
                return 1;
            }
            if (this.size < h.size) {
                return -1;
            }

            int this_num;
            int h_num;

            if (this.size == h.size) //If both are positive and of same size, we can move backwards to determine the bigger number
            {
                for (i = this.size - 1; i >= 0; i--) {
                    this_num = this.num.charAt(i) - 48;
                    h_num = h.num.charAt(i) - 48;
                    if (this_num > h_num) {
                        return 1;
                    } else if (this_num < h_num) {
                        return -1;
                    }
                }

            }
        }

        if (this.negative == true && h.negative == true) //If both nums are negative
        {
            if (this.size > h.size) {
                return -1;
            }
            if (this.size < h.size) {
                return 1;
            }

            int this_num;
            int h_num;

            if (this.size == h.size) //If both are negative and of same size, we can move backwards to determine the bigger number
            {
                for (i = this.size - 1; i >= 0; i--) {
                    this_num = this.num.charAt(i) - 48;
                    h_num = h.num.charAt(i) - 48;


                    if (this_num > h_num) {
                        return -1;
                    } else if (this_num < h_num) {
                        return 1;
                    }
                }

            }


        }
        return 0; //dummy return

    }

    public String toString() {
        String revstring = "";
        int i = 0;
        for (i = this.num.length() - 1; i >= 0; i--) {
            revstring += this.num.charAt(i);
        }
        if (this.negative == true) {
            revstring = "-" + revstring;
        }


        return revstring;
    }
}



