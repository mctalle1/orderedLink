package orderedSet;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a concrete implementation of a Set using Links. It models the mathematical set abstraction 
 * in which the set must not contain duplicates (objects with a duplicate 
 * state determined by calling the Equals method of each object element) and that the values in the 
 * list must be ordered (determined using the compareTo method of each object element.) lowest to highest.
 * 
 * @author Matthew Talle and Kevin Caverly
 * @version Spring 2023
 */

public class OrderedLink<R extends Comparable<R>> implements OrderedInterface<R>{

    /** boolean representing whether the linked list is descending or ascending */
    private boolean descending;
    /** Represents the head node of this linked list based set */
    private Node head;
    /** The count of elements in this linked list */
    private int numElements;

    /** Create an OrderedLink */
    public OrderedLink(){
        this.descending = false;
        this.head = null;
        this.numElements = 0;
    }
    
    /**
     * Creates an 'descending' empty set.
     * @param descending if set is to be descending
     */
    public OrderedLink(boolean descending){
        this();
        this.descending = descending;
    }
    
    /**
     * Create a OrderedLink based on the values from any Collection.
     * @param collection collection whose data to be added to this OrderedLink
     * @param descending if set is to be descending
     * @throws IllegalArgumentException if collection is null
     */
    public OrderedLink(Collection<R> collection, boolean descending)throws IllegalArgumentException{
        this(descending);
        if(collection == null){
            throw new IllegalArgumentException();
        }
        addAll(collection);
    }

    /** Clear the set so that the are 0 elements. */
    public final void clear(){
        numElements = 0;
        this.head = null;
        
    }

    /** Return true if this set is empty.
     * @return boolean indicating if set is empty
     */
    public boolean isEmpty(){
        if(numElements == 0){
            return true;
        }
        return false;
    }

    /** Get the number of elements in this linked list. 
     * @return number of elements in this ordered link
    */
    public int size(){
        return this.numElements;
    }

    /** Print each item in the set. */
    public void printList(){
        for(R data:this){
            System.out.println(data);
        }
    }

    /** 
     * Display this class as a String.
     * @return elements in ordered link in displayable format
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        Node current = head;
        int count = 0;
        while(current != null){
            if(count != numElements - 1){
                sb.append(current.data.toString());
                sb.append(", ");
                count ++;
            }
            else{
                sb.append(current.data.toString());
            }
            current = current.next;
        }
        sb.append(">");
        return sb.toString();
    }

    /**
     * Get the item at the index position specified.
     * @param location index to retrieve an item from
     * @return the item from a particular index
     * @throws NoSuchElementException
     */
    public R get(int location)throws NoSuchElementException{
        if(location > numElements || numElements == 0){
            throw new NoSuchElementException();
        }
        Node current = head;
        for(int i = 0; i < location; i++){
            current = current.next;
        }
        return current.data;
    }

    /**
     * Return the index of the first object in the list which matches this object
     * @param input the object whose position to find
     * @return index of first object passed
     * @throws IllegalArgumentException
     */
    private int getPosition(Object input)throws IllegalArgumentException{
        if(input == null){
            throw new IllegalArgumentException();
        }
        Node current = head;
        int toReturn = 0;
        while(current != null){
            if(!current.data.equals(input)){
                toReturn ++;
                current = current.next;
            }
            else{
                current = null;
            }
        }
        if(toReturn > numElements){
            toReturn = -1;
        }
        return toReturn;
    }

    /** 
     * Add an item in order where it needs to go within the set
     * @return boolean indicating if item was added
     */
    public boolean add(R element){
       
        boolean toReturn = false;
        if(!contains(element)){
            insertInOrder(element);
            toReturn = true;
        }
        return toReturn;    
    }

    /**
     * A private method to insert an item in the correct position within the set. 
     * Does not check if item is already in the list.
     * @param element element to be inserted at the right order
     */
    private void insertInOrder(R element){
        Node newElement = new Node(element);

        /** checks if set is empty and directly inserts element */
        if(head == null){
            head = newElement;
        }

        /** 
         * checks if element is greater than the head node and 
         * inserts if ascending and vice versa */
        else if((!descending && element.compareTo(head.data) <= 0)||(descending && element.compareTo(head.data) >= 0)){
            newElement.next = head;
            head = newElement;
        }

        /** 
         * iterates through the list and inserts the element passed in the
         * right order depending if it is ascending or descending
         */
        else{
            Node current = head;
            boolean flag = true;
            Iterator<R> it = iterator();
            while(it.hasNext() && flag && current.next != null){
                if(!this.descending){
                    if(current.next.data.compareTo(element) >= 0){
                        newElement.next = current.next;
                        current.next = newElement;
                        flag = false;
                    }
                }
                else{
                    if(current.next.data.compareTo(element) <= 0){
                        newElement.next = current.next;
                        current.next = newElement;
                        flag = false;
                    }
                }
                current = current.next;
                it.next();
            }
            /** Adds element to the end of list if the least or greatest */
            if(flag){
                current.next = newElement;
            }
        }
    numElements++;
    }

    /** 
     * Add all items from another SetInterface implementation to this set provided 
     * the generic types are the same.
     * @return boolean indicating if all elements have been added
     * @param collection whose data to add all elements from
     */
    public boolean addAll(Collection<? extends R> collection)throws NullPointerException{
        boolean toReturn = true;
        if(collection == null){
            throw new NullPointerException();
        }
        int prevSize = numElements;
        for(R data : collection){
            add(data);
        }
        if(numElements == prevSize){
            toReturn = false;
        }
        return toReturn;
    }

    /** 
     * Determine if a specific object is in the list. This is determined 
     * by not just the memory location of input, but also by checking if they have the same state. 
     * @return boolean indicating if element passed is contained within this set
     * @param element to check if contained within this set
     * */
    public boolean contains(Object element)throws IllegalArgumentException{
        if(element == null){
            throw new IllegalArgumentException();
        }
        boolean toReturn = false;
        
         Iterator<R> it = iterator();
         while(it.hasNext() && !toReturn){
             if(it.next().equals(element)){
                 toReturn = true;
             }
         }
         return toReturn;
        }

    /** 
     * Returns true if this list contains all of the elements of the specified 
     * Collection instance. 
     * @return boolean
     * @param collection to check if all its data is contained within ours
     * */
    public boolean containsAll(Collection<?> collection)throws IllegalArgumentException{
        if(collection == null){
            throw new IllegalArgumentException();
        }
        boolean toReturn = true;
        for(R item:(Collection<R>)collection){
            if(!contains(item)){
                toReturn = false;
            }  
        }
        return toReturn;
    }

    /** 
     * Removes the specified element from this set if it is present. 
     * (This set will not contain the element once the call returns.) 
     * @param item to remove from set
     * @return boolean indicating if item was succesfully removed
     * */
    public boolean remove(Comparable item){
        boolean toReturn = false;
        Node current = head;

        int index = this.getPosition(item);
        if(index != -1){
            if(index == 0){
                head = head.next;
                numElements --;
            }
            else{

                for(int i = 0; i < index - 1; i++){
                    current = current.next;
                }
                if(current.next != null){
                    current.next = current.next.next;
                    numElements --;
                }
            }
            
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * Removes from this list all of its elements that are contained in the specified 
     * CollectionInterface instance. If the specified collection is also a set, this operation 
     * effectively modifies this list so that its value is the asymmetric set 
     * difference of the two sets.
     * @param collection whose data to remove all from in this set
     * @return boolean indicating if all items were removed successfully from set
     */
    public boolean removeAll(Collection<?> collection)throws NullPointerException{
        if(collection == null){
            throw new NullPointerException();
        }
        boolean toReturn = false;
        
        int prevSize = numElements;
        for(Object data:collection){
            remove(data);
        }
        if(numElements == prevSize){
            toReturn = false;
        }
        return toReturn;

    }
    
    /**
     * Retains only the elements in this set that are contained in the 
     * specified Collection instance. In other words, removes from this set all of its elements 
     * that are not contained in the specified collection. If the specified collection is also an set, 
     * this operation effectively modifies this set so that its value is the intersection of the two set.
     * @param collection whose elements to retain
     * @param boolean indicating if elements were succesfully retained
     */
    public boolean retainAll(Collection<?> collection)throws IllegalArgumentException{
        boolean toReturn = false;
        if(collection == null){
            throw new IllegalArgumentException();
        }
         Node current = head;
         while(current != null){
             if(!collection.contains(current.data)){
                 remove(current.data);
                 toReturn = true;
             }
             current = current.next;
         }
        return toReturn;
    }

    /**
     * Find a node at a specified position in the collection.
     * @param position to get node at
     * @return Node specified
     */
    private OrderedLink<R>.Node getNodeAt(int position){
        Node current = head;
        int count = 0;

        while(current != null){
            if(count != position){
                current = current.next;
                count ++;
            }
        }
        return current;
    }

    /**
     * Compares the specified object with this set for equality. Returns true if 
     * the specified object is also an OrderedInterface and the two set have the 
     * same size, and every member of the specified set is contained in this set
     * @param other the specified object to be compared
     * @return boolean indicating if items were equal or not
     */
    public boolean equals(Object other){
        boolean result = false;
        if(other instanceof OrderedLink){
            OrderedLink<R> temp = (OrderedLink<R>) other;
            if(this.size() == temp.size() && containsAll(temp) == true){
                result = true;
            }
        }
        return result;
    }
    /**
     * This function will return true if this set is descending and false if it is ascending.
     * @return boolean indicating if set is descending or ascending
     */
    public boolean isReversed(){
        boolean toReturn = false;
        if(this.descending){
            toReturn = true;
        }
        return toReturn;
    }

    /**
     * Reverse the order of the set. If the set is in a lowest to highest (non descending) 
     * state, this function will put it into highest to lowest state, and reverse the order 
     * of the items in the set. At that point adding more items would maintain the highest 
     * to lowest order of the set. If the set is in a highest to lowest (descending) state 
     * state, calling this method would order the elements lowest to highest. Adding 
     * additional elements would then maintain the lowest to highest order.
     */
    public void reverse() {
        
        Node start = head;
        Node temp = null;
        Node end = null;

        while(start!=null){
            temp=start.next;
            start.next = end;
            end = start;
            start = temp;
        }
        head = end;

        if(this.descending){
            this.descending = false;
        }
        else{
            this.descending = true;
        }
    }   

    /**
     * Return an iterator for this OrderedLink
     */
    public Iterator<R> iterator(){
        LinkIterator iter = new LinkIterator();
        return iter;
    }

    /**
     * An iterator implementation to get each successive item in the Iterator
     */
    private class LinkIterator implements Iterator<R>{

        /** The current node */
        private Node current;
        /** Creates a LinkIterator */
        private LinkIterator(){
            this.current = head;
        }
        
        /** Is there another element to access in this linked list?
         * @return true if the current node has a next, but false if the next is null.
         */
        public boolean hasNext(){
            return(current != null);
        }
    
        /** Get the next element in the linked list if one exists.
         * @return the data of the next node
         */
        public R next(){
            R data = current.data;
            current = current.next;
            return data;
        }
    }

    /** 
     * Simple class that models a through a linked list. 
     */
    private class Node{
        /** The data contained within the node */
        private R data;
        /** The next node in the list */
        private Node next;

        /**
         * Constructs a node
         * @param item data within node
         */
        private Node(R item){
            this.data = item;
            this.next = null;
        }
    }
    
}
