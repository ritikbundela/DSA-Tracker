package com.dsatrackeradv.dsa_tracker_adv.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // Custom response model for Gemini API
    public static class GeminiResponse {
        private List<Candidate> candidates;

        public List<String> getSimilarProblems() {
            return candidates != null ? candidates.stream()
                    .map(candidate -> candidate.getContent().getParts().get(0).getText())
                    .filter(text -> text != null && !text.isEmpty())
                    .collect(Collectors.toList()) : Collections.emptyList();
        }

        public void setCandidates(List<Candidate> candidates) {
            this.candidates = candidates;
        }

        public static class Candidate {
            private Content content;

            public Content getContent() {
                return content;
            }

            public void setContent(Content content) {
                this.content = content;
            }
        }

        public static class Content {
            private List<Part> parts;

            public List<Part> getParts() {
                return parts;
            }

            public void setParts(List<Part> parts) {
                this.parts =    parts;
            }
        }

        public static class Part {
            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }

    public List<String> getSimilarProblems(String title, String topic) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        // Prepare the request body
//        String requestBody = String.format(
//                "{\"contents\": [{\"parts\": [{\"text\": \"give Similar Problems by reading this problem and give similar problems in " +
//                        "bullet points individually with proper link for solving that problem do not include line breaks i want only similar problems %s, topic: %s\"}]}]}",
//                title, topic
//        );
//        String requestBody = String.format(
//                "{\"contents\": [{\"parts\": [{\"text\": \"Given the problem titled '%s' with topic '%s', return 5 similar problems.\n\n" +
//                        "- Each problem must be in a separate bullet point.\n" +
//                        "- Format: [Problem Title](https://link-to-problem)\n" +
//                        "- Do not include extra explanation or line breaks.\n" +
//                        "- Only include the problem title and the direct problem link.\n\n" +
//                        "Output only the list. Do not include anything else.\"}]}]}",
//                title, topic
//        );
//        String requestBody = String.format(
//                "{\"contents\": [{\"parts\": [{\"text\": \"You are given a problem titled: '%s' under the topic '%s'.\\n" +
//                        "Give 5 similar problems in bullet points.\\n" +
//                        "Each bullet should include the problem title with a proper clickable link in Markdown format.\\n" +
//                        "Example format: [Problem Title](https://link-to-problem.com).\\n" +
//                        "Don't include any explanations, just the list.\\n" +
//                        "Don't add any extra line or text before/after the list.\"}]}]}",
//                title, topic
//        );

        String requestBody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"You are given a problem titled: '%s' under the topic '%s'.\\n" +
                        "Give at least 8–10 similar problems from trusted platforms like LeetCode, GeeksforGeeks, Codeforces, InterviewBit, or HackerRank.\\n" +
                        "Each problem should be in bullet point format like this: [Problem Title](https://valid-url.com).\\n" +
                        "Only include valid clickable problem links.\\n" +
                        "No extra explanation, no headings or footers — just the bullet list.\"}]}]}",
                title, topic
        );



        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP entity with body and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make POST request and map to GeminiResponse
            ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(url, entity, GeminiResponse.class);
            return response.getBody() != null ? response.getBody().getSimilarProblems() : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            return Collections.singletonList("Error fetching similar problems: " + e.getMessage());
        }
    }

}
