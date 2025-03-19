package com.redhat.demo;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class)
public interface LeaseAnalyzer {

    @UserMessage("Extract key details from the following lease agreement text")
    LeaseReport analyze(String leaseText);
    
}