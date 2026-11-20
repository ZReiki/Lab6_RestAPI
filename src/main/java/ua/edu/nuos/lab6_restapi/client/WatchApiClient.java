package ua.edu.nuos.lab6_restapi.client;

import ua.edu.nuos.lab6_restapi.client.model.WatchDto;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class WatchApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/watches";

    private final HttpClient httpClient;
    private final Gson gson;

    public WatchApiClient() {
        this.httpClient = HttpClient.newHttpClient();

        // Реєструємо адаптери для LocalDate, бо стандартний Gson не вміє напряму працювати з java.time.LocalDate
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .create();
    }

    public List<WatchDto> getAll() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL)).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            System.out.println("Помилка отримання списку. Статус: " + response.statusCode());
            return Collections.emptyList();
        }

        Type type = TypeToken.getParameterized(List.class, WatchDto.class).getType();
        return gson.fromJson(response.body(), type);
    }

    public WatchDto getById(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/" + id)).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) {
            return null;
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Помилка запиту. Статус: " + response.statusCode());
        }
        return gson.fromJson(response.body(), WatchDto.class);
    }

    public WatchDto create(WatchDto watch) throws Exception {
        String jsonBody = gson.toJson(watch);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new RuntimeException("Не вдалося створити запис. Статус: " + response.statusCode());
        }
        return gson.fromJson(response.body(), WatchDto.class);
    }

    public WatchDto update(long id, WatchDto watch) throws Exception {
        String jsonBody = gson.toJson(watch);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) {
            return null;
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Не вдалося оновити запис. Статус: " + response.statusCode());
        }
        return gson.fromJson(response.body(), WatchDto.class);
    }

    public boolean delete(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/" + id)).DELETE().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 204 || response.statusCode() == 200;
    }
}