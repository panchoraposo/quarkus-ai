package com.redhat.demo;

import io.quarkiverse.langchain4j.ollama.OllamaChatLanguageModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class OllamaConfig {

    @Produces
    @ApplicationScoped
    public OllamaChatLanguageModel createOllamaModel() {
        return OllamaChatLanguageModel.builder()
                .baseUrl("http://localhost:11434")
                .model("mistral")
                .build();
    }
}