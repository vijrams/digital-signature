package com.wkelms.ebilling.digsig.api

import com.wkelms.ebilling.digsig.api.service.DigSigException
import com.wkelms.ebilling.digsig.api.service.DigSigService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
public class DigSigController {
    def logger = LoggerFactory.getLogger(this.class);
    @Autowired
    private DigSigService digSigService;
    @RequestMapping(path = "getServiceInfo", method = RequestMethod.GET)
    public String getServiceInfo() {
        try {
        digSigService.getServiceInfo()
        } catch (IOException e) {
            logger.error("Message = "+e.message +" stackTrace = "+e.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/sign", method = RequestMethod.POST)
    public ResponseEntity<Resource> sign(@RequestParam("invoice") MultipartFile invoice, @RequestParam("senderCountry") String senderCountry,@RequestParam("clientCountry") String clientCountry,@RequestParam("clientLawId") String clientLawId,@RequestParam("senderLawId") String senderLawId,@RequestParam("referenceId") String referenceId,@RequestParam("invoiceCount") Integer invoiceCount) {
        logger.info("Signing Invoice for referenceId : ${referenceId}");

        ByteArrayResource resource
        if (invoice.isEmpty()) {
            logger.error("No Invoice Selected to Sign")
            return new ResponseEntity("No Invoice Selected", HttpStatus.OK);
        }

        def signedInvoiceBytes
        try {
            signedInvoiceBytes = digSigService.sign(invoice.bytes,senderCountry.trim(),clientCountry.trim(),referenceId.trim(),senderLawId.trim(),clientLawId.trim(),invoiceCount)
            resource =  new ByteArrayResource(signedInvoiceBytes);

        } catch(DigSigException dse){
            logger.error("Message = "+dse.message +" stackTrace = "+dse.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (IOException e) {
            logger.error("Message = "+e.message +" stackTrace = "+e.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .contentLength(signedInvoiceBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @RequestMapping(path = "/validate", method = RequestMethod.POST)
    public String validate(@RequestParam("signedInvoice") MultipartFile signedInvoice, @RequestParam("senderCountry") String senderCountry,@RequestParam("clientCountry") String clientCountry) {

        Resource file;
        logger.info("Validating Signed Invoice ");
        if (signedInvoice.isEmpty()) {
            logger.error("No Signed Invoice Selected")
            return new ResponseEntity("No Signed Invoice Selected", HttpStatus.BAD_REQUEST);
        }

        def resultJson
        try {
             resultJson = digSigService.validate(signedInvoice.bytes,senderCountry,clientCountry)

        } catch(DigSigException dse){
            logger.error("Message = "+dse.message +" stackTrace = "+dse.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Message = "+e.message+" stackTrace = "+e.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(path = "/validateArchive", method = RequestMethod.POST)
    public String validateArchive(@RequestParam("signedInvoice") MultipartFile signedInvoice) {
        logger.info("validateArchive Signed Invoice ");
        if (signedInvoice.isEmpty()) {
            logger.error("No Signed Invoice Selected")
            return new ResponseEntity("No Signed Invoice Selected", HttpStatus.BAD_REQUEST);
        }

        def resultJson
        try {
            resultJson = digSigService.validateArchive(signedInvoice.bytes)

        } catch(DigSigException dse){
            logger.error("Message = "+dse.message +" stackTrace = "+dse.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Message = "+e.message+" stackTrace = "+e.stackTrace)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}