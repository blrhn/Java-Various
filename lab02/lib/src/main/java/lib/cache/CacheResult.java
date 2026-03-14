package lib.cache;

// U - ProcessedPersonsData

public record CacheResult<U>(
        U processedData,
        boolean loadedFromCache
) {}
