
package com.wkelms.ebilling.digsig.api.trustweaver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InputType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="JobType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OutputType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Document" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="SignedDocument" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="SenderTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReceiverTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AgreementIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClientData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ExcludeOriginalDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateRequest", propOrder = {
    "inputType",
    "jobType",
    "outputType",
    "document",
    "signedDocument",
    "senderTag",
    "receiverTag",
    "agreementIdentifier",
    "clientData",
    "excludeOriginalDocument"
})
public class ValidateRequest {

    @XmlElement(name = "InputType")
    protected String inputType;
    @XmlElement(name = "JobType")
    protected String jobType;
    @XmlElement(name = "OutputType")
    protected String outputType;
    @XmlElement(name = "Document")
    protected byte[] document;
    @XmlElement(name = "SignedDocument")
    protected byte[] signedDocument;
    @XmlElement(name = "SenderTag")
    protected String senderTag;
    @XmlElement(name = "ReceiverTag")
    protected String receiverTag;
    @XmlElement(name = "AgreementIdentifier")
    protected String agreementIdentifier;
    @XmlElement(name = "ClientData")
    protected String clientData;
    @XmlElement(name = "ExcludeOriginalDocument")
    protected boolean excludeOriginalDocument;

    /**
     * Gets the value of the inputType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * Sets the value of the inputType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputType(String value) {
        this.inputType = value;
    }

    /**
     * Gets the value of the jobType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * Sets the value of the jobType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobType(String value) {
        this.jobType = value;
    }

    /**
     * Gets the value of the outputType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputType() {
        return outputType;
    }

    /**
     * Sets the value of the outputType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputType(String value) {
        this.outputType = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocument(byte[] value) {
        this.document = value;
    }

    /**
     * Gets the value of the signedDocument property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getSignedDocument() {
        return signedDocument;
    }

    /**
     * Sets the value of the signedDocument property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setSignedDocument(byte[] value) {
        this.signedDocument = value;
    }

    /**
     * Gets the value of the senderTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderTag() {
        return senderTag;
    }

    /**
     * Sets the value of the senderTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderTag(String value) {
        this.senderTag = value;
    }

    /**
     * Gets the value of the receiverTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverTag() {
        return receiverTag;
    }

    /**
     * Sets the value of the receiverTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverTag(String value) {
        this.receiverTag = value;
    }

    /**
     * Gets the value of the agreementIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementIdentifier() {
        return agreementIdentifier;
    }

    /**
     * Sets the value of the agreementIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementIdentifier(String value) {
        this.agreementIdentifier = value;
    }

    /**
     * Gets the value of the clientData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientData() {
        return clientData;
    }

    /**
     * Sets the value of the clientData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientData(String value) {
        this.clientData = value;
    }

    /**
     * Gets the value of the excludeOriginalDocument property.
     * 
     */
    public boolean isExcludeOriginalDocument() {
        return excludeOriginalDocument;
    }

    /**
     * Sets the value of the excludeOriginalDocument property.
     * 
     */
    public void setExcludeOriginalDocument(boolean value) {
        this.excludeOriginalDocument = value;
    }

}
