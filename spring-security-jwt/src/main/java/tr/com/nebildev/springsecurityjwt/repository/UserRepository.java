package tr.com.nebildev.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.nebildev.springsecurityjwt.entity.SampleUser;

public interface UserRepository extends JpaRepository<SampleUser, Long> {

    SampleUser findByUsername(String username);
}
