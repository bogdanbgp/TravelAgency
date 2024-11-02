package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.dto.request.user.AddUserRequest;
import com.travelagency.travelagency.dto.request.user.UpdateUserRequest;
import com.travelagency.travelagency.dto.response.user.UserResponse;
import com.travelagency.travelagency.dto.response.user.UpdateUserResponse;
import com.travelagency.travelagency.entity.Role;
import com.travelagency.travelagency.entity.Tour;
import com.travelagency.travelagency.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Confirm UserMapper is a Spring Component (so Spring can recognize and inject it)
@Component
public class UserMapper {

    // Mapping from AddUserRequest to User entity
    public User fromRequest(AddUserRequest addUserRequest) {
        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setFirstName(addUserRequest.getFirstName());
        user.setLastName(addUserRequest.getLastName());
        user.setAge(addUserRequest.getAge());
        user.setEmail(addUserRequest.getEmail());
        user.setMobile(addUserRequest.getMobile());
        user.setPassword(addUserRequest.getPassword());

        // Set roles from role IDs if provided
        if (addUserRequest.getRoleIds() != null) {
            List<Role> roles = new ArrayList<>();
            for (Long roleId : addUserRequest.getRoleIds()) {
                Role role = new Role();
                role.setId(roleId);
                roles.add(role);
            }
            user.setRoles(roles);
        }
        // Set tours from tour IDs if provided
        if (addUserRequest.getTourIds() != null && !addUserRequest.getTourIds().isEmpty()) {
            List<Tour> tours = new ArrayList<>();
            for (Long tourId : addUserRequest.getTourIds()) {
                Tour tour = new Tour();
                tour.setId(tourId);
                tours.add(tour);
            }
            user.setTours(tours);
        }
        return user; // Return the constructed User entity
    }
                // Mapping from User entity to UserResponse DTO
                public UserResponse toResponse(User user) {
                    List<String> roleNames = user.getRoles().stream()
                            .map(Role::getRoleName)
                            .collect(Collectors.toList());
                    List<String> tourNames = user.getTours().stream()
                            .map(Tour::getTourName)
                            .collect(Collectors.toList());

                    return new UserResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getAge(),
                            user.getEmail(),
                            user.getMobile(),
                            roleNames,
                            tourNames
                    );
                }



    // Mapping from UpdateUserRequest to existing User entity
    public User fromUpdateRequest(UpdateUserRequest updateUserRequest, User existingUser) {
        existingUser.setUsername(updateUserRequest.getUsername());
        existingUser.setFirstName(updateUserRequest.getFirstName());
        existingUser.setLastName(updateUserRequest.getLastName());
        existingUser.setAge(updateUserRequest.getAge());
        existingUser.setEmail(updateUserRequest.getEmail());
        existingUser.setMobile(updateUserRequest.getMobile());
        // Only update password if it's provided
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            existingUser.setPassword(updateUserRequest.getPassword());
        }

        return existingUser; // Return the updated User entity
    }
                // Mapping from User entity to UpdateUserResponse DTO
                public UpdateUserResponse toUpdateResponse(User user) {
                    return new UpdateUserResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getAge(),
                            user.getEmail(),
                            user.getMobile()
                    );
                }
}
