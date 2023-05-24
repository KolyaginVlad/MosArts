package ru.cpc.mosarts.data.repositories

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import ru.cpc.mosarts.domain.models.AnswerVariant
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.QuestionType
import ru.cpc.mosarts.domain.models.QuestionVal
import ru.cpc.mosarts.domain.models.TestResults
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor() : TestRepository {
	override suspend fun getSimpleTest(): Result<PersistentList<Question>> {
		return Result.success(
			persistentListOf(
				Question(
					questionType = QuestionType.TextOptions,
					answerVariants = arrayListOf(AnswerVariant("18"), AnswerVariant("10")),
					question = QuestionVal(textQuestion = "How old are you?"),
					questionId = 1,
					cost = 1,
					rightAnswer = UserAnswer("18"),
					explain = "you are 18"
				),
				Question(
					questionType = QuestionType.MusicImage,
					answerVariants = arrayListOf(
						AnswerVariant(
							"guitar",
							"https://www.cortguitarsshop.ru/netcat_files/multifile/2772/ad880_ns_akusticheskaya_gitara_l_1_1.png",
						),
						AnswerVariant(
							"piano",
							"https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Pianodroit.jpg/800px-Pianodroit.jpg"
						)
					),
					questionId = 2,
					cost = 1,
					question = QuestionVal(
						textQuestion = "what instrument?",
						source = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
					),
					rightAnswer = UserAnswer("guitar"),
					explain = "this is quitar"
				
				),
				Question(
					questionType = QuestionType.VideoMusic,
					answerVariants = arrayListOf(
						AnswerVariant(
							"guitar",
							"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
						),
						AnswerVariant(
							"piano",
							"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-8.mp3"
						)
					),
					questionId = 3,
					cost = 1,
					question = QuestionVal(
						textQuestion = "what instrument?",
						source = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
					),
					rightAnswer = UserAnswer("guitar"),
					explain = "this is video"
				)
			)
		)
	}
	
	override suspend fun sendSimpleTest(answers: TestResults): Result<Unit> {
		
		delay(1000)
		return Result.success(Unit)
	}
}