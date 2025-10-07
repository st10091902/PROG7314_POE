package com.marcomarais.welltrack.data

import com.marcomarais.welltrack.data.remote.ApiService
import com.marcomarais.welltrack.data.remote.FoodDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlinx.coroutines.runBlocking

class MealRepositoryTest {

    private lateinit var server: MockWebServer

    @Before fun setup() { server = MockWebServer(); server.start() }
    @After  fun teardown() { server.shutdown() }

    @Test
    fun `fetchFood parses response`() = runBlocking {
        server.enqueue(
            MockResponse().setBody("""{"barcode":"123","name":"Oats 100g","calories":389}""")
        )

        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val dto: FoodDto = api.getFood("123")

        assertEquals("Oats 100g", dto.name)
        assertEquals(389, dto.calories)
    }
}
