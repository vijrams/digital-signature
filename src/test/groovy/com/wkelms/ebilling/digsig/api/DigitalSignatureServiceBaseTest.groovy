package com.wkelms.ebilling.digsig.api

import com.wkelms.ebilling.digsig.api.dao.SharedocDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.mock.web.MockMultipartFile
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import java.nio.file.Files
import java.nio.file.Paths
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by vijay.ramamoorthi on 6/16/2021, Wed
 **/
class DigitalSignatureServiceBaseTest {

    @Autowired
    private SharedocDao sharedocDao

    MockMultipartFile getMockMultiPartFile(String param, String parcelType) throws IOException {
        MockMultipartFile multiPartFile = null
        String filename
        switch (parcelType) {
            case "LEDES98": filename = "sample98bi.txt"
                multiPartFile = new MockMultipartFile(param, filename, "text/plain", Files.readAllBytes(Paths.get("src/test/resources/" + filename)))
                break
            case "XML21": filename = "sample.xml"
                multiPartFile = new MockMultipartFile(param, filename, "text/xml", Files.readAllBytes(Paths.get("src/test/resources/" + filename)))
                break
            case "LEDES98_SIGNED": filename = "sample98BI.signed.txt"
                multiPartFile = new MockMultipartFile(param, filename, "text/plain", Files.readAllBytes(Paths.get("src/test/resources/" + filename)))
                break
            case "LEDESXML_SIGNED": filename = "sample98BI.signed.txt"
                multiPartFile = new MockMultipartFile(param, filename, "text/plain", Files.readAllBytes(Paths.get("src/test/resources/" + filename)))
                break
            case "LEDES_SIGNED_BAD": filename = "sample98BI-bad.signed.txt"
                multiPartFile = new MockMultipartFile(param, filename, "text/plain", Files.readAllBytes(Paths.get("src/test/resources/" + filename)))
                break
        }
        return multiPartFile
    }

    def getDefaultParams(def rType) {
        MultiValueMap<String, String> nMap = new LinkedMultiValueMap<String, Object>()

        def params = [:]
        if(rType=="SIGN") params = ["senderCountry":"USA","clientCountry":"USA","clientLawId":"LAW000002135","senderLawId":"LAW000001245","referenceId":"P00021045642","invoiceCount":"1" ]
        if(rType=="VALI") params = ["senderCountry":"USA","clientCountry":"USA"]
        if(rType=="VALIOLD") params = ["sender_country":"USA","recipient_country":"USA","law_id":"LAW000002135"]
        params.each{ k,v ->
            nMap.add(k,v)
        }
        nMap
    }

    def checkDigSigRecord(refId, sender, recipient, result){
        String [] args = new String[4]
        args[0] = refId
        args[1] = sender
        args[2] = recipient
        args[3] = result
        String sql = "select * from digital_signatures where reference_id =? and sender_id =? and recipient_id =? and signing_result=?"
        sharedocDao.jdbcTemplate.query(sql,
                    new ResultSetExtractor<Boolean>() {
                        @Override
                        Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                            return rs.next()
                        }
                    }, args)
    }

}
