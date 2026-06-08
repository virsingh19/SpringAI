package com.app.realestate.controller.chat;

import com.app.realestate.model.LLMPrompt;
import com.app.realestate.service.chat.ChatService;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping(value = "v1/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;


    @GetMapping("/list-models")
    public ResponseEntity<List<OllamaApi.Model>> listModels() {
        List<OllamaApi.Model> response = chatService.listModels();
        return ResponseEntity.ok(response);
    }

    @PostMapping ("/simple")
    public ResponseEntity<String> chat(@RequestBody LLMPrompt llmPrompt) {
        String response = chatService.llmChat(llmPrompt);
        logger.info("Chat response: {}", response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/houses")
    public ResponseEntity<String> toolChat(@RequestBody LLMPrompt llmPrompt) {
        String response = chatService.agentFindHouses(llmPrompt);
        logger.info("Response in controller: {}", response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
