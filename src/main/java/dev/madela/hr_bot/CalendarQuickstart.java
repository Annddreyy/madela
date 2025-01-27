package dev.madela.hr_bot;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;

public class CalendarQuickstart {
    private static final String APPLICATION_NAME = "Google Calendar API HRBot";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static Calendar service;

    public static void createEvent(String title, String start, String end) throws IOException, GeneralSecurityException {
        FileInputStream credentialsStream = new FileInputStream("credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singletonList(CalendarScopes.CALENDAR));
        credentials.refreshIfExpired();

        service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ZoneId zoneId = ZoneId.of(ZoneId.systemDefault().toString());
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.now());
        String offsetString = offset.getId();

        start = start + offsetString;
        end = end + offsetString;

        Event event = new Event()
                .setSummary(title);

        DateTime startDateTime = new DateTime(start);
        EventDateTime startTime = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone(ZoneId.systemDefault().toString());
        event.setStart(startTime);

        DateTime endDateTime = new DateTime(end);
        EventDateTime endTime = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone(ZoneId.systemDefault().toString());
        event.setEnd(endTime);

        String calendarId = "7b40dfb7d6086a335ee17ff269dae830e74542ef7d6b3e62d6441c55b26603b8@group.calendar.google.com";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s", event.getHtmlLink());
    }
}
