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

public class WSQuickSearch_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_wsDataClusterPK_QNAME = new QName("", "wsDataClusterPK");
    private static final QName ns2_WSDataClusterPK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSDataClusterPK");
    private CombinedSerializer ns2_myWSDataClusterPK_LiteralSerializer;
    private static final QName ns1_wsViewPK_QNAME = new QName("", "wsViewPK");
    private static final QName ns2_WSViewPK_TYPE_QNAME = new QName("urn-com-amalto-xtentis-webservice", "WSViewPK");
    private CombinedSerializer ns2_myWSViewPK_LiteralSerializer;
    private static final QName ns1_searchedValue_QNAME = new QName("", "searchedValue");
    private static final QName ns3_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns3_myns3_string__java_lang_String_String_Serializer;
    private static final QName ns1_maxItems_QNAME = new QName("", "maxItems");
    private static final QName ns3_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns3_myns3__int__int_Int_Serializer;
    private static final QName ns1_skip_QNAME = new QName("", "skip");
    private static final QName ns1_spellTreshold_QNAME = new QName("", "spellTreshold");
    private static final QName ns1_doAndBetweenWords_QNAME = new QName("", "doAndBetweenWords");
    private static final QName ns3_boolean_TYPE_QNAME = SchemaConstants.QNAME_TYPE_BOOLEAN;
    private CombinedSerializer ns3_myns3__boolean__boolean_Boolean_Serializer;
    
    public WSQuickSearch_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public WSQuickSearch_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myWSDataClusterPK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSDataClusterPK.class, ns2_WSDataClusterPK_TYPE_QNAME);
        ns2_myWSViewPK_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", com.amalto.workbench.webservices.WSViewPK.class, ns2_WSViewPK_TYPE_QNAME);
        ns3_myns3_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns3_string_TYPE_QNAME);
        ns3_myns3__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns3_int_TYPE_QNAME);
        ns3_myns3__boolean__boolean_Boolean_Serializer = (CombinedSerializer)registry.getSerializer("", boolean.class, ns3_boolean_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSQuickSearch instance = new com.amalto.workbench.webservices.WSQuickSearch();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsDataClusterPK_QNAME)) {
                member = ns2_myWSDataClusterPK_LiteralSerializer.deserialize(ns1_wsDataClusterPK_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setWsDataClusterPK((com.amalto.workbench.webservices.WSDataClusterPK)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsDataClusterPK_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_wsViewPK_QNAME)) {
                member = ns2_myWSViewPK_LiteralSerializer.deserialize(ns1_wsViewPK_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setWsViewPK((com.amalto.workbench.webservices.WSViewPK)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_wsViewPK_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_searchedValue_QNAME)) {
                member = ns3_myns3_string__java_lang_String_String_Serializer.deserialize(ns1_searchedValue_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setSearchedValue((java.lang.String)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_searchedValue_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_maxItems_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_maxItems_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setMaxItems(((Integer)member).intValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_maxItems_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_skip_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_skip_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setSkip(((Integer)member).intValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_skip_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_spellTreshold_QNAME)) {
                member = ns3_myns3__int__int_Int_Serializer.deserialize(ns1_spellTreshold_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setSpellTreshold(((Integer)member).intValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_spellTreshold_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_doAndBetweenWords_QNAME)) {
                member = ns3_myns3__boolean__boolean_Boolean_Serializer.deserialize(ns1_doAndBetweenWords_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setDoAndBetweenWords(((Boolean)member).booleanValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_doAndBetweenWords_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSQuickSearch instance = (com.amalto.workbench.webservices.WSQuickSearch)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        com.amalto.workbench.webservices.WSQuickSearch instance = (com.amalto.workbench.webservices.WSQuickSearch)obj;
        
        if (instance.getWsDataClusterPK() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myWSDataClusterPK_LiteralSerializer.serialize(instance.getWsDataClusterPK(), ns1_wsDataClusterPK_QNAME, null, writer, context);
        if (instance.getWsViewPK() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myWSViewPK_LiteralSerializer.serialize(instance.getWsViewPK(), ns1_wsViewPK_QNAME, null, writer, context);
        if (instance.getSearchedValue() == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3_string__java_lang_String_String_Serializer.serialize(instance.getSearchedValue(), ns1_searchedValue_QNAME, null, writer, context);
        if (new Integer(instance.getMaxItems()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__int__int_Int_Serializer.serialize(new Integer(instance.getMaxItems()), ns1_maxItems_QNAME, null, writer, context);
        if (new Integer(instance.getSkip()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__int__int_Int_Serializer.serialize(new Integer(instance.getSkip()), ns1_skip_QNAME, null, writer, context);
        if (new Integer(instance.getSpellTreshold()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__int__int_Int_Serializer.serialize(new Integer(instance.getSpellTreshold()), ns1_spellTreshold_QNAME, null, writer, context);
        if (new Boolean(instance.isDoAndBetweenWords()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns3_myns3__boolean__boolean_Boolean_Serializer.serialize(new Boolean(instance.isDoAndBetweenWords()), ns1_doAndBetweenWords_QNAME, null, writer, context);
    }
}
