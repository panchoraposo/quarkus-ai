package com.redhat.demo;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface BlogReaderService {
    
    @SystemMessage("You are an assistant that receives the body of an HTML page and sum up the article in that page. Add key takeaways to the end of the sum up.")
    @UserMessage("""
        The body will be sent as URL
        """)
    String prepare();

    @UserMessage("""
                Here's the URL:
                ```
                {url}
                ```                
            """)
    String sendBody(String url);

    @UserMessage("""
                That's it. You can sum up the article and add key takeaways to the end of the sum up.
            """)
    String sumUp();
}