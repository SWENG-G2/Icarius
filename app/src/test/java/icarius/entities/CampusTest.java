package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    private static final String FIRST_BIRD_LIST_IMAGE_URL = "littlePicOfDerek.png";
    private static final String FIRST_BIRD_NAME = "Derek";
    private static final long FIRST_BIRD_ID = 23;
    private static final String SECOND_BIRD_LIST_IMAGE_URL = "littlePicOfDonCorleone.png";
    private static final String SECOND_BIRD_NAME = "Don Corleone";
    private static final long SECOND_BIRD_ID = 6;
    private static final String RESPONSE_BODY_ID = "id: " + id;
    private static final String CAMPUS_NAME = "newCampus";
    private static Campus testCampus;
    private static final String CAMPUS_READ_XML = "<presentation xmlns=\"urn:SWENG\" xmlns:SWENG=\"https://raw.githubusercontent.com/SWENG-G2/xml_standard/proposal-1/standard.xsd\">\n"
            +
            "  <info>\n" +
            "    <title>York</title>\n" +
            "    <author>Joe</author>\n" +
            "    <date>2023-06-02</date>\n" +
            "    <numSlides>1</numSlides>\n" +
            "  </info>\n" +
            "  <slide width=\"1920\" height=\"-5\" title=\"" + FIRST_BIRD_ID + "\">\n" +
            "    <text fontName=\"mono\" fontSize=\"22\" colour=\"#000000FF\" xCoordinate=\"520\" yCoordinate=\"0\" width=\"-4\" height=\"-5\">"
            + FIRST_BIRD_NAME + "</text>\n"
            +
            "    <text fontName=\"mono\" fontSize=\"18\" colour=\"#000000FF\" xCoordinate=\"520\" width=\"1400\" height=\"-5\" yCoordinate=\"30\"/>\n"
            +
            "    <image url=\"" + FIRST_BIRD_LIST_IMAGE_URL
            + "\" width=\"480\" height=\"-1\" xCoordinate=\"0\" yCoordinate=\"0\"/>\n" +
            "  </slide>\n" +
            "  <slide width=\"1920\" height=\"-5\" title=\"" + SECOND_BIRD_ID + "\">\n" +
            "    <text fontName=\"mono\" fontSize=\"22\" colour=\"#000000FF\" xCoordinate=\"520\" yCoordinate=\"0\" width=\"-4\" height=\"-5\">"
            + SECOND_BIRD_NAME + "</text>\n"
            +
            "    <text fontName=\"mono\" fontSize=\"18\" colour=\"#000000FF\" xCoordinate=\"520\" width=\"1400\" height=\"-5\" yCoordinate=\"30\"/>\n"
            +
            "    <image url=\"" + SECOND_BIRD_LIST_IMAGE_URL
            + "\" width=\"480\" height=\"-1\" xCoordinate=\"0\" yCoordinate=\"0\"/>\n" +
            "  </slide>\n" +
            "</presentation>";

    /**
     * Set up method, just to create the test campus and give it a name.
     */
    @BeforeAll
    static void setUp() {
        // Prepare bird
        UserClient userMock = Mockito.mock(UserClient.class);
        testCampus = new Campus(userMock);
        testCampus.setName(CAMPUS_NAME);
    }

    /**
     * Creates a mock post request that returns a mock response
     * when the request is sent. Then the name parameter value is
     * compared to the parameter that
     * is added to the request in the method under test.
     */
    @Test
    void canCreateCampus() {
        // Mock request
        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);
        ServerResponse postResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        doReturn(postResponse).when(mockPostRequest).send();

        assertTrue(testCampus.create(null, mockPostRequest));

        verify(mockPostRequest, times(1)).addParameter("name", "newCampus");
        assertEquals(testCampus.getId(), id);

    }

    /**
     * Creates a mock get request and has a response with an xml that stores all the
     * bird's id's, name's and list image url's for that campus. It then checks the
     * bird list field stored in the campus to make sure they contain the right
     * values for the corresponding fields.
     */
    @Test
    void canReadCampus() {
        GetRequest mockGetRequestForCampus = Mockito.mock(GetRequest.class);
        ServerResponse getResponseForCampus = new ServerResponse(200, CAMPUS_READ_XML, null);
        doReturn(getResponseForCampus).when(mockGetRequestForCampus).send();

        testCampus.setId(id);
        testCampus.read(mockGetRequestForCampus);

        assertEquals(testCampus.birds.get(0).getId(), FIRST_BIRD_ID);
        assertEquals(testCampus.birds.get(0).getName(), FIRST_BIRD_NAME);
        assertEquals(testCampus.birds.get(0).getListImageURL(), FIRST_BIRD_LIST_IMAGE_URL);
        assertEquals(testCampus.birds.get(1).getId(), SECOND_BIRD_ID);
        assertEquals(testCampus.birds.get(1).getName(), SECOND_BIRD_NAME);
        assertEquals(testCampus.birds.get(1).getListImageURL(), SECOND_BIRD_LIST_IMAGE_URL);
    }

    /**
     * Creates a mock patch request and response for when the request is sent. It
     * changes the name parameter of the test campus and puts the new parameter into
     * a hashmap and compares the hashmap to what was added to the request in the
     * method under test.
     */
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

    /**
     * Creates a mock delete request and response for when the request is sent. Sets
     * the campus id then deletes it, verifies that the id is added to the request.
     */
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
