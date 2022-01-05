package com.wkelms.ebilling.digsig.api.service

import com.wkelms.ebilling.digsig.api.dao.SharedocDao
import com.wkelms.ebilling.digsig.api.trustweaver.SignRequest
import com.wkelms.ebilling.digsig.api.trustweaver.SwitchService
import com.wkelms.ebilling.digsig.api.trustweaver.ValidateArchiveRequest
import com.wkelms.ebilling.digsig.api.trustweaver.ValidateRequest
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import groovy.json.JsonBuilder
import java.io.ByteArrayOutputStream
/**
 * Created by ranadeep.palle on 4/17/2017.
 */
@Slf4j
@Service
class DigSigService {

    private static localeMap
    static {
        def countries = java.util.Locale.getISOCountries()
        localeMap = [:]
        countries.each{
            def locale = new Locale("", it)
            localeMap.put(locale.getISO3Country().toUpperCase(),locale?.getCountry())
            localeMap.put(locale?.getCountry(),locale?.getCountry())
        }
    }

    @Autowired
    private SharedocDao sdDao

    @Value('${digsig.wdslURL}')
    private  String wsdlUrl

    def iso3CountryCodeToIso2CountryCode(String iso3CountryCode) {
        def retVal = localeMap?.get(iso3CountryCode)
        if(!retVal) {
            log.error("Invalid Country Code, Expecting 2/3 letter ISO Country Codes")
            throw new Exception("Invalid Country Code, Expecting 2/3 letter ISO Country Codes")
        }
        return retVal
    }

    def legacyResponse(vResponse, status=null, desc =null) {
        def doc = ""
        def details = ""
        if (vResponse) {
            status = (vResponse?.result?.code == "OK") ? "Valid" : vResponse?.result?.code
            desc = vResponse?.result?.desc
            details = vResponse?.details
            if (vResponse.class.simpleName == "ValidateResponse" && status == "Valid")
                doc = new String(vResponse.archive)
        }

        return "<ValidationResponse>\n" +
                "  <status>${status}</status>\n" +
                "  <desc>${desc}</desc>\n" +
                "  <signed_document><![CDATA[${doc}]]></signed_document>" +
                "  <details>${details}</details>" +
                "  <signatures></signatures></ValidationResponse>"
    }

    def getSwichService(){
        URL u = new URL(wsdlUrl)
        return new SwitchService(u)
    }

    def getServiceInfo(){
        def switchService = getSwichService()
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def serviceInfo = switchServiceSoap.getServiceInfo()
        def jsonResponse = new JsonBuilder(serviceInfo).toPrettyString()
        if(log.isDebugEnabled())
            log.debug("getServiceInfo Response: $jsonResponse")
        jsonResponse
    }

    def sign(invoiceBytes,senderCountry,clientCountry,referenceId,senderLawId,clientLawId,invoiceCount){
        sdDao.insertDigSigRecord(senderCountry,clientCountry,referenceId,senderLawId,clientLawId,invoiceCount)
        def switchService = getSwichService()
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def signRequest =  new SignRequest()

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )
        outputStream.write("Content-Type: text/plain charset=utf-8\r\n\r\n".getBytes())
        outputStream.write(invoiceBytes)

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
        log.info("Successfully Signed Invoice for For ReferenceId = ${referenceId}")
        addDigitalSignatureRecord(signResponse,referenceId)
        log.debug("sign Response For ReferenceId = ${referenceId} SignedDocument : ${signResponse.signedDocument}")
        signResponse.signedDocument
    }

    def validate(signedInvoice, senderCountry, clientCountry, isXML=false){
        log.info("Validating Signed Invoice")
        def switchService = getSwichService()
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def validateRequest = new ValidateRequest()
        validateRequest.inputType           = 'SMIME'
        validateRequest.jobType             = 'CADESA'
        validateRequest.outputType          = 'GENERIC'
        validateRequest.signedDocument      = signedInvoice
        validateRequest.senderTag           = iso3CountryCodeToIso2CountryCode(senderCountry)
        validateRequest.receiverTag         = iso3CountryCodeToIso2CountryCode(clientCountry)
        validateRequest.agreementIdentifier = null
        def validateResponse = switchServiceSoap.validate(validateRequest)
        log.info("Successfully validated Signed Invoice")
        def jsonResponse = new JsonBuilder(validateResponse.result).toPrettyString()
        log.debug("validate Response: $jsonResponse")
        return isXML?legacyResponse(validateResponse):jsonResponse
    }

    def validateArchive(signedInvoice, isXML=false){
        log.info("validateArchive for Signed Invoice")
        def switchService = getSwichService()
        def switchServiceSoap = switchService.getSwitchServiceSoap()
        def validateArchiveRequest = new ValidateArchiveRequest()
        validateArchiveRequest.inputType        = 'SMIME'
        validateArchiveRequest.jobType          = 'DETAILS'
        validateArchiveRequest.outputType       = 'GENERIC'
        validateArchiveRequest.signedDocument   = signedInvoice
        def validateArchiveResponse = switchServiceSoap.validateArchive(validateArchiveRequest)
        log.info("validateArchive for Signed Invoice is Success")
        def jsonResponse = new JsonBuilder(validateArchiveResponse.result).toPrettyString()
        log.debug("validateArchive Response: $jsonResponse")
        return isXML?legacyResponse(validateArchiveResponse):jsonResponse
    }

    def addDigitalSignatureRecord(signResponse,referenceId){
        log.info("AddDigitalSignatureRecord : referenceId "+referenceId +", Result Code "+signResponse.result.code +", desc = "+signResponse.result.desc)
        sdDao.updateDigSigRecord(signResponse.result.code ,signResponse.details,1,referenceId)
    }

}
