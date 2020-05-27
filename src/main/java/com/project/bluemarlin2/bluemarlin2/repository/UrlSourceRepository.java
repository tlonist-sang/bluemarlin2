package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.UrlSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UrlSourceRepository extends JpaRepository<UrlSource, Long> {

    @Query("select distinct u from UrlSource u" +
            " join fetch u.member m" +
            " where m.userId =: userId and u.id =: urlId")
    List<UrlSource> findAllByUserIdAndUrlId(@Param("userId") String userId, @Param("urlId") Long urlId);

    @Override
    Optional<UrlSource> findById(Long aLong);
}
