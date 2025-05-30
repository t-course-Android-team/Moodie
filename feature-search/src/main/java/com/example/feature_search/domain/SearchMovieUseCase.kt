package com.example.feature_search.domain

import com.example.domain.WatchedMoviesRepo
import com.example.feature_search.data.remote.ChatRequest
import com.example.feature_search.data.remote.Message

class SearchMovieUseCase(private val api: RemoteOpenRouterRepository , private val repository: WatchedMoviesRepo) {

    suspend operator fun invoke(movie :MovieForSearch) : String{
        return try{
            with(movie) {
                val prompt = buildString {
                    append("pick 10 movies ")
                    if (genre != null) append("in the genre $genre ")
                    if (released != null) append("of these years $released ")
                    if (mood!=null) append("in these move $mood ")
                    if (country != null) append("from these country $country ")
                    if (reference != null) append("similar to $reference ")
                    append("Here is a list of movies that I have already watched. They don't need to be offered ")
                    append(repository.getPagedWatchedMovies(0, 50).map { movie -> movie.name })
                    append("in the answer, give only the names of the films and nothing else")
                    append("Format response EXACTLY like this: 'Movie 1*Movie 2*...*Movie 10'")
                    append("THE ANSWER SHOULD BE NOTHING BUT TEN MOVIES.")
                }

                val response = api.getChatResponse(
                    ChatRequest(
                        model ="deepseek/deepseek-chat-v3-0324:free",
                        messages = listOf(
                            Message(role= "user", content = prompt )
                        )
                    )
                )
                val movies = (response.body()?.choices?.firstOrNull()?.message?.content.toString())
                return formatMoviesResponse(movies)
            }


        } catch (e: Exception){
         return   "Error: ${e.message}"
        }

    }
    private fun formatMoviesResponse(rawResponse: String): String {
        return rawResponse
//            .replace(" ", "*")
//            .split('|')
//            .mapNotNull { line ->
//                val regex = """^\s*\d+[.)]\s*(.+?)\s*$""".toRegex()
//                regex.find(line.trim())?.groupValues?.get(1)
//            }
//            .mapIndexed { index, title -> "${index + 1}. $title" }
//            .joinToString("*")
    }
}