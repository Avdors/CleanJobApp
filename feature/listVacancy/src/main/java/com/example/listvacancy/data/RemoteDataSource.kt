package com.example.listvacancy.data

import com.example.core.api.UrlProvider
import com.example.listvacancy.data.model.ListResponseOfferAndVacancies
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import java.net.UnknownHostException

class RemoteDataSource(private val client: HttpClient) {
   suspend fun getData(): ListResponseOfferAndVacancies {
        val json = client.get {
            val url = UrlProvider.getUrl("vacancies") ?: throw Exception("URL not found")
            url(url)
        }.bodyAsText()
        return try {
            Json.decodeFromString<ListResponseOfferAndVacancies>(json)
        }catch (e: UnknownHostException) {
            // Обработка отсутствия интернета
            throw Exception("Проблема с интернет-соединением: ${e.message}")
        } catch (e: HttpRequestTimeoutException) {
            // Обработка таймаутов
            throw Exception("Время ожидания истекло: ${e.message}")
        } catch (e: ClientRequestException) {
            // Обработка ошибок 4xx (например, 404 Not Found)
            throw Exception("Ошибка клиента: ${e.message}")
        } catch (e: ServerResponseException) {
            // Обработка ошибок сервера 5xx
            throw Exception("Ошибка сервера: ${e.message}")
        } catch (e: ResponseException) {
            // Обработка прочих ошибок HTTP
            throw Exception("Ошибка HTTP: ${e.message}")
        } catch (e: Exception) {
            // Обработка всех остальных исключений
            throw Exception("Неизвестная ошибка: ${e.message}")
        }
    }

}