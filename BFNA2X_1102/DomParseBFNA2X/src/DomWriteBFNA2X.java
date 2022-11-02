import java.io.File;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class DomWriteBFNA2X {
    public static void main(String argv[]){
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.newDocument();

        } catch (ParserConfigurationException e) {
        }

        Element root = doc.createElementNS("DOMBFNA2X", "users");
        doc.appendChild(root);

        root.appendChild(createUser(doc, "1", "Ákos", "Mészáros", "Mérnökinformatikus"));
        root.appendChild(createUser(doc, "2", "János", "Kerekes", "Sofőr"));
        root.appendChild(createUser(doc, "3", "Elemér", "Mixáth", "pilóta"));

        TransformerFactory transfactory = TransformerFactory.newInstance();
        Transformer transf = null;
        try {
            transf = transfactory.newTransformer();
        } catch (TransformerConfigurationException e) {
        }

        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource src = new DOMSource(doc);

        File fw = new File("users1BFNA2X.xml");

        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(fw);

        try {
            transf.transform(src,console);
            transf.transform(src, file);
        } catch (TransformerException e) {
        }
    }

    private static Node createUser(Document doc, String id, String firstName, String lastName, String profession) {
        Element user = doc.createElement("user");

        user.setAttribute("id", id);
        user.appendChild(createUserElement(doc,"firstname",firstName));
        user.appendChild(createUserElement(doc,"lastname",lastName));
        user.appendChild(createUserElement(doc,"profession",profession));

        return user;
    }

    private static Node createUserElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}
