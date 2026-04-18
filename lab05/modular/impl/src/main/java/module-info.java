module impl {
    exports impl.utils;

    requires spi;

    provides ex.api.AnalysisService with impl.algs.KMeanAnalysis, impl.algs.KMedianAnalysis;
}