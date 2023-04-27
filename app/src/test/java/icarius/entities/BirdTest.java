package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.App;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BirdTest {

    private static final String TEST_PATH = "/test";
    private static final String[] parameters = { "name", "listImageURL", "heroImageURL", "soundURL", "aboutMe",
            "abountMeVideoURL", "location", "locationImageURL", "diet", "dietImageURL" };
    private static final String[] parametersValues = { "Dalia", "listImage.png", "heroImage.png", "quack.mp3",
            "Birthday 20 days ago.", "abountMeVideo.mp4", "3rd floor lab", "location.png", "Avocados and kikos",
            "diet.png" };
    private static final HashMap<String, String> requestParams = new HashMap<>();

    private static final String RESPONSE_BODY = "id: 22";

    @BeforeAll
    static void setUp() {
        IntStream.range(0, parameters.length).forEach(idx -> requestParams.put(parameters[idx], parametersValues[idx]));
    }

    @Test
    void canCreateBird() throws IOException {
        // Mock request
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        PostRequest mockRequest = Mockito.mock(PostRequest.class);

        ServerResponse response = new ServerResponse(200, RESPONSE_BODY, null);
        doReturn(response).when(mockRequest).send();

        // Prepare bird
        Bird testBird = new Bird(clientMock);
        testBird.setName(parameters[0]);
        testBird.setListImageURL(parameters[1]);
        testBird.setHeroImageURL(parameters[2]);
        testBird.setSoundURL(parameters[3]);
        testBird.setAboutMe(parameters[4]);
        testBird.setAboutMeVideoURL(parameters[5]);
        testBird.setLocation(parameters[6]);
        testBird.setLocationImageURL(parameters[7]);
        testBird.setDiet(parameters[8]);
        testBird.setDietImageURL(parameters[9]);

        // Test Method
        Long generatedBirdId = testBird.create(null, mockRequest);

        // TODO - assert parameters where added to request
        assertEquals(22, generatedBirdId);
    }

    @Test
    void canReadBird() {

    }

    @Test
    void canUpdateBird() {

    }

    @Test
    void canDeleteBird() {

    }
}
