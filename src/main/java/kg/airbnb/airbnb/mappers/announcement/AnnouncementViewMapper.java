package kg.airbnb.airbnb.mappers.announcement;

import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Feedback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        response.setUserID(announcement.getOwner().getId());
        response.setOwnerImage(announcement.getOwner().getImage());
        response.setOwnerFullName(announcement.getOwner().getFullName());
        response.setOwnerEmail(announcement.getOwner().getEmail());
        return response;
    }

    public List<AdminPageAnnouncementResponse> viewAllAdminPageAnnouncementResponses(List<Announcement> announcements) {
        List<AdminPageAnnouncementResponse> adminPageAnnouncementResponses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            adminPageAnnouncementResponses.add(viewAdminPageAnnouncementResponse(announcement));
        }
        return adminPageAnnouncementResponses;
    }


    public AdminPageAnnouncementResponse viewAdminPageAnnouncementResponse(Announcement announcement) {
        if (announcement == null) {
            return null;
        }

        AdminPageAnnouncementResponse adminPageAnnouncementResponse = new AdminPageAnnouncementResponse();
        adminPageAnnouncementResponse.setAnnouncementId(announcement.getId());
        adminPageAnnouncementResponse.setTitle(announcement.getTitle());
        adminPageAnnouncementResponse.setImages(announcement.getImages());
        adminPageAnnouncementResponse.setPrice(announcement.getPrice());
        adminPageAnnouncementResponse.setMaxGuests(announcement.getMaxGuests());
        adminPageAnnouncementResponse.setRating(calculateRating(announcement));
        adminPageAnnouncementResponse.setLocation(announcement.getLocation().getFullAddress());
        adminPageAnnouncementResponse.setStatus(announcement.getStatus());
        return adminPageAnnouncementResponse;


    }

    public List<UserAnnouncementResponse> viewAdminPageAllAnnouncementsResponse(List<Announcement> announcements) {
        List<UserAnnouncementResponse> userAnnouncementResponsesList = new ArrayList<>();
        for (Announcement announcement : announcements) {
            userAnnouncementResponsesList.add(
                    new UserAnnouncementResponse(announcement, calculateRating(announcement)));
        }
        return userAnnouncementResponsesList;
    }

    public Double calculateRating(Announcement announcement) {

        double rating = 0.0;
        int sumOfTotalRatings = 0;
        int fives = 0;
        int fours = 0;
        int threes = 0;
        int twos = 0;
        int ones = 0;

        List<Feedback> feedbacks = announcement.getFeedbacks();

        if (feedbacks.size() <= 0) {
            rating = 0;
        }

        sumOfTotalRatings = feedbacks.size();

        for (Feedback feedback : feedbacks) {
            if (feedback.getRating() == 5) {
                fives++;
            } else if (feedback.getRating() == 4) {
                fours++;
            } else if (feedback.getRating() == 3) {
                threes++;
            } else if (feedback.getRating() == 2) {
                twos++;
            } else if (feedback.getRating() == 1) {
                ones++;
            }
            //formula of getting rating of announcement
            rating = (5 * fives + 4 * fours + 3 * threes + 2 * twos + ones) / (double) (sumOfTotalRatings);
        }
        return rating;
    }

}
