package com.wkelms.ebilling.digsig.api


import com.wkelms.ebilling.digsig.api.service.DigSigService
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.support.MissingServletRequestPartException

import javax.xml.ws.soap.SOAPFaultException

@Slf4j
@RestController
class DigSigController {

    @Autowired
    private DigSigService digSigService

    @RequestMapping(path = "getServiceInfo", method = RequestMethod.GET)
    public String getServiceInfo() {
        try {
        digSigService.getServiceInfo()
        } catch (IOException e) {
            log.error("Message = "+e.message +" stackTrace = "+e.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST)
        }
    }

    @RequestMapping(path = "/sign", method = RequestMethod.POST)
    ResponseEntity<Resource> sign(@RequestParam("invoice") MultipartFile invoice,
                                  @RequestParam("senderCountry") String senderCountry,
                                  @RequestParam("clientCountry") String clientCountry,
                                  @RequestParam("clientLawId") String clientLawId,
                                  @RequestParam("senderLawId") String senderLawId,
                                  @RequestParam("referenceId") String referenceId,
                                  @RequestParam("invoiceCount") Integer invoiceCount) {
        log.info("Signing Invoice for referenceId : ${referenceId}")

        ByteArrayResource resource
        if (invoice?.isEmpty()) {
            log.error("No Invoice Selected to Sign")
            return new ResponseEntity('{"error": "Required request part \'invoice\' is not present"}', HttpStatus.BAD_REQUEST)
        }

        try {
            def signedInvoiceBytes = digSigService.sign(invoice.bytes,senderCountry.trim(),clientCountry.trim(),referenceId.trim(),senderLawId.trim(),clientLawId.trim(),invoiceCount)
            resource =  new ByteArrayResource(signedInvoiceBytes)
            ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .contentLength(signedInvoiceBytes.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource)

        }
        catch(SOAPFaultException se){
            log.error("Message = "+se.message +" stackTrace = "+se.stackTrace)
            def json = createJson(se)
            return  ResponseEntity.unprocessableEntity()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json)
        }
       catch (Exception e) {
            log.error("Message = "+e.message +" stackTrace = "+e.stackTrace)
            def json = createJson(e)
           def re = ResponseEntity.badRequest()
           if(e.message =="Invalid Country Code, Expecting 2/3 letter ISO Country Codes")
               re = ResponseEntity.unprocessableEntity()
            return re.contentType(MediaType.APPLICATION_JSON)
                    .body(json)
        }
    }

    private String createJson(def se) {
        def json = new JsonBuilder()
        def root = json error: se.message
        json.toString()
    }

    @RequestMapping(path = "/digital_signatures/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    String validateLegacy(@RequestParam("file") String signedInvoice,
                          @RequestParam("sender_country") String senderCountry,
                          @RequestParam("recipient_country") String clientCountry,
                          @RequestParam("law_id") String lawId) {
        log.info("Validating Signed Invoice -legacy")
        try {
            if (signedInvoice.isEmpty()) {
                log.error("No Signed Invoice Selected")
                throw new Exception("No Signed Invoice Selected")
            }
            def resp = digSigService.validate(signedInvoice.bytes,senderCountry,clientCountry,true)
            resp
        }catch (Exception e) {
            digSigService.legacyResponse(null,"Unknown", e.message)
        }
    }

    @RequestMapping(path = "/validate", method = RequestMethod.POST)
    ResponseEntity validate(@RequestParam("signedInvoice") MultipartFile signedInvoice,
                    @RequestParam("senderCountry") String senderCountry,
                    @RequestParam("clientCountry") String clientCountry) {
        log.info("Validating Signed Invoice ")
        if (signedInvoice.isEmpty()) {
            log.error("No Signed Invoice Selected")
            return new ResponseEntity('{"error": "Required request part \'signedInvoice\' is not present"}', HttpStatus.BAD_REQUEST)
        }

        try {
            def resultJson = digSigService.validate(signedInvoice.bytes,senderCountry,clientCountry)
            ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resultJson)

        } catch (Exception e) {
            log.error("Message = "+e.message+" stackTrace = "+e.stackTrace)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(createJson(e))
        }

    }

    @RequestMapping(path = "/digital_signatures/validate_archive", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    String validateArchiveLegacy(@RequestParam("file") String signedInvoice) {
        log.info("ValidateArchive Signed Invoice -legacy")
        try {
            if (signedInvoice.isEmpty()) {
                log.error("No Signed Invoice Selected")
                throw new Exception("No Signed Invoice Selected")
            }
            def resp = digSigService.validateArchive(signedInvoice.bytes,true)
            resp
        }catch (Exception e) {
            digSigService.legacyResponse(null,"Unknown", e.message)
        }
    }

    @RequestMapping(path = "/validateArchive", method = RequestMethod.POST)
    ResponseEntity validateArchive(@RequestParam("signedInvoice") MultipartFile signedInvoice) {
        log.info("validateArchive Signed Invoice ")
        if (signedInvoice.isEmpty()) {
            log.error("No Signed Invoice Selected")
            return new ResponseEntity('{"error": "Required request part \'signedInvoice\' is not present"}', HttpStatus.BAD_REQUEST)
        }

        try {
            def resultJson = digSigService.validateArchive(signedInvoice.bytes)
            ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resultJson)
        } catch (Exception e) {
            log.error("Message = "+e.message+" stackTrace = "+e.stackTrace)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(createJson(e))
        }

    }

    @ExceptionHandler([ServletRequestBindingException.class, MissingServletRequestPartException.class])
    public final ResponseEntity<Object> handleHeaderException(Exception ex, WebRequest request)
    {
        String error = '{"error": "' +ex.getLocalizedMessage() +'"}'
        def contentType = MediaType.APPLICATION_JSON
        if(request?.getRequest().getRequestURI().toString()?.contains("digital_signature")) {
            error = digSigService.legacyResponse(null, "Unknown", ex.getLocalizedMessage())
            contentType = MediaType.APPLICATION_XML
        }
        log.error(ex.getLocalizedMessage())
        return ResponseEntity
                .badRequest()
                .contentType(contentType)
                .body(error)
    }

}