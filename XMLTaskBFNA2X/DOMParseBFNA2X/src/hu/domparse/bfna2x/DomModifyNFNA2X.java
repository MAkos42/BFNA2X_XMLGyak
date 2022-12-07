package hu.domparse.bfna2x;

import java.io.File;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.*;

public class DomModifyNFNA2X {

    public static void main(String argv[]) throws Exception {
        File xmlFile = new File("XMLTaskBFNA2X/XMLBFNA2X.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        XPath xpath = XPathFactory.newInstance().newXPath();

        //első rekord id attribútumának megváltoztatása
        NodeList purchases = (NodeList) xpath.compile("//purchase").evaluate(doc,
                XPathConstants.NODESET);
        NamedNodeMap attr = purchases.item(0).getAttributes();
        Node nodeAttr = attr.getNamedItem("id");
        nodeAttr.setTextContent("p333333333333333");

        //'u413423' userid-ű user emailének megváltoztatása
        NodeList emails = (NodeList) xpath.compile("//user[@userid=u413423]/email").evaluate(doc,
                XPathConstants.NODESET);

        for (int i = 0; i < emails.getLength(); i++) {
            emails.item(i).getChildNodes().item(0).setTextContent("potataman@gmail.com");
        }

        //utolsó applikáció nevének megváltoztatása
        NodeList appname = (NodeList) xpath.compile("//application[last()]/title")
                .evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < appname.getLength(); i++) {
            appname.item(i).getChildNodes().item(0).setTextContent("High Fleet");
        }

        //'Indie' kategória törlése
        NodeList categories = (NodeList) xpath.compile("//categories[category='Indie']").evaluate(doc,
                XPathConstants.NODESET);

        NodeList category = categories.item(0).getChildNodes();
        Element e = (Element) categories.item(0);

        for (int i = 0; i < category.getLength(); i++) {
            Node node = category.item(i);
            if (node.getTextContent().contains("Indie")) {
                e.removeChild(node);
            }
        }

        //'VR' kategória 'Virtual Reality'-ra átnevezése
        NodeList vrcat = (NodeList) xpath.compile("//category[.='VR']").evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < vrcat.getLength(); i++) {
            vrcat.item(i).setTextContent("Virtual Reality");
        }

        TransformerFactory transfactory = TransformerFactory.newInstance();
        Transformer transf = transfactory.newTransformer();

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //fájlkiíró konfigurálása
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        File wf = new File("XMLTaskBFNA2X/XMLModBFNA2X.xml"); // output fájl létrehozása

        doc.getDocumentElement().normalize();

        DOMSource src = new DOMSource(doc);

        StreamResult file = new StreamResult(wf);

        transf.transform(src, file); //output fájlba írás

    }
}