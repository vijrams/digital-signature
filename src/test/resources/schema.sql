drop table digital_signatures if exists;
CREATE TABLE digital_signatures(sender_id varchar(30),recipient_id varchar(30),	reference_id varchar(30), requested_at datetime, signing_result varchar(255), signing_details ntext, signature_count int ,invoice_count int ,signed_at datetime,sender_country varchar(50),recipient_country varchar(50),created_at datetime);
