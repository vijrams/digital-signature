
package com.wkelms.ebilling.digsig.api.trustweaver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateArchiveRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateArchiveRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InputType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="JobType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OutputType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SignedDocument" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="Archive" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="ValidationResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateArchiveRequest", propOrder = {
    "inputType",
    "jobType",
    "outputType",
    "signedDocument",
    "archive",
    "validationResult"
})
public class ValidateArchiveRequest {

    @XmlElement(name = "InputType")
    protected String inputType;
    @XmlElement(name = "JobType")
    protected String jobType;
    @XmlElement(name = "OutputType")
    protected String outputType;
    @XmlElement(name = "SignedDocument")
    protected byte[] signedDocument;
    @XmlElement(name = "Archive")
    protected byte[] archive;
    @XmlElement(name = "ValidationResult")
    protected String validationResult;

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
     * Gets the value of the archive property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getArchive() {
        return archive;
    }

    /**
     * Sets the value of the archive property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setArchive(byte[] value) {
        this.archive = value;
    }

    /**
     * Gets the value of the validationResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidationResult() {
        return validationResult;
    }

    /**
     * Sets the value of the validationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidationResult(String value) {
        this.validationResult = value;
    }

}
