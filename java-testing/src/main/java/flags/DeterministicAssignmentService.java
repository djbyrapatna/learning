package flags;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

public class DeterministicAssignmentService implements AssignmentService{

    private final ExperimentStore store;

    private int deterministicHash(String experimentKey, String userId){
        String hashString = experimentKey + userId;
        int positiveHashCode = hashString.hashCode() & 0x7fffffff;
        int percentHash = positiveHashCode % 100;

        return percentHash;
    }


    public DeterministicAssignmentService(ExperimentStore store){
        this.store = store;
    }

    public Variant assign(String experimentKey, String userId){
        if (experimentKey.isBlank()){
            throw new IllegalArgumentException("Experiment key cannot be null or blank");
        }
        if (userId.isBlank()){
            throw new IllegalArgumentException("User id cannot be null or blank");
        }
        Optional<ExperimentConfig> configOpt = store.get(experimentKey);
        if (configOpt.isEmpty()){
            throw new IllegalArgumentException("Experiment key "+experimentKey+" does not correspond to a valid experiment");
        }
        ExperimentConfig config = configOpt.get();

        int treatmentPercent = deterministicHash(experimentKey, userId);

        if (treatmentPercent < config.getTreatmentPercent()){
            return Variant.TREATMENT;
        } else{
            return Variant.CONTROL;
        }

        
    }
}
