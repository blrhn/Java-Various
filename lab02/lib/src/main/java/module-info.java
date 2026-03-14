import lib.person.parser.PersonFileParser;
import lib.person.parser.PersonFileTextRenderer;
import lib.person.reader.PersonFileReader;
import lib.person.stats.PersonStatsAnalyzer;

module lib {
    exports lib.person.model;
    exports lib.cache;
    exports lib.person.stats;
    requires spi;
    requires javafx.graphics;
    requires javafx.controls;

    provides spi.FileParser with PersonFileParser;
    provides spi.FileRenderer with PersonFileTextRenderer;
    provides spi.FileReader with PersonFileReader;
    provides spi.FileAnalyzer with PersonStatsAnalyzer;
}