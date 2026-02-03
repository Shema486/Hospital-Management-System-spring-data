package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.LoginUserRequest;
import com.hospital.Hms.dto.request.SystemUserRequest;
import com.hospital.Hms.dto.response.LoginUserResponse;
import com.hospital.Hms.dto.response.SystemUserResponse;

import com.hospital.Hms.entity.SystemUser;
import com.hospital.Hms.exception.BadRequestException;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.SystemUSerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SystemUserService {

    private final SystemUSerRepository uSerRepository;
    private final PasswordUtil passwordUtil;
    private final static String SYSTEM_USER = "systemUser";

    @Autowired
    public SystemUserService(SystemUSerRepository uSerRepository, PasswordUtil passwordUtil) {
        this.uSerRepository = uSerRepository;
        this.passwordUtil = passwordUtil;
    }

    @Transactional
    @CachePut(value = SYSTEM_USER, key = "#result.userId")
    public SystemUserResponse save(SystemUserRequest request){
        if(request ==null){
            throw new NotFoundException("User is required");
        }
        SystemUser user = mapToEntity(request);
        SystemUser saved  = uSerRepository.save(user);
        return mapToResponse(saved);
    }

    public LoginUserResponse login(LoginUserRequest request){
        SystemUser user = uSerRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new NotFoundException("Invalid username or password"));
        if (!user.getIsActive()) {
            throw new BadRequestException("User account is deactivated");
        }

        if (!passwordUtil.checkPassword(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        return new LoginUserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
    @Transactional
    @CachePut(value = SYSTEM_USER, key = "#result.userId")
    public SystemUserResponse updateUser(Long id, SystemUserRequest request) {
        SystemUser user = uSerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));

        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordUtil.hashPassword(request.getPassword()));
        }

        user.setRole(request.getRole());

        SystemUser saved = uSerRepository.save(user);

        return mapToResponse(saved);
    }


    @Transactional(readOnly = true)
    public Page<SystemUserResponse>findAllUser(String search,Pageable pageable){
        Page<SystemUser> page;
        if (search ==null || search.isBlank()){
            page = uSerRepository.findAll(pageable);
        }else {
            page = uSerRepository.findAllByUsername(search,pageable);
        }
        return page.map(this::mapToResponse);

    }

    @Transactional(readOnly = true)
    @Cacheable(value = SYSTEM_USER, key = "#userId")
    public SystemUserResponse findByUserId(Long userId) {
        SystemUser user = uSerRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return mapToResponse(user);
    }


    @Transactional
    @CacheEvict(value = SYSTEM_USER, key = "#id")
    public void deactivateUser(Long id){
        SystemUser user = uSerRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User not found"));
        user.setIsActive(false);
        uSerRepository.save(user);
    }


    public SystemUser mapToEntity (SystemUserRequest request){
        SystemUser user = new SystemUser();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
        user.setRole(request.getRole());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
    public SystemUserResponse mapToResponse(SystemUser systemUser){
        return new SystemUserResponse(
                systemUser.getUserId(),
                systemUser.getUsername(),
                systemUser.getFullName(),
                systemUser.getRole(),
                systemUser.getIsActive(),
                systemUser.getCreatedAt()
        );
    }
}
