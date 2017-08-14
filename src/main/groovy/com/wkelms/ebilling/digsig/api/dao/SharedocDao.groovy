package com.datacert.ebilling.digsig.api.dao

import com.datacert.ebilling.digsig.api.service.DigSigException
import org.slf4j.LoggerFactory
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

@Component
public class SharedocDao {

    private final JdbcTemplate jdbcTemplate

    @Autowired
    private PlatformTransactionManager transactionManager;

    def logger = LoggerFactory.getLogger(this.class);

    @Autowired
    public SharedocDao(JdbcTemplate template) {
        this.jdbcTemplate = template
    }

    public insertDigSigRecord(senderCountry, clientCountry, referenceId, senderLawId, clientLawId, invoiceCount) {
        logger.info("inserting digsig record ${referenceId}")
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            String sql = "INSERT INTO einvoice.dbo.digital_signatures (sender_id, recipient_id, reference_id, requested_at,invoice_count,sender_country,recipient_country,created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, senderLawId, clientLawId, referenceId, (new Date()).toTimestamp(), invoiceCount, senderCountry, clientCountry, (new Date()).toTimestamp());
            transactionManager.commit(txStatus);
        }
        catch (Exception e) {
            transactionManager.rollback(txStatus);
            logger.error("Exception in insertDigSigRecord : Message = ${e.message} | Stacktrace: ${e.stackTrace}")
            throw new DigSigException(e.message);
        }
    }

    public updateDigSigRecord(signing_result, signing_details, signature_count, referenceId) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            logger.info("updating digsig record ${referenceId}")
            String sql = "update einvoice.dbo.digital_signatures set signing_result=?, signing_details=?, signature_count=?, signed_at=? where reference_id =?"
            jdbcTemplate.update(sql, signing_result, signing_details, signature_count, (new Date()).toTimestamp(), referenceId);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            logger.error("Exception in updateDigSigRecord : Message = ${e.message} | Stacktrace: ${e.stackTrace}")
            throw new DigSigException(e.message);
        }
    }
}
