package io.muic.designpattern.repositories;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {PersistenceContext.class})
public class ChessRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @MockBean
    ChessRepository chessRepository;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        User player1 = new User();
        player1.setId(1);
        player1.setUsername("player1");
        player1.setPassword("pass");
        userRepository.save(player1);
        Mockito.when(userRepository.findByUsername("player1")).thenReturn(player1);

        User player2 = new User();
        player2.setId(2);
        player2.setUsername("player2");
        player2.setPassword("pass");
        userRepository.save(player2);
        Mockito.when(userRepository.findByUsername("player2")).thenReturn(player2);

        User player3 = new User();
        player3.setId(2);
        player3.setUsername("player3");
        player3.setPassword("pass");
        userRepository.save(player3);
        Mockito.when(userRepository.findByUsername("player3")).thenReturn(player3);

        Chess chess1 = new Chess();
        chess1.setHost(player1);
        chess1.setPlayer(player2);
        chess1.setOngoing(true);

        Chess chess2 = new Chess();
        chess2.setHost(player1);
        chess2.setPlayer(player2);
        chess2.setOngoing(false);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void findAllByOngoingIsTrueAndHostOrPlayer() {
        User user = userRepository.findByUsername("player1");
        //assertEquals(chessRepository.findAllByOngoingIsTrueAndHostIsOrPlayerIs(user, user).size(), 1);

    }
}