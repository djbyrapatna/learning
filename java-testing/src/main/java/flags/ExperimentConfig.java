package flags;

public class ExperimentConfig{
    private String experimentKey;
    private int treatmentPercent;

    private boolean validateExperimentKey(String key){
        if (key == null){
            return false;
        }
        return !key.isBlank();
    }

    private boolean validateTreatmentPercent(int percent){
        return (percent >=0 && percent <=100);
    }

    public ExperimentConfig(String experimentKey, int treatmentPercent){
        if (!validateExperimentKey(experimentKey)){
            throw new IllegalArgumentException("Null, blank, or invalid experiment key");
        }
        if (!validateTreatmentPercent(treatmentPercent)){
            throw new IllegalArgumentException("Treament percent out of range 0-100");
        }
        this.experimentKey = experimentKey;
        this.treatmentPercent = treatmentPercent;

    }

    public String getExperimentKey(){
        return experimentKey;
    }

    public int getTreatmentPercent(){
        return treatmentPercent;
    }

}
