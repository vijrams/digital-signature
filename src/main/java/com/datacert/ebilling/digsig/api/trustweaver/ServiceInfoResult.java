
package com.datacert.ebilling.digsig.api.trustweaver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceInfoResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceInfoResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Major" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Minor" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Build" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ProductEdition" type="{http://www.trustweaver.com/tsswitch}ProdEdType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceInfoResult", propOrder = {
    "major",
    "minor",
    "build",
    "productEdition"
})
public class ServiceInfoResult {

    @XmlElement(name = "Major")
    protected int major;
    @XmlElement(name = "Minor")
    protected int minor;
    @XmlElement(name = "Build")
    protected int build;
    @XmlElement(name = "ProductEdition", required = true)
    protected ProdEdType productEdition;

    /**
     * Gets the value of the major property.
     * 
     */
    public int getMajor() {
        return major;
    }

    /**
     * Sets the value of the major property.
     * 
     */
    public void setMajor(int value) {
        this.major = value;
    }

    /**
     * Gets the value of the minor property.
     * 
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Sets the value of the minor property.
     * 
     */
    public void setMinor(int value) {
        this.minor = value;
    }

    /**
     * Gets the value of the build property.
     * 
     */
    public int getBuild() {
        return build;
    }

    /**
     * Sets the value of the build property.
     * 
     */
    public void setBuild(int value) {
        this.build = value;
    }

    /**
     * Gets the value of the productEdition property.
     * 
     * @return
     *     possible object is
     *     {@link ProdEdType }
     *     
     */
    public ProdEdType getProductEdition() {
        return productEdition;
    }

    /**
     * Sets the value of the productEdition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProdEdType }
     *     
     */
    public void setProductEdition(ProdEdType value) {
        this.productEdition = value;
    }

}
