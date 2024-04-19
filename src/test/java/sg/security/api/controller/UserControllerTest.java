package sg.security.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sg.security.api.data.*;
import sg.security.api.dto.ChangePassword;
import sg.security.api.dto.LoginRequest;
import sg.security.api.dto.User;
import sg.security.api.mapper.RoleMapper;
import sg.security.api.service.email.EmailVerificationService;
import sg.security.api.service.user.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController controller;

    @Mock
    UserService userService;
    @Mock
    EmailVerificationService emailVerificationService;

    @Mock
    RoleMapper roleMapper;
    MockMvc mockMvc;

    LoginRequest loginRequest;


    LoginRequestDTOData loginRequestDTOData;

    LoginResponseDTOData loginResponseDTOData;

    RegisterRequestDTOData registerRequestDTOData;

    UserData userData;

    String token;
    UserJpaData userJpaData;


    @BeforeEach
    void setUp() {

        this.registerRequestDTOData = new RegisterRequestDTOData();
        this.loginRequestDTOData = new LoginRequestDTOData();
        this.loginResponseDTOData = new LoginResponseDTOData();
        this.registerRequestDTOData = new RegisterRequestDTOData();
        this.token = String.valueOf(UUID.randomUUID());
        this.userData = new UserData();
        this.userJpaData = new UserJpaData();
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
    }

    @Nested
    class FindAll {

        private MvcResult performGet() throws Exception {

            return mockMvc.perform(get("/users")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void usersIsOk() throws Exception {

            when(UserControllerTest.this.userService.findAllUsers()).thenReturn(userData.getList());

            final MvcResult mvcResult = this.performGet();

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).findAllUsers();
        }

    }

    @Nested
    class FindById {

        private MvcResult performGet(Integer userId) throws Exception {

            return mockMvc.perform(get("/users/{userId}", userId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void userIsOk() throws Exception {

            when(UserControllerTest.this.userService.findById(anyInt())).thenReturn(userData.get(1));

            final MvcResult mvcResult = this.performGet(1);

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).findById(anyInt());
        }

    }

    @Nested
    class FindByUsername {

        private MvcResult performGet(String username) throws Exception {

            return mockMvc.perform(get("/users/username/{username}", username)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void usernameIsOk() throws Exception {

            when(UserControllerTest.this.userService.findByUsername(anyString())).thenReturn(userData.get(1));

            final MvcResult mvcResult = this.performGet("username");

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).findByUsername(anyString());
        }

    }

    @Nested
    class FindByEmail {

        private MvcResult performGet(String email) throws Exception {

            return mockMvc.perform(get("/users/email/{email}", email)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void usernameIsOk() throws Exception {

            when(UserControllerTest.this.userService.findByEmail(anyString())).thenReturn(userData.get(1));

            final MvcResult mvcResult = this.performGet("email@gmail.com");

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).findByEmail(anyString());
        }

    }

    @Nested
    class Update {

        private MvcResult performGet(Integer userId, User user) throws Exception {

            return mockMvc.perform(put("/users/{userId}/", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(user)))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void updateIsOk() throws Exception {

            doNothing().when(UserControllerTest.this.userService).update(anyInt(), any());

            final MvcResult mvcResult = this.performGet(1, userData.get(1));

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).update(anyInt(), any());
        }

    }

    @Nested
    class Change {

        private MvcResult performGet(ChangePassword changePassword) throws Exception {

            return mockMvc.perform(put("/users/change/password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(changePassword)))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void changeIsOk() throws Exception {

            doNothing().when(UserControllerTest.this.userService).changePassword(any());

            final MvcResult mvcResult = this.performGet(ChangePassword.builder().currentPassword("currentPassword").newPassword("newPassword").confirmationPassword("confirmationPassword").build());

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).changePassword(any());

        }

    }

    @Nested
    class Delete {

        private MvcResult performGet(Integer userId) throws Exception {

            return mockMvc.perform(delete("/users/{userId}", userId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void deleteIsOk() throws Exception {

            doNothing().when(UserControllerTest.this.userService).delete(anyInt());

            final MvcResult mvcResult = this.performGet(1);

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(UserControllerTest.this.userService).delete(anyInt());

        }

    }

}
