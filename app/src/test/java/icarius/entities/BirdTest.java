package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;
import okhttp3.OkHttpClient;

public class BirdTest {
    private static final String[] parameters = { "name", "listImageURL", "heroImageURL", "soundURL", "aboutMe",
            "abountMeVideoURL", "location", "locationImageURL", "diet", "dietImageURL" };
    private static final String[] parametersValues = { "Dalia", "listImage.png", "heroImage.png", "quack.mp3",
            "Birthday 20 days ago.", "abountMeVideo.mp4", "3rd floor lab", "location.png", "Avocados and kikos",
            "diet.png" };
    private static final HashMap<String, String> requestParams = new HashMap<>();

    private static final long id = 22;
    private static final String RESPONSE_BODY_ID = "id: " + id;
    private static final String RESPONSE_BODY_NAME = "name: " + parameters[0];
    private static Bird testBird;

    @BeforeAll
    static void setUp() {
        IntStream.range(0, parameters.length).forEach(idx -> requestParams.put(parameters[idx], parametersValues[idx]));

        // Prepare bird
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        testBird = new Bird(clientMock);
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
    }

    @Test
    void canCreateBird() throws IOException {
        // Mock request
        PostRequest mockRequest = Mockito.mock(PostRequest.class);

        ServerResponse response = new ServerResponse(200, RESPONSE_BODY_ID, null);
        doReturn(response).when(mockRequest).send();

        // Test Method
        Long generatedBirdId = testBird.create(null, mockRequest);

        // TODO - assert parameters where added to request
        assertEquals(id, generatedBirdId);
    }

    @Test
    void canReadBird() {
        // have empty bird object
        // .read on bird
        // does bird have parameters from mock response?
        GetRequest mockGetRequest = Mockito.mock(GetRequest.class);
        ServerResponse getResponse = new ServerResponse(200, RESPONSE_BODY_NAME, null); // replace RESPONSE_BODY_NAME
                                                                                        // with xml response
        doReturn(getResponse).when(mockGetRequest).send();

        testBird.read(mockGetRequest);

        // TODO - assert parameters where added to request
        // TODO - assert whether parameters in mock XML response are in bird object
        // TODO - assert returns bird name from mock XML response
        assertEquals(parameters[0], testBird.getName());
    }

    @Test
    void canUpdateBird() {
        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        PatchRequest mockPatchRequest = Mockito.mock(PatchRequest.class);
        GetRequest mockGetRequest = Mockito.mock(GetRequest.class);

        ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        ServerResponse getResponse = new ServerResponse(200, RESPONSE_BODY_NAME, null);
        doReturn(postResponse).when(mockPostRequest).send();
        doReturn(getResponse).when(mockPatchRequest).send();
        doReturn(getResponse).when(mockGetRequest).send();

        testBird.create(null, mockPostRequest);
        String newBirdieName = "Dophelia";
        testBird.setName(newBirdieName);
        testBird.update(null, mockPatchRequest);
        String generatedBirdName = testBird.read(mockGetRequest);

        // TODO - assert parameters where added to request
        assertEquals(newBirdieName, generatedBirdName);
    }

    @Test
    void canDeleteBird() {
        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        DeleteRequest mockDeleteRequest = Mockito.mock(DeleteRequest.class);

        ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        ServerResponse deleteResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);

        doReturn(postResponse).when(mockPostRequest).send();
        doReturn(deleteResponse).when(mockDeleteRequest).send();

        testBird.create(null, mockPostRequest);
        Boolean generatedBirdDeleted = testBird.delete(null, mockDeleteRequest);

        assertEquals(false, generatedBirdDeleted);
    }
}
