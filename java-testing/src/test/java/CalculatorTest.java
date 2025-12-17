import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

public class CalculatorTest{

    @Test
    void add_TwoNumbers_returnsSum(){
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2,3));
    }

    @Test
    void divide_byzero_throwsexceptions(){
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, ()->{calc.divide(0,10);});
    }
}
