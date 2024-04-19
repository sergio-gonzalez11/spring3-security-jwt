package sg.security.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sg.security.api.data.RegisterRequestDTOData;
import sg.security.api.dto.LoginRequest;
import sg.security.api.dto.LoginResponse;
import sg.security.api.dto.RegisterRequest;
import sg.security.api.service.auth.AuthServiceImpl;
import sg.security.api.service.role.RoleServiceImpl;
import sg.security.api.service.user.UserDetailsServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationTest {

    private final MockMvc mockMvc;

    private LoginRequest loginRequest;

    private RegisterRequestDTOData registerRequestDTOData;


    public AuthenticationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    protected ResultActions login(String username, String password) throws Exception {

        return mockMvc.perform(post("/auths/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                        LoginRequest.builder()
                                .username(username)
                                .password(password)
                                .build()
                )))
                .andDo(print())
                .andExpect(status().isOk());
    }


    protected ResultActions register(RegisterRequest registerRequest) throws Exception {

        return mockMvc.perform(post("/auths/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(registerRequest)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    protected ResultActions verifyEmail(String token) throws Exception {

        return mockMvc.perform(get("/auths/email/verification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("token", new ObjectMapper().writeValueAsString(token)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
