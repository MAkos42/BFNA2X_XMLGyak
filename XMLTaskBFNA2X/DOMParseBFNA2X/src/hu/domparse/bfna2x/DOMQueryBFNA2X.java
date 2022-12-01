package hu.domparse.bfna2x;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import javax.xml.xpath.*;

public class DOMQueryBFNA2X {
    public static void main(String[] args) throws Exception {

        File xmlFile = new File("XMLTaskBFNA2X/XMLBFNA2X.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        XPath xpath = XPathFactory.newInstance().newXPath();

        String expression1 = "/store/purchase[1]/application";
        String expression2 = "//user[@userid=413423]";
        String expression3 = "//application";
        String expression4 = "store/purchase/user[contains(email,'@gmail.com')]";
        String expression5 = "//purchase[.//category='VR']";

        // root elem első purchase elemének az applikációjának a kiírása
        System.out.println("1. expression = " + expression1);
        NodeList nodelist = (NodeList) xpath.compile(expression1).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodelist.getLength(); i++) {

            Node node = nodelist.item(i);
            System.out.println("Aktuális elem: " + node.getNodeName());
            WriteAllChildren(nodelist, 0);
            System.out.println();
        }

        // '413423' userid-vel rendelkező user adatai
        System.out.println("2. expression = " + expression2);
        NodeList nodelist2 = (NodeList) xpath.compile(expression2).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodelist2.getLength(); i++) {

            Node node = nodelist2.item(i);
            System.out.println("Aktuális elem: " + node.getNodeName());
            WriteAllChildren(nodelist2, 0);
            System.out.println();
        }

        // minden application elem
        System.out.println("3. expression = " + expression3);
        NodeList nodelist3 = (NodeList) xpath.compile(expression3).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodelist3.getLength(); i++) {

            Node node = nodelist3.item(i);
            System.out.println("Aktuális elem: " + node.getNodeName());
            WriteAllChildren(nodelist3, 0);
            System.out.println();
        }

        // '@gmail.com'-ra végződő emaillal rendelkező userek
        System.out.println("4. expression = " + expression4);
        NodeList nodelist4 = (NodeList) xpath.compile(expression4).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodelist4.getLength(); i++) {

            Node node = nodelist4.item(i);
            System.out.println("Aktuális elem: " + node.getNodeName());
            WriteAllChildren(nodelist4, 0);
            System.out.println();
        }

        // 'VR' kategóriával rendelkező userek
        System.out.println("5. expression = " + expression5);
        NodeList nodelist5 = (NodeList) xpath.compile(expression5).evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodelist5.getLength(); i++) {

            Node node = nodelist5.item(i);
            System.out.println("Aktuális elem: " + node.getNodeName());
            WriteAllChildren(nodelist5, 0);
            System.out.println();
        }
    }

    private static void WriteAllChildren(NodeList childNodes, int indent) {
        for (int i = 0; i < childNodes.getLength(); i++) { // minden gyerek node-on végig futva
            Node node = childNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) { // Element típusú node-ok kiválasztása
                Element element = (Element) node;

                NamedNodeMap attributes = element.getAttributes(); // attribútumok keresése

                indent(indent);
                if (attributes.getLength() > 0) // attribútumokkal rendelkező Element-ek kiírása
                    System.out.println("[" + element.getNodeName() + "] " + attributes.item(0).toString() + " BEGIN");
                else // Attribútum nélküli elemek kiírása
                    System.out.println("[" + element.getNodeName() + "] BEGIN");

                WriteAllChildren(element.getChildNodes(), indent + 1); // 1-el behúzva rekurzív meghívása

                indent(indent);
                System.out.println("[" + element.getNodeName() + "] END");
            }

            if (node.getNodeType() == Node.TEXT_NODE) { // szöveggel rendelkező node-ok tartalmának kiírása
                if (!node.getNodeValue().trim().isEmpty()) {
                    indent(indent);
                    System.out.println(node.getNodeValue());
                }
            }

        }
    }

    private static void indent(int indent) { // behúzás megvalósítása
        for (int k = 0; k < indent; k++) {
            System.out.print("    ");
        }
    }

}
