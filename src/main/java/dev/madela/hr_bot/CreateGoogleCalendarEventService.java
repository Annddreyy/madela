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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class GoogleCalendar {
    private static final String APPLICATION_NAME = "Google Calendar API HRBot";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static Calendar service;
    private static final Logger logger = LoggerFactory.getLogger(GoogleCalendar.class);

    public static void createEvent(String title, String start, String end) throws IOException, GeneralSecurityException {
        FileInputStream credentialsStream = new FileInputStream("credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singletonList(CalendarScopes.CALENDAR));
        credentials.refreshIfExpired();

        service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startLocalDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endLocalDateTime = LocalDateTime.parse(end, formatter);

        DateTime startDateTime = new DateTime(startLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        DateTime endDateTime = new DateTime(endLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        Event event = new Event()
                .setSummary(title);

        EventDateTime startTime = new EventDateTime()
                .setDateTime(startDateTime);
        event.setStart(startTime);

        EventDateTime endTime = new EventDateTime()
                .setDateTime(endDateTime);
        event.setEnd(endTime);

        String calendarId = "7b40dfb7d6086a335ee17ff269dae830e74542ef7d6b3e62d6441c55b26603b8@group.calendar.google.com";
        event = service.events().insert(calendarId, event).execute();

        logger.info("Event created: {}", event.getHtmlLink());
    }
}
