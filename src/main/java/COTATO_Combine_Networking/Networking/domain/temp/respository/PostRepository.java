package COTATO_Combine_Networking.Networking.domain.temp.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import COTATO_Combine_Networking.Networking.domain.temp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
