package com.springboot.myapp.service;

import com.springboot.myapp.dto.OllamaResponse;
import com.springboot.myapp.dto.PromptDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {
    private final ChatClient chatClient;

    // initializing this chat client without any context
    public OllamaService(ChatClient.Builder builder ){
        chatClient= builder
                .defaultSystem("""
        You are a senior backend spring boot expert.
        Explain concepts clearly with real world examples.
        Give small precise answers.
        """)
                .build();
    }

    //Flux<String>
    public OllamaResponse chat(PromptDto promptDto) {
        // (try this first) return  new OllamaResponse("working");
        return new OllamaResponse(chatClient
                .prompt()
                .user(promptDto.prompt())
                .call()
                //.stream()
                .content());
    }
}
