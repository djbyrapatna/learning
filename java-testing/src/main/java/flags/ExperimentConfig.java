package flags;
import java.util.HashMap;
import java.util.Map;

public class ExperimentConfig{
    private String experimentKey;
    private Map<Variant, Integer> weights;
    private String salt;
    //private int treatmentPercent;

    private boolean validateString(String testString){
        if (testString == null){
            return false;
        }
        return !testString.isBlank();
    }

    private boolean validateTreatmentPercent(int percent){
        return (percent >=0 && percent <=100);
    }

    private boolean validateWeights(Map<Variant, Integer> weights){
        for (Integer weight : weights.values()){
            if (weight < 0){
                return false;
            }
        }
        return true;
        
    }

    public ExperimentConfig(String experimentKey, Map<Variant, Integer> weights, String salt){
        if (!validateString(experimentKey)){
            throw new InvalidInputException("Null, blank, or invalid experiment key");
        }
        if (!validateWeights(weights)){
            throw new InvalidInputException("One or more weights are below zero");
        }
        if (!validateString(salt)){
            throw new InvalidInputException("Null, blank, or invalid salt string");
        }
        this.experimentKey = experimentKey;
        this.weights = weights;
        this.salt = salt;

    }

    public String getExperimentKey(){
        return experimentKey;
    }

    public String getSaltString(){
        return salt;
    }

    public Map<Variant, Integer> getWeights(){
        return weights;
    }

    public int getTotalWeight(){
        int total = 0;
        for (int weight: weights.values()){
            total+=weight;
        }
        return total;
    }
    

}
