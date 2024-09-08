package com.designershop.security;

import com.designershop.entities.UserProfile;
import com.designershop.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public MyUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByLogin(username);
        return new MyUser(userProfile);
    }
}
