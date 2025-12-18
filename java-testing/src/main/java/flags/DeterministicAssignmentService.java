package flags;


import java.util.Map;
import java.util.HashMap;
import java.util.Optional;



public class DeterministicAssignmentService implements AssignmentService{

    private final ExperimentStore store;
    private final Bucketer bucketer;

    private int deterministicHash(String experimentKey, String userId){
        String hashString = experimentKey + userId;
        int positiveHashCode = hashString.hashCode() & 0x7fffffff;
        int percentHash = positiveHashCode % 100;

        return percentHash;
    }

    private Variant determineVariant(ExperimentConfig config, int bucket, int totalWeight){
        Map<Variant, Integer> weights = config.getWeights();

        int controlWeight = weights.get(Variant.CONTROL);
        if (bucket < controlWeight){
            return Variant.CONTROL;
        } 
        
        int treatmentWeight = weights.get(Variant.TREATMENT);
        if (bucket < controlWeight + treatmentWeight){
            return Variant.TREATMENT;
        } 
        
        if(weights.containsKey(Variant.TREATMENT_2)){
            int optionalWeight = weights.get(Variant.TREATMENT_2);
            if (bucket < controlWeight + treatmentWeight + optionalWeight){
                return Variant.TREATMENT_2;
            }
            else{
                throw new IllegalStateException("Bucket value exceeds total weight");
            }
        }
        else{
            throw new IllegalStateException("Bucket value exceeds total weight");
        }

            
    }
        
    

    public DeterministicAssignmentService(ExperimentStore store, Bucketer bucketer){
        this.store = store;
        this.bucketer = bucketer;
    }




    public Variant assign(String experimentKey, String userId){
        if (experimentKey.isBlank()){
            throw new InvalidInputException("Experiment key cannot be null or blank");
        }
        if (userId.isBlank()){
            throw new InvalidInputException("User id cannot be null or blank");
        }
        Optional<ExperimentConfig> configOpt = store.get(experimentKey);
        if (configOpt==null || !configOpt.isPresent()){
            throw new UnknownExperimentException("Experiment key "+experimentKey+" does not correspond to a valid experiment");
        }
        ExperimentConfig config = configOpt.get();

        String salt = config.getSaltString();
        int modulo = config.getTotalWeight();

        int bucket = bucketer.bucket(experimentKey, userId, salt, modulo);

        return determineVariant(config, bucket, modulo);

        
    }
}
