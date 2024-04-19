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
import sg.security.api.data.LoginRequestDTOData;
import sg.security.api.data.LoginResponseDTOData;
import sg.security.api.data.RegisterRequestDTOData;
import sg.security.api.dto.LoginRequest;
import sg.security.api.dto.RegisterRequest;
import sg.security.api.service.auth.AuthServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;
    @Mock
    AuthServiceImpl authService;
    MockMvc mockMvc;
    LoginRequestDTOData loginRequestDTOData;
    LoginResponseDTOData loginResponseDTOData;
    RegisterRequestDTOData registerRequestDTOData;
    String token;

    @BeforeEach
    void setUp() {

        this.loginRequestDTOData = new LoginRequestDTOData();
        this.loginResponseDTOData = new LoginResponseDTOData();
        this.registerRequestDTOData = new RegisterRequestDTOData();
        this.token = UUID.randomUUID().toString();
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.authController).build();

    }

    @Nested
    class Login {

        private MvcResult performGet(LoginRequest loginRequest) throws Exception {
            return mockMvc.perform(post("/auths/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(loginRequest)))
                    .andDo(print())
                    .andReturn();
        }

        @Test
        void whenLoginIsOk() throws Exception {

            when(AuthControllerTest.this.authService.login(any())).thenReturn(loginResponseDTOData.get(1));

            final MvcResult mvcResult = this.performGet(loginRequestDTOData.get(1));

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(AuthControllerTest.this.authService).login(any());

        }

        @Test
        void whenLoginIsBadRequest() throws Exception {

            final MvcResult mvcResult = this.performGet(null);

            assertEquals(400, mvcResult.getResponse().getStatus());
        }

    }


    @Nested
    class Register {

        private MvcResult performGet(RegisterRequest registerRequest) throws Exception {
            return mockMvc.perform(post("/auths/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper()
                                    .registerModule(new JavaTimeModule())
                                    .writeValueAsString(registerRequest)))
                    .andDo(print())
                    .andReturn();
        }

        @Test
        void whenRegisterIsOk() throws Exception {

            doNothing().when(AuthControllerTest.this.authService).register(any(), any());

            final MvcResult mvcResult = this.performGet(registerRequestDTOData.get(1));

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(AuthControllerTest.this.authService).register(any(), any());
        }

        @Test
        void whenRegisterIsBadRequest() throws Exception {

            final MvcResult mvcResult = this.performGet(null);

            assertEquals(400, mvcResult.getResponse().getStatus());
        }
    }

    @Nested
    class Email {

        private MvcResult performGet(String token) throws Exception {
            return mockMvc.perform(get("/auths/email/verification")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .queryParam("token", new ObjectMapper().writeValueAsString(token)))
                    .andDo(print())
                    .andReturn();
        }

        @Test
        void whenVerifyEmailIsOk() throws Exception {

            doNothing().when(AuthControllerTest.this.authService).sendEmailConfirmation(any());

            final MvcResult mvcResult = this.performGet(token);

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(AuthControllerTest.this.authService).sendEmailConfirmation(any());

        }
    }
}
