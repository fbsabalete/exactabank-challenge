package com.exactaworks.exactabank

import com.exactaworks.exactabank.client.PhoneCreditApiClient
import com.exactaworks.exactabank.dto.PhoneCreditApiRequest
import com.exactaworks.exactabank.dto.TransactionRequest
import com.exactaworks.exactabank.helper.DataHelper
import com.exactaworks.exactabank.model.PixKeyType
import com.exactaworks.exactabank.model.TransactionType
import com.exactaworks.exactabank.repository.AccountRepository
import com.exactaworks.exactabank.repository.PixKeyRepository
import com.exactaworks.exactabank.repository.TransactionRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal


private const val transactionsBaseUri = "/api/transactions"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
private class ExactabankIntegrationTests {

	@Autowired
	private val mockMvc: MockMvc? = null

	@Autowired
	private val transactionRepository: TransactionRepository? = null
	@Autowired
	private val accountRepository: AccountRepository? = null
	@Autowired
	private val pixKeyRepository: PixKeyRepository? = null

	@Autowired
	private val dataHelper: DataHelper? = null
	@Autowired
	private val objectMapper: ObjectMapper? = null

	@MockBean
	private val phoneCreditApiClient: PhoneCreditApiClient? = null

	@BeforeEach
	fun setUp() {
		transactionRepository?.deleteAll();

		dataHelper!!.populate()
	}
	@Test
	fun `Create a WITHDRAWAL transaction, and verify balance is updated`() {
		val accountId = 1L
		val agencyNumber = 123
		val transactionAmount = BigDecimal.TEN
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.WITHDRAWAL,
			amount = transactionAmount,
			agencyNumber = agencyNumber
		)

		val oldBalance = accountRepository?.findByIdOrNull(accountId)?.balance!!

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))

		val newBalance = accountRepository.findByIdOrNull(accountId)?.balance

		assert(oldBalance.minus(transactionAmount) == newBalance)
		assert(transactionRepository!!.findAll().size == 1)

	}

	@Test
	fun `Create a DEPOSIT transaction, and verify balance is updated`() {
		val accountId = 1L
		val agencyNumber = 123
		val transactionAmount = BigDecimal.TEN
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.DEPOSIT,
			amount = transactionAmount,
			agencyNumber = agencyNumber
		)

		val oldBalance = accountRepository?.findByIdOrNull(accountId)?.balance!!

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))

		val newBalance = accountRepository.findByIdOrNull(accountId)?.balance

		assert(oldBalance.plus(transactionAmount) == newBalance)
		assert(transactionRepository!!.findAll().size == 1)

	}

	@Test
	fun `Create a PHONE_RECHARGE transaction, and verify balance is updated`() {
		val accountId = 1L
		val transactionAmount = BigDecimal.TEN
		val phoneNumber = "(11) 999999999"
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.PHONE_RECHARGE,
			amount = transactionAmount,
			phoneNumber = phoneNumber
		)

		val phoneCreditApiRequest = PhoneCreditApiRequest(phoneNumber)
		Mockito.`when`(phoneCreditApiClient!!.rechargePhone(phoneCreditApiRequest))
			.thenReturn(ResponseEntity.ok().build())

		val oldBalance = accountRepository?.findByIdOrNull(accountId)?.balance!!

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))

		val newBalance = accountRepository.findByIdOrNull(accountId)?.balance

		assert(oldBalance.minus(transactionAmount) == newBalance)
		assert(transactionRepository!!.findAll().size == 1)
		Mockito.verify(phoneCreditApiClient).rechargePhone(phoneCreditApiRequest)

	}

	@Test
	fun `Create a PIX transaction, and verify balance is updated on both accounts`() {
		val accountId = 1L
		val targetPixAccountId = 2L
		val transactionAmount = BigDecimal.TEN
		val pixKey = "email@email.com"
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.PIX,
			amount = transactionAmount,
			pixKeyType = PixKeyType.EMAIL,
			pixKey = pixKey
		)


		val oldSourceBalance = accountRepository?.findByIdOrNull(accountId)?.balance!!
		val oldTargetBalance = accountRepository.findByIdOrNull(targetPixAccountId)?.balance!!

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))

		val newSourceBalance = accountRepository.findByIdOrNull(accountId)?.balance
		val newTargetBalance = accountRepository.findByIdOrNull(targetPixAccountId)?.balance!!

		assert(oldSourceBalance.minus(transactionAmount) == newSourceBalance)
		assert(oldTargetBalance.plus(transactionAmount) == newTargetBalance)
		assert(transactionRepository!!.findAll().size == 1)

	}

	@Test
	fun `Try to create a PIX transaction, and verify required fields are not sent`() {
		val accountId = 1L
		val transactionAmount = BigDecimal.TEN
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.PIX,
			amount = transactionAmount
		)

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.BAD_REQUEST.value()))
			.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("pixKey")))
			.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("pixKeyType")))

	}

	@Test
	fun `Try to create a DEPOSIT transaction, and verify required fields are not sent`() {
		val accountId = 1L
		val transactionAmount = BigDecimal.TEN
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.DEPOSIT,
			amount = transactionAmount
		)

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.BAD_REQUEST.value()))
			.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("agencyNumber")))

	}

	@Test
	fun `Try to create a WITHDRAWAL transaction, and verify required fields are not sent`() {
		val accountId = 1L
		val transactionAmount = BigDecimal.TEN
		val transactionRequest = TransactionRequest(
			accountId = accountId,
			transactionType = TransactionType.WITHDRAWAL,
			amount = transactionAmount
		)

		mockMvc!!.perform(
			MockMvcRequestBuilders.post(transactionsBaseUri)
				.content(objectMapper!!.writeValueAsString(transactionRequest))
				.contentType(MediaType.APPLICATION_JSON)
		)
			.andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.BAD_REQUEST.value()))
			.andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("agencyNumber")))

	}

	@TestConfiguration
	class TestConfig {
		@Bean
		fun testHelper(accountRepository: AccountRepository, pixKeyRepository: PixKeyRepository): DataHelper {
			return DataHelper(accountRepository = accountRepository, pixKeyRepository = pixKeyRepository)
		}
	}

}
