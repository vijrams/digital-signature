
package com.wkelms.ebilling.digsig.api.trustweaver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SignRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SignRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InputType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="JobType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OutputType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Document" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="SenderTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReceiverTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NrOfItems" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="SignerIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AgreementIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClientData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignRequest", propOrder = {
    "inputType",
    "jobType",
    "outputType",
    "document",
    "senderTag",
    "receiverTag",
    "nrOfItems",
    "signerIdentifier",
    "agreementIdentifier",
    "clientData"
})
public class SignRequest {

    @XmlElement(name = "InputType")
    protected String inputType;
    @XmlElement(name = "JobType")
    protected String jobType;
    @XmlElement(name = "OutputType")
    protected String outputType;
    @XmlElement(name = "Document")
    protected byte[] document;
    @XmlElement(name = "SenderTag")
    protected String senderTag;
    @XmlElement(name = "ReceiverTag")
    protected String receiverTag;
    @XmlElement(name = "NrOfItems")
    protected Integer nrOfItems;
    @XmlElement(name = "SignerIdentifier")
    protected String signerIdentifier;
    @XmlElement(name = "AgreementIdentifier")
    protected String agreementIdentifier;
    @XmlElement(name = "ClientData")
    protected String clientData;

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
     * Gets the value of the nrOfItems property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNrOfItems() {
        return nrOfItems;
    }

    /**
     * Sets the value of the nrOfItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNrOfItems(Integer value) {
        this.nrOfItems = value;
    }

    /**
     * Gets the value of the signerIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerIdentifier() {
        return signerIdentifier;
    }

    /**
     * Sets the value of the signerIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerIdentifier(String value) {
        this.signerIdentifier = value;
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

}
