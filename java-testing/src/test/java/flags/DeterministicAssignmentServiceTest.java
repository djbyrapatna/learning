package flags;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.SecureRandom;
import java.util.Map;
import java.util.HashMap;


class FakeBucketer implements Bucketer{
    @Override
    public int bucket(String experimentKey, String userId, String salt, int modulo){
        if(experimentKey=="exp1"){
            return 10 % modulo;
        } else if (experimentKey=="user2"){
            return 90 % modulo;
        } else{
            return 50 % modulo;
        }
    }
}




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
        HashMap<Variant, Integer> valid_weights = new HashMap<>();
        valid_weights.put(Variant.CONTROL, 7);
        valid_weights.put(Variant.TREATMENT, 2);
        valid_weights.put(Variant.TREATMENT_2, 1);
        String valid_salt = "random_salt_123";

        assertThrows(InvalidInputException.class, 
            ()->{new ExperimentConfig(null, valid_weights, valid_salt);} );
        assertThrows(InvalidInputException.class, 
            ()->{new ExperimentConfig(" ", valid_weights, valid_salt);} );
        
        HashMap<Variant, Integer> negative_weights = new HashMap<>();
        negative_weights.put(Variant.CONTROL, 7);
        negative_weights.put(Variant.TREATMENT, -1);
        assertThrows(InvalidInputException.class, 
            ()->{new ExperimentConfig("abc", negative_weights, valid_salt);} );
        
        assertThrows(InvalidInputException.class, 
            ()->{new ExperimentConfig("abc", valid_weights, null);} );
        assertDoesNotThrow(()->{new ExperimentConfig("abc", valid_weights, valid_salt);});
        

    }

    @Test
    void inmemory_experimentstore_validated_correctly(){
        HashMap<Variant, Integer> valid_weights = new HashMap<>();
        valid_weights.put(Variant.CONTROL, 7);
        valid_weights.put(Variant.TREATMENT, 2);
        valid_weights.put(Variant.TREATMENT_2, 1);
        String valid_salt = "random_salt_123";

        ExperimentConfig config1 = new ExperimentConfig("abc", valid_weights, valid_salt);
        ExperimentConfig config2 = new ExperimentConfig("def", valid_weights, valid_salt);
        ExperimentConfig config3 = new ExperimentConfig("a123xy", valid_weights, valid_salt);
        InMemoryExperimentStore imstore = new InMemoryExperimentStore();
        imstore.put(config1);
        imstore.put(config2);
        imstore.put(config3);
        assertEquals(imstore.get("abc").get(), config1);
        assertEquals(imstore.get("def").get(), config2);
        assertEquals(imstore.get("a123xy").get(), config3);
        assertTrue(imstore.get("xyz")==null);
    }


    @Test
    void determ_hash(){
        HashMap<Variant, Integer> valid_weights = new HashMap<>();
        valid_weights.put(Variant.CONTROL, 7);
        valid_weights.put(Variant.TREATMENT, 2);
        valid_weights.put(Variant.TREATMENT_2, 1);
        String valid_salt = "random_salt_123";

        ExperimentConfig config1 = new ExperimentConfig("abc", valid_weights, valid_salt);

        HashMap<Variant, Integer> valid_weights_2 = new HashMap<>();
        valid_weights_2.put(Variant.CONTROL, 5);
        valid_weights_2.put(Variant.TREATMENT, 5);
        //valid_weights.put(Variant.TREATMENT_2, 1);
        String valid_salt_2 = "AHHHHHHHHHHHHHHHHH";

        ExperimentConfig config2 = new ExperimentConfig("def", valid_weights_2, valid_salt_2);
        InMemoryExperimentStore imstore = new InMemoryExperimentStore();
        imstore.put(config1);
        imstore.put(config2);
 
        String uid1 = "dhruvajb";
        String uid2 = "chandanak98";
        
        DeterministicAssignmentService service = new DeterministicAssignmentService(imstore, new Sha256Bucketer());
        Variant var1 = service.assign("abc", uid1);
        assertThrows(UnknownExperimentException.class,
            ()->{service.assign("a123xy", uid1);});
        Variant var2 = service.assign("def", uid1);
        Variant var3 = service.assign("def", uid2);
        
        for(int i = 0; i < 10; i++){
            Variant var1Test = service.assign("abc", uid1);
            Variant var2Test = service.assign("def", uid1);
            Variant var3Test = service.assign("def", uid2);
            assertEquals(var1, var1Test);
            assertEquals(var2, var2Test);
            assertEquals(var3, var3Test);
        }

        String [] uidStrings = {"userA", "userB", "userC", "userD", "userE", "userF", "userG", "userH", "userI", "userJ", 
                                "userK", "userL", "userM", "userN", "userO", "userP", "userQ", "userR", "userS", "userT"
        };

        Variant [] variants = new Variant[20];
        for (int i=0; i<20; i++){
            variants[i] = service.assign("abc", uidStrings[i]);
        }


        ExperimentConfig config3 = new ExperimentConfig("def", valid_weights_2, valid_salt_2+"modified");
        imstore.put(config3);
        DeterministicAssignmentService newService = new DeterministicAssignmentService(imstore, new Sha256Bucketer());

        boolean allSame = true;

        for (int i=0; i<20; i++){
            Variant varTest = newService.assign("def", uidStrings[i]);
            allSame = allSame && (varTest == variants[i]);
        }

        assertFalse(allSame);




    }

    @Test
    void boundary_condition_check(){
        HashMap<Variant, Integer> valid_weights = new HashMap<>();
        valid_weights.put(Variant.CONTROL, 10);
        valid_weights.put(Variant.TREATMENT, 0);
        valid_weights.put(Variant.TREATMENT_2, 0);
        String valid_salt = "random_salt_123";
        ExperimentConfig config0 = new ExperimentConfig("h", valid_weights, valid_salt);
        
        InMemoryExperimentStore imstore = new InMemoryExperimentStore();
        imstore.put(config0);

        DeterministicAssignmentService service = new DeterministicAssignmentService(imstore, new Sha256Bucketer());
        for(int i=0; i<10000; i++){
            String uid = randomAlphaNumeric(10);
            Variant var0 = service.assign("h", uid);
            assertEquals(Variant.CONTROL, var0);
        }
        

    }


    
}
