package interfaces;

import java.util.Set;

public interface CarrierInterface {

    String getId();

    String getName();

    String getPhone();

    String getEmail();

    boolean isActive();

    String getImage();

    int getAmountOfCharacters();

    boolean isNumOnly();

    String getPrefix();

    Set<TrackAndTraceInterface> getTrackAndTraceInterfaces();

}
