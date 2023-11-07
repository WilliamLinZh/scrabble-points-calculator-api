package com.scrabble.calculator.controller;

import com.scrabble.calculator.dto.response.ResponseEntity;
import com.scrabble.calculator.entity.Score;
import com.scrabble.calculator.exception.InvalidException;
import com.scrabble.calculator.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PointController {
    @Autowired
    PointService pointService;

    @CrossOrigin
    @PostMapping("/score")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    ResponseEntity addScore(@RequestBody(required = false) Score score) {
        if (score == null || score.getScore() == null) {
            throw new InvalidException("Missing Score.");
        }

        pointService.addScore(score.getScore());
        return new ResponseEntity("Score Saved.");
    }

    @CrossOrigin
    @GetMapping("/topTenScores")
    List<Integer> topTenScores() {
        return pointService.retrieveTopTenScores();
    }
}
