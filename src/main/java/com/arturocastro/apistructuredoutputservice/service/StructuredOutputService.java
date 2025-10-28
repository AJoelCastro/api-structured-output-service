package com.arturocastro.apistructuredoutputservice.service;

import com.arturocastro.apistructuredoutputservice.model.SOModel;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StructuredOutputService {

    private final OpenAIClient client;

    public StructuredOutputService(@Value("${openai.api.key}") String apiKey) {
        client = new OpenAIOkHttpClient.Builder()
                .apiKey(apiKey)
                .build();
    }

    public Response getStructuredOutput(SOModel som){
        if (som == null || som.getPrompt() == null || som.getPrompt().isEmpty()) {
            throw new IllegalArgumentException("Prompt vacío o nulo");
        }

        String prompt = """
            Eres un asistente experto en cocina.
            Devuelve una respuesta en formato JSON válido con los pasos y duración estimada.

            Pregunta: %s

            Formato de respuesta:
            {
              "question": "<repite la pregunta>",
              "answer": [
                {
                  "content": "Paso 1: ...",
                  "duration": 0.0
                }
              ]
            }
            """.formatted(som.getPrompt());

        ResponseCreateParams params = ResponseCreateParams
                .builder()
                .model(ChatModel.GPT_4_1_MINI)
                .maxOutputTokens(som.getMaxTokens())
                .temperature(som.getTemperature())
                .input(prompt)
                .build();
        return client.responses().create(params);
    }

}
