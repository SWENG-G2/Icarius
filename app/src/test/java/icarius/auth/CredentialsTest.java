package icarius.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CredentialsTest {
    /**
     * Test Credentials can be made with username and password
     * Test that we can retrieve username and password from credentials
     */
    @Test
    public void canCreateCredentialsTest() {
        String TEST_USERNAME = "test_username";
        String TEST_PASSWORD = "test_password";

        Credentials credentials = new Credentials(TEST_USERNAME, TEST_PASSWORD);
        assertEquals(TEST_USERNAME, credentials.getUsername());
        assertEquals(TEST_PASSWORD, credentials.getPassword());
    }
}
