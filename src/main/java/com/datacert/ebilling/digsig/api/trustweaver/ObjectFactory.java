
package com.datacert.ebilling.digsig.api.trustweaver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.datacert.ebilling.digsig.api.trustweaver package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidateArchiveResult_QNAME = new QName("http://www.trustweaver.com/tsswitch", "ValidateArchiveResult");
    private final static QName _ValidateArchiveRequest_QNAME = new QName("http://www.trustweaver.com/tsswitch", "ValidateArchiveRequest");
    private final static QName _GetServiceInfoResult_QNAME = new QName("http://www.trustweaver.com/tsswitch", "GetServiceInfoResult");
    private final static QName _SignResult_QNAME = new QName("http://www.trustweaver.com/tsswitch", "SignResult");
    private final static QName _ValidateResult_QNAME = new QName("http://www.trustweaver.com/tsswitch", "ValidateResult");
    private final static QName _SignRequest_QNAME = new QName("http://www.trustweaver.com/tsswitch", "SignRequest");
    private final static QName _ValidateRequest_QNAME = new QName("http://www.trustweaver.com/tsswitch", "ValidateRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.datacert.ebilling.digsig.api.trustweaver
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SignRequest }
     * 
     */
    public SignRequest createSignRequest() {
        return new SignRequest();
    }

    /**
     * Create an instance of {@link SignResponse }
     * 
     */
    public SignResponse createSignResponse() {
        return new SignResponse();
    }

    /**
     * Create an instance of {@link ValidateResponse }
     * 
     */
    public ValidateResponse createValidateResponse() {
        return new ValidateResponse();
    }

    /**
     * Create an instance of {@link ValidateArchiveRequest }
     * 
     */
    public ValidateArchiveRequest createValidateArchiveRequest() {
        return new ValidateArchiveRequest();
    }

    /**
     * Create an instance of {@link ValidateRequest }
     * 
     */
    public ValidateRequest createValidateRequest() {
        return new ValidateRequest();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link ValidateArchiveResponse }
     * 
     */
    public ValidateArchiveResponse createValidateArchiveResponse() {
        return new ValidateArchiveResponse();
    }

    /**
     * Create an instance of {@link ServiceInfoResult }
     * 
     */
    public ServiceInfoResult createServiceInfoResult() {
        return new ServiceInfoResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateArchiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "ValidateArchiveResult")
    public JAXBElement<ValidateArchiveResponse> createValidateArchiveResult(ValidateArchiveResponse value) {
        return new JAXBElement<ValidateArchiveResponse>(_ValidateArchiveResult_QNAME, ValidateArchiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateArchiveRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "ValidateArchiveRequest")
    public JAXBElement<ValidateArchiveRequest> createValidateArchiveRequest(ValidateArchiveRequest value) {
        return new JAXBElement<ValidateArchiveRequest>(_ValidateArchiveRequest_QNAME, ValidateArchiveRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceInfoResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "GetServiceInfoResult")
    public JAXBElement<ServiceInfoResult> createGetServiceInfoResult(ServiceInfoResult value) {
        return new JAXBElement<ServiceInfoResult>(_GetServiceInfoResult_QNAME, ServiceInfoResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "SignResult")
    public JAXBElement<SignResponse> createSignResult(SignResponse value) {
        return new JAXBElement<SignResponse>(_SignResult_QNAME, SignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "ValidateResult")
    public JAXBElement<ValidateResponse> createValidateResult(ValidateResponse value) {
        return new JAXBElement<ValidateResponse>(_ValidateResult_QNAME, ValidateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "SignRequest")
    public JAXBElement<SignRequest> createSignRequest(SignRequest value) {
        return new JAXBElement<SignRequest>(_SignRequest_QNAME, SignRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.trustweaver.com/tsswitch", name = "ValidateRequest")
    public JAXBElement<ValidateRequest> createValidateRequest(ValidateRequest value) {
        return new JAXBElement<ValidateRequest>(_ValidateRequest_QNAME, ValidateRequest.class, null, value);
    }

}
