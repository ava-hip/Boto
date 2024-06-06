package fr.avahip.boto.management.gifs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GifRepository extends JpaRepository<Gif, String> {

    @Query("SELECT g.url FROM Gif g WHERE :message LIKE CONCAT('%', g.gifKey, '%') ")
    List<String> findUrlByKeyLike(String message);
}