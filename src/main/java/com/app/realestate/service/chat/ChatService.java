package com.app.realestate.service.chat;

import com.app.realestate.model.LLMPrompt;
import com.app.realestate.tools.SearchTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private OllamaApi ollamaAPI;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private SearchTools searchTools;


    public void populateDataCache() {
        // populates the data cache upon initialization...
        // Not being utilized yet
    }

    public List<OllamaApi.Model> listModels() {
        OllamaApi.ListModelResponse response = ollamaAPI.listModels();
        return response.models();
    }

    public String llmChat(LLMPrompt llmPrompt) {
        logger.info("Prompt string: {}", llmPrompt.getUserPrompt());

        return chatClient.prompt()
                .user(llmPrompt.getUserPrompt())
                .call()
                .content();
    }

    public String agentFindHouses(LLMPrompt llmPrompt) {
        String systemPrompt = llmPrompt.getSystemPrompt();
        if (systemPrompt == null || systemPrompt.isEmpty()) {
            systemPrompt = "You are a real estate agent. " +
                            "Use the available tools to find houses based on what the user provides. " +
                            "If the user provides a type only, use findHousesByType. " +
                            "If the user provides both a type and a zip code, use findHousesByTypeAndZipCode. " +
                            "If the user provides a property ID, use findHouseByPropertyId. " +
                            "If the user asks for all houses with no filter, use findAllHouses. Also, " +
                            "don't replace _ with - in the value of type field";
        }

        String response = ChatClient.create(chatModel)
                .prompt()
                .system(systemPrompt)
                .user(llmPrompt.getUserPrompt())
                .tools(searchTools)
                .call()
                .content();

        logger.info("Response in controller: {}", response);

        return response;
    }
}
