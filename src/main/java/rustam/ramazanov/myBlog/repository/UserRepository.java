package rustam.ramazanov.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rustam.ramazanov.myBlog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Методы для работы с таблицей
}
