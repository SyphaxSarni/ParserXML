package com.company;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Programme {
    static int operation = 0;
    final static String[] nomfichier = {"M457.xml","M674.xml","poema","fiches.txt","renault.html","boitedialog.fxml"};
    final static String[] nomsortie = {"sortie2.xml","sortie1.xml","neruda.xml","fiches1.xml","fiches2.xml","renault.xml","javafx.xml"};
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        if (args.length < 1) {
            System.out.println("\n  Attention vous avez oublié de spécifier le nom du répertoire à traiter !");
        } else {
            String pathProjet = args[0];
            System.out.println("\n  Le nom du répertoire a été spécifié :\n  " + pathProjet + "\n");
            parcourir(pathProjet);
        }

    }

    public static void parcourir(String dossier) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = factory.newDocumentBuilder();
        DOMImplementation domimp = parseur.getDOMImplementation();
        Document doc = null;
        File f = new File(dossier);
        if(f.isFile())
        {

            InputStream ips = new FileInputStream(dossier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            boolean b = false;
            TransformerFactory transform = TransformerFactory.newInstance();
            Transformer tr = transform.newTransformer();
            StreamResult res;
            DOMSource ds;
            Element racine;
            switch(operation)
            {
                case 0:
                case 1:
                    doc = domimp.createDocument(null,"TEI_S",null);
                    doc.setXmlStandalone(true);
                    Element prem_ele = doc.createElement(nomfichier[operation]);
                    racine = doc.getDocumentElement();
                    racine.appendChild(prem_ele);
                    while ((ligne = br.readLine()) != null) {
                        if (ligne.contains("<p>")) b = true;
                        if (b == true) {
                            Element texte = doc.createElement("texte");
                            prem_ele.appendChild(texte).appendChild(doc.createTextNode(filtrer(ligne)));
                        }
                        if (ligne.contains("</p>")) b = false;
                    }
                    br.close();
                    ds = new DOMSource(doc);
                    res = new StreamResult(new File(nomsortie[operation]));
                    tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    tr.setOutputProperty(OutputKeys.INDENT, "yes");
                    tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
                    tr.transform(ds, res);
                    operation ++;
                    break;
                case 2:
                    doc = domimp.createDocument(null,"poema",null);
                    doc.setXmlStandalone(true);
                    racine = doc.getDocumentElement();
                    if((ligne = br.readLine())!=null)
                    {
                        Element texte = doc.createElement("titulo");
                        racine.appendChild(texte).appendChild(doc.createTextNode(filtrer(ligne)));
                    }
                    Element texte = null;
                    while ((ligne = br.readLine()) != null) {
                        if (ligne.length()==0)
                        {   b=false;
                            texte = doc.createElement("verso");
                        }
                        else b=true;
                        if (b == true) {
                            racine.appendChild(texte).appendChild(doc.createElement("estrofa")).appendChild(doc.createTextNode(filtrer(ligne)));
                        }
                    }
                    br.close();
                    ds = new DOMSource(doc);
                    res = new StreamResult(new File(nomsortie[operation]));
                    tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    tr.setOutputProperty(OutputKeys.INDENT, "yes");
                    tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "neruda.dtd");
                    tr.transform(ds, res);
                    operation++;
                    break;
                case 3:
                    Fiches fiches = new Fiches();
                    fiches.fiches(dossier);
                    operation++;
                    break;
                case 4:
                    renault re = new renault();
                    re.renault_const(dossier);
                    operation++;
                    break;
                case 5:
                    javaFX jafx = new javaFX();
                    jafx.java_fx(dossier);
                    operation++;
                    break;
                default:
                    break;
            }
        }
        else
        {
            String[] fichiers = f.list();
            for(String s:fichiers)
            {
                File f2 = new File(dossier+"/"+s);
                if(f2.isFile())
                {
                    parcourir(dossier+"/"+s);
                }
            }
            for(String s:fichiers)
            {
                File f2 = new File(dossier+"/"+s);
                if(f2.isDirectory())
                {
                    parcourir(dossier+"/"+s);
                }
            }
        }
    }

    public static String filtrer(String s)
    {
        StringBuilder sp = new StringBuilder();
        boolean b=true;
        for(int i =0; i<s.length(); i++)
        {
            if(s.charAt(i)=='<') b = false;
            if(b==true && s.charAt(i)!='\t')
            {
                sp.append(s.charAt(i));
            }
            if(s.charAt(i) =='>') b = true;
        }
        return String.valueOf(sp);
    }
}
