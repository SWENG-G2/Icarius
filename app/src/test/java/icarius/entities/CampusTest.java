package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.auth.UserClient;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;

public class CampusTest {

    private static final long id = 22;
    private static final String RESPONSE_BODY_ID = "id: " + id;

    private static final String CAMPUS_NAME = "newCampus";

    private static Campus testCampus;

    @BeforeAll
    static void setUp() {
        // Prepare bird
        UserClient userMock = Mockito.mock(UserClient.class);
        testCampus = new Campus(userMock);
        testCampus.setName(CAMPUS_NAME);
        // testCampus.setId(id);
    }

    @Test
    void canCreateCampus() {
        // Mock request
        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        doReturn(postResponse).when(mockPostRequest).send();

        // Test Method

        // TODO - assert parameters where added to request
        // assertTrue(testCampus.create(null, mockPostRequest));
        // assertEquals(id, testCampus.getId());

        
        // Test Method
        assertTrue(testCampus.create(null, mockPostRequest));

        verify(mockPostRequest, times(1)).addParameter("name", "newCampus");
        assertEquals(testCampus.getId(), id);

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
        PatchRequest mockPatchRequest = Mockito.mock(PatchRequest.class);
        ServerResponse patchResponse = new ServerResponse(200, "", null);
        doReturn(patchResponse).when(mockPatchRequest).send();

        String newCampusName = "Kroy";

        testCampus.setName(newCampusName);

        try {
            testCampus.update(null, mockPatchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        testCampus.setId(id);
        testCampus.update(null, mockPatchRequest);

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("newName", newCampusName);
        parameters.put("id", Long.toString(id));

        verify(mockPatchRequest, times(1)).addParameters(parameters);
        assertEquals(testCampus.getId(), id);
    }

    @Test
    void canDeleteCampus() {
        DeleteRequest mockDeleteRequest = Mockito.mock(DeleteRequest.class);
        ServerResponse postResponse = new ServerResponse(200, Long.toString(id), null);
        doReturn(postResponse).when(mockDeleteRequest).send();

        testCampus.setId(id);
        testCampus.delete(null, mockDeleteRequest);

        verify(mockDeleteRequest, times(1)).addParameter("id", Long.toString(id));
    }
}
