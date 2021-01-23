package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class javaFX {
    public javaFX() {
    }

    public void java_fx(String path_exam) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        javaFX a = new javaFX();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = factory.newDocumentBuilder();
        Document boite_dialog = parseur.parse(path_exam);
        DOMImplementation domimp = parseur.getDOMImplementation();
        Document doc = domimp.createDocument((String)null, "Racine", (DocumentType)null);
        doc.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();
        rac.setAttribute("xmlns:fx", "http://javafx.com/fxml");
        a.readJavaFX(boite_dialog, doc, rac);
        DOMSource ds = new DOMSource(doc);
        StreamResult res = new StreamResult(new File("javafx.xml"));
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer tr = transform.newTransformer();
        tr.setOutputProperty("encoding", "UTF-8");
        tr.setOutputProperty("indent", "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.setOutputProperty("doctype-public", "yes");
        tr.transform(ds, res);
    }


    public void readJavaFX(Node node, Document doc, Element e) {
        Element txt = null;
        Attr attr = null;
        javaFX a = new javaFX();;
        if (node.hasAttributes()) {
            NamedNodeMap atts = node.getAttributes();
            int special = 0;

            for(int i = special; i < atts.getLength(); ++i) {
                txt = doc.createElement("texte");
                attr = (Attr)atts.item(i);
                txt.setAttribute(attr.getName(), "x");
                txt.appendChild(doc.createTextNode(attr.getValue()));
                e.appendChild(txt);
            }
        }

        if (node.getFirstChild() != null) {
            a.readJavaFX(node.getFirstChild(), doc, e);
        }

        if (node.getNextSibling() != null) {
            a.readJavaFX(node.getNextSibling(), doc, e);
        }

    }
}
