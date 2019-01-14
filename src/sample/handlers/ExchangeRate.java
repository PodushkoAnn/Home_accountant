package sample.handlers;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class ExchangeRate {

    private static final String FILE_NAME = "src/sample/resources/rates.xml";

    public static float getRateFromFile(String from, String to) throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(FILE_NAME);

        float fromRate = Float.parseFloat(findRate(document, from));
        float toRate = Float.parseFloat(findRate(document, to));

        return fromRate / toRate;
    }

    private static String findRate(Document document, String valute) throws DOMException, XPathExpressionException {

        XPathFactory pathFactory = XPathFactory.newInstance();
        XPath xpath = pathFactory.newXPath();

        XPathExpression expr = xpath.compile("/ValCurs/Valute[CharCode='" + valute + "']/Value");

        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            return n.getTextContent().replace(',' , '.');
        }
        return "1";
    }

}
