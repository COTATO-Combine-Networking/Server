package COTATO_Combine_Networking.Networking.domain.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import COTATO_Combine_Networking.Networking.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
