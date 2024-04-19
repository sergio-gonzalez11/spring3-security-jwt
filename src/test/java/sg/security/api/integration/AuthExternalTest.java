package sg.security.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import sg.security.api.data.RegisterRequestDTOData;
import sg.security.api.dto.LoginRequest;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class AuthExternalTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    LoginRequest loginRequest;

    RegisterRequestDTOData registerRequestDTOData;

    String token;

    private AuthenticationTest authenticationTest;

    @BeforeEach
    void setUp() {

        this.loginRequest = LoginRequest.builder()
                .username("sergioxl")
                .password("123456")
                .build();

        this.registerRequestDTOData = new RegisterRequestDTOData();
        this.authenticationTest = new AuthenticationTest(mockMvc);
    }

    @Test
    void whenUnauthenticatedThen401() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Nested
    class Login {

        @Test
        void whenLoginIsOk() throws Exception {

            authenticationTest.login(loginRequest.getUsername(), loginRequest.getPassword())
                    .andExpect(status().isOk());
        }

        @Test
        void whenLoginIsBadRequest() throws Exception {

            authenticationTest.login(loginRequest.getUsername(), loginRequest.getPassword())
                    .andExpect(status().isBadRequest());
        }

  /*      @Test
        @WithMockUser(roles = "accessAdmin")
        void test_login_returnsTokenResponse() throws Exception {

            MvcResult result  = mockMvc.perform(post("/auths/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(AuthControllerTest.this.loginRequestDTOData.get(1))))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            LoginResponse response = mapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class);

            mockMvc.perform(get("/roles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()))
                    .andDo(print())
                    .andExpect(status().isOk());
        }*/
    }


    @Nested
    class Register {
        @Test
        void whenRegisterIsOk() throws Exception {

            authenticationTest.register(registerRequestDTOData.get(1))
                    .andExpect(status().isOk());
        }

        @Test
        void whenRegisterIsBadRequest() throws Exception {

            authenticationTest.register(registerRequestDTOData.get(1))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class Email {
        @Test
        void whenVerifyEmailIsOk() throws Exception {

            authenticationTest.verifyEmail(token)
                    .andExpect(status().isOk());
        }

        @Test
        void whenVerifyEmailIsIsBadRequestl() throws Exception {

            authenticationTest.verifyEmail(token)
                    .andExpect(status().isBadRequest());
        }
    }
}
