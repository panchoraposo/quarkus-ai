package com.redhat.demo;

import dev.langchain4j.data.pdf.PdfFile;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.PdfUrl;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class)
public interface LeaseAnalyzer {

    @UserMessage("Analyze the given document")
    LeaseReport analyze(@PdfUrl PdfFile pdfFile);
    
}