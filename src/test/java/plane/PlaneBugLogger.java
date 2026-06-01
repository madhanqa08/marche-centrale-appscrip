package plane;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PlaneBugLogger
{

    // ─── PLANE CONFIGURATION ─────────────────────────────────────────────────
    private static final String BASE_URL       = "https://pm.appscrip.co";
    private static final String API_TOKEN      = "plane_api_2d7265961d06449ab6420d57ed038171";
    private static final String WORKSPACE_SLUG = "new-kommerce";
    private static final String PROJECT_ID     = "815e4154-2125-4c64-863c-962acad896e1";
    private static final String BACKLOG_STATE  = "28e32191-22f9-484a-8edc-531d54aa75e9";
    private static final String LOGIN_LABEL    = "a9506d4b-483e-4239-a71a-7972c9b67c55";

    // ─── CLOUDINARY CONFIGURATION ────────────────────────────────────────────
    private static final String CLOUDINARY_CLOUD_NAME = "dvqqfqjhm";
    private static final String CLOUDINARY_API_KEY    = "819337579852929";
    private static final String CLOUDINARY_API_SECRET = "9KcmDb9c7IdQICCvO5WVcts3vBY"; // ← paste your secret here

    // ─── SCREENSHOT PATH ─────────────────────────────────────────────────────
    private static final String SCREENSHOT_PATH =
            "reports/screenshots/tescase030_1779538778290.png";
    // ─────────────────────────────────────────────────────────────────────────

    private final HttpClient httpClient;

    public PlaneBugLogger() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MAIN — run directly, no browser, no TestNG
    // ─────────────────────────────────────────────────────────────────────────
    public static void main(String[] args) throws Exception {
        PlaneBugLogger logger = new PlaneBugLogger();

        // Step 1: Upload screenshot to Cloudinary
        System.out.println("📸 Uploading screenshot to Cloudinary...");
        String screenshotUrl = logger.uploadToCloudinary(SCREENSHOT_PATH);

        // Step 2: Log bug to Plane with screenshot
        System.out.println("🐛 Logging bug to Plane...");
        logger.logBug(
                "Login button not responding on Android 13",
                "Steps to reproduce:\n"
                        + "1. Open app\n"
                        + "2. Tap Login\n"
                        + "3. Nothing happens\n\n"
                        + "Expected: Navigate to dashboard\n"
                        + "Actual: No response",
                "high",
                screenshotUrl
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // UPLOAD IMAGE TO CLOUDINARY — returns public URL
    // ─────────────────────────────────────────────────────────────────────────
    public String uploadToCloudinary(String filePath) throws Exception {
        Path path = Path.of(filePath);

        if (!path.toFile().exists()) {
            System.err.println("❌ Image not found: " + filePath);
            return null;
        }

        byte[] fileBytes = Files.readAllBytes(path);
        String base64Image = Base64.getEncoder().encodeToString(fileBytes);

        // Detect file type
        String fileName = path.getFileName().toString().toLowerCase();
        String fileType = "image/png";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) fileType = "image/jpeg";
        else if (fileName.endsWith(".webp")) fileType = "image/webp";
        else if (fileName.endsWith(".gif"))  fileType = "image/gif";

        // Generate timestamp and signature for Cloudinary auth
        long timestamp = System.currentTimeMillis() / 1000;
        String signatureString = "timestamp=" + timestamp + CLOUDINARY_API_SECRET;
        String signature = sha1Hex(signatureString);

        // Build multipart body
        String boundary = "----CloudinaryBoundary" + System.currentTimeMillis();
        List<byte[]> parts = new ArrayList<>();

        // file field
        parts.add(("--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n"
                + "Content-Type: " + fileType + "\r\n\r\n").getBytes());
        parts.add(fileBytes);
        parts.add("\r\n".getBytes());

        // api_key field
        parts.add(("--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"api_key\"\r\n\r\n"
                + CLOUDINARY_API_KEY + "\r\n").getBytes());

        // timestamp field
        parts.add(("--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"timestamp\"\r\n\r\n"
                + timestamp + "\r\n").getBytes());

        // signature field
        parts.add(("--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"signature\"\r\n\r\n"
                + signature + "\r\n").getBytes());

        // closing boundary
        parts.add(("--" + boundary + "--\r\n").getBytes());

        // Merge all parts
        int total = 0;
        for (byte[] p : parts) total += p.length;
        byte[] body = new byte[total];
        int offset = 0;
        for (byte[] p : parts) {
            System.arraycopy(p, 0, body, offset, p.length);
            offset += p.length;
        }

        // Cloudinary upload endpoint
        String uploadEndpoint = "https://api.cloudinary.com/v1_1/" + CLOUDINARY_CLOUD_NAME + "/image/upload";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uploadEndpoint))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(BodyPublishers.ofByteArray(body))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        System.out.println("Cloudinary Status   : " + response.statusCode());
        System.out.println("Cloudinary Response : " + response.body());

        if (response.statusCode() == 200) {
            String url = extractJsonValue(response.body(), "secure_url");
            System.out.println("✅ Uploaded to Cloudinary: " + url);
            return url;
        } else {
            System.err.println("❌ Cloudinary upload failed: " + response.body());
            return null;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // LOG BUG TO PLANE with screenshot in description
    // ─────────────────────────────────────────────────────────────────────────
    public void logBug(String title, String description,
                       String priority, String screenshotUrl) throws Exception {

        // Embed screenshot URL as image in description
        String descriptionHtml;
        if (screenshotUrl != null) {
            descriptionHtml =
                    "<p>" + escapeJson(description) + "</p>" +
                            "<p><strong>Screenshot:</strong></p>" +
                            "<img src=\\\"" + screenshotUrl + "\\\" alt=\\\"screenshot\\\" " +
                            "style=\\\"max-width:100%;\\\" />";
        } else {
            descriptionHtml = "<p>" + escapeJson(description) + "</p>";
        }

        String requestBody =
                "{\n" +
                        "  \"name\": \"" + escapeJson(title) + "\",\n" +
                        "  \"description_html\": \"" + descriptionHtml + "\",\n" +
                        "  \"priority\": \"" + priority + "\",\n" +
                        "  \"state\": \"" + BACKLOG_STATE + "\",\n" +
                        "  \"label_ids\": [\"" + LOGIN_LABEL + "\"]\n" +
                        "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/v1/workspaces/" + WORKSPACE_SLUG
                        + "/projects/" + PROJECT_ID + "/issues/"))
                .header("Content-Type", "application/json")
                .header("X-API-Key", API_TOKEN)
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
        System.out.println("Issue Create Status : " + response.statusCode());

        if (response.statusCode() == 201) {
            System.out.println("✅ Bug logged to Plane successfully!");
            if (screenshotUrl != null) {
                System.out.println("🖼️ Screenshot: " + screenshotUrl);
            }
        } else {
            System.err.println("❌ Failed: " + response.body());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SHA1 HASH — used for Cloudinary signature
    // ─────────────────────────────────────────────────────────────────────────
    private String sha1Hex(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest(input.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────────────────────────────────
    private String escapeJson(String value) {
        if (value == null) return "";
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String extractJsonValue(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) return null;
        start += search.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}