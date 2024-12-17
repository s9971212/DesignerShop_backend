package com.designershop.users;

import com.designershop.entities.UserRole;
import com.designershop.exceptions.UserException;
import com.designershop.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public Set<UserRole> readUserRole(String userType, String sellerType, String designerType, String adminType) throws UserException {
        Set<String> roleIds = new HashSet<>();
        roleIds.add(userType);
        roleIds.add(sellerType);
        roleIds.add(designerType);
        roleIds.add(adminType);

        return userRoleRepository.findByRoleIds(roleIds);
    }
}
