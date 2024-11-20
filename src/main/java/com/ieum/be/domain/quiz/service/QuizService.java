package com.ieum.be.domain.quiz.service;

import com.ieum.be.domain.quiz.entity.Quiz;
import com.ieum.be.domain.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizList() {
        return quizRepository.findAll();
    }
}
