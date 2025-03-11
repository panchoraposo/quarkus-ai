package com.redhat.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.ollama.OllamaChatLanguageModel;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogReaderService {

    private final OllamaChatLanguageModel model;

    public BlogReaderService(OllamaChatLanguageModel model) {
        this.model = model;
    }

    public String summarize(String url) throws IOException {
        // Obtener contenido de la URL
        Document doc = Jsoup.connect(url).get();
        String text = doc.select("p, h1, h2, h3, h4").stream()
                .map(Element::text)
                .collect(Collectors.joining("\n"));

        // Dividir texto en fragmentos de máximo 512 caracteres
        List<String> segments = splitText(text, 512);

        // Generar un resumen por cada segmento
        StringBuilder summary = new StringBuilder();
        for (String segment : segments) {
            List<ChatMessage> messages = List.of(
                new SystemMessage("Eres un asistente que resume artículos."),
                new UserMessage("Resume el siguiente texto:\n" + segment)
            );

            summary.append(model.generate(messages)).append("\n\n");
        }

        return summary.toString();
    }

    private List<String> splitText(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        int length = text.length();
        for (int i = 0; i < length; i += chunkSize) {
            chunks.add(text.substring(i, Math.min(length, i + chunkSize)));
        }
        return chunks;
    }

}