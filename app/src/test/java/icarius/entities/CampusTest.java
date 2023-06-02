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
    private static final String LIST_IMAGE_URL = "listImage.png";
    private static final String RESPONSE_BODY_ID = "id: " + id;
    private static final String CAMPUS_NAME = "newCampus";
    private static Campus testCampus;
    private static final String[] parametersValues = { "Dalia", "listimage.png", "heroImage.png", "quack.mp3",
            "Birthday 20 days ago.", "abountMeVideo.mp4", "3rd floor lab", "location.png", "Avocados and kikos",
            "diet.png" };
    private static final String CAMPUS_READ_XML = "<presentation xmlns=\"urn:SWENG\" xmlns:SWENG=\"https://raw.githubusercontent.com/SWENG-G2/xml_standard/proposal-1/standard.xsd\">\n"
            +
            "  <info>\n" +
            "    <title>York</title>\n" +
            "    <author>Joe</author>\n" +
            "    <date>2023-06-02</date>\n" +
            "    <numSlides>1</numSlides>\n" +
            "  </info>\n" +
            "  <slide width=\"1920\" height=\"-5\" title=\"5\">\n" +
            "    <text fontName=\"mono\" fontSize=\"22\" colour=\"#000000FF\" xCoordinate=\"520\" yCoordinate=\"0\" width=\"-4\" height=\"-5\">Darren</text>\n"
            +
            "    <text fontName=\"mono\" fontSize=\"18\" colour=\"#000000FF\" xCoordinate=\"520\" width=\"1400\" height=\"-5\" yCoordinate=\"30\"/>\n"
            +
            "    <image url=\"\" width=\"480\" height=\"-1\" xCoordinate=\"0\" yCoordinate=\"0\"/>\n" +
            "  </slide>\n" +
            "</presentation>";

    private static final String BIRD_READ_XML = "<presentation xmlns=\"urn:SWENG\" xmlns:SWENG=\"https://raw.githubusercontent.com/SWENG-G2/xml_standard/proposal-1/standard.xsd\">"
            +
            "<info>" +
            "<title>Daphne</title>" +
            "<author>Joe</author>" +
            "<date>2023-05-11</date>" +
            "<numSlides>0</numSlides>" +
            "</info>" +
            "<slide width=\"1920\" height=\"485\" title=\"heroSlide\">" +
            "<rectangle width=\"1920\" height=\"100\" xCoordinate=\"0\" yCoordinate=\"0\" colour=\"#E89266FF\"/>" +
            "<text xCoordinate=\"20\" yCoordinate=\"25\" colour=\"#000000FF\" fontName=\"mono\" fontSize=\"28\" width=\"-4\" height=\"-5\">"
            + parametersValues[0] + "</text>" +
            "<audio url=\"" + parametersValues[2] + "\" loop=\"false\" xCoordinate=\"-3\" yCoordinate=\"0\"/>" +
            "<image url=\"" + parametersValues[1]
            + "\" width=\"1700\" height=\"360\" xCoordinate=\"-2\" yCoordinate=\"115\"/>" +
            "<circle radius=\"175\" xCoordinate=\"-2\" yCoordinate=\"-120\" colour=\"#00000000\" borderWidth=\"15\" borderColour=\"#8A8178FF\"/>"
            +
            "</slide>" +
            "<slide width=\"1920\" height=\"-1\" title=\"About me\">" +
            "<video xCoordinate=\"-2\" yCoordinate=\"0\" width=\"1820\" height=\"250\" loop=\"false\" url=\""
            + parametersValues[4] + "\"/>" +
            "<text xCoordinate=\"20\" yCoordinate=\"250\" colour=\"#000000FF\" fontName=\"mono\" fontSize=\"18\" width=\"1880\" height=\"-5\">"
            + parametersValues[3] + "</text>" +
            "</slide>" +
            "<slide width=\"1920\" height=\"-1\" title=\"Diet\">" +
            "<image url=\"" + parametersValues[8]
            + "\" width=\"1700\" height=\"200\" xCoordinate=\"-2\" yCoordinate=\"0\"/>" +
            "<text xCoordinate=\"20\" yCoordinate=\"210\" colour=\"#000000FF\" fontName=\"mono\" fontSize=\"18\" width=\"1880\" height=\"-5\">"
            + parametersValues[7] + "</text>" +
            "</slide>" +
            "<slide width=\"1920\" height=\"-1\" title=\"Location\">" +
            "<image url=\"" + parametersValues[6]
            + "\" width=\"1700\" height=\"200\" xCoordinate=\"-2\" yCoordinate=\"0\"/>" +
            "<text xCoordinate=\"20\" yCoordinate=\"210\" colour=\"#000000FF\" fontName=\"mono\" fontSize=\"18\" width=\"1880\" height=\"-5\">"
            + parametersValues[5] + "</text>" +
            "</slide>\n" +
            "</presentation>\n";

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
     * 
     */
    @Test
    void canReadCampus() {
        GetRequest mockGetRequestForCampus = Mockito.mock(GetRequest.class);

        ServerResponse getResponseForCampus = new ServerResponse(200, CAMPUS_READ_XML, null);
        ServerResponse getResponseForBird = new ServerResponse(200, CAMPUS_READ_XML, null);
        doReturn(getResponseForCampus).when(mockGetRequestForCampus).send();
        doReturn(getResponseForBird).when(any(Bird.class)).read(null);

        testCampus.setId(id);
        testCampus.read(mockGetRequestForCampus);

        verify(testCampus, times(1)).addBirdToBirds(id, LIST_IMAGE_URL, null);
    }

    @Test
    void canAddBirdToBirds() {
        GetRequest mockGetRequestForBird = Mockito.mock(GetRequest.class);

        ServerResponse getResponseForBird = new ServerResponse(200, CAMPUS_READ_XML, null);
        doReturn(getResponseForBird).when(mockGetRequestForBird).send();

        testCampus.addBirdToBirds(id, LIST_IMAGE_URL, mockGetRequestForBird);
    }

    /**
     * Creates a mock patch request and response for when the request is sent. It
     * changes the name parameter of the test campus and puts the new parameter into
     * a
     * hashmap and compares the hashmap to what was added to the request in the
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
