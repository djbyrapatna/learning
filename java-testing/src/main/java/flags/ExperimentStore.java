package flags;
import java.util.Optional;

public interface ExperimentStore{
    void put(ExperimentConfig config);
    Optional<ExperimentConfig> get(String experimentKey);
}
