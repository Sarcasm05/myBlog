package rustam.ramazanov.myBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rustam.ramazanov.myBlog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}