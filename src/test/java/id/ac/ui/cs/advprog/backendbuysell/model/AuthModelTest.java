package id.ac.ui.cs.advprog.backendbuysell.model;

import com.google.common.collect.Sets;
import id.ac.ui.cs.advprog.backendbuysell.auth.dto.UserProfileResponse;
import id.ac.ui.cs.advprog.backendbuysell.auth.exception.NoUserProfileExistException;
import id.ac.ui.cs.advprog.backendbuysell.auth.model.ApplicationUserPermission;
import id.ac.ui.cs.advprog.backendbuysell.auth.model.ApplicationUserRole;
import id.ac.ui.cs.advprog.backendbuysell.auth.model.User;
import id.ac.ui.cs.advprog.backendbuysell.auth.model.UserProfile;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static id.ac.ui.cs.advprog.backendbuysell.auth.model.ApplicationUserPermission.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthModelTest {
    @Test
    public void testGetPermission() {
        assertEquals("order:read_all", ApplicationUserPermission.ORDER_READ_ALL.getPermission());
        assertEquals("order:read_self", ORDER_READ_SELF.getPermission());
    }
    @Test
    public void testGetPermissions() {
        Set<ApplicationUserPermission> expectedPermissions = Sets.newHashSet(ORDER_READ_ALL, ORDER_DELETE);
        assertEquals(expectedPermissions, ApplicationUserRole.ADMIN.getPermissions());

        expectedPermissions = Sets.newHashSet(ORDER_CREATE, ORDER_READ_SELF, ORDER_UPDATE);
        assertEquals(expectedPermissions, ApplicationUserRole.USER.getPermissions());
    }

    @Test
    public void testGetGrantedAuthority() {
        Set<SimpleGrantedAuthority> expectedAuthorities = Stream.of(ORDER_READ_ALL, ORDER_DELETE)
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        expectedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        assertEquals(expectedAuthorities, ApplicationUserRole.ADMIN.getGrantedAuthority());

        expectedAuthorities = Stream.of(ORDER_CREATE, ORDER_READ_SELF, ORDER_UPDATE)
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        expectedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        assertEquals(expectedAuthorities, ApplicationUserRole.USER.getGrantedAuthority());
    }
    @Test
    public void testGetAuthorities_Admin() {
        User user = User.builder()
                .email("admin@example.com")
                .password("password")
                .role("ADMIN")
                .active(true)
                .build();

        Set<SimpleGrantedAuthority> expectedAuthorities = ApplicationUserRole.ADMIN.getGrantedAuthority();
        assertEquals(expectedAuthorities, user.getAuthorities());
    }

    @Test
    public void testGetAuthorities_User() {
        User user = User.builder()
                .email("user@example.com")
                .password("password")
                .role("USER")
                .active(true)
                .build();

        Set<SimpleGrantedAuthority> expectedAuthorities = ApplicationUserRole.USER.getGrantedAuthority();
        assertEquals(expectedAuthorities, user.getAuthorities());
    }

    @Test
    public void testGetUsername() {
        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .role("ADMIN")
                .active(true)
                .build();

        assertEquals("test@example.com", user.getUsername());
    }

    @Test
    public void testIsActive() {
        User activeUser = User.builder()
                .email("active@example.com")
                .password("password")
                .role("ADMIN")
                .active(true)
                .build();

        User inactiveUser = User.builder()
                .email("inactive@example.com")
                .password("password")
                .role("ADMIN")
                .active(false)
                .build();

        assertTrue(activeUser.isEnabled() && activeUser.isAccountNonExpired() && activeUser.isAccountNonLocked() && activeUser.isCredentialsNonExpired());
        assertFalse(inactiveUser.isEnabled() && inactiveUser.isAccountNonExpired() && inactiveUser.isAccountNonLocked() && inactiveUser.isCredentialsNonExpired());
    }
    @Test
    public void testGetSetUserName() {
        UserProfile profile = new UserProfile();
        profile.setUserName("John Doe");

        assertEquals("John Doe", profile.getUserName());
    }

    @Test
    public void testBuilder() {
        UserProfileResponse response = UserProfileResponse.builder()
                .id(1)
                .email("test@example.com")
                .userName("John Doe")
                .address("123 Main St")
                .phoneNumber("123-456-7890")
                .about("I love buying and selling!")
                .build();

        assertEquals(1, response.getId().intValue());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("John Doe", response.getUserName());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("123-456-7890", response.getPhoneNumber());
        assertEquals("I love buying and selling!", response.getAbout());
    }

    @Test
    public void testAllArgsConstructor() {
        UserProfileResponse response = new UserProfileResponse(1, "test@example.com", "John Doe", "123 Main St", "123-456-7890", "I love buying and selling!");

        assertEquals(1, response.getId().intValue());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("John Doe", response.getUserName());
        assertEquals("123 Main St", response.getAddress());
        assertEquals("123-456-7890", response.getPhoneNumber());
        assertEquals("I love buying and selling!", response.getAbout());
    }

    @Test
    public void testExceptionMessage() {
        NoUserProfileExistException exception = new NoUserProfileExistException();
        assertNotNull(exception);
    }

}
