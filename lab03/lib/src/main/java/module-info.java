module lib {
    exports lib.client;
    exports lib.client.model.dto;

    requires tools.jackson.databind;
    requires java.net.http;

    opens lib.client.model.dto to tools.jackson.databind;
    exports lib.quiz;
    exports lib.quiz.model;
    exports lib.quiz.generator;
}