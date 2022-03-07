package org.introduct.pcip.pcipdemo.soap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoapRequest {

    private String headerContent;
    private Object body;

    public SoapRequest(String headerContent, Object body) {
        this.headerContent = headerContent;
        this.body = body;
    }

    public SoapRequest(Object body) {
        this.body = body;
    }

    public String getHeaderContent(){
        return headerContent;
    }

    public Object getBody() {
        return body;
    }
}