package tr.com.nebildev.springsecurityjwt.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tr.com.nebildev.springsecurityjwt.entity.Role;
import tr.com.nebildev.springsecurityjwt.enums.RoleType;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleTestRepository;

    @AfterEach
    public void tearDown(){
        roleTestRepository.deleteAll();
    }

    @Test
    void itShouldGetARole(){
        final Role role = new Role(null, RoleType.USER);
        roleTestRepository.save(role);

        final Role byName = roleTestRepository.findByName(RoleType.USER);

        assertThat(byName).isNotNull();
        assertThat(byName.getId()).isEqualTo(1L);
        Assertions.assertThat(byName.getName()).isEqualTo(RoleType.USER);

    }

}