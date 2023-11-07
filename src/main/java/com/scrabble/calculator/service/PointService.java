package com.scrabble.calculator.service;

import com.scrabble.calculator.entity.Score;

import java.util.List;

public interface PointService {
    Score addScore(Integer score);
    List<Integer> retrieveTopTenScores();
}
