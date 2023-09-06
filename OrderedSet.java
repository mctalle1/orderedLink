package orderedSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a concrete implementation of a <b>OrderedSet</b> using generic arrays.  It models the
 * mathematical <i>set</i> abstraction in which the set must not contain duplicates (objects with
 * a duplicate state determined by calling the <code>equals</code> method of each object element)
 * and that the values in the set must be ordered (determined using the compareTo method of each
 * object element.) lowest to highest. In the documentation the term 'set' will sometimes be used
 * to informally mean OrderedSet.
 * <br> 
 * <b>NOTE: The with an ORDEREDSET the elements must not be duplicates, and they must be ordered
 * lowest to highest, numerically or alphabetically.</b>
 *
 * @author Matthew Talle and Kevin Caverly
 * @version Spring 2023
 */


public class OrderedSet<R extends Comparable<R>> implements OrderedInterface<R>{
    private R[] data;
    public static final int DEFAULT_CAPACITY = 10;
    private boolean descending;
    private int numElements;

    public OrderedSet(int capacity)throws IllegalStateException{
        if(capacity < 0){
            throw new IllegalStateException();
        }
        this.data = (R[]) new Comparable[capacity];
        this.numElements = 0;
        this.descending = false;
    }

    public OrderedSet(){
        this(DEFAULT_CAPACITY);
    }

    public OrderedSet(Collection<R> collection, boolean descending)throws IllegalArgumentException{
        if(collection == null){
            throw new IllegalArgumentException();
        }
        else{
            this.data = (R[])new Comparable[collection.size()];
            addAll(collection);
            this.descending = descending;
            numElements = collection.size();
        }
    }
    /**
     * Clear function for the given set that iterates through the data and removes all items
     */ 
    public void clear(){
        for(int i = 0; i < numElements; i++ ){
            data[i] = null;
        }
        numElements = 0;
    }
    /**
     * Checks to see if the given set is empty
     * @return Returns a boolean indicating if the set is empty or not
     */
    public boolean isEmpty(){
        if(numElements == 0){
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * Gets the number of elements within the given set.
     * @return Returns an integer representing the number of items in the set.
     */
    public int size(){
        return numElements;
    }
    /**
     * Checks the capacity of the given set.
     * @return Returns an integer representing the capacity of the set.
     */
    public int capacity(){
        return this.data.length;
    }
    /**
     * Makes sure that there is enough room to fit new data in the set by making a copy 
     * @param size Size of the given set
     */
    protected void ensureCapacity(int size){
            R[] temp = (R[]) new Comparable[size];
            System.arraycopy(data, 0, temp, 0, numElements);

            this.data = temp;
        
    }
    /**
     * Get a reference to the element at the location specified by index
     * @param index An integer value of a specific index in the set
     * @return Returns the element at a given index
     * @throws NoSuchElementException 
     */
    public R get(int index) throws NoSuchElementException{
        if(index > numElements || numElements == 0){
            throw new NoSuchElementException();
        }
        R result = this.data[index];
        return result;
    }
    
    /**
     * Gets the index of the first element that matches input
     * @param input A reference to an item that is trying to be found within the set
     * @return Returns an integer representing the index at which the item was found or
     * -1 if the item wasn't contained within the set
     * @throws IllegalArgumentException
     */

     private int getPosition(Object input) throws IllegalArgumentException{
        int result = -1;
        if(input == null){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < numElements; i++){
            if(data[i].equals(input)){
                result = i;
            }
        }    
        return result;
    }
    /**
     * Adds the specified item to the set if it is not already in the set
     * @param element A specified element to be added to the set
     * @return Returns a boolean representing if the item was added or not
     * @throws IllegalArgumentException
     */
    public boolean add(R element) throws IllegalArgumentException{
        boolean result = false;
        if(element == null){
            throw new IllegalArgumentException();
        }
        if(this.contains(element) == false){
            if(this.capacity() == numElements){
                this.ensureCapacity(capacity()*2);
            }
            result = insertInOrder(element);
            numElements ++;
        }
        return result;
    }
    /**
     * Checks to see if the set contains a given item
     * @param item An object passed in to see if it's contained in the set
     * @return Returns a boolean true is the item is in the list and false otherwise
     * @throws IllegalArgumentException
     */
    public boolean contains(Object item) throws IllegalArgumentException{
        boolean result = false;
        if(item == null){
            throw new IllegalArgumentException();
        }
        else{
            for(int i = 0; i < numElements && !result; i++){
                if(data[i].equals(item)){
                    result = true;
                }
            }
        }
        return result;
    }
    /**
     * Inserts an element in it's correct spot based on alphetized order or numeric order
     * @param element the given element to insert into the list
     * @return Returns a boolean representing if the element was successfully added or not
     */
    private boolean insertInOrder(R element){
        boolean result = false;
        for(int i = 0; i < numElements; i++){
            if(this.data[i].compareTo(element) <= 0 && descending){
                makeSpace(i);
                this.data[i] = element;
                i = numElements;
                result = true;
            }
            if(this.data[i].compareTo(element) >= 0 && !descending){
                makeSpace(i);
                this.data[i] = element;
                i = numElements;
                result = true;
            }
        }
        if(result == false){
            this.data[numElements] = element;
            result = true; 
        }
        return result;
    }

    /**
     * Makes room in the set to add new items
     * @param index An integer representing the index at which it needs to make space
     */
    private void makeSpace(int index){
        if(this.capacity() == numElements){
            ensureCapacity(capacity()*2);
        }
        R[] newData = (R[]) new Comparable[this.data.length];
        for(int i = 0; i < index; i++){
            newData[i] = this.data[i];
        }
        for(int i = index + 1; i < newData.length; i++){
            newData[i] = this.data[i - 1];
        }
        newData[index] = null;
        this.data = newData;
    }
    /**
     * Adds all the elements of a set to this set in their respective order
     * @param collection A set that contains values to be added to this set
     * @return Returns a boolean true if the items were successfully added, false otherwise.
     * @throws IllegalArgumentException
     */
    public boolean addAll(Collection<? extends R> collection)throws IllegalArgumentException{
        boolean toReturn = true;
        int prevSize = numElements;
        for(R element : collection){
            add(element);
        }
        if(numElements == prevSize){
            toReturn = false;
        }
        return toReturn;
    }
    
    /**
     * Removes a specified item from the set
     * @param item The given item to remove from this set
     * @return Returns a boolean true if the item was successfully removed, false otherwise.
     * @throws IllegalArgumentException
     */
    public boolean remove(Comparable item)throws IllegalArgumentException{
        boolean result = false;
        if(item == null){
            throw new IllegalArgumentException();
        }
        int index = this.getPosition(item);
        if(index != -1){

            R[] newData = (R[]) new Comparable[this.data.length];
    
            for(int i = 0; i < index; i++){
                newData[i] = this.data[i];
            }
            for(int i = index; i < newData.length - 1; i++){
                newData[i] = this.data[i + 1];
            }
            this.data = newData;
            numElements -= 1;
            result = true;
        }
        
        return result;
    }
    
    /**
     * Removes all the elements from this set that are contained within the set that is passed in
     * @param collection This is the set of values that is trying to be removed from this set
     * @return Returns a boolean true if the items were successfully removed, false otherwise.
     * @throws IllegalArgumentException
     */
    public boolean removeAll(Collection<?> collection)throws NullPointerException{
        boolean result = false;
        if(collection == null){
            throw new NullPointerException();     
        }
 
        for(int i = 0; i < numElements; i++){
            if(collection.contains(data[i])){
                remove(data[i]);
                result = true;
            }
        } 
        return result; 
    }

    /**
     * Retains the elements contained within the given set in this set. 
     * @param collection The set being passed in with values to retain
     * @return Returns a boolean true if the items were successfully retained, false otherwise.
     * @throws IllegalArgumentException
     */
    public boolean retainAll(Collection<?> collection)throws IllegalArgumentException{
        boolean result = false;
        if(collection == null){
            throw new IllegalArgumentException();
        }
        int i = 0;
        while( i< numElements){
            if(!collection.contains(data[i])){
                remove(data[i]);
                result = true;
                i--;
            }
            
            i++;
            
        }       
        return result;
    }

    /**
     * Checks to see if this set contains all of the elements of the specified set.
     * @param collection The specified set to be passed in
     * @return Returns a boolean true if all the values are contained in this set, false otherwise.
     * @throws IllegalArgumentException
     */
    public boolean containsAll(Collection<?> collection) throws IllegalArgumentException{
        boolean result = false;
        int counter = 0;
        if(collection == null){
            throw new IllegalArgumentException();
        }
        
        for(Object data: collection){
            if(contains(data)){
                counter ++;
            }
        }

        if(counter == collection.size()){
            result = true;
        }
        return result;
    }
    /**
     * Compares items contained within both sets for equality
     * @param other The specified item(s) of which being compared.
     * @return Returns a boolean true if the object is a OrderedSet instance, the two sets are the
     * same size, and every member of the given set is contained within this set.
     */
    public boolean equals(Object other){
        boolean result = false; 
        if(other instanceof Collection){
            Collection<?> temp = (Collection<?>) other;
            if(this.size() == temp.size() && (this.isEmpty() && temp.isEmpty() || this.containsAll(temp))){
                result = true;
            }
        }
        return result;
    }

    /**
     * Print each item in the set by calling the toString of each element in the list and outputting 
     * the result to the console.
     */
    public void printList(){
        for(int i = 0; i < numElements; i++){
            System.out.println(data[i]);
        }
    }

    /**
     * A private helper method for swapping items in the array of data.
     * @param index1 Index of the first item to swap.
     * @param index2 Index of the second item to swap.
     */
    private void swap(int index1, int index2){
        R toSwap = data[index1];
        data[index1] = data[index2];
        data[index2] = toSwap;
    }

    /**
     * This function will return true if this set is descending 
     * and false if it is not descending.
     * @return True if descending, false if not descending.
     */
    public boolean isReversed(){
        boolean result = false;
        if(this.descending){
            result = true;
        }
        return result;
    }

    /**
     * Reverse the order of the set. If the set is in a lowest to highest 
     * (non descending) state, this function will put it into highest to lowest 
     * state, and reverse the order of the items in the set. At that point adding 
     * more items would maintain the highest to lowest order of the set. If the 
     * set is in a highest to lowest (descending) state state, calling this method
     *  would order the elements lowest to highest. Adding additional elements 
     * would then maintain the lowest to highest order.
     * 
     */
    public void reverse(){
        int size = numElements;
        R rev_Data;
        for(int i = 0; i < size/2 ; i++){
            rev_Data = data[i];
            data[i] = data[size - i - 1];
            data[size - i - 1] = rev_Data;
        }
        if(this.descending){
            this.descending = false;
        }
        else{
            this.descending = true;
        } 
    }

    
    /**
     * Constructs a string with elements of the OrderedSet in a specified format
     * @return Returns the constructed string representation in the form of <e,e,e>
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        for(int i = 0; i < numElements; i++){
            if(i != numElements - 1){
                sb.append(data[i].toString());
                sb.append(", ");   
            }
            else{
                sb.append(data[i].toString());
            }
        }
            sb.append(">");
            return sb.toString();
        }
    
    /**
     * Provides a method for traversing an array using a well-known Java interface.
     */
    public Iterator<R> iterator(){
        ArrayIterator iter = new ArrayIterator();
        return iter;
    }

    /** Simple class that models an interator for arrays. Note: as of Java 8 Iterator 
     * interface contains remove as a default method. */
    private class ArrayIterator implements Iterator<R>{
        /** location of the iterator */
        private int index;

        /** Create a new iterator at the start of the collection.*/
        public ArrayIterator(){
            index = 0;
        }

        /** Returns the next element of the collection. */
        public R next(){
            R nextData = data[index];
            index++;
            return nextData;
            
        }
        /** Determines if there are elements left in the set. */
        public boolean hasNext(){
            boolean result = false;
            if(index < numElements){
                result = true;
            }
            return result;
        }
    }
}
