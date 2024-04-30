package sg.security.api.service.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.data.dto.RoleData;
import sg.security.api.data.jpa.RoleJpaData;
import sg.security.api.data.jpa.UserJpaData;
import sg.security.api.dto.role.Role;
import sg.security.api.entity.role.RoleJpa;
import sg.security.api.exception.RoleNotFoundException;
import sg.security.api.mapper.RoleMapper;
import sg.security.api.repository.role.RoleJpaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleJpaRepository roleJpaRepository;

    @Mock
    RoleMapper roleMapper;

    @InjectMocks
    RoleServiceImpl roleService;

    UserJpaData userJpaData;

    RoleJpaData roleJpaData;

    RoleData roleData;


    @BeforeEach
    void setUp() {
        this.userJpaData = new UserJpaData();
        this.roleJpaData = new RoleJpaData();
        this.roleData = new RoleData();
    }


    @Nested
    class FindAll {

        @Test
        void findAll() {

            when(RoleServiceTest.this.roleJpaRepository.findAll()).thenReturn(roleJpaData.getList());

            List<Role> roleList = roleService.findAll();
            assertNotNull(roleList);
            assertEquals(roleJpaData.getList().size(), roleList.size());

            verify(RoleServiceTest.this.roleJpaRepository).findAll();

        }

        @Test
        void findAllIn() {

            when(RoleServiceTest.this.roleJpaRepository.findAllByIdIn(anyList())).thenReturn(roleJpaData.getList());

            List<Role> roleList = roleService.findByIds(List.of(1, 2, 3, 4, 5));
            assertNotNull(roleList);
            assertEquals(roleJpaData.getList().size(), roleList.size());

            verify(RoleServiceTest.this.roleJpaRepository).findAllByIdIn(anyList());

        }
    }

    @Nested
    class FindRole {

        @Test
        void findByRole() {

            when(RoleServiceTest.this.roleJpaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(roleJpaData.get(1)));
            when(RoleServiceTest.this.roleMapper.toDTO(any(RoleJpa.class))).thenReturn(roleData.get(1));

            Role role = roleService.findByRoleId(1);
            assertNotNull(role);

            verify(RoleServiceTest.this.roleJpaRepository).findById(anyInt());

        }

        @Test
        void findByRoleException() {

            when(RoleServiceTest.this.roleJpaRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(RoleNotFoundException.class, () -> RoleServiceTest.this.roleService.findByRoleId(1));

            verify(RoleServiceTest.this.roleJpaRepository).findById(anyInt());

        }
    }

    @Nested
    class FindRoleName {

        @Test
        void findByRoleName() {

            when(RoleServiceTest.this.roleJpaRepository.findByName(anyString())).thenReturn(Optional.ofNullable(roleJpaData.get(1)));
            when(RoleServiceTest.this.roleMapper.toDTO(any(RoleJpa.class))).thenReturn(roleData.get(1));

            Role role = roleService.findByRoleName("name");
            assertNotNull(role);

            verify(RoleServiceTest.this.roleJpaRepository).findByName(anyString());

        }

        @Test
        void findByRoleNameException() {

            when(RoleServiceTest.this.roleJpaRepository.findByName(anyString())).thenReturn(Optional.empty());

            assertThrows(RoleNotFoundException.class, () -> RoleServiceTest.this.roleService.findByRoleName("name"));

            verify(RoleServiceTest.this.roleJpaRepository).findByName(anyString());

        }
    }

    @Nested
    class CreateRole {

        @Test
        void save() {

            when(RoleServiceTest.this.roleJpaRepository.save(any())).thenReturn((roleJpaData.get(1)));
            when(RoleServiceTest.this.roleMapper.toJPA(any(Role.class))).thenReturn(roleJpaData.get(1));

            roleService.save(roleData.get(1));

            verify(RoleServiceTest.this.roleJpaRepository).save(any());

        }

        @Test
        void saveException() {

            when(RoleServiceTest.this.roleJpaRepository.save(any())).thenReturn(Optional.empty());

            assertThrows(Exception.class, () -> RoleServiceTest.this.roleService.save(null));

            verify(RoleServiceTest.this.roleJpaRepository).save(any());

        }
    }

    @Nested
    class UpdateRole {

        @Test
        void update() throws Exception {

            when(RoleServiceTest.this.roleJpaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(roleJpaData.get(1)));
            when(RoleServiceTest.this.roleJpaRepository.save(any())).thenReturn((roleJpaData.get(1)));

            roleService.update(roleData.get(1));

            verify(RoleServiceTest.this.roleJpaRepository).findById(anyInt());
            verify(RoleServiceTest.this.roleJpaRepository).save(any());

        }

        @Test
        void updateException() {

            when(RoleServiceTest.this.roleJpaRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(RoleNotFoundException.class, () -> RoleServiceTest.this.roleService.update(roleData.get(1)));

            verify(RoleServiceTest.this.roleJpaRepository).findById(anyInt());

        }
    }

    @Nested
    class Delete {

        @Test
        void deleteById() {

            doNothing().when(RoleServiceTest.this.roleJpaRepository).deleteById(anyInt());

            roleService.delete(1);

            verify(RoleServiceTest.this.roleJpaRepository).deleteById(anyInt());

        }
    }


}
