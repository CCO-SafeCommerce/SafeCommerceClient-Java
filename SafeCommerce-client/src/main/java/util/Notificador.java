package util;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Notificador {

    public void enviarMensagemSlack(String mensagem) throws IOException, InterruptedException {
        String payload = "{\"text\": \"" + mensagem + "\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://hooks.slack.com/services/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"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response);

    }
    public void createIssue(String summary, String description) throws IOException, InterruptedException {
        String url = "https://safe-commercefr.atlassian.net/rest/api/2/issue";
        String payload = "{\n" +
                "    \"fields\": {\n" +
                "       \"project\":\n" +
                "       {\n" +
                "          \"key\": \"SAF\"\n" +
                "       },\n" +
                "       \"summary\": \"" + summary + "\",\n" +
                "       \"description\": \"" + description + "\",\n" +
                "       \"issuetype\": {\n" +
                "          \"name\": \"Task\"\n" +
                "       }\n" +
                "   }\n" +
                "}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthenticationHeader("pedrogustavofr000@gmail.com", "LURKLN39VJ4kELmxReGA1DA2"))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response);

    }
    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Notificador notificador = new Notificador();
        notificador.createIssue("AI MEU DEUS", "TA PEGANO FOGO");
    }

}
