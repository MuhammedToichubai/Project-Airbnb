package kg.airbnb.airbnb.services.impl;

import kg.airbnb.airbnb.dto.responses.*;
import kg.airbnb.airbnb.enums.Role;
import kg.airbnb.airbnb.exceptions.ForbiddenException;
import kg.airbnb.airbnb.mappers.UserProfileViewMapper;
import kg.airbnb.airbnb.mappers.announcement.AnnouncementViewMapper;
import kg.airbnb.airbnb.mappers.booking.BookingViewMapper;
import kg.airbnb.airbnb.models.auth.User;
import kg.airbnb.airbnb.repositories.UserRepository;
import kg.airbnb.airbnb.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProfileViewMapper viewMapper;
    private final AnnouncementViewMapper announcementViewMapper;

    public UserServiceImpl(UserRepository userRepository
            , UserProfileViewMapper viewMapper, AnnouncementViewMapper announcementViewMapper) {
        this.userRepository = userRepository;
        this.viewMapper = viewMapper;
        this.announcementViewMapper = announcementViewMapper;
    }

    @Override
    public UserProfileResponse getUserBookingsAndAnnouncements() {

        User user = getAuthenticatedUser();
        return viewMapper.entityToDto(user);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() ->
                new ForbiddenException("An unregistered user cannot post an ad !"));
    }

    public SimpleResponse deleteUser(Long id) {
        User users = getAuthenticatedUser();
        if (users.getRole().equals(Role.ADMIN)) {
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            return new SimpleResponse("Пользователь успешно удалён!");
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    public List<UserResponse> getAllUser() {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            return UserProfileViewMapper.viewFindAllUser(userRepository.findAll());
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }

    public List<UserBookingsResponse> getAllBookings(Long id) {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            User users = userRepository.findById(id).get();
            return BookingViewMapper.viewAllBooking(users.getBookings());

        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }
    public List<UserAnnouncementResponse> getAllAnnouncements(Long id) {
        User user = getAuthenticatedUser();
        if (user.getRole().equals(Role.ADMIN)) {
            User users = userRepository.findById(id).get();
            return announcementViewMapper.viewAdminPageAllAnnouncementsResponse(users.getAnnouncements());
        } else {
            throw new ForbiddenException("Only admin can access this page!");
        }
    }
}
