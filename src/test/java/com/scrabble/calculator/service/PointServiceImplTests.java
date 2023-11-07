package com.scrabble.calculator.service;

import com.scrabble.calculator.entity.Score;
import com.scrabble.calculator.repository.PointRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class PointServiceImplTests {

    @MockBean
    PointRepository pointRepository;

    @Autowired
    PointService pointService;

    @Test
    public void shouldAddScore() {
        Score score = new Score().setScore(33).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));

        given(pointRepository.save(any(Score.class))).willAnswer((invocation) -> invocation.getArgument(0));

        Score resultScore = pointService.addScore(score.getScore());

        assertThat(resultScore.getScore()).isEqualTo(score.getScore());
        assertThat(resultScore.getCreatedDate().before(score.getCreatedDate())).isEqualTo(false);
        assertThat(resultScore.getId()).isEqualTo(null);
    }

    @Test
    public void shouldRetrieveTopTenScores() {
        Score score1 = new Score().setScore(33).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score2 = new Score().setScore(30).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score3 = new Score().setScore(28).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score4 = new Score().setScore(28).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score5 = new Score().setScore(25).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score6 = new Score().setScore(23).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score7 = new Score().setScore(20).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score8 = new Score().setScore(18).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score9 = new Score().setScore(17).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        Score score10 = new Score().setScore(17).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));

        List<Score> scores = Arrays.asList(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10);

        given(pointRepository.getTopTenScores()).willAnswer((invocation) -> scores);

        List<Integer> scoreNumberList= pointService.retrieveTopTenScores();

        assertThat(scoreNumberList.size()).isEqualTo(10);
        assertThat(scoreNumberList.get(0)).isEqualTo(33);
        assertThat(scoreNumberList.get(9)).isEqualTo(17);
    }

}
