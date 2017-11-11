package repositories;

import io.muic.designpattern.model.User;
import io.muic.designpattern.repositories.UserRepository;
import io.muic.designpattern.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceContext;


@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {PersistenceContext.class})
public class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @MockBean
    UserRepository userRepository;




    @Before
    public void setUp() {
        User alex = new User();
        alex.setUsername("vex");alex.setId(1);alex.setPassword("23");
        userRepository.save(alex);
        Mockito.when(userRepository.findByUsername(alex.getUsername()))
                .thenReturn(alex);
    }

    @Test
    public void addAndFindName() {


        String userName = "vex";
        User found = userRepository.findByUsername(userName);
        User found1 = userRepository.findByUsername("jo");
        System.out.println(found1);

        System.out.println("size: "+ userRepository.findAll().size());
        assert(found.getUsername().equals(userName));
    }
}
