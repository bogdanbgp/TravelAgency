package com.travelagency.travelagency.dto.request.role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleToUserRequest {


    @NotNull(message = "User ID should not be null")
    private Long userId; // ID of the user who receives the role

    @NotNull(message = "Role ID should not be null")
    private Long roleId; // ID of the role to be assigned to the user
}

// 1. În serviciu, clasa este folosită pentru a asocia un rol unui utilizator prin intermediul userId și roleId.
// 2. Se caută utilizatorul prin userId folosind userRepository.findById(userId).
// 3. Se caută rolul prin roleId folosind roleRepository.findById(roleId).
// 4. user.addRole(rol) - se adaugă rolul utilizatorului.

// Stocăm userId și roleId pentru a aplica roluri utilizatorilor.
