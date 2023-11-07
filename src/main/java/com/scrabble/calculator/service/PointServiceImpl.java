package com.scrabble.calculator.service;

import com.scrabble.calculator.entity.Score;
import com.scrabble.calculator.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PointServiceImpl implements PointService {
    @Autowired
    PointRepository pointRepository;

    @Override
    public Score addScore(Integer score) {
        Score scoreObject = new Score().setScore(score).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        return pointRepository.save(scoreObject);
    }

    @Override
    public List<Integer> retrieveTopTenScores() {
        List<Score> scores = pointRepository.getTopTenScores();
        List<Integer> topTenScores = scores.stream().map(score -> score.getScore()).collect(Collectors.toList());
        return topTenScores;
    }
}
