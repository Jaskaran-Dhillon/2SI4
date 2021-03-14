public class MaxHeap {
    private Integer[] heap;
    private int arraysize;
    private int heapsize;

    public MaxHeap(int s) {
       arraysize = s;
       heap = new Integer[s];
    }

    public MaxHeap(Integer[] someArray) {
        arraysize = someArray.length;
        heap = new Integer[arraysize];

        for (int i : someArray) { //add all elements from array into heap
            insert(i);
        }
    }

    public void insert(int n) {
        if (arraysize == heapsize) //if the heap is full, must double the size of the heap
           { Integer[] tempHeap = new Integer[2*arraysize];
               arraysize *= 2;

               for (int i = 0; i < heap.length; i++) { //duplicate the current heap into the larger one
                   tempHeap[i] = heap[i];
               }
               heap = tempHeap;
           }


        int i = heapsize;
        heap[i] = n; //insert into next available slot

        heapsize++;
        while (true) { //children nodes must be smaller than parent, keep swapping
            //(i - 1) / 2 is the eqn used to get parent of node at i
            if (heap[(i - 1) / 2] < n) { //if parent is less than n, swap

                int temp = heap[(i - 1) / 2];//swap
                heap[(i - 1) / 2] = n;
                heap[i] = temp;

                i = (i - 1) / 2; //index moves to parent so we can traverse up
            }
            else { //parent is bigger than n, ideal spot is reached so stop loop
               break;
            }
        }


    }
    public Integer deleteMax() {
        int max = heap[0]; //max element to return is at 0
        heap[0] = heap[heapsize - 1];//get the smallest elem on lowest level
        heap[heapsize - 1]=null;//must make deleted element = null
        heapsize--;

        //fix the heap order
        heap_fix(heap, 0);


        return max; //return the max value
    }
    private void heap_fix(Integer arr[], int i)
    {
        int largest = i; 
        int left = (2*i) + 1; 
        int right = (2*i) + 2; 

       
        if (left < heapsize && arr[left] > arr[largest])
            largest = left;

        
        if (right < heapsize && arr[right] > arr[largest])
            largest = right;

       
        if (largest != i) {
            int temp_swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp_swap;

          
            heap_fix(arr, largest);
        }
    }
    public static void heapsort(Integer[] arrayToSort) {
        MaxHeap temp = new MaxHeap(arrayToSort); //create heap from array, delete max back into array
        int size_final = temp.heapsize; //the heap-size will constantly change so we must use temp variable to hold max # of runs
        for (int i = 0; i < size_final; i++) {
            arrayToSort[i] = temp.deleteMax(); //0->n will contain the heap values in descending order, all old elements are over-written
        }
    }
    public String toString() {
        String heap_output = "";
        for (int i = 0; i < heapsize; i++) {
            heap_output += (heap[i] + ", ");
        }
        return  heap_output;
    }

    public int getCapacity() {
        return arraysize;
    }
    public int getSize() {
        return heapsize;
    }

}
