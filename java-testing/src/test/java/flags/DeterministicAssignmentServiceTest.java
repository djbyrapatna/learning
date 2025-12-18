package flags;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.SecureRandom;

public class DeterministicAssignmentServiceTest {

    private static final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static String randomAlphaNumeric(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be non-negative");
        }
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RANDOM.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(idx));
        }
        return sb.toString();
    }

    @Test
    void experiment_configs_validated_correctly(){
        assertThrows(IllegalArgumentException.class, 
            ()->{new ExperimentConfig(null, 10);} );
        assertThrows(IllegalArgumentException.class, 
            ()->{new ExperimentConfig(" ", 10);} );
        assertThrows(IllegalArgumentException.class, 
            ()->{new ExperimentConfig("abc", -1);} );
        assertThrows(IllegalArgumentException.class, 
            ()->{new ExperimentConfig("abc", 101);} );
        assertDoesNotThrow(()->{new ExperimentConfig("abc", 80);});
        assertDoesNotThrow(()->{new ExperimentConfig("abc", 0);});
        assertDoesNotThrow(()->{new ExperimentConfig("abc def", 0);});

    }

    @Test
    void inmemory_experimentstore_validated_correctly(){
        ExperimentConfig config1 = new ExperimentConfig("abc", 80);
        ExperimentConfig config2 = new ExperimentConfig("def", 30);
        ExperimentConfig config3 = new ExperimentConfig("a123xy", 35);
        InMemoryExperimentStore imstore = new InMemoryExperimentStore();
        imstore.put(config1);
        imstore.put(config2);
        imstore.put(config3);
        assertEquals(imstore.get("abc").get(), config1);
        assertEquals(imstore.get("def").get(), config2);
        assertEquals(imstore.get("a123xy").get(), config3);
        assertTrue(imstore.get("xyz").isEmpty());
    }


    @Test
    void determ_hash(){
        ExperimentConfig config1 = new ExperimentConfig("abc", 80);
        ExperimentConfig config2 = new ExperimentConfig("def", 30);
        ExperimentConfig config3 = new ExperimentConfig("a123xy", 50);
        InMemoryExperimentStore imstore = new InMemoryExperimentStore();
        imstore.put(config1);
        imstore.put(config2);
        imstore.put(config3);
        String uid1 = "dhruvajb";
        String uid2 = "chandanak98";
        DeterministicAssignmentService service = new DeterministicAssignmentService(imstore);
        Variant var1 = service.assign("abc", uid1);
        Variant var2 = service.assign("a123xy", uid1);
        Variant var3 = service.assign("a123xy", uid2);
        for(int i = 0; i < 10; i++){
            Variant var1Test = service.assign("abc", uid1);
            Variant var2Test = service.assign("a123xy", uid1);
            Variant var3Test = service.assign("a123xy", uid2);
            assertEquals(var1, var1Test);
            assertEquals(var2, var2Test);
            assertEquals(var3, var3Test);
            
        }

    }

    @Test
    void boundary_condition_check(){
        ExperimentConfig config0 = new ExperimentConfig("h", 0);
        ExperimentConfig config100 = new ExperimentConfig("03$$$mg", 100);
        InMemoryExperimentStore imstore = new InMemoryExperimentStore();
        imstore.put(config0);
        imstore.put(config100);
        DeterministicAssignmentService service = new DeterministicAssignmentService(imstore);
        for(int i=0; i<10000; i++){
            String uid = randomAlphaNumeric(10);
            Variant var0 = service.assign("h", uid);
            Variant var100 = service.assign("03$$$mg", uid);
            assertEquals(Variant.CONTROL, var0);
            assertEquals(Variant.TREATMENT, var100);
            
        }
        


    }


    
}
