package sg.security.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import sg.security.api.data.dto.LoginRequestData;
import sg.security.api.data.dto.LoginResponseData;
import sg.security.api.data.dto.RegisterRequestData;
import sg.security.api.data.dto.RoleData;
import sg.security.api.data.jpa.UserJpaData;
import sg.security.api.dto.auth.LoginRequest;
import sg.security.api.dto.role.CreateRole;
import sg.security.api.mapper.RoleMapper;
import sg.security.api.service.auth.AuthServiceImpl;
import sg.security.api.service.role.RoleServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @InjectMocks
    RoleController roleController;

    @Mock
    AuthController authController;
    @Mock
    AuthServiceImpl authService;

    @Mock
    RoleServiceImpl roleService;

    @Mock
    RoleMapper roleMapper;
    MockMvc mockMvc;

    LoginRequest loginRequest;


    LoginRequestData loginRequestData;

    LoginResponseData loginResponseData;

    RegisterRequestData registerRequestData;

    RoleData roleData;

    String token;
    UserJpaData userJpaData;


    @BeforeEach
    void setUp() {

        this.registerRequestData = new RegisterRequestData();
        this.loginRequestData = new LoginRequestData();
        this.loginResponseData = new LoginResponseData();
        this.registerRequestData = new RegisterRequestData();
        this.token = String.valueOf(UUID.randomUUID());
        this.roleData = new RoleData();
        this.userJpaData = new UserJpaData();
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.roleController).build();
    }

    @Nested
    class FindAll {

        private MvcResult performGet() throws Exception {

            return mockMvc.perform(get("/roles")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void rolesIsOk() throws Exception {

            when(RoleControllerTest.this.roleService.findAll()).thenReturn(roleData.getList());

            final MvcResult mvcResult = this.performGet();

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(RoleControllerTest.this.roleService).findAll();
        }

    }


    @Nested
    class FindById {

        private MvcResult performGet(Integer roleId) throws Exception {

            return mockMvc.perform(get("/roles/{roleId}", roleId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void roleIsOk() throws Exception {

            when(RoleControllerTest.this.roleService.findByRoleId(anyInt())).thenReturn(roleData.get(1));

            final MvcResult mvcResult = this.performGet(1);

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(RoleControllerTest.this.roleService).findByRoleId(anyInt());
        }

    }

    @Nested
    class FindByName {

        private MvcResult performGet(String roleName) throws Exception {

            return mockMvc.perform(get("/roles/name/{roleName}", roleName)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void roleNameIsOk() throws Exception {

            when(RoleControllerTest.this.roleService.findByRoleName(anyString())).thenReturn(roleData.get(1));

            final MvcResult mvcResult = this.performGet("roleName");

            assertEquals(200, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(RoleControllerTest.this.roleService).findByRoleName(anyString());
        }

    }

    @Nested
    class Create {

        private MvcResult performGet(CreateRole createRole) throws Exception {

            return mockMvc.perform(post("/roles")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(createRole)))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void createIsOk() throws Exception {

            doNothing().when(RoleControllerTest.this.roleService).save(any());

            final MvcResult mvcResult = this.performGet(CreateRole.builder().name("name").description("description").build());

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(RoleControllerTest.this.roleService).save(any());
        }

    }

    @Nested
    class Delete {

        private MvcResult performGet(Integer roleId) throws Exception {

            return mockMvc.perform(delete("/roles/{roleId}", roleId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andReturn();
        }


        @Test
        void deleteIsOk() throws Exception {

            doNothing().when(RoleControllerTest.this.roleService).delete(anyInt());

            final MvcResult mvcResult = this.performGet(1);

            assertEquals(204, mvcResult.getResponse().getStatus());
            assertNotNull(mvcResult);

            verify(RoleControllerTest.this.roleService).delete(anyInt());
        }

    }

}
