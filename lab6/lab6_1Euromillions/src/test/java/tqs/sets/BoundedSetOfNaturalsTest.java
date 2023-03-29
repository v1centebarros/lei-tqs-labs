/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(20);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
    }

    @Test
    void testAddElementTwice() {
        setA.add(99);
        assertThrows(IllegalArgumentException.class, () -> setA.add(99));
    }

    @Test
    void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};
        int[] duplicatedElems = new int[]{10,10,20,20};

        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));

        assertThrows(IllegalArgumentException.class, () -> setD.add(elems));

        assertThrows(IllegalArgumentException.class, () -> BoundedSetOfNaturals.fromArray(elems));

        assertThrows(IllegalArgumentException.class, () -> setD.add(duplicatedElems));

        assertThrows(IllegalArgumentException.class, () -> BoundedSetOfNaturals.fromArray(duplicatedElems));
    }


    @Test
    @DisplayName("Test intersection of two sets")
    void testIntersect() {
        BoundedSetOfNaturals result = setB.intersects(setC);
        assertEquals(2, result.size());
        assertTrue(result.contains(50));
        assertTrue(result.contains(60));
    }

}
