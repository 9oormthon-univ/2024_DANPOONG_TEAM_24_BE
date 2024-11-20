package com.ieum.be.domain.quiz.controller;

import com.ieum.be.domain.quiz.entity.Quiz;
import com.ieum.be.domain.quiz.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getQuizList() {
        return this.quizService.getQuizList();
    }
}
