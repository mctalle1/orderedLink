package orderedSet;
import java.util.Collection;
/**
 * This is an interface which models a Collection that contains <b>no</b> duplicate elements. It
 * models the mathematical <i>set</i> abstraction. This interface is based on the Java
 * <code>Set</code> interface, but there are some important differences. including the addition of
 * merge and unMerge functionality.
 *
 * No constructor will construct a set containing duplicate elements.  Elements should be added in
 * the in order from highest to lowest, or lowest to highest, depending on the state of the
 * object.
 *
 * <b> Parameters in OrderedSet and OrderedLink should have good descriptive names!</b>
 *
 * @author William Kreahling
 * @version 2023
 */
public interface OrderedInterface<Q extends Comparable<? super Q>> extends Collection<Q> {

    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element q to this set if the set contains no element q2
     * such that q != null &amp;&amp; q.equals(q2). If this set already contains the element,
     * the call leaves the set unchanged and returns false. In combination with the restriction on
     * constructors, this ensures that sets never contain duplicate elements. Will insert elements
     * from highest to lowest, or lowest to higest according to the current state of the
     * Collection.
     * @param q element to be added to this set.
     * @return true if the set did not already contain the element.
     * @throws IllegalArgumentException if the element to be added is null.
     */
    public boolean add(Q q);
    /**
     * Clears all data from this set.
     */
    public void clear();
    /**
     * Determine if a specific object is in the set.
     * @param o object to search for
     * @return true if the object is in the set, false otherwise.
     * @throws IllegalArgumentException generated if the object to search for is null.
     */
    public boolean contains(Object o);
    /**
     * Returns true if this set contains all of the elements of the specified collection. If the
     * specified collection is also a set, this method returns true if it is a subset of this set.
     * @param c collection to be checked for containment in this set.
     * @return true if this set contains all the elements of the specified collection.
     * @throws IllegalArgumentException If the specified collection is null or contains any null
     * elements.
     */
    public boolean containsAll(Collection<?> c);
    /**
     * Determine if this set contains no elements.
     * @return true if this set contains no elements, false otherwise.
     */
    public boolean isEmpty();
    /**
     * Removes the specified element from this set if it is present. (This set will not contain
     * the element once the call returns.)
     * @param item item to remove from the set
     * @return true if this set contained the element (or equivalently, if this set changed as a
     * result of the call). 
     * @throws IllegalArgumentException if the specified object is null or not Comparable. Note
     * this is needed for compatabolity with the Collection&lt;Q&gt; interface.
     */
    public default boolean remove(Object item) {
        if(item == null || !(item instanceof Comparable)) {
            throw new IllegalArgumentException("Cannot remove nothing from the set");
        }
        return remove(item);
    }
    /**
     * Removes the specified element from this set if it is present. (This set will not contain
     * the element once the call returns.)
     * @param element to remove from this set
     * @return true if this set contained the element (or equivalently, if this set changed as a
     * result of the call). 
     * @throws IllegalArgumentException if the specified object is null.
     */
    public boolean remove(Comparable element);

    /**
     * Retains only the elements in this set that are contained in the specified collection.
     * In other words, removes from this set all of its elements that are
     * not contained in the specified collection. If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the intersection of the two
     * sets. Ordering must be maintained.
     * @param c collection containing elements to be retained in this set.
     * @return true if this set is changed because of this call, false otherwise.
     * @throws IllegalArgumentException if the specified collection is null.
     */
    public boolean retainAll(Collection<?> c);
    
    /**
     * Returns the number of elements in this set (its cardinality).
     * @return the number of elements in this set (its cardinality).
     */
    public int size();

    /**
     * Print each item in the set by calling the toString of each
     * element in the list and outputting the result to the console.
     */
    public void printList();


    /** 
     * Reverse the order of the set. If the set is in a lowest to highest (non reversed) state,
     * this function will put it into highest to lowest state, and reverse the order of the
     * items in the set. At that point adding more items would maintain the highest to lowest
     * order of the set. If the set is in a highest to lowest (reversed) state state, calling this
     * method would order the elements lowest to highest. Adding additional elements would then
     * maintain the lowest to highest order. 
     */
    public void reverse();


    /**
     * This function will return true if this set is reversed and false if it is not reversed.
     * @return true if reversed, false if not reversed.
     */
    public boolean isReversed();

    /**
     * This operation is not supported in OrderedSet/OrderedLink
     * @throws UnsupportedOperationException operation is not supported.
     */
    public default Object[] toArray() {
        throw new UnsupportedOperationException("toArray is not supported for Program 3");
    }
    
    /**
     * This operation is not supported in OrderedSet/OrderedLink
     * @throws UnsupportedOperationException operation is not supported.
     */
    public default <T> T[] toArray(T[] array) {
        throw new UnsupportedOperationException("toArray is not supported for Program 3");
    }
}
