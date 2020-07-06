
public class BSTSet {
    private TNode root;

    public BSTSet() {
        root = null;
    }  //constructor 1

    public BSTSet(int[] input) { //constructor 2
        int i;

        root = new TNode(input[0], null, null);


        for (i = 1; i < input.length; i++) {

            this.add(input[i]);

        }
    }
// n or log(n)
    public void add(int v) { //adds V to the set as long as it does not already contain it
        if(isIn(v) == false)
        {
            TNode current_node = this.root; //node used to find where to add the element

            if(current_node == null) { //if empty tree then we will create a new root
                this.root = new TNode(v, null, null); //Adds root for empty tree
                return;
            }

            TNode place_holder = current_node; //Will hold the place of the node right before where we need to add
            while(current_node != null) { //run until we find where to add node
               place_holder = current_node;
                if(v > current_node.element) {
                    current_node =current_node.right;
                }
                else if(v < current_node.element) {
                    current_node = current_node.left;
                }
            }

            if (v >  place_holder.element) { //The placeholder is the last node right before null, will decide here where to add element
                place_holder.right = new TNode(v, null, null);
            }
            else if (v <  place_holder.element){
                place_holder.left = new TNode(v, null, null);
            }
        }
    }
//n or log(n)
    public boolean remove (int num) {

        if (isIn(num)==false) {
            return false;

        } else if (isIn(num)== true) {
            remove_helper(num, root);  //calls helper to use recursion to find and remove node
            return true;
        }
        return false;
    }


    private TNode remove_helper (int num, TNode x) {
        if (num > x.element) //move right if number is smaller
        {
            x.right = remove_helper(num, x.right);
        }
        else if (num < x.element) {
            x.left = remove_helper(num, x.left); //move left if number is small
        }
        else if (x.right != null && x.left != null) { //If deleting node with 2 childs
            x.element = (find_min(x.right));  //replace with smallest number in right subtree
            x.right = remove_min(x.right); //delete smallest node in right subtree

        }
        else {
//for case of 1 child
            if(x.left != null) //if left is not null, then we can replace it with whatever is there
            {x = x.left;}
            else {x = x.right;} //else we replace with the right side
        }
        return x; //redundant return

    }
//n or log n
    private int find_min(TNode a) {
        TNode current_node = a;

        while(current_node.left != null){
            current_node = current_node.left;            //move left to find lowest value
        }
        return current_node.element;
    }
    private TNode remove_min (TNode a) {
        TNode current_node = a;
        while(current_node.left != null){
            current_node = current_node.left;            //move left to find lowest value
        }
        return current_node.right;
    }

    public boolean isIn(int num) {
        TNode current_node=root; //simple traverse to find an element
        while (current_node != null) {
            if (current_node.element == num) {
                return true;
            } else if (num > current_node.element) {
                current_node = current_node.right;
            } else if (num < current_node.element) {
                current_node = current_node.left;
            }

        }
        return false;
    }


//theta 1
    private boolean isEmpty() {
        if (root == null) { //if root is null then set is obviously empty
            return true;
        } else {
            return false;
        }
    }
//theta 1
    public BSTSet union(BSTSet s) { // If any of the sets are empty while the other isnt, then our answer is simple
        if (this.isEmpty() == true) {
            BSTSet union = s;
            return union;
        } else if (s.isEmpty() == true) {
            BSTSet union = this;
            return union;
        } else {
            return this.real_union(s.root); //We add all the elements (excluding duplicates) of s to this and return
        }
    }
//n or log n
    private BSTSet real_union(TNode current_node) {
        BSTSet union = this;
        if (current_node != null) { //recursive traverse of tree, add all elements as we go
            union.add(current_node.element);
            union.real_union(current_node.right); //will run through root then all left nodes, then current, then all right
            union.real_union(current_node.left);
        }
        return union;
    }





    public int size () {

        return size_help(root);
    }

    private int size_help(TNode current_node) {
        if (current_node == null) {
            return 0;
        }
        else {

            int num = size_help(current_node.left) + 1 + size_help(current_node.right); //if null children, returns 1, parent will return 1 + what children return, hence giving size of tree
            return num;
        }
    }
    public int height()
    {
        if (isEmpty())
        {
            return -1;
        }
        else
             return height_help(root);
    }
private int height_help(TNode aNode){
        if (aNode == null) { //returns -1 since we start height at 0
            return -1;
        }

        int left_side = height_help(aNode.left); //very similar to size but since height starts from 0, we return -1 for lowest
        int right_side = height_help(aNode.right);

        if (left_side > right_side) { //if null children, will return 0, if parent gets 0, then it will return 1, effectively return height of tree
            return left_side + 1;
        } else { //returns whichever side is larger as height does not need to be same for all branches
            return right_side + 1;
        }
    }


    public void printBSTSet() {
        if (isEmpty() == true) {
            System.out.println("This set is empty");
        } else {
            System.out.print("The set elements are: ");
            printBSTSet(root);
            System.out.println();
        }
    }

    private void printBSTSet(TNode current_node) {
        if (current_node != null) {
            printBSTSet(current_node.left);
            System.out.print(" " + current_node.element + " ");
            printBSTSet(current_node.right);
        }
    }
//n^2
    public void printNonRec() { //in order printing of tree using stacks

        if(root == null) { //if empty then tell user
            System.out.println("This set is empty");
            return;
        }
        TNode temp = root;
        MyStack s = new MyStack();
        while(temp != null) { //push all left nodes first
            s.push(temp);
            temp = temp.left;
        }

        while(!s.isEmpty()) {
            TNode t = s.pop(); //when hit null, pop the node, print, and check if any nodes in left
            System.out.print(t.element + " ");
            if(t.right != null) {
                TNode tmp = t.right;  //if node exists in right, repeat, push all left nodes... etc etc
                while(tmp != null) {
                    s.push(tmp);
                    tmp = tmp.left;
                }
            }
        }

    }
    public BSTSet intersection(BSTSet k) { //Uses nonrecprint, instead of printing every node, we check if element belongs in k, if so then we add it to intersection set.
       BSTSet intersection = new BSTSet();
        if(this.isEmpty() || k.isEmpty()) {
            return intersection;
        }
        TNode temp = root;
        MyStack s = new MyStack();
        while(temp != null) {
            s.push(temp);
            temp = temp.left;
        }

        while(!s.isEmpty()) {
            TNode t = s.pop();

            if(k.isIn(t.element))
            {
                intersection.add(t.element);
            }

            if(t.right != null) {
                TNode tmp = t.right;
                while(tmp != null) {
                    s.push(tmp);
                    tmp = tmp.left;
                }
            }
        }
        return intersection;
    }
}

////////////////////////////////////////////// MyStack
import java.util.EmptyStackException;

public class MyStack { //code heavily referenced from lecture slides
    private SLLNode head;
    public MyStack() {
        head = null;
    }

    public boolean isEmpty() {
        return (head == null);
    }

    public void push(TNode e)
    {
        head = new SLLNode(e, head);
    }

    public TNode pop() throws EmptyStackException {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("**Underflow**");
        }
        else {
            TNode e = head.element;
            head = head.next;
            return e;
        }
    }

    public TNode top() throws IllegalArgumentException {
        if (isEmpty() == true) {
            throw new IllegalArgumentException("**Stack Underflow/ already empty**");
        } else {
            return (head.element);
        }
    }
}
///////////////////////////////////////// TNode
public class TNode {
    int element;
    TNode left;
    TNode right;
    TNode (int i, TNode l, TNode r)
    {
        element = i;
        left = l;
        right = r;
    }
}
///////////////////////////////////////// SLLNode

public class SLLNode {
    public TNode element;
    public SLLNode next;

    public SLLNode (TNode e, SLLNode n) {
        this.element = e;
        this.next = n;
    }
}

