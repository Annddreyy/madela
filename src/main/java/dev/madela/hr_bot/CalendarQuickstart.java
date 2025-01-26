package dev.madela.hr_bot;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

public class CalendarQuickstart {
    private static final String APPLICATION_NAME = "Google Calendar API HRBot";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static Calendar service;
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        FileInputStream credentialsStream = new FileInputStream("credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singletonList(CalendarScopes.CALENDAR));
        credentials.refreshIfExpired();

        service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
        listUpcomingEvents();
        createEvent("Тест", "2025-01-26T19:00:00", "2025-01-27T22:00:00");
    }

    private static void listUpcomingEvents() throws IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("7b40dfb7d6086a335ee17ff269dae830e74542ef7d6b3e62d6441c55b26603b8@group.calendar.google.com")
                .setMaxResults(10)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .setTimeMin(now)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("Предстоящих событий не найдено");
        } else {
            System.out.println("Предстоящие события:");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }

    public static void createEvent(String title, String start, String end) throws IOException {
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
