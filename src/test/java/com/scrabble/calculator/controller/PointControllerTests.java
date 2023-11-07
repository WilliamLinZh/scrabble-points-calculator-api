package com.scrabble.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrabble.calculator.dto.response.*;
import com.scrabble.calculator.service.PointService;
import com.scrabble.calculator.entity.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PointController.class)
class PointControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PointService pointService;

	@Test
	void shouldAddScore() throws Exception {
		Score score = new Score().setScore(33).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));

		ResponseEntity responseEntity = new ResponseEntity("Score Saved.");

		given(pointService.addScore(any(Integer.class))).willAnswer((invocation) -> score);

		this.mockMvc.perform(post("/api/score")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(new Score().setScore(33))))
				.andExpect(status().isNoContent())
				.andExpect(content().json(objectMapper.writeValueAsString(responseEntity)));
	}

	@Test
	void shouldReturnErrorMessageWhenAddScoreWithNoScore() throws Exception {
		Score score = new Score().setScore(33).setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));

		ErrorMessage message = new ErrorMessage("Missing Score.");

		given(pointService.addScore(any(Integer.class))).willAnswer((invocation) -> score);

		this.mockMvc.perform(post("/api/score")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest())
				.andExpect(content().json(objectMapper.writeValueAsString(message)));
	}

	@Test
	void shouldReturnErrorMessageWhenAddScoreWithOtherException() throws Exception {
		ErrorMessage message = new ErrorMessage("Service encounter internal error.");

		given(pointService.addScore(any(Integer.class))).willThrow(new DataIntegrityViolationException(""));

		this.mockMvc.perform(post("/api/score")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(objectMapper.writeValueAsString(new Score().setScore(23))))
				.andExpect(status().isInternalServerError())
				.andExpect(content().json(objectMapper.writeValueAsString(message)));
	}

	@Test
	void shouldRetrieveTopTenScores() throws Exception {
		List<Integer> topTenScoreList = Arrays.asList(34, 32, 20, 18, 17, 16, 10, 9, 3);
		given(pointService.retrieveTopTenScores()).willReturn(topTenScoreList);

		this.mockMvc.perform(get("/api/topTenScores"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(topTenScoreList.size())));
	}
}
