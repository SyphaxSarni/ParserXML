package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import org.w3c.dom.Node;

public class Fiches {
    public Fiches() {
    }

    public void fiches(String path_exam) throws IOException, ParserConfigurationException, TransformerException {
        Fiches a = new Fiches();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = factory.newDocumentBuilder();
        DOMImplementation domimp = parseur.getDOMImplementation();
        DocumentType dtd = domimp.createDocumentType("FICHES", (String)null, "fiches.dtd");
        Document doc = domimp.createDocument((String)null, "FICHES", dtd);
        Node pi = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"fiche.xsl\"");
        Document doc_fiche2 = domimp.createDocument((String)null, "FICHES", (DocumentType)null);
        Node pi_2 = doc_fiche2.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"fiche.xsl\"");
        doc.setXmlStandalone(true);
        doc_fiche2.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();
        doc.insertBefore(pi, rac);
        Element rac_fiche2 = doc_fiche2.getDocumentElement();
        doc_fiche2.insertBefore(pi_2, rac_fiche2);
        a.readTxtFile(path_exam, doc, rac, doc_fiche2, rac_fiche2);
        DOMSource ds = new DOMSource(doc);
        StreamResult res = new StreamResult(new File("sortie_FICHE1.xml"));
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer tr = transform.newTransformer();
        tr.setOutputProperty("encoding", "UTF-8");
        tr.setOutputProperty("indent", "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.setOutputProperty("doctype-system", "fiches.dtd");
        tr.transform(ds, res);
        a.maj_fiche("./sortie_FICHE1.xml");
        ds = new DOMSource(doc_fiche2);
        res = new StreamResult(new File("sortie_FICHE2.xml"));
        transform = TransformerFactory.newInstance();
        tr = transform.newTransformer();
        tr.setOutputProperty("encoding", "UTF-8");
        tr.setOutputProperty("indent", "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.setOutputProperty("doctype-public", "yes");
        tr.transform(ds, res);
        a.maj_fiche("./sortie_FICHE2.xml");
    }



    public void readTxtFile(String path, Document doc, Element e, Document doc2, Element e2) throws IOException {
        File infile = new File(path);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(infile), "UTF8"));
        int id = 1;
        String act = null;
        String line = in.readLine();
        Element fiche = null;
        Element fiche2 = null;
        Element ar = null;
        Element ar_2 = null;
        Element fr_2 = null;
        Element fr = null;
        Element Do = null;
        Element Do_fr = null;
        Element sd_fr = null;
        Element sd = null;
        Element ve = null;
        Element ve_fr = null;
        Element ve_fr_2 = null;
        Element ve_2 = null;
        Element Do_2 = null;
        Element Do_fr_2 = null;
        Element sd_2 = null;

        for(Object var28 = null; line != null; line = in.readLine()) {
            Element ty;
            Element ty_2;
            if (line.endsWith("BE")) {
                fiche = doc.createElement("FICHE");
                fiche2 = doc2.createElement("FICHE");
                fiche.setAttribute("id", String.valueOf(id));
                fiche2.setAttribute("id", String.valueOf(id));
                ++id;
                e.appendChild(fiche);
                e2.appendChild(fiche2);
                ty = doc.createElement("BE");
                ty_2 = doc2.createElement("BE");
                fiche.appendChild(ty);
                fiche2.appendChild(ty_2);
                ty.appendChild(doc.createTextNode(line.substring(0, line.length() - 2)));
                ty_2.appendChild(doc2.createTextNode(line.substring(0, line.length() - 2)));
            } else if (line.endsWith("TY")) {
                ty = doc.createElement("TY");
                ty_2 = doc2.createElement("TY");
                fiche.appendChild(ty);
                fiche2.appendChild(ty_2);
                ty.appendChild(doc.createTextNode("TY : " + line.substring(0, line.length() - 2)));
                ty_2.appendChild(doc2.createTextNode("TY : " + line.substring(0, line.length() - 2)));
            } else if (line.endsWith("AU")) {
                ty = doc.createElement("AU");
                fiche.appendChild(ty);
                ty.appendChild(doc.createTextNode("AU : " + line.substring(0, line.length() - 2)));
                ty_2 = doc2.createElement("AU");
                fiche2.appendChild(ty_2);
                ty_2.appendChild(doc2.createTextNode("AU : " + line.substring(0, line.length() - 2)));
            } else if (line.startsWith("AR")) {
                act = "ar";
                ar = doc.createElement("Langue");
                ar.setAttribute("id", "AR");
                fiche.appendChild(ar);
                ar.appendChild(Do);
                ar.appendChild(sd);
                ar_2 = doc2.createElement("Langue");
                ar_2.setAttribute("id", "AR");
                fiche2.appendChild(ar_2);
            } else if (line.endsWith("FR")) {
                act = "fr";
                fr = doc.createElement("Langue");
                fr.setAttribute("id", "FR");
                fiche.appendChild(fr);
                fr.appendChild(Do_fr);
                fr.appendChild(sd_fr);
                fr_2 = doc2.createElement("Langue");
                fr_2.setAttribute("id", "FR");
                fiche2.appendChild(fr_2);
            } else if (line.endsWith("DO")) {
                Do = doc.createElement("DO");
                Do.appendChild(doc.createTextNode("DO : " + line.substring(0, line.length() - 2)));
                Do_fr = doc.createElement("DO");
                Do_fr.appendChild(doc.createTextNode("DO : " + line.substring(0, line.length() - 2)));
                Do_2 = doc2.createElement("DO");
                fiche2.appendChild(Do_2);
                Do_2.appendChild(doc2.createTextNode("DO : " + line.substring(0, line.length() - 2)));
            } else if (line.endsWith("SD")) {
                sd = doc.createElement("SD");
                sd.appendChild(doc.createTextNode("SD : " + line.substring(0, line.length() - 2)));
                sd_fr = doc.createElement("SD");
                sd_fr.appendChild(doc.createTextNode("SD : " + line.substring(0, line.length() - 2)));
                sd_2 = doc2.createElement("SD");
                fiche2.appendChild(sd_2);
                sd_2.appendChild(doc2.createTextNode("SD : " + line.substring(0, line.length() - 2)));
            } else if (line.endsWith("VE :")) {
                ve = doc.createElement("VE");
                ve.appendChild(doc.createTextNode("VE : " + line.substring(0, line.length() - 4)));
                ve_fr = doc.createElement("VE");
                ve_fr.appendChild(doc.createTextNode("VE : " + line.substring(0, line.length() - 4)));
                ve_2 = doc2.createElement("VE");
                ve_2.appendChild(doc2.createTextNode("VE : " + line.substring(0, line.length() - 4)));
                ve_fr_2 = doc2.createElement("VE");
                ve_fr_2.appendChild(doc2.createTextNode("VE : " + line.substring(0, line.length() - 4)));
                if (act.equals("ar")) {
                    ar.appendChild(ve);
                    ar_2.appendChild(ve_2);
                } else {
                    fr.appendChild(ve_fr);
                    fr_2.appendChild(ve_fr_2);
                }
            } else if (line.endsWith("DF :")) {
                ve = doc.createElement("DF");
                ve.appendChild(doc.createTextNode("DF : " + line.substring(0, line.length() - 4)));
                ve_fr = doc.createElement("DF");
                ve_fr.appendChild(doc.createTextNode("DF : " + line.substring(0, line.length() - 4)));
                ve_2 = doc2.createElement("DF");
                ve_2.appendChild(doc2.createTextNode("DF : " + line.substring(0, line.length() - 4)));
                ve_fr_2 = doc2.createElement("DF");
                ve_fr_2.appendChild(doc2.createTextNode("DF : " + line.substring(0, line.length() - 4)));
                if (act.equals("ar")) {
                    ar.appendChild(ve);
                    ar_2.appendChild(ve_2);
                } else {
                    fr.appendChild(ve_fr);
                    fr_2.appendChild(ve_fr_2);
                }
            } else if (line.endsWith("PH :")) {
                ve = doc.createElement("PH");
                ve.appendChild(doc.createTextNode("PH : " + line.substring(0, line.length() - 4)));
                ve_fr = doc.createElement("PH");
                ve_fr.appendChild(doc.createTextNode("PH : " + line.substring(0, line.length() - 4)));
                ve_2 = doc2.createElement("PH");
                ve_2.appendChild(doc2.createTextNode("PH : " + line.substring(0, line.length() - 4)));
                ve_fr_2 = doc2.createElement("PH");
                ve_fr_2.appendChild(doc2.createTextNode("PH : " + line.substring(0, line.length() - 4)));
                if (act.equals("ar")) {
                    ar.appendChild(ve);
                    ar_2.appendChild(ve_2);
                } else {
                    fr.appendChild(ve_fr);
                    fr_2.appendChild(ve_fr_2);
                }
            } else if (line.endsWith("NT :")) {
                ve = doc.createElement("NT");
                ve.appendChild(doc.createTextNode("NT : " + line.substring(0, line.length() - 4)));
                ve_fr = doc.createElement("NT");
                ve_fr.appendChild(doc.createTextNode("NT : " + line.substring(0, line.length() - 4)));
                ve_2 = doc2.createElement("NT");
                ve_2.appendChild(doc2.createTextNode("NT : " + line.substring(0, line.length() - 4)));
                ve_fr_2 = doc2.createElement("NT");
                ve_fr_2.appendChild(doc2.createTextNode("NT : " + line.substring(0, line.length() - 4)));
                if (act.equals("ar")) {
                    ar.appendChild(ve);
                    ar_2.appendChild(ve_2);
                } else {
                    fr.appendChild(ve_fr);
                    fr_2.appendChild(ve_fr_2);
                }
            } else if (line.contains(" ص ") || line.contains("،ص ") || line.contains("p.") || line.contains("الساعة")) {
                ve = doc.createElement("RF");
                ve_fr = doc.createElement("RF");
                ve_2 = doc2.createElement("RF");
                ve_fr_2 = doc2.createElement("RF");
                String balises = "\\s+(NT|DF|VE|PH|NT)\\s+";
                String txt = "(.*\\.\\s*).*";
                Pattern ptr_bal = Pattern.compile(balises);
                Pattern ptr_txt = Pattern.compile(txt);
                Matcher matcher_balise = ptr_bal.matcher(line);
                Matcher matcher_txt = ptr_txt.matcher(line);
                ArrayList<String> list = new ArrayList();
                ArrayList list_txt = new ArrayList();

                while(matcher_balise.find()) {
                    if (matcher_balise.group(1).equals("RF")) {
                        list.add(matcher_balise.group(1) + " | ");
                    } else {
                        list.add(matcher_balise.group(1) + " : ");
                    }
                }

                if (matcher_txt.find()) {
                    list_txt.add(matcher_txt.group(1));
                }

                Collections.reverse(list);
                StringBuilder sb = new StringBuilder();
                list.forEach(sb::append);
                ve.appendChild(doc.createTextNode("RF | " + String.valueOf(sb) + matcher_txt.group(1).replaceAll("\t", "\t\t")));
                ve_fr.appendChild(doc.createTextNode("RF | " + String.valueOf(sb) + matcher_txt.group(1).replaceAll("\t", "\t\t")));
                ve_2.appendChild(doc2.createTextNode("RF | " + String.valueOf(sb) + matcher_txt.group(1).replaceAll("\t", "\t\t")));
                ve_fr_2.appendChild(doc2.createTextNode("RF | " + String.valueOf(sb) + matcher_txt.group(1).replaceAll("\t", "\t\t")));
                if (act.equals("ar")) {
                    ar.appendChild(ve);
                    ar_2.appendChild(ve_2);
                } else {
                    fr.appendChild(ve_fr);
                    fr_2.appendChild(ve_fr_2);
                }
            }
        }

        in.close();
    }

    public void maj_fiche(String path) throws IOException {
        ArrayList<String> list = new ArrayList();
        List<String> lines = Files.readAllLines(Paths.get(path));
        lines.forEach((line) -> {
            if (line.contains("xml-stylesheet")) {
                String stylesheet;
                String fiches;
                if (path == "./sortie_FICHE2.xml") {
                    stylesheet = "<?xml-stylesheet type=\"text/xsl\" href=\"fiche.xsl\"?>\n";
                    list.add(stylesheet);
                    fiches = "<FICHES>\n";
                    list.add(fiches);
                } else {
                    stylesheet = "<!DOCTYPE FICHES SYSTEM \"fiches.dtd\">\n";
                    list.add(stylesheet);
                    fiches = "<?xml-stylesheet type=\"text/xsl\" href=\"fiche.xsl\"?>\n";
                    list.add(fiches);
                }
            } else {
                list.add(line + "\n");
            }

        });
        File fiche = new File(path);
        if (fiche.delete()) {
            System.out.print("");
        }

        BufferedWriter out;
        if (path == "./sortie_FICHE2.xml") {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("fiche2.xml"), "UTF-8"));
        } else {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("fiche1.xml"), "UTF-8"));
        }
        list.forEach((line) -> {
            try {
                out.write(line);
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
        out.close();
    }
}
