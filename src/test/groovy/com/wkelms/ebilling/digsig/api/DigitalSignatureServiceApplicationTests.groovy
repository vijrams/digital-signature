package com.wkelms.ebilling.digsig.api

import org.junit.Assert
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = DigitalSignatureServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
class DigitalSignatureServiceApplicationTests extends DigitalSignatureServiceBaseTest{

	private MockMvc mockMvc

	@Autowired
	private WebApplicationContext webApplicationContext

	@Before
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
	}

	@Test
	void test010GetInfoPage() {
		MvcResult r = mockMvc.perform(MockMvcRequestBuilders.get("/actuator/info")
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk()).andReturn()
		byte[] content = r.getResponse().getContentAsByteArray()
		String json = new String(content, "UTF-8")
		Assert.assertTrue(json == "{}")
	}

	@Test
	void test012GetRootPage() {
		MvcResult r = mockMvc.perform(MockMvcRequestBuilders.get("/")
				.characterEncoding("UTF-8"))
				.andExpect(status().isNotFound()).andReturn()
		byte[] content = r.getResponse().getContentAsByteArray()
		String json = new String(content, "UTF-8")
		Assert.assertTrue(json=="")
	}

	@Test
	void test020SignInvoice() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("invoice", "LEDES98")
		MultiValueMap<String, String> nMap = getDefaultParams("SIGN")

		MvcResult r = mockMvc.perform(MockMvcRequestBuilders.multipart("/sign")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andReturn()
		def content = r.getResponse().getContentAsByteArray()
		new File('src/test/resources/sample98BI.signed.txt').withOutputStream {
			it.write content
		}
		Assert.assertTrue(new String(content).contains("multipart/signed;"))
		Assert.assertTrue(checkDigSigRecord(nMap.getFirst('referenceId'),nMap.getFirst('senderLawId'),nMap.getFirst('clientLawId'),"OK"))
	}

	@Test
	void test021SignInvoiceMissingFile() {
		MultiValueMap<String, String> nMap = getDefaultParams("SIGN")
		nMap.remove("senderCountry")
		mockMvc.perform(MockMvcRequestBuilders.multipart("/sign")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Required request part \'invoice\' is not present"))
	}

	@Test
	void test022SignInvoiceMissingParam() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("invoice", "LEDES98")
		MultiValueMap<String, String> nMap = getDefaultParams("SIGN")
		nMap.remove("senderCountry")
		mockMvc.perform(MockMvcRequestBuilders.multipart("/sign")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Required request parameter \'senderCountry\' for method parameter type String is not present"))
	}

	@Test
	void test023SignInvoiceWrongCountry() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("invoice", "LEDES98")
		MultiValueMap<String, String> nMap = getDefaultParams("SIGN")
		nMap.add("senderCountry", "USA1")
		mockMvc.perform(MockMvcRequestBuilders.multipart("/sign")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Invalid Country Code, Expecting 2/3 letter ISO Country Codes"))
	}

	@Test
	void test030ValidateInvoice() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("code").value("OK"))
				.andExpect(jsonPath("desc").value("The signature is valid."))
	}

	@Test
	void test031ValidateInvoiceMissingFile() {
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Required request part 'signedInvoice' is not present"))
	}

	@Test
	void test032ValidateInvoiceMissingParam() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")
		nMap.remove("senderCountry")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Required request parameter 'senderCountry' for method parameter type String is not present"))
	}

	@Test
	void test033ValidateInvoiceWrongCountry() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")
		nMap.add("senderCountry", "USA1")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Invalid Country Code, Expecting 2/3 letter ISO Country Codes"))
	}

	@Test
	void test034ValidateInvoiceWrongFile() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDES_SIGNED_BAD")
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("code").value("SignatureInvalid"))
				.andExpect(jsonPath("desc").value("The document signature is invalid"))
	}

	@Test
	void test040ValidateArchive() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDES98_SIGNED")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validateArchive")
				.file(multiPartFile)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("code").value("OK"))
				.andExpect(jsonPath("desc").value("The signature is valid."))
	}

	@Test
	void test041ValidateArchiveMissingFile() {

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validateArchive")
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("error").value("Required request part 'signedInvoice' is not present"))
	}

	@Test
	void test050ValidateInvoiceLegacy() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("file", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALIOLD")
		nMap.add("file", new String(multiPartFile.getBytes(),"UTF-8"))
		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Valid"))
				.andExpect(xpath("/ValidationResponse/desc").string("The signature is valid."))
	}

	@Test
	void test051ValidateInvoiceLegacyMissingFile() {
		MultiValueMap<String, String> nMap = getDefaultParams("VALIOLD")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Unknown"))
				.andExpect(xpath("/ValidationResponse/desc").string("Required request parameter 'file' for method parameter type String is not present"))
	}

	@Test
	void test052ValidateInvoiceLegacyMissingParam() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("file", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALIOLD")
		nMap.remove("sender_country")
		nMap.add("file", new String(multiPartFile.getBytes(),"UTF-8"))

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Unknown"))
				.andExpect(xpath("/ValidationResponse/desc").string("Required request parameter 'sender_country' for method parameter type String is not present"))
	}

	@Test
	void test053ValidateInvoiceLegacyWrongCountry() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("file", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALIOLD")
		nMap.add("sender_country", "USA1")
		nMap.add("file", new String(multiPartFile.getBytes(),"UTF-8"))

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Unknown"))
				.andExpect(xpath("/ValidationResponse/desc").string("Invalid Country Code, Expecting 2/3 letter ISO Country Codes"))
	}

	@Test
	void test054ValidateInvoiceLegacyWrongFile() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("file", "LEDES_SIGNED_BAD")
		MultiValueMap<String, String> nMap = getDefaultParams("VALIOLD")
		nMap.add("file", new String(multiPartFile.getBytes(),"UTF-8"))

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("SignatureInvalid"))
				.andExpect(xpath("/ValidationResponse/desc").string("The document signature is invalid"))

	}

	@Test
	void test060ValidateArchiveLegacy() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("file", "LEDES98_SIGNED")
		MultiValueMap<String, String> nMap = new LinkedMultiValueMap<String, Object>()
		nMap.add("file", new String(multiPartFile.getBytes(),"UTF-8"))

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate_archive")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Valid"))
				.andExpect(xpath("/ValidationResponse/desc").string("The signature is valid."))
	}

	@Test
	void test061ValidateArchiveLegacyMissingFile() {

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate_archive")
				.characterEncoding("UTF-8"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Unknown"))
				.andExpect(xpath("/ValidationResponse/desc").string("Required request parameter 'file' for method parameter type String is not present"))
	}

	@Test
	void test070SignXMLInvoice() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("invoice", "LEDESXML")
		MultiValueMap<String, String> nMap = getDefaultParams("SIGN")

		MvcResult r = mockMvc.perform(MockMvcRequestBuilders.multipart("/sign")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andReturn()
		def content = r.getResponse().getContentAsByteArray()
		new File('src/test/resources/sample.signed.xml').withOutputStream {
			it.write content
		}
		Assert.assertTrue(new String(content).contains("multipart/signed;"))
		Assert.assertTrue(checkDigSigRecord(nMap.getFirst('referenceId'),nMap.getFirst('senderLawId'),nMap.getFirst('clientLawId'),"OK"))
	}

	@Test
	void test071ValidateXMLInvoice() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDESXML_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("code").value("OK"))
				.andExpect(jsonPath("desc").value("The signature is valid."))
	}

	@Test
	void test072ValidateXMLInvoiceLegacy() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("file", "LEDESXML_SIGNED")
		MultiValueMap<String, String> nMap = getDefaultParams("VALIOLD")
		nMap.add("file", new String(multiPartFile.getBytes(),"UTF-8"))

		mockMvc.perform(MockMvcRequestBuilders.multipart("/digital_signatures/validate")
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
				.andExpect(xpath("/ValidationResponse/status").string("Valid"))
				.andExpect(xpath("/ValidationResponse/desc").string("The signature is valid."))
	}

	@Test
	void test080ValidateProdSignedInvoiceInStag() {
		MockMultipartFile multiPartFile = getMockMultiPartFile("signedInvoice", "LEDES_SIGNED_PROD")
		MultiValueMap<String, String> nMap = getDefaultParams("VALI")

		mockMvc.perform(MockMvcRequestBuilders.multipart("/validate")
				.file(multiPartFile)
				.params(nMap)
				.characterEncoding("UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("code").value("SignerInvalid"))
				.andExpect(jsonPath("desc").value("The signing certificate is issued under a policy which is unknown"))
	}

}
