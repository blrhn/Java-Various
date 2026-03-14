package lib.cache;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

// T - File
// U - ProcessedPersonData

public class WeakRefCache<T, U> {
    private final Map<T, WeakReference<U>> cache = new WeakHashMap<>();

    public CacheResult<U> getOrCompute(T file, Supplier<U> compute) {
        WeakReference<U> ref = cache.get(file);
        U data = ref == null ? null : ref.get();

        if (data != null) {
            return new CacheResult<>(data, true);
        }

        data = compute.get();
        cache.put(file, new WeakReference<>(data));

        return new CacheResult<>(data, false);
    }
}
