package com.wkelms.ebilling.digsig.api.dao


import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Created by ranadeep.palle on 4/17/2017.
 */
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition

@Slf4j
@Component
class SharedocDao {

    private final JdbcTemplate jdbcTemplate

    @Autowired
    private PlatformTransactionManager transactionManager

    @Autowired
    SharedocDao(JdbcTemplate template) {
        this.jdbcTemplate = template
    }

    def insertDigSigRecord(senderCountry, clientCountry, referenceId, senderLawId, clientLawId, invoiceCount) {
        log.info("inserting digsig record ${referenceId}")
        TransactionDefinition txDef = new DefaultTransactionDefinition()
        TransactionStatus txStatus = transactionManager.getTransaction(txDef)
        try {
            String sql = "INSERT INTO digital_signatures (sender_id, recipient_id, reference_id, requested_at,invoice_count,sender_country,recipient_country,created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            jdbcTemplate.update(sql, senderLawId, clientLawId, referenceId, (new Date()), invoiceCount, senderCountry, clientCountry, (new Date()))
            transactionManager.commit(txStatus)
        }
        catch (Exception e) {
            transactionManager.rollback(txStatus)
            log.error("Exception in insertDigSigRecord : Message = ${e.message} | Stacktrace: ${e.stackTrace}")
            throw new Exception(e.message)
        }
    }

    def updateDigSigRecord(signing_result, signing_details, signature_count, referenceId) {
        TransactionDefinition txDef = new DefaultTransactionDefinition()
        TransactionStatus txStatus = transactionManager.getTransaction(txDef)
        try {
            log.info("updating digsig record ${referenceId}")
            String sql = "update digital_signatures set signing_result=?, signing_details=?, signature_count=?, signed_at=? where reference_id =?"
            jdbcTemplate.update(sql, signing_result, signing_details, signature_count, (new Date()), referenceId)
            transactionManager.commit(txStatus)
        } catch (Exception e) {
            transactionManager.rollback(txStatus)
            log.error("Exception in updateDigSigRecord : Message = ${e.message} | Stacktrace: ${e.stackTrace}")
            throw new Exception(e.message)
        }
    }
}
