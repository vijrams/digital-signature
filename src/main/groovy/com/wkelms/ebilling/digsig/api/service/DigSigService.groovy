package com.wkelms.ebilling.digsig.api.service

import com.wkelms.ebilling.digsig.api.dao.SharedocDao
import com.wkelms.ebilling.digsig.api.trustweaver.SignRequest
import com.wkelms.ebilling.digsig.api.trustweaver.SwitchService
import com.wkelms.ebilling.digsig.api.trustweaver.ValidateArchiveRequest
import com.wkelms.ebilling.digsig.api.trustweaver.ValidateRequest
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import groovy.json.JsonBuilder
import java.io.ByteArrayOutputStream
/**
 * Created by ranadeep.palle on 4/17/2017.
 */

@Service
class DigSigService {
    def Logger LOGGER = LoggerFactory.getLogger(this.class)
    private static localeMap;
    static {
        def countries = java.util.Locale.getISOCountries();
        localeMap = new HashMap<String, Locale>(countries.length);
        for (String country : countries) {
        def locale = new Locale("", country);
        localeMap.put(locale.getISO3Country().toUpperCase(), locale);
        }
    }
    @Autowired
    private SharedocDao sdDao

    def iso3CountryCodeToIso2CountryCode(String iso3CountryCode) {
        if(StringUtils.isNotBlank(iso3CountryCode)) {
            if(iso3CountryCode.length() == 3)
            return localeMap.get(iso3CountryCode).getCountry();
            else if(iso3CountryCode.length() == 2)
            return iso3CountryCode
        }
        LOGGER.error("Invalid Country Code, Expecting 3 letters in Country Code")
        throw new DigSigException("Invalid Country Code, Expecting 3 letters in Country Code");
    }

    def getServiceInfo(){
        def switchService = new SwitchService();
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def serviceInfo = switchServiceSoap.getServiceInfo()
        def jsonResponse = new JsonBuilder(serviceInfo).toPrettyString()
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("getServiceInfo Response: $jsonResponse")
        jsonResponse
    }

    def sign(invoiceBytes,senderCountry,clientCountry,referenceId,senderLawId,clientLawId,invoiceCount){
        sdDao.insertDigSigRecord(senderCountry,clientCountry,referenceId,senderLawId,clientLawId,invoiceCount);
        def switchService = new SwitchService();
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def signRequest =  new SignRequest()

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write("Content-Type: text/plain; charset=utf-8\r\n\r\n".getBytes());
        outputStream.write(invoiceBytes);

        signRequest.inputType           = 'GENERIC'
        signRequest.jobType             = 'CADESA'
        signRequest.outputType          = 'SMIME'
        signRequest.document            = outputStream.toByteArray()
        signRequest.senderTag           = iso3CountryCodeToIso2CountryCode(senderCountry)
        signRequest.receiverTag         = iso3CountryCodeToIso2CountryCode(clientCountry)
        signRequest.nrOfItems           = invoiceCount
        signRequest.signerIdentifier    = null
        signRequest.agreementIdentifier = null
        def signResponse = switchServiceSoap.sign(signRequest)
        LOGGER.info("Successfully Signed Invoice for For ReferenceId = ${referenceId}")
        addDigitalSignatureRecord(signResponse,referenceId)
        LOGGER.debug("sign Response For ReferenceId = ${referenceId} SignedDocument : ${signResponse.signedDocument}")
        signResponse.signedDocument
    }

    def validate(signedInvoice, senderCountry, clientCountry){
        LOGGER.info("Validating Signed Invoice")
        def switchService = new SwitchService();
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def validateRequest = new ValidateRequest();
        validateRequest.inputType           = 'SMIME'
        validateRequest.jobType             = 'CADESA'
        validateRequest.outputType          = 'GENERIC'
        validateRequest.signedDocument      = signedInvoice
        validateRequest.senderTag           = iso3CountryCodeToIso2CountryCode(senderCountry)
        validateRequest.receiverTag         = iso3CountryCodeToIso2CountryCode(clientCountry)
        validateRequest.agreementIdentifier = null
        def validateResponse = switchServiceSoap.validate(validateRequest)
        def result =validateResponse.result
        LOGGER.info("Successfully validated Signed Invoice")
        def jsonResponse = new JsonBuilder(validateResponse.result).toPrettyString()
        LOGGER.debug("validate Response: $jsonResponse")
        jsonResponse
    }

    def validateArchive(signedInvoice){
        LOGGER.info("validateArchive for Signed Invoice")
        def switchService = new SwitchService();
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def validateArchiveRequest = new ValidateArchiveRequest();
        validateArchiveRequest.inputType        = 'SMIME'
        validateArchiveRequest.jobType          = 'DETAILS'
        validateArchiveRequest.outputType       = 'GENERIC'
        validateArchiveRequest.signedDocument   = signedInvoice
        def validateArchiveResponse = switchServiceSoap.validateArchive(validateArchiveRequest)
        def result =validateArchiveResponse.result
        LOGGER.info("validateArchive for Signed Invoice is Success")
        def jsonResponse = new JsonBuilder(validateArchiveResponse.result).toPrettyString()
        LOGGER.debug("validateArchive Response: $jsonResponse")
        jsonResponse
    }

    def addDigitalSignatureRecord(signResponse,referenceId){
        LOGGER.info("AddDigitalSignatureRecord : referenceId "+referenceId +", Result Code "+signResponse.result.code +", desc = "+signResponse.result.desc)
        sdDao.updateDigSigRecord(signResponse.result.code ,signResponse.details,1,referenceId)
    }

}
