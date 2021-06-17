package com.wkelms.ebilling.digsig.api

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DigitalSignatureServiceApplication {


	@Value('${digsig.trustweaver.keystore}')
	private String trustweaverKeystore

	@Value('${digsig.trustweaver.keystoreType}')
	private String trustweaverKeystoreType

	@Value('${digsig.trustweaver.keystorePassword}')
	private String trustweaverKeystorePassword

	@Value('${digsig.trustweaver.truststore}')
	private String trustweaverCATrustStore

	@Value('${digsig.trustweaver.truststoreType}')
	private String trustweaverCATrustStoreType

	@Value('${digsig.trustweaver.truststorePassword}')
	private String trustweaverCATrustStorePassword

	@Value('${digsig.instance}')
	private String digsigInstance

    @Value('${digsig.hostnameVerification}')
    private String digsigHostnameVerification

	static void main(String[] args) {
		System.setProperty("sun.security.ssl.allowUnsafeRenegotiation","true")
		SpringApplication.run(DigitalSignatureServiceApplication.class, args)
	}

	@Bean
	CommandLineRunner init() {
		def customHttpsSSLConfig = new CustomHttpsSSLConfig()
		customHttpsSSLConfig.init(digsigInstance, digsigHostnameVerification, trustweaverKeystore, trustweaverKeystoreType, trustweaverKeystorePassword, trustweaverCATrustStore, trustweaverCATrustStoreType, trustweaverCATrustStorePassword)
	}

}
