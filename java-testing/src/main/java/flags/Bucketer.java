package flags;

public interface Bucketer{
    int bucket(String experimentKey, String userId, String salt, int modulo);
}
