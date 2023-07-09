package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.StudentService;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import java.security.Principal;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserService userService;

    //    private final StudentService studentService;

    public AccountResource(UserService userService, StudentService studentService) {
        this.userService = userService;
        //        this.studentService = studentService;
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @param principal the current user; resolves to {@code null} if not authenticated.
     * @return the current user.
     * @throws AccountResourceException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    @SuppressWarnings("unchecked")
    public AdminUserDTO getAccount(Principal principal) {
        if (principal instanceof AbstractAuthenticationToken) {
            AdminUserDTO user = userService.getUserFromAuthentication((AbstractAuthenticationToken) principal);
            Set<String> authorities = user.getAuthorities();
            if (authorities.contains("ROLE_ADMIN")) {
                user.setAuthorities(Set.of("ROLE_ADMIN"));
            } else if (authorities.contains("ROLE_CLERIC")) {
                user.setAuthorities(Set.of("ROLE_CLERIC"));
            }
            return user;
        } else {
            throw new AccountResourceException("User could not be found");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }
}
