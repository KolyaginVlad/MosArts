package ru.cpc.mosarts.data.repositories

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import ru.cpc.mosarts.domain.models.Answer
import ru.cpc.mosarts.domain.models.AnswerType
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.QuestionType
import ru.cpc.mosarts.domain.models.QuestionVal
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

class TestRepositoryImpl  @Inject constructor() : TestRepository {
	override suspend fun getSimpleTest(): Result<PersistentList<Question>> {
		return Result.success(
			persistentListOf(
				Question(
					questionType = QuestionType.Text,
					answerType = AnswerType.Text,
					answerVariants = Answer(),
					question = QuestionVal(textQuestion = "How old are you?"),
					correctAnswer = Answer(textAnswer = "18"),
					questionId = 0
				),
				Question(
					questionType = QuestionType.Text,
					answerType = AnswerType.Options,
					answerVariants = Answer(optionsAnswer = arrayListOf("18","10","11")),
					question = QuestionVal(textQuestion = "How old are you?"),
					correctAnswer = Answer(optionsAnswer = arrayListOf("18")),
					questionId = 1
				),
				
			)
		)
	}
}