package icarius.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDetails {
    private final String type;
    private final Boolean process;

    public static final String HERO_IMAGE_URL = "heroImageURL";
    public static final String LIST_IMAGE_URL = "listImageURL";
    public static final String VIDEO_URL = "aboutMeVideoURL";
    public static final String SOUND_URL = "soundURL";
    public static final String DIET_IMAGE_URL = "dietImageURL";
    public static final String LOCATION_IMAGE_URL = "locationImageURL";

    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
}
