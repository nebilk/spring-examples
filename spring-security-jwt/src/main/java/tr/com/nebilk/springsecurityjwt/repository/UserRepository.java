package tr.com.nebilk.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.nebilk.springsecurityjwt.entity.SampleUser;

public interface UserRepository extends JpaRepository<SampleUser, Long> {

    SampleUser findByUsername(String username);
}
