package com.project.eventManagement.service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.eventManagement.entity.Event;
import com.project.eventManagement.entity.User;
import com.project.eventManagement.exception.UnAuthorizedAccessException;
import com.project.eventManagement.exception.UserNotFoundException;
import com.project.eventManagement.repository.EventRepository;
import com.project.eventManagement.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found with this email :" + email));

        return user;

    }

    public List<Event> getCreatedEvents(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).get();
        if (user.getAuthority().equals("ADMIN")) {
            List<Event> createdEvents = eventRepository.findByCreator(userRepository.findById(userId).get());
            return createdEvents;
        } else {
            List<Event> createdEvents = eventRepository.findByCreator(user);
            return createdEvents;
        }

    }

    public Set<Event> getParticipatedEvents(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username).get();
        if (currentUser.getAuthority().equals("ADMIN")) {
            Set<Event> participatingEvents = userRepository.findById(userId).get().getParticipatingEvents();
            return participatingEvents;

        } else {
            Set<Event> participatingEvents = currentUser.getParticipatingEvents();
            return participatingEvents;
        }
    }

    public User getUserById(Long userId) {
        User currUser = eventService.getCurrentUser();
        System.out.println(currUser);
        if (currUser.getAuthority().equals("ADMIN")) {
            Optional<User> user = userRepository.findByUserId(userId);
            if (user.isPresent()) {
                return user.get();
            } else {
                throw new UserNotFoundException("User not found with the id:" + userId);
            }
        } else {
            throw new UnAuthorizedAccessException("You don't have access to view this user");
        }

    }
}
