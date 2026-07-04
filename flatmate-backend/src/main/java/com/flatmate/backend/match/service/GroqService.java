package com.flatmate.backend.match.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flatmate.backend.listing.entity.Listing;
import com.flatmate.backend.match.dto.MatchResult;
import com.flatmate.backend.tenant.entity.TenantProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroqService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    public MatchResult findMatch(TenantProfile tenant, Listing listing) {

        try {

            String prompt = """
                    You are a room compatibility evaluator.

                    Room Listing
                    
                    Location: %s
                    Rent: %.0f
                    Available From: %s
                    
                    Tenant
                    
                    Preferred Location: %s
                    Budget Range: %.0f - %.0f
                    Move In Date: %s

                    Score compatibility from 0 to 100.
                    
                    Give the highest importance to:
                    1. Preferred location
                    2. Budget compatibility
                    3. Move-in date compatibility
                    
                    If both location and budget are a close match, the score should generally be above 80.
                    
                    Return ONLY valid JSON.
                    
                      Do not use markdown.
                    
                      Do not wrap the JSON inside ```.
                    
                      The response must be exactly in this format:
                    
                      {
                        "score": 85,
                        "explanation": "Short explanation"
                      }
                    """.formatted(
                            listing.getLocation(),
                            listing.getRent(),
                            listing.getAvailableFrom(),
                            tenant.getPreferredLocation(),
                            tenant.getMinBudget(),
                            tenant.getMaxBudget(),
                            tenant.getMoveInDate()
);

            Map<String, Object> body = Map.of(
                    "model", model,
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    ),
                    "temperature", 0
            );
            System.out.println("Calling Groq...");
            String response = restClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(String.class);
            System.out.println(response);
            JsonNode root = objectMapper.readTree(response);

            String content = root.path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
            content = content
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            JsonNode result = objectMapper.readTree(content);

            int score = Math.max(0, Math.min(100, result.get("score").asInt()));

            return MatchResult.builder()
                    .score(score)
                    .explanation(result.get("explanation").asText())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}