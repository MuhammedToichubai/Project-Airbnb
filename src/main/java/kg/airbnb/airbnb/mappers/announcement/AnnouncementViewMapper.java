package kg.airbnb.airbnb.mappers.announcement;

import kg.airbnb.airbnb.dto.response.AnnouncementInnerPageResponse;
import kg.airbnb.airbnb.models.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementViewMapper {

    public AnnouncementInnerPageResponse entityToDtoConverting(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        AnnouncementInnerPageResponse response = new AnnouncementInnerPageResponse();
        response.setId(announcement.getId());
        response.setImages(announcement.getImages());
        response.setHouseType(announcement.getHouseType());
        response.setMaxGuests(announcement.getMaxGuests());
        response.setTitle(announcement.getTitle());
        response.setLocation(announcement.getLocation().getAddress());
        response.setDescription(announcement.getDescription());
        response.setOwnerImage(announcement.getOwner().getImage());
        response.setOwnerFullName(announcement.getOwner().getFullName());
        response.setOwnerEmail(announcement.getOwner().getEmail());
        return response;
    }

}
