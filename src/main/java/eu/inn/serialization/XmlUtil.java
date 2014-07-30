/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.inn.serialization;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Paterna
 */
public final class XmlUtil {

    private final static DocumentBuilderFactory domFactory;
    private final static XPathFactory xpathFactory;

    static {
        domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        xpathFactory = XPathFactory.newInstance();
    }

    private XmlUtil() {
    }

    public static Document createDocument() throws ParserConfigurationException {


        DocumentBuilder builder = domFactory.newDocumentBuilder();

        return builder.newDocument();
    }

    public static Document loadXMLFromFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        return loadXMLFromFile(new File(filename));
    }

    public static Document loadXMLFromFile(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = domFactory
                .newDocumentBuilder();
        return builder.parse(file);

    }

    public static Document loadXMLFromString(String content) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(content.getBytes()));

    }

    public static String getOuterXml(Node node) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            return writer.toString();
        } catch (Exception ex) {
            return "Error getting XML";
        }
    }

    public static void saveXML(Node input, File output) throws TransformerException {

        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(output);
        DOMSource source = new DOMSource(input);
        transformer.transform(source, result);
    }

    public static NodeList findXPathList(Document xml, String xpath) throws XPathExpressionException {

        XPath path = xpathFactory.newXPath();
        XPathExpression expr = path.compile(xpath);
        return (NodeList) expr.evaluate(xml, XPathConstants.NODESET);
    }

    public static NodeList findXPathList(Node xml, String xpath) throws XPathExpressionException {

        XPath path = xpathFactory.newXPath();
        XPathExpression expr = path.compile(xpath);
        return (NodeList) expr.evaluate(xml, XPathConstants.NODESET);
    }

    public static String findXPathString(Document xml, String xpath) throws XPathExpressionException {

        XPath path = xpathFactory.newXPath();
        XPathExpression expr = path.compile(xpath);
        return expr.evaluate(xml);
    }

    public static String getPathUnawareNamespace(String prop) {

        return "*[local-name()='" + prop + "']";
    }

    public static String findXPathString(Node xml, String xpath) throws XPathExpressionException {

        XPath path = xpathFactory.newXPath();
        XPathExpression expr = path.compile(xpath);
        return expr.evaluate(xml);
    }

    public static Element findXPathElement(Document xml, String xpath) throws XPathExpressionException {
        NodeList list = findXPathList(xml, xpath);
        if (list.getLength() == 1 && list.item(0).getNodeType() == Node.ELEMENT_NODE) {
            return (Element) list.item(0);
        } else {
            return null;
        }
    }

    public static Element findXPathElement(Node xml, String xpath) throws XPathExpressionException {
        NodeList list = findXPathList(xml, xpath);
        if (list.getLength() == 1 && list.item(0).getNodeType() == Node.ELEMENT_NODE) {
            return (Element) list.item(0);
        } else {
            return null;
        }
    }

    public static Schema loadSchema(File xsdFile) throws SAXException {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);

        return factory.newSchema(xsdFile);

    }

    public static Schema loadSchema(String xsdFilePath) throws SAXException {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        return factory.newSchema(new File(xsdFilePath));
    }

    public static void validateDocument(Document xmlDocument, String xsdContent) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        byte currentXMLBytes[] = xsdContent.getBytes();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(currentXMLBytes);
        Source sourceSchema = new StreamSource(byteArrayInputStream);
        Schema schema = schemaFactory.newSchema(sourceSchema);
        validateDocument(xmlDocument, schema);
    }

    public static void validateDocument(Document xmlDocument, Schema schema) throws SAXException, IOException {
        Validator validator = schema.newValidator();

        validator.validate(new DOMSource(xmlDocument));
    }
}
