package ru.cpc.mosarts.data.repositories

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import ru.cpc.mosarts.domain.models.AnswerVariant
import ru.cpc.mosarts.domain.models.Difficulty
import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.domain.models.Question
import ru.cpc.mosarts.domain.models.QuestionType
import ru.cpc.mosarts.domain.models.QuestionVal
import ru.cpc.mosarts.domain.models.TestParams
import ru.cpc.mosarts.domain.models.TestResults
import ru.cpc.mosarts.domain.models.ThemeData
import ru.cpc.mosarts.domain.models.UserAnswer
import ru.cpc.mosarts.domain.repositories.TestRepository
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor() : TestRepository {
    override suspend fun getSimpleTest(args: TestParams): Result<PersistentList<Question>> {
        return when (args.name) {
			NamesOfTest.TextOptions -> optionsTest(args.difficulty)
			NamesOfTest.MusicImage -> musicTest(args.difficulty)
			NamesOfTest.VideoMusic -> videoTest(args.difficulty)
		}
	}

	override suspend fun sendSimpleTest(answers: TestResults): Result<Unit> {

		delay(1000)
		return Result.success(Unit)
	}

	override suspend fun getThemes(): Result<List<ThemeData>> {
		delay(300)
		return Result.success(
			listOf(
				ThemeData(
					0,
					"Музыка"
				),
				ThemeData(
					1,
					"Хореография"
				),
				ThemeData(
					2,
					"Изобразительное искусство"
				),
				ThemeData(
					3,
					"Театр"
				),
			)
		)
	}

	override suspend fun sendSelectedThemes(themes: List<ThemeData>): Result<Unit> {
		delay(100)
		return Result.success(Unit)
	}

	private fun optionsTest(difficulty: Difficulty): Result<PersistentList<Question>> {
		when (difficulty) {
			Difficulty.Easy -> return Result.success(
				persistentListOf(
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("дирижёр"),
							AnswerVariant("композитор"),
							AnswerVariant("концертмейстер"),
							AnswerVariant("вокалист")
						),
						question = QuestionVal(textQuestion = "Кто сочиняет музыку?"),
						questionId = 11,
						cost = 1,
						rightAnswer = UserAnswer("композитор"),
						explain = "композитор. Композитор – автор, создатель музыкальных произведений."
					),
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("Санкт-Петербург"),
							AnswerVariant("Москва"),
							AnswerVariant("Екатеринбург"),
							AnswerVariant("Нижний Новгород")
						),
						question = QuestionVal(textQuestion = "В каком городе находится Государственный академический Большой театр России?"),
						questionId = 12,
						cost = 1,
						rightAnswer = UserAnswer("Москва"),
						explain = "Москва. Государственный академический Большой театр России расположен в Москве по адресу: Театральная площадь, д. 1."
					),
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("танцуют"),
							AnswerVariant("поют"),
                            AnswerVariant("дирижируют"),
                            AnswerVariant("играют на музыкальных инструментах")
                        ),
						question = QuestionVal(textQuestion = "Чем занимаются участники хора?"),
						questionId = 13,
						cost = 1,
						rightAnswer = UserAnswer("поют"),
						explain = "поют. Хор – певческий коллектив, исполняющий вокальную музыку."
					),

					)
			)

			Difficulty.Medium -> return Result.success(
				persistentListOf(
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("бандонеон"),
							AnswerVariant("аккордеон"),
							AnswerVariant("баян"),
							AnswerVariant("гармонь \"Хромка\"")
						),
						question = QuestionVal(textQuestion = "Как называется инструмент, в котором одна из клавиатур напоминает клавиатуру фортепиано?"),
                        questionId = 21,
						cost = 2,
						rightAnswer = UserAnswer("аккордеон"),
						explain = "аккордеон. Аккордеон – музыкальный инструмент, в котором  правая клавиатура фортепианного типа, то есть, напоминает клавиатуру фортепиано."
					),
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("4"),
                            AnswerVariant("5"),
                            AnswerVariant("6"),
							AnswerVariant("8")
						),
						question = QuestionVal(textQuestion = "Сколько струн на виолончели?"),
						questionId = 22,
						cost = 2,
						rightAnswer = UserAnswer("4"),
						explain = "4. Виолончель – струнный смычковый музыкальный инструмент, который имеет 4 струны."
					),
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("композитор"),
							AnswerVariant("трубач"),
                            AnswerVariant("скрипач"),
                            AnswerVariant("пианист")
                        ),
						question = QuestionVal(textQuestion = "Денис Мацуев – …"),
						questionId = 23,
						cost = 2,
						rightAnswer = UserAnswer("пианист"),
						explain = "пианист. Денис Мацуев – российский пианист, Народный артист РФ, победитель XI Международного конкурса имени П.И. Чайковского. "
					),

					)
			)

			Difficulty.Hard -> return Result.success(
				persistentListOf(
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("опера"),
							AnswerVariant("балет"),
							AnswerVariant("симфония"),
							AnswerVariant("соната")
						),
						question = QuestionVal(textQuestion = "Как называется музыкальный спектакль, содержание которого воплощается через музыку и танец?"),
						questionId = 31,
						cost = 3,
                        rightAnswer = UserAnswer("балет"),
						explain = "балет. Балет – вид сценического искусства, содержание которого выражается в музыкально-хореографических образах."
					),
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("прима"),
							AnswerVariant("секунда"),
							AnswerVariant("минута"),
							AnswerVariant("октава")
						),
						question = QuestionVal(textQuestion = "Какого музыкального интервала не существует?"),
                        questionId = 32,
						cost = 3,
						rightAnswer = UserAnswer("минута"),
						explain = "Музыкальный интервал – расстояние между двумя различными по высоте звуками. Прима, секунда, октава – музыкальные интервалы. Минута не является музыкальным интервалом, минута – единица измерения времени. "
					),
					Question(
						questionType = QuestionType.TextOptions,
						answerVariants = arrayListOf(
							AnswerVariant("скрипка, контрабас, домра, арфа"),
							AnswerVariant("балалайка, гитара, гобой, виолончель"),
							AnswerVariant("контрабас, виолончель, тромбон, укулеле"),
							AnswerVariant("арфа, скрипка, домра, кларнет")
						),
						question = QuestionVal(textQuestion = "Выберите вариант ответа, где перечислены струнные музыкальные инструменты."),
						questionId = 33,
						cost = 1,
						rightAnswer = UserAnswer("пианист"),
						explain = "скрипка, контрабас, домра, арфа. Скрипка, контрабас, домра, арфа, балалайка, гитара, виолончель, укулеле – струнные музыкальные инструменты. Гобой, тромбон, кларнет – духовые музыкальные инструменты."
					),
				)
			)

			else -> {
				return Result.success(
					persistentListOf()
				)
			}
		}
	}


	private fun musicTest(difficulty: Difficulty): Result<PersistentList<Question>> {
        when (difficulty) {
			else -> return Result.success(
				persistentListOf(
					Question(
						questionType = QuestionType.MusicImage,
						answerVariants = arrayListOf(
							AnswerVariant(
								"Рояль",
								"data:image/jpeg;base64,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"
							),
							AnswerVariant(
								"Скрипка",
								"data:image/jpeg;base64,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"
							),
							AnswerVariant(
								"Малый барабан",
								"data:image/jpeg;base64,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"
							)
						),
						questionId = 42,
						cost = 1,
						question = QuestionVal(
							textQuestion = "Какой это инструмент?",
							source = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
						),
						rightAnswer = UserAnswer("Скрипка"),
						explain = "Скрипка — королева музыкальных инструментов — прошла долгий путь. Её предки встречаются в музыкальных культурах почти всех народов мира. И на деревенском празднике, и в лучшем концертном зале скрипка превосходно играет свою роль — выражает самые глубокие человеческие чувства!"

                    ),
                )
            )
        }
    }

    private fun videoTest(difficulty: Difficulty): Result<PersistentList<Question>> {
        when (difficulty) {
			else -> return Result.success(
				persistentListOf(
					Question(
						questionType = QuestionType.VideoMusic,
						answerVariants = arrayListOf(
							AnswerVariant(
								"1",
								"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
							),
							AnswerVariant(
								"2",
								"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-17.mp3"
							),
							AnswerVariant(
								"3",
								"https://www.soundhelix.com/examples/mp3/SoundHelix-Song-11.mp3"
							)
						),
						questionId = 42,
						cost = 1,
						question = QuestionVal(
							textQuestion = "Какой музыкальный фрагмент подходит сюда?",
							source = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4",
						),
						rightAnswer = UserAnswer("1"),
					),
				)
			)
		}
	}
}