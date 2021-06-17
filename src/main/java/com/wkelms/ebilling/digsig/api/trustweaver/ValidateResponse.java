
package com.wkelms.ebilling.digsig.api.trustweaver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Result" type="{http://www.trustweaver.com/tsswitch}Result" minOccurs="0"/&gt;
 *         &lt;element name="Document" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="ValidationResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Archive" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="Details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateResponse", propOrder = {
    "result",
    "document",
    "validationResult",
    "archive",
    "details"
})
public class ValidateResponse {

    @XmlElement(name = "Result")
    protected Result result;
    @XmlElement(name = "Document")
    protected byte[] document;
    @XmlElement(name = "ValidationResult")
    protected String validationResult;
    @XmlElement(name = "Archive")
    protected byte[] archive;
    @XmlElement(name = "Details")
    protected String details;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setResult(Result value) {
        this.result = value;
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
     * Gets the value of the details property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetails(String value) {
        this.details = value;
    }

}
