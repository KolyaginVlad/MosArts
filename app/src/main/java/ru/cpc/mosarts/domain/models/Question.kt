package ru.cpc.mosarts.domain.models

data class Question(
	val questionType: QuestionType,
	val answerVariants: ArrayList<AnswerVariant>? = null,
	val question: QuestionVal,
	val questionId: Long,
	val cost: Int,
	val explain: String? = null,
	val rightAnswer: UserAnswer,
)

data class AnswerVariant(
	val textVariant: String,
	val source: String? = null
)

data class QuestionVal(
	val textQuestion: String? = null,
	val source: String? = null, //что-то там
)

enum class QuestionType {
	TextText,
	TextOptions,
	ImageConnect,
	MusicImage,
	VideoMusic,
	TextDraw,
}
