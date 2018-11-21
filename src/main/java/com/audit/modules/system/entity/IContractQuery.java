
package com.audit.modules.system.entity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.naming.spi.ObjectFactory;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IContractQuery", targetNamespace = "http://tempuri.org/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IContractQuery {


    /**
     * 
     * @param cqs
     * @return
     *     returns org.tempuri.ContractQueryOut
     */
    @WebMethod(operationName = "ContractQuerySrvRun", action = "http://tempuri.org/IContractQuery/ContractQuerySrvRun")
    @WebResult(name = "ContractQuerySrvRunResult", partName = "ContractQuerySrvRunResult")
    public ContractQueryOut contractQuerySrvRun(
            @WebParam(name = "cqs", partName = "cqs")
                    ContractQueryIn cqs);

}
