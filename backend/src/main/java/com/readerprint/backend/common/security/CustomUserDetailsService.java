package com.readerprint.backend.common.security;

import com.readerprint.backend.user.entity.User;
import com.readerprint.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // UserDetail도 같이 조회해서 블락상태도 같이 체크하도록
        return userRepository.findByUserIdWithDetail(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));

    }

}
