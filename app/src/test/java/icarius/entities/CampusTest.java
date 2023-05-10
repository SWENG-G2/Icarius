package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

public class CampusTest {

    private static final long id = 22;
    private static final String RESPONSE_BODY_ID = "id: " + id;

    private static final String CAMPUS_NAME = "newCampus";

    private static Campus testCampus;

    @BeforeAll
    static void setUp() {
        // Prepare bird
        OkHttpClient clientMock = Mockito.mock(OkHttpClient.class);
        testCampus = new Campus(clientMock);
        testCampus.setName(CAMPUS_NAME);
        testCampus.setId(id);
    }

    @Test
    void canCreateCampus() {
        // Mock request
        PostRequest mockRequest = Mockito.mock(PostRequest.class);

        ServerResponse response = new ServerResponse(200, RESPONSE_BODY_ID, null);
        doReturn(response).when(mockRequest).send();

        // Test Method

        // TODO - assert parameters where added to request
        assertTrue(testCampus.create(null, mockRequest));
        assertEquals(id, testCampus.getId());
    }

    @Test
    void canReadCampus() {

        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        GetRequest mockGetRequest = Mockito.mock(GetRequest.class);

        ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        ServerResponse getResponse = new ServerResponse(200, CAMPUS_NAME, null);
        doReturn(postResponse).when(mockPostRequest).send();
        doReturn(getResponse).when(mockGetRequest).send();

        testCampus.create(null, mockPostRequest);

        // TODO - assert parameters where added to request
        assertTrue(testCampus.read(mockGetRequest));
        assertEquals(CAMPUS_NAME, testCampus.getName());
    }

    @Test
    void canUpdateCampus() {
        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        PatchRequest mockPatchRequest = Mockito.mock(PatchRequest.class);
        GetRequest mockGetRequest = Mockito.mock(GetRequest.class);

        ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        ServerResponse getResponse = new ServerResponse(200, CAMPUS_NAME, null);
        doReturn(postResponse).when(mockPostRequest).send();
        doReturn(getResponse).when(mockPatchRequest).send();
        doReturn(getResponse).when(mockGetRequest).send();

        testCampus.create(null, mockPostRequest);
        String newCampusName = "Hull";
        testCampus.setName(newCampusName);
        testCampus.update(null, mockPatchRequest);
        String generatedCampusName = testCampus.read(mockGetRequest);

        // TODO - assert parameters where added to request
        assertEquals(newCampusName, generatedCampusName);
    }

    @Test
    void canDeleteCampus() {
        /// PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        DeleteRequest mockDeleteRequest = Mockito.mock(DeleteRequest.class);

        // ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID,
        // null);
        ServerResponse deleteResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);

        // doReturn(postResponse).when(mockPostRequest).send();
        doReturn(deleteResponse).when(mockDeleteRequest).send();

        // testCampus.create(null, mockPostRequest);
        Boolean generatedCampusDeleted = testCampus.delete(null, mockDeleteRequest);

        assertEquals(false, generatedCampusDeleted);
    }
}
