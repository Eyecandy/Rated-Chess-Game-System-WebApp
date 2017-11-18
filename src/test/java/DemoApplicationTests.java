import io.muic.designpattern.configurations.SecurityConfiguration;
import io.muic.designpattern.model.User;

import io.muic.designpattern.repositories.UserRepository;
import io.muic.designpattern.services.UserService;
import io.muic.designpattern.services.UserServiceImpl;
import org.hibernate.service.spi.InjectService;
import org.junit.Before;
import org.junit.Test;




import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)

public class DemoApplicationTests {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @Before
    public void setUp() {
        User alex = new User();

        alex.setUsername("vex");alex.setId(1);alex.setPassword("23");

        /*
        Mockito.when(userRepository.findByUsername(alex.getUsername()))
                .thenReturn(alex);
        */
        //serService.saveUser(alex);
        Mockito.when(userService.findUserByUsername(alex.getUsername()))
                .thenReturn(alex);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String userName = "vex";
        User found = userService.findUserByUsername(userName);
        assert(found.getUsername().equals(userName));
    }

}