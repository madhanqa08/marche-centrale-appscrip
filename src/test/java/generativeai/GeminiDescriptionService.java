package generativeai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class GeminiDescriptionService {


    private final OpenAIClient client;
    private final String websiteName;
    private final String model;

    public GeminiDescriptionService(String websiteName) {
        String apiKey = System.getenv("GROQ_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GROQ_API_KEY is missing in environment variables.");
        }

        String baseUrl = System.getenv("GROQ_BASE_URL");
        if (baseUrl == null || baseUrl.isBlank()) {
            baseUrl = "https://api.groq.com/openai/v1";
        }

        String groqModel = System.getenv("GROQ_MODEL");
        if (groqModel == null || groqModel.isBlank()) {
            throw new IllegalStateException("GROQ_MODEL is missing in environment variables.");
        }

        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();

        this.websiteName = websiteName;
        this.model = groqModel;
    }

    public String generateDescription(String testCaseId,
                                      String module,
                                      String title,
                                      String expectedResult,
                                      String priority,
                                      String label,
                                      String actualError) {

        String prompt = """
        You are a senior QA automation engineer.

        Analyze the failed automation test case and actual console error.

        Generate ONE accurate bug report based ONLY on the real visible issue.

        Output format:
        Website || Module || Description || Expected Result

        Important Requirement:
        - The output format was previously:
          Website || Module || Description
        - Now the format is changed to:
          Website || Module || Description || Expected Result
        - The 255 character limit applies ONLY to:
          Website || Module || Description
        - Expected Result does NOT have a character limit and can be longer if required
        - Expected Result is separated after:
          ||
        - Expected Result MUST always start with:
          "Expected Result : "

        Example:
        Marche Centrale || sign up || The Sign Up button is enabled when the Terms and Conditions checkbox is unchecked. || Expected Result : The system should keep the Sign Up button disabled when the Terms and Conditions checkbox is unchecked.

        Rules:
        - Website must be: %s
        - Module should be lowercase
        - Description must be strict third-person format
        - Expected Result must also be strict third-person format
        - Description and Expected Result must sound like real manual tester bug report statements
        - Use simple business/user language only
        - Never mention technical terms or automation terms
        - Never mention stale element, timeout, exception, locator, selenium, stack trace, reference error, click intercepted, DOM, xpath, CSS selector, or webdriver
        - Never use phrases like:
          "when attempting"
          "during the process"
          "does not respond properly"
          "preventing the user"
        - Never mention automation execution details
        - Describe only visible UI or business issue
        - Keep Description short, direct, natural, and professional
        - Description must stay within the 255 character limit together with:
          Website || Module || Description
        - Expected Result can be longer and has no strict limit
        - Expected Result must strongly describe what should happen from end-user perspective
        - Expected Result must start exactly with:
          "Expected Result : "
        - After "Expected Result : " the sentence must begin with:
          "The user should be able to"
          or
          "The page should"
          or
          "The system should"
        - Do not mention Test Case ID
        - Do not add markdown or bullets
        - Output must contain ONLY one line

        Preferred Description Style:
        Marche Centrale || sign up || The Profile icon does not open after clicking. || Expected Result : The user should be able to open the Profile section after clicking the Profile icon.

        Marche Centrale || profile || The Edit Profile popup does not appear after clicking the edit button. || Expected Result : The user should be able to view the Edit Profile popup after clicking the edit button.

        Marche Centrale || cart || The cart page does not load after clicking the cart icon. || Expected Result : The user should be able to view the cart page after clicking the cart icon.

        Bad Examples:
        Marche Centrale || sign up || When attempting to click...
        Marche Centrale || sign up || The page does not respond properly...
        Marche Centrale || sign up || Stale element reference error occurs...

        Test Case ID:
        %s

        Module:
        %s

        Test Title:
        %s

        Expected Result:
        %s

        Priority:
        %s

        Label:
        %s

        Actual Failure Log:
        %s
        """.formatted(
                websiteName,
                testCaseId,
                module,
                title,
                expectedResult,
                priority,
                label,
                actualError
        );

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(model)
                .addUserMessage(prompt)
                .build();

        ChatCompletion response = client.chat().completions().create(params);

        String result = response.choices().stream()
                .findFirst()
                .flatMap(choice -> choice.message().content())
                .orElse(null);

        if (result == null || result.isBlank()) {
            return websiteName + " || " +
                    normalizeModule(module) +
                    " || Description not generated || Expected result not generated";
        }

        return cleanup(result);
    }

    private String normalizeModule(String module) {
        if (module == null) {
            return "";
        }

        return module.trim()
                .toLowerCase()
                .replace(" ", "")
                .replace("-", "")
                .replace("_", "");
    }

    private String cleanup(String text) {
        return text.replace("\n", " ")
                .replace("\r", " ")
                .trim();
    }
}