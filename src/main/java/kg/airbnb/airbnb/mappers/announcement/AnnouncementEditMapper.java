package kg.airbnb.airbnb.mappers.announcement;

import kg.airbnb.airbnb.dto.requests.AnnouncementRequest;
import kg.airbnb.airbnb.models.Announcement;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AnnouncementEditMapper {

    public Announcement saveAnnouncement(AnnouncementRequest request) {
        if (request == null) {
            return null;
        }
        Announcement announcement = new Announcement();
        dtoToEntityConverting(request, announcement);
        return announcement;
    }

    private void dtoToEntityConverting(AnnouncementRequest request, Announcement announcement) {

        announcement.setTitle(request.getTitle());
        announcement.setDescription(request.getDescription());
        announcement.setImages(request.getImages());
        announcement.setStatus(announcement.getStatus());
        announcement.setPrice(request.getPrice());
        announcement.setMaxGuests(request.getMaxGuests());
        announcement.setHouseType(request.getHouseType());
        announcement.setCreatedAt(LocalDate.now());
    }
}
