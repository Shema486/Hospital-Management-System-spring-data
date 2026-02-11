package com.hospital.Hms.service;

import com.hospital.Hms.entity.SystemUser;
import com.hospital.Hms.entity.UserInfo;
import com.hospital.Hms.repository.SystemUSerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    private final SystemUSerRepository uSerRepository;

    public UserDetailService(SystemUSerRepository uSerRepository){
        this.uSerRepository = uSerRepository;
    }
    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = uSerRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));

        return new UserInfo(user);

    }
}
