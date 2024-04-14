//@SpringBootTest(classes = [BackendDesappApiApplication::class])
//class UserRepositoryTest {
//
//    @MockBean
//    private lateinit var userRepository: UserRepository
//
//    private fun aUser(): User {
//        return UserBuilder()
//            .withFirstName("Juan")
//            .withLastName("Lopez")
//            .withEmail("juan.lopez@example.com")
//            .withAddress("Calle Falsa 123")
//            .withPassword("Password1!")
//            .withCvu("1234567890123456789012")
//            .withWalletAddress("12345678")
//            .build()
//    }
//
//    @Test
//    fun `findById returns correct user when user exists`() {
//        val user = aUser()
//        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.of(user))
//
//        val optionalUser = userRepository.findById(1L)
//
//        assertTrue(optionalUser.isPresent)
//        assertEquals(user, optionalUser.get())
//    }
//
//    @Test
//    fun `findById returns empty when user does not exist`() {
//        Mockito.`when`(userRepository.findById(1L)).thenReturn(Optional.empty())
//
//        val optionalUser = userRepository.findById(1L)
//
//        assertFalse(optionalUser.isPresent)
//    }
//}