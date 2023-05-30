package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.auth.User;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;

public class BirdTest {
    private static final String[] parameters = { "name", "heroImageURL", "soundURL", "aboutMe",
            "abountMeVideoURL", "location", "locationImageURL", "diet", "dietImageURL" };
    private static final String[] parametersValues = { "Dalia", "heroImage.png", "quack.mp3",
            "Birthday 20 days ago.", "abountMeVideo.mp4", "3rd floor lab", "location.png", "Avocados and kikos",
            "diet.png" };
    private static final HashMap<String, String> requestParams = new HashMap<>();

    private static final long id = 22;
    private static final String RESPONSE_BODY_ID = "id: " + id;
    private static final String RESPONSE_BODY_NAME = "name: " + parameters[0];
    private static Bird testBird;

    private static final String birdReadXML = "<presentation xmlns=\"urn:SWENG\" xmlns:SWENG=\"https://raw.githubusercontent.com/SWENG-G2/xml_standard/proposal-1/standard.xsd\">"
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

    @BeforeAll
    static void setUp() {
        IntStream.range(0, parameters.length).forEach(idx -> requestParams.put(parameters[idx], parametersValues[idx]));

        // Prepare bird
        User userMock = Mockito.mock(User.class);
        testBird = new Bird(userMock);
        testBird.setName(parametersValues[0]);
        testBird.setHeroImageURL(parametersValues[1]);
        testBird.setSoundURL(parametersValues[2]);
        testBird.setAboutMe(parametersValues[3]);
        testBird.setAboutMeVideoURL(parametersValues[4]);
        testBird.setLocation(parametersValues[5]);
        testBird.setLocationImageURL(parametersValues[6]);
        testBird.setDiet(parametersValues[7]);
    }

    @Test
    void canCreateBird() throws IOException {
        // Mock request
        PostRequest mockRequest = Mockito.mock(PostRequest.class);

        ServerResponse response = new ServerResponse(200, RESPONSE_BODY_ID, null);
        doReturn(response).when(mockRequest).send();

        // Test Method
        assertTrue(testBird.create(null, mockRequest));

        // TODO - assert parameters where added to request
        assertEquals(id, testBird.getId());
    }

    @Test
    void canReadBird() {
        Bird blankBird = new Bird(null);

        GetRequest mockGetRequest = Mockito.mock(GetRequest.class);
        ServerResponse getResponse = new ServerResponse(200, birdReadXML, null);
        doReturn(getResponse).when(mockGetRequest).send();

        try {
            blankBird.read(mockGetRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        blankBird.setId(id);
        assertTrue(blankBird.read(mockGetRequest));
        assertEquals(parametersValues[0], blankBird.getName());
        assertEquals(parametersValues[1], blankBird.getHeroImageURL());
        assertEquals(parametersValues[2], blankBird.getSoundURL());
        assertEquals(parametersValues[3], blankBird.getAboutMe());
        assertEquals(parametersValues[4], blankBird.getAboutMeVideoURL());
        assertEquals(parametersValues[5], blankBird.getLocation());
        assertEquals(parametersValues[6], blankBird.getLocationImageURL());
        assertEquals(parametersValues[7], blankBird.getDiet());
        assertEquals(parametersValues[8], blankBird.getDietImageURL());
    }

    @Test
    void canUpdateBird() {

        PatchRequest mockPatchRequest = Mockito.mock(PatchRequest.class);
        ServerResponse patchResponse = new ServerResponse(200, "", null);
        doReturn(patchResponse).when(mockPatchRequest).send();

        String newBirdieName = "Dophelia";
        String newBirdieHeroImageURL = "quack.png";
        String newBirdieSoundURL = "squaaaaaark.mp3";
        String newBirdieAboutMe = "tall and beautiful";
        String newBirdieAboutMeVideoURL = "strollAroundTheLake.mp4";
        String newBirdieLocation = "lake";
        String newBirdieLocationImageURL = "pond.png";
        String newBirdieDiet = "squeaky cheese and cheeky squeeze";
        String newBirdieDietImageURL = "halloumi.png";

        testBird.setName(newBirdieName);
        testBird.setHeroImageURL(newBirdieHeroImageURL);
        testBird.setSoundURL(newBirdieSoundURL);
        testBird.setAboutMe(newBirdieAboutMe);
        testBird.setAboutMeVideoURL(newBirdieAboutMeVideoURL);
        testBird.setLocation(newBirdieLocation);
        testBird.setListImageURL(newBirdieLocationImageURL);
        testBird.setDiet(newBirdieDiet);
        testBird.setDietImageURL(newBirdieDietImageURL);

        try {
            testBird.update(null, mockPatchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        testBird.setId(id);
        assertTrue(testBird.update(null, mockPatchRequest));

        // Confirm information added to request
        // HashMap<String, String> sentRequestParams = mockPatchRequest.getParams();
        // assertNull(sentRequestParams);
        // assertEquals(newBirdieName, sentRequestParams.get("name"));
        // assertEquals(newBirdieHeroImageURL, sentRequestParams.get("heroImageURL"));
        // assertEquals(newBirdieSoundURL, sentRequestParams.get("soundURL"));
        // assertEquals(newBirdieAboutMe, sentRequestParams.get("aboutMe"));
        // assertEquals(newBirdieAboutMeVideoURL,
        // sentRequestParams.get("abountMeVideoURL"));
        // assertEquals(newBirdieLocation, sentRequestParams.get("location"));
        // assertEquals(newBirdieLocationImageURL,
        // sentRequestParams.get("locationImageURL"));
        // assertEquals(newBirdieDiet, sentRequestParams.get("diet"));
        // assertEquals(newBirdieDietImageURL, sentRequestParams.get("dietImageURL"));
    }

    @Test
    void canDeleteBird() {

        DeleteRequest mockDeleteRequest = Mockito.mock(DeleteRequest.class);
        ServerResponse deleteResponse = new ServerResponse(200, RESPONSE_BODY_ID, null);
        doReturn(deleteResponse).when(mockDeleteRequest).send();
        
        testBird.setId(id);
        assertTrue(testBird.delete(null, mockDeleteRequest));
        
        // HashMap<String, String> sentRequestParams = mockDeleteRequest.getParams();
        // assertNull(sentRequestParams);
        // assertEquals(id, sentRequestParams.get("id"));
    }
}
