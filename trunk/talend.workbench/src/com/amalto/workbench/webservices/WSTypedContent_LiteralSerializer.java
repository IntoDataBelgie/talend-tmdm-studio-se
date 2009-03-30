// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, construire R40)
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class WSTypedContent_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_url_QNAME = new QName("", "url");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final QName ns1_wsBytes_QNAME = new QName("", "wsBytes");
    private static final QName ns2_WSByteArray_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSByteArray");
    private CombinedSerializer ns2_myWSByteArray_LiteralSerializer;
    private static final QName ns1_contentType_QNAME = new QName("", "contentType");
    
    public WSTypedContent_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSTypedContent_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
        ns2_myWSByteArray_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSByteArray.class, ns2_WSByteArray_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSTypedContent instance = new com.amalto.workbench.webservices.WSTypedContent();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_url_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_url_QNAME, reader, context);
                instance.setUrl((java.lang.String)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsBytes_QNAME)) {
                member = ns2_myWSByteArray_LiteralSerializer.deserialize(ns1_wsBytes_QNAME, reader, context);
                instance.setWsBytes((com.amalto.workbench.webservices.WSByteArray)member);
                reader.nextElementContent();
            }
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_contentType_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_contentType_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setContentType((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_contentType_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSTypedContent instance = (com.amalto.workbench.webservices.WSTypedContent)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSTypedContent instance = (com.amalto.workbench.webservices.WSTypedContent)obj;
        
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getUrl(), ns1_url_QNAME, null, writer, context);
        ns2_myWSByteArray_LiteralSerializer.serialize(instance.getWsBytes(), ns1_wsBytes_QNAME, null, writer, context);
        if (instance.getContentType() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getContentType(), ns1_contentType_QNAME, null, writer, context);
    }
}
