package ru.cpc.mosarts.domain.models

data class Question(
	val questionType: QuestionType,
	val answerType: AnswerType,
	val answerVariants: Answer,
	val question: QuestionVal,
	val questionId: Long,
	val correctAnswer: Answer
)

data class Answer(
	val textAnswer: String?= null,
	val optionsAnswer: ArrayList<String>?= null,
	val drawingAnswer: String? = null//что-то там
)

data class QuestionVal(
	val textQuestion: String?= null,
	val imageQuestion: String? = null, //что-то там
	val musicQuestion: String? = null//что-то там
)

enum class QuestionType {
	Text,
	Image,
	Music
}

enum class AnswerType {
	Text,
	Options,
	Drawing
}