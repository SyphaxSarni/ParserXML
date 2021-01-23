package com.company;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class renault {
    public renault() {
    }

    public void renault_const(String path_exam) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        renault a = new renault();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        factory.setFeature("http://xml.org/sax/features/namespaces", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder parseur = factory.newDocumentBuilder();
        Document renault = parseur.parse(path_exam);
        DOMImplementation domimp = parseur.getDOMImplementation();
        Document doc = domimp.createDocument((String)null, "Concessionnaires", (DocumentType)null);
        doc.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();
        Element rac_html = renault.getDocumentElement();
        a.readRenaultFile(rac_html, doc, rac);
        DOMSource ds = new DOMSource(doc);
        StreamResult res = new StreamResult(new File("renault.xml"));
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer tr = transform.newTransformer();
        tr.setOutputProperty("encoding", "UTF-8");
        tr.setOutputProperty("indent", "yes");
        tr.setOutputProperty("doctype-public", "yes");
        tr.transform(ds, res);
    }



    public void readRenaultFile(Element elt, Document doc, Element e) {
        renault a = new renault();
        NodeList p_list = elt.getElementsByTagName("p");

        for(int i = 0; i < p_list.getLength(); ++i) {
            Element p = (Element)p_list.item(i);
            NodeList strong_list = p.getChildNodes();
            if (strong_list.getLength() >= 4 && strong_list.item(1).getNodeName().equals("strong")) {
                Element nom = doc.createElement("Nom");
                Element adresse = doc.createElement("Adresse");
                Element tel = doc.createElement("Num_téléphone");
                nom.appendChild(doc.createTextNode(strong_list.item(1).getFirstChild().getNodeValue().replaceAll("[\n\r]", " ")));
                int new_nextsibling_item = a.getNextFilteredSibling(strong_list, 2);
                adresse.appendChild(doc.createTextNode(strong_list.item(new_nextsibling_item).getNextSibling().getNodeValue().replaceAll("[\n\r]", " ").replace(":", "").trim()));
                new_nextsibling_item = a.getNextFilteredSibling(strong_list, new_nextsibling_item + 1);
                tel.appendChild(doc.createTextNode(strong_list.item(new_nextsibling_item).getNextSibling().getNodeValue().replaceAll("[\n\r]", " ").replace(":", "").trim()));
                e.appendChild(nom);
                e.appendChild(adresse);
                e.appendChild(tel);
            }
        }

    }

    public int getNextFilteredSibling(NodeList nodelist, int begin_item) {
        int x;
        for(x = begin_item; nodelist.item(x).getNodeName().equals("#text") || nodelist.item(x).getNodeName().contains("br"); ++x) {
        }

        return x;
    }
}
