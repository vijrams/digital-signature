
package com.wkelms.ebilling.digsig.api.trustweaver;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProdEdType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProdEdType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Enterprise"/&gt;
 *     &lt;enumeration value="Center"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProdEdType")
@XmlEnum
public enum ProdEdType {

    @XmlEnumValue("Enterprise")
    ENTERPRISE("Enterprise"),
    @XmlEnumValue("Center")
    CENTER("Center");
    private final String value;

    ProdEdType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProdEdType fromValue(String v) {
        for (ProdEdType c: ProdEdType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
