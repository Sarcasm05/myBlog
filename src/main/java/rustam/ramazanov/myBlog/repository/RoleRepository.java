package rustam.ramazanov.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rustam.ramazanov.myBlog.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
