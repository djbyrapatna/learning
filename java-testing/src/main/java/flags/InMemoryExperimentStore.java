package flags;
import java.util.HashMap;
import java.util.Optional;

public class InMemoryExperimentStore implements ExperimentStore{
    
    private HashMap<String, ExperimentConfig> map;

    public InMemoryExperimentStore(){
        map = new HashMap<>();
    }


    @Override
    public void put(ExperimentConfig config){
        String key = config.getExperimentKey();
        map.put(key, config);
    }

    @Override  
    public Optional<ExperimentConfig> get(String experimentKey){
        if(map.containsKey(experimentKey)){
            ExperimentConfig config = map.get(experimentKey);
            return Optional.of(config);
        }
        return null;
    }
}
