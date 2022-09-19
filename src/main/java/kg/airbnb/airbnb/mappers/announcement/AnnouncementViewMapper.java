package kg.airbnb.airbnb.mappers.announcement;

import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.models.Announcement;
import kg.airbnb.airbnb.models.Feedback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnnouncementViewMapper {


    public AnnouncementSaveResponse convertingEntityToDto(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        AnnouncementSaveResponse response = new AnnouncementSaveResponse(
                "Announcement saved successfully !",
                announcement.getId(),
                announcement.getImages(),
                announcement.getHouseType(),
                announcement.getMaxGuests(),
                announcement.getPrice(),
                announcement.getTitle(),
                announcement.getDescription(),
                announcement.getLocation().getRegion().getId(),
                announcement.getLocation().getRegion().getRegionName(),
                announcement.getLocation().getCity(),
                announcement.getLocation().getAddress()
        );
        return response;

    }

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
        response.setPrice(announcement.getPrice());
        response.setUserID(announcement.getOwner().getId());
        response.setOwnerImage(announcement.getOwner().getImage());
        response.setOwnerFullName(announcement.getOwner().getFullName());
        response.setOwnerEmail(announcement.getOwner().getEmail());
        response.setOwnerPhoneNumber(announcement.getOwner().getPhoneNumber());
        response.setLikeCount(announcement.getLike());
        response.setBookmarkCount(announcement.getBookmark());
        response.setViewAnnouncementCount(announcement.getViewAnnouncementHistoryCount());
        response.setColorOfLike(announcement.getColorOfLike());
        response.setColorOfBookmark(announcement.getColorOfBookmark());
        response.setRegionId(announcement.getLocation().getRegion().getId());
        response.setRegionName(announcement.getLocation().getRegion().getRegionName());
        response.setTownProvince(announcement.getLocation().getCity());
        return response;
    }

    public List<AdminPageAnnouncementResponse> viewAllAdminPageAnnouncementResponses(List<Announcement> announcements) {
        List<AdminPageAnnouncementResponse> adminPageAnnouncementResponses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            adminPageAnnouncementResponses.add(viewAdminPageAnnouncementResponse(announcement));
        }
        return adminPageAnnouncementResponses;
    }

    public AdminPageApplicationsAnnouncementResponse entityToDtoConver(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        AdminPageApplicationsAnnouncementResponse response = new AdminPageApplicationsAnnouncementResponse();
        response.setAnnouncementId(announcement.getId());
        response.setTitle(announcement.getTitle());
        response.setImages(announcement.getImages());
        response.setHouseType(announcement.getHouseType());
        response.setMaxGuests(announcement.getMaxGuests());
        response.setLocation(announcement.getLocation().getFullAddress());
        response.setDescription(announcement.getDescription());
        response.setOwnerImage(announcement.getOwner().getImage());
        response.setOwnerFullName(announcement.getOwner().getFullName());
        response.setOwnerEmail(announcement.getOwner().getEmail());
        response.setOwnerPhoneNumber(announcement.getOwner().getPhoneNumber());
        response.setStatus(announcement.getStatus());

        return response;
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

    public Double calculateRating(Announcement announcement) {

        double rating = 0.0;
        int fives = 0;
        int fours = 0;
        int threes = 0;
        int twos = 0;
        int ones = 0;

        List<Feedback> allFeedbacksOfAnnouncement = announcement.getFeedbacks();
        List<Integer> ratings = new ArrayList<>();
        for (Feedback feedback : allFeedbacksOfAnnouncement) {
            if (feedback.getRating() != null) {
                ratings.add(feedback.getRating());
            }
        }

        if (ratings.size() <= 0) {
            rating = 0.0;
        } else {

            for (int i = 0; i < ratings.size(); i++) {
                if (ratings.get(i) == 5) {
                    fives++;
                } else if (ratings.get(i) == 4) {
                    fours++;
                } else if (ratings.get(i) == 3) {
                    threes++;
                } else if (ratings.get(i) == 2) {
                    twos++;
                } else if (ratings.get(i) == 1) {
                    ones++;
                }
            }
            //formula of getting rating of announcement
            rating = (5 * fives + 4 * fours + 3 * threes + 2 * twos + ones) / (double) ratings.size();
        }
        return rating;
    }

    public AnnouncementCardResponse viewCardAnnouncement(Announcement announcement) {

        if (announcement == null) {
            return null;
        }

        AnnouncementCardResponse response = new AnnouncementCardResponse();
        response.setId(announcement.getId());
        response.setTitle(announcement.getTitle());
        response.setDescription(announcement.getDescription());
        response.setPrice(announcement.getPrice());
        response.setMaxGuests(announcement.getMaxGuests());
        response.setLocation(announcement.getLocation().getAddress() + ", " +
                announcement.getLocation().getCity() + ", " +
                announcement.getLocation().getRegion().getRegionName());
        response.setImages(announcement.getImages());
        response.setStatus(announcement.getStatus());
        double a = 0;
        double b = 0;
        for (Feedback f : announcement.getFeedbacks()) {
            a = a + f.getRating();
            b++;
        }
        double rating = a / b;
        response.setRating(rating);
        response.setType(String.valueOf(announcement.getHouseType()));
        return response;
    }

    public List<AnnouncementCardResponse> viewCard(List<Announcement> announcements) {
        List<AnnouncementCardResponse> responses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            responses.add(viewCardAnnouncement(announcement));
        }
        return responses;
    }

    public AnnouncementSearchResponse entityToDtoConversion(Announcement announcement) {
        if (announcement == null) {
            return null;
        }
        AnnouncementSearchResponse response = new AnnouncementSearchResponse();
        response.setAnnouncementId(announcement.getId());
        response.setAnnouncementInfo(announcement.getLocation().getRegion().getRegionName()
                + ", " + announcement.getLocation().getCity()
                + ", " + announcement.getLocation().getAddress()
                + ", " + announcement.getHouseType());
        return response;
    }

    public List<AnnouncementSearchResponse> getViewAllSearchAnnouncements(List<Announcement> announcements) {
        List<AnnouncementSearchResponse> responses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            responses.add(entityToDtoConversion(announcement));
        }
        return responses;
    }
}
