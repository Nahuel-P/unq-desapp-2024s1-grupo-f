package ar.edu.unq.desapp.grupoF.backenddesappapi.webservice

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.UserVolumeReport
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.builder.UserBuilder
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IUserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@WithMockUser(username = "michael", roles = ["USER"])
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: IUserService

    @MockBean
    private lateinit var commonService: ICommonService



    @Test
    fun `getUsers returns OK status when service returns data`() {
        val user1 = UserBuilder().withFirstName("Micahel").withLastName("Scott").withEmail("prisonmike@gmail.com").build()
        user1.id = 1L
        val user2 = UserBuilder().withFirstName("Dwight").withLastName("Schrute").withEmail("battlestargalatica@gmail.com").build()
        user2.id = 2L

        `when`(userService.getUsers()).thenReturn(listOf(user1, user2))

        mockMvc.perform(
            get("/user/users")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `getUsers returns BAD_REQUEST status when service throws exception`() {
        `when`(userService.getUsers()).thenThrow(RuntimeException("Error from Service"))

        mockMvc.perform(
            get("/user/users")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getUserByID returns OK status when service returns data`() {
        val user = UserBuilder().withFirstName("Michael").withLastName("Scott").withEmail("prisonmike@gmail.com").build()
        user.id=1L

        `when`(commonService.getUser(1L)).thenReturn(user)

        mockMvc.perform(
            get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `getUserByID returns BAD_REQUEST status when service throws exception`() {
        `when`(commonService.getUser(1L)).thenThrow(RuntimeException("Error from Service"))

        mockMvc.perform(
            get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getOperatedVolume returns OK status when service returns data`() {
        val startDate = "2024-01-01"
        val endDate = "2024-12-31"
        val userVolumeReport = mock(UserVolumeReport::class.java)

        `when`(userVolumeReport.totalUSD).thenReturn(1000.0)


        `when`(userService.getOperatedVolumeBy(1L, LocalDate.parse(startDate).atStartOfDay(), LocalDate.parse(endDate).atTime(23, 59, 59))).thenReturn(userVolumeReport)

        mockMvc.perform(
            get("/user/operatedVolume/1/$startDate/$endDate")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `getOperatedVolume returns BAD_REQUEST status when service throws exception`() {
        val startDate = "2024-01-01"
        val endDate = "2024-12-31"

        `when`(userService.getOperatedVolumeBy(1L, LocalDate.parse(startDate).atStartOfDay(), LocalDate.parse(endDate).atTime(23, 59, 59))).thenThrow(RuntimeException("Error from Service"))

        mockMvc.perform(
            get("/user/operatedVolume/1/$startDate/$endDate")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }
}
