package com.openclassrooms.starterjwt.IT;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class DatabaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String email, String firstName, String lastName, String rawPassword, boolean isAdmin) {
        User user = new User(email, lastName, firstName, passwordEncoder.encode(rawPassword), isAdmin);
        return userRepository.save(user);
    }

    @Test
    void testH2DatabaseIsActive() {
        // Créer un utilisateur pour tester l'écriture dans la base de données
        String rawPassword = "password";
        User user = createUser("test@test.com", "Jane", "Doe", rawPassword, false);

        // Vérifier si l'utilisateur a été enregistré
        User foundUser = userRepository.findByEmail("test@test.com").orElse(null);
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@test.com");

        // Utiliser la variable user pour vérifier si elle a été correctement
        // enregistrée
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("test@test.com");

        // Vérifier si le mot de passe est correct
        assertThat(passwordEncoder.matches(rawPassword, foundUser.getPassword())).isTrue();
    }
}