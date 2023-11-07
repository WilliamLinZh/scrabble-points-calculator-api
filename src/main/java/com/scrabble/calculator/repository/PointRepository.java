package com.scrabble.calculator.repository;

import com.scrabble.calculator.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Score, Integer> {
    @Query(value = "SELECT * FROM SCORE s ORDER BY s.score DESC LIMIT 10",nativeQuery = true)
    List<Score> getTopTenScores();
}
