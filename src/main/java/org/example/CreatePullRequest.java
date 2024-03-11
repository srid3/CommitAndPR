package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreatePullRequest {

    public static void createPR(String branchName, String title, String body, String token) throws IOException {
        String jsonData = "{\"title\":\"" + title + "\",\"body\":\"" + body + "\",\"head\":\"srid3:" + branchName + "\",\"base\":\"master\"}";
        String apiUrl = "https://api.github.com/repos/sridamul/SetupRepoLocally/pulls";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int statusCode = connection.getResponseCode();

        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        if (statusCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("Pull Request Created Successfully!");
            System.out.println("Response:\n" + response.toString());
        } else {
            System.out.println("Failed to create pull request. Status code: " + statusCode);
            System.out.println("Response:\n" + response.toString());
        }
    }
}
