package com.pdx.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@JacksonXmlRootElement(localName = "xml")
@Data
public class ParseXmlForWx {
    @JacksonXmlProperty(localName = "ToUserName")
    private String ToUserName;
    @JacksonXmlProperty(localName = "FromUserName")
    private String FromUserName;
    @JacksonXmlProperty(localName = "CreateTime")
    private String CreateTime;
    @JacksonXmlProperty(localName = "MsgType")
    private String MsgType;
    @JacksonXmlProperty(localName = "Event")
    private String Event;
    @JacksonXmlProperty(localName = "EventKey")
    private String EventKey;
    @JacksonXmlProperty(localName = "Content")
    private String Content;
}
