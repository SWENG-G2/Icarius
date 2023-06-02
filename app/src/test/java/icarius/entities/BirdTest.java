package icarius.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import icarius.auth.UserClient;
import icarius.http.DeleteRequest;
import icarius.http.GetRequest;
import icarius.http.PatchRequest;
import icarius.http.PostRequest;
import icarius.http.ServerResponse;

public class BirdTest {
    private static final String[] parameters = { "name", "listImageURL", "heroImageURL", "soundURL", "aboutMe",
            "abountMeVideoURL", "location", "locationImageURL", "diet", "dietImageURL" };
    private static final String[] parametersValues = { "Dalia", "listimage.png", "heroImage.png", "quack.mp3",
            "Birthday 20 days ago.", "abountMeVideo.mp4", "3rd floor lab", "location.png", "Avocados and kikos",
            "diet.png" };
    private static final long id = 9;
    private static Bird testBird;
    private static final HashMap<String, String> requestParams = new HashMap<>();
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

    /**
     * This sets up the test bird, places all the parameter values (bird name,
     * location, diet image,etc) with their coressponding key
     */
    @BeforeAll
    static void setUp() {
        IntStream.range(0, parameters.length).forEach(idx -> requestParams.put(parameters[idx], parametersValues[idx]));

        // Prepare bird
        UserClient userMock = Mockito.mock(UserClient.class);
        testBird = new Bird(userMock);
        testBird.setName(parametersValues[0]);
        testBird.setListImageURL(parametersValues[1]);
        testBird.setHeroImageURL(parametersValues[2]);
        testBird.setSoundURL(parametersValues[3]);
        testBird.setAboutMe(parametersValues[4]);
        testBird.setAboutMeVideoURL(parametersValues[5]);
        testBird.setLocation(parametersValues[6]);
        testBird.setLocationImageURL(parametersValues[7]);
        testBird.setDiet(parametersValues[8]);
        testBird.setDietImageURL(parametersValues[9]);
    }

    /**
     * Creates a mock post request that returns a mock response
     * when the request is sent. Then the parameter values are
     * put into a hashmap and is compared to the parameters that
     * are added to the request in the method under test.
     */
    @Test
    void canCreateBird() {
        PostRequest mockPostRequest = Mockito.mock(PostRequest.class);

        ServerResponse postResponse = new ServerResponse(200, Long.toString(id), null);
        doReturn(postResponse).when(mockPostRequest).send();
        // Test Method

        assertTrue(testBird.create(null, mockPostRequest));

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", parametersValues[0]);
        parameters.put("listImageURL", parametersValues[1]);
        parameters.put("heroImageURL", parametersValues[2]);
        parameters.put("soundURL", parametersValues[3]);
        parameters.put("aboutMe", parametersValues[4]);
        parameters.put("aboutMeVideoURL", parametersValues[5]);
        parameters.put("location", parametersValues[6]);
        parameters.put("locationImageURL", parametersValues[7]);
        parameters.put("diet", parametersValues[8]);
        parameters.put("dietImageURL", parametersValues[9]);

        verify(mockPostRequest, times(1)).addParameters(parameters);
        assertEquals(testBird.getId(), id);
    }

    /**
     * Creates a blank bird object, mock get request which returns a response when
     * the request is sent. Then we try and read the bird from the server without
     * the id being set, then once the id is set we read the bird. Then each
     * parameter read from the server is compared to the parameetrs set on the bird
     * object.
     */
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

    /**
     * Creates a mock patch request and response for when the request is sent. It
     * changes the parameters of the test bird and puts the new parameters into a
     * hashmap and compares the hashmap to what was added to the request in the
     * method under test.
     */
    @Test
    void canUpdateBird() {
        PatchRequest mockPatchRequest = Mockito.mock(PatchRequest.class);
        ServerResponse patchResponse = new ServerResponse(200, "", null);
        doReturn(patchResponse).when(mockPatchRequest).send();

        String newBirdieName = "Dophelia";
        String newBirdieListImageURL = "listimageimageimage.png";
        String newBirdieHeroImageURL = "quack.png";
        String newBirdieSoundURL = "squaaaaaark.mp3";
        String newBirdieAboutMe = "tall and beautiful";
        String newBirdieAboutMeVideoURL = "strollAroundTheLake.mp4";
        String newBirdieLocation = "lake";
        String newBirdieLocationImageURL = "pond.png";
        String newBirdieDiet = "squeaky cheese and cheeky squeeze";
        String newBirdieDietImageURL = "halloumi.png";

        testBird.setName(newBirdieName);
        testBird.setListImageURL(newBirdieListImageURL);
        testBird.setHeroImageURL(newBirdieHeroImageURL);
        testBird.setSoundURL(newBirdieSoundURL);
        testBird.setAboutMe(newBirdieAboutMe);
        testBird.setAboutMeVideoURL(newBirdieAboutMeVideoURL);
        testBird.setLocation(newBirdieLocation);
        testBird.setLocationImageURL(newBirdieLocationImageURL);
        testBird.setDiet(newBirdieDiet);
        testBird.setDietImageURL(newBirdieDietImageURL);

        try {
            testBird.update(null, mockPatchRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        testBird.setId(id);
        testBird.update(null, mockPatchRequest);

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", newBirdieName);
        parameters.put("listImageURL", newBirdieListImageURL);
        parameters.put("heroImageURL", newBirdieHeroImageURL);
        parameters.put("soundURL", newBirdieSoundURL);
        parameters.put("aboutMe", newBirdieAboutMe);
        parameters.put("aboutMeVideoURL", newBirdieAboutMeVideoURL);
        parameters.put("location", newBirdieLocation);
        parameters.put("locationImageURL", newBirdieLocationImageURL);
        parameters.put("diet", newBirdieDiet);
        parameters.put("dietImageURL", newBirdieDietImageURL);

        verify(mockPatchRequest, times(1)).addParameters(parameters);
        assertEquals(testBird.getId(), id);
    }

    /**
     * Creates a mock delete request and response for when the request is sent. Sets
     * the birds id then deletes it, verifies that the id is added to the request.
     */
    @Test
    void canDeleteBird() {
        DeleteRequest mockDeleteRequest = Mockito.mock(DeleteRequest.class);
        ServerResponse postResponse = new ServerResponse(200, Long.toString(id), null);
        doReturn(postResponse).when(mockDeleteRequest).send();

        testBird.setId(id);
        testBird.delete(null, mockDeleteRequest);

        verify(mockDeleteRequest, times(1)).addParameter("id", Long.toString(id));
    }
}
