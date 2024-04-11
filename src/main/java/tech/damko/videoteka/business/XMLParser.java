package tech.damko.videoteka.business;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import tech.damko.videoteka.presentation.Pictures;
import tech.damko.videoteka.model.Movie;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XMLParser {

    public XMLParser(){}

    private void createNewXmlFile(String xmlFile){

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;

        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createNewXmlFile.");
        }

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("filmi");
        doc.appendChild(rootElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;

        try {

            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFile));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createNewXmlFile.");
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createNewXmlFile.");
        }

    }

    public ArrayList<Movie> readXML(String xmlFile){


        ArrayList<Movie> filmi = new ArrayList<Movie>();
        Movie x = null;

        try
        {
            File file = new File(xmlFile);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize ();

            NodeList listOfDiscs = document.getElementsByTagName("disc");

            int i = 0;
            while( i < listOfDiscs.getLength()){

                Node discNode = listOfDiscs.item(i);
                String disc = discNode.getAttributes().getNamedItem("disc").getNodeValue();

                NodeList nameFromDiscNL = discNode.getChildNodes();

                int j = 0;
                while(j < nameFromDiscNL.getLength()){

                    Node nameFromDiscNode = nameFromDiscNL.item(j);
                    String nameFromDisc = nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue();

                    NodeList nameNL = nameFromDiscNode.getChildNodes();

                    int z = 0;
                    while(z < nameNL.getLength()){

                        Node nameNode = nameNL.item(z);
                        NodeList nameChildNL = nameNode.getChildNodes();

                        String name = nameNode.getAttributes().getNamedItem("name").getNodeValue();
                        String genre = "";
                        String rating = "";
                        String description = "";
                        String stars = "";
                        String director = "";
                        String duration = "";
                        String releaseDate = "";
                        String url = "";
                        String imageSrcDec = "";
                        String imageSrc = "";

                        int u = 0;
                        while(u < nameChildNL.getLength())
                        {
                            Node nameChild = nameChildNL.item(u);
                            String childAttrib = nameChild.getNodeName();

                            if(childAttrib.equals("genre")){
                                genre = nameChild.getAttributes().getNamedItem("genre").getNodeValue();
                            }
                            if(childAttrib.equals("rating")){
                                rating = nameChild.getAttributes().getNamedItem("rating").getNodeValue();
                            }
                            if(childAttrib.equals("description")){
                                description = nameChild.getAttributes().getNamedItem("description").getNodeValue();
                            }
                            if(childAttrib.equals("stars")){
                                stars = nameChild.getAttributes().getNamedItem("stars").getNodeValue();
                            }
                            if(childAttrib.equals("director")){
                                director = nameChild.getAttributes().getNamedItem("director").getNodeValue();
                            }
                            if(childAttrib.equals("duration")){
                                duration = nameChild.getAttributes().getNamedItem("duration").getNodeValue();
                            }
                            if(childAttrib.equals("releaseDate")){
                                releaseDate = nameChild.getAttributes().getNamedItem("releaseDate").getNodeValue();
                            }
                            if(childAttrib.equals("url")){
                                url = nameChild.getAttributes().getNamedItem("url").getNodeValue();
                            }
                            if(childAttrib.equals("imageSrcDec")){
                                imageSrc = nameChild.getAttributes().getNamedItem("imageSrcDec").getNodeValue();
                            }

                            ++u;
                        }

                        x = new Movie(disc,name,genre,rating,description,stars,"",director,duration,"",releaseDate,nameFromDisc,url,imageSrc);
                        filmi.add(x);

                        ++z;
                    }

                    ++j;
                }

                ++i;
            }


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.readXML. Generiran bo XML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.readXML. Generiran bo XML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.readXML. Generiran bo XML");
        }

        return filmi;
    }

    public void createXML(ArrayList<Movie> filmiObject,String xmlFile,boolean merge){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements
            Document doc = null;
            Element rootElement = null;

            if(merge){
                doc = docBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            }
            else{
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("filmi");
                doc.appendChild(rootElement);
            }


            String disc="";
            String nameFromDisc = "";
            Element discE = null;
            Element nameFromDiscE = null;
            Element film = null;
            Attr attr = null;
            Element x = null;

            for(Movie f : filmiObject){

                if(!merge){
                    if(!f.getDisc().toLowerCase().equals(disc.toLowerCase())){

                        disc = f.getDisc();
                        discE = doc.createElement("disc");
                        attr = doc.createAttribute("disc");
                        attr.setValue(disc);
                        discE.setAttributeNode(attr);
                        rootElement.appendChild(discE);
                    }
                }
                else{
                    if(!f.getDisc().toLowerCase().equals(disc.toLowerCase())){

                        if(!this.existsDiscXML(xmlFile, f.getDisc())){
                            disc = f.getDisc();
                            discE = doc.createElement("disc");
                            attr = doc.createAttribute("disc");
                            attr.setValue(disc);
                            discE.setAttributeNode(attr);
                            doc.getFirstChild().appendChild(discE);
                        }
                        else{
                            disc = f.getDisc();
                            NodeList listOfDiscs = doc.getElementsByTagName("disc");
                            int i = 0;
                            while( i < listOfDiscs.getLength()){

                                Node discNode = listOfDiscs.item(i);
                                String discX = discNode.getAttributes().getNamedItem("disc").getNodeValue();
                                if(discX.toLowerCase().equals(disc.toLowerCase())){
                                    discE = (Element)discNode;
                                    break;
                                }

                                ++i;
                            }

                        }

                    }

                }
                if(!f.getNameFromDisc().equals(nameFromDisc)){

                    nameFromDisc = f.getNameFromDisc();
                    nameFromDiscE = doc.createElement("nameFromDisc");
                    attr = doc.createAttribute("nameFromDisc");
                    attr.setValue(nameFromDisc);
                    nameFromDiscE.setAttributeNode(attr);
                    discE.appendChild(nameFromDiscE);
                }

                film = doc.createElement("name");
                attr = doc.createAttribute("name");
                attr.setValue(f.getName());
                film.setAttributeNode(attr);

                x = doc.createElement("genre");
                attr = doc.createAttribute("genre");
                attr.setValue(f.getGenre());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("rating");
                attr = doc.createAttribute("rating");
                attr.setValue(f.getRating());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("description");
                attr = doc.createAttribute("description");
                attr.setValue(f.getDescription());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("stars");
                attr = doc.createAttribute("stars");
                attr.setValue(f.getStars());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("director");
                attr = doc.createAttribute("director");
                attr.setValue(f.getDirector());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("duration");
                attr = doc.createAttribute("duration");
                attr.setValue(f.getDuration());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("releaseDate");
                attr = doc.createAttribute("releaseDate");
                attr.setValue(f.getReleaseDate());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("url");
                attr = doc.createAttribute("url");
                attr.setValue(f.getUrl());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("imageSrcDec");
                attr = doc.createAttribute("imageSrcDec");
                attr.setValue(f.getImageSrc());
                x.setAttributeNode(attr);
                film.appendChild(x);

                x = doc.createElement("imageSrc");
                attr = doc.createAttribute("imageSrc");

                String imageSrc = f.getImageSrc();

                final String http = "HTTPS:";
                String nameTemp = f.getName().toLowerCase();
                String nameFromDiscTemp = f.getNameFromDisc().toLowerCase();

                ServicePersistent sp = new ServicePersistent();

                nameTemp = sp.cleanString(nameTemp);
                nameFromDiscTemp = sp.cleanString(nameFromDiscTemp);

                Pictures pic = new Pictures();
                if(imageSrc != null && !imageSrc.equals("") && (imageSrc.contains(http) || imageSrc.contains(http.toLowerCase()))){

                    imageSrc = ServicePersistent.getEncodedStringByteArray(imageSrc);
                    byte[] im = ServicePersistent.decodeStringToByteArray(imageSrc);
                    BufferedImage bi = pic.resizeImage(380, 330, im);
                    imageSrc = ServicePersistent.getEncodedStringBufferedImage(bi);

                }

                attr.setValue(imageSrc);
                x.setAttributeNode(attr);
                film.appendChild(x);

                nameFromDiscE.appendChild(film);

            }

            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);


            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);

            this.reduceDiscXML(xmlFile, disc);

            System.out.println("Done");

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.createXML");
        }

    }


    @SuppressWarnings("unused")
    private Element getDiscElement(String xmlFile,String disc){

        Element discE = null;

        try
        {

            File file = new File(xmlFile);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize ();

            NodeList listOfDiscs = document.getElementsByTagName("disc");

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) ) return discE;

                ++i;

            }


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.getDiscElement.");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.getDiscElement.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.getDiscElement.");
        }

        return discE;
    }

    @SuppressWarnings("unused")
    private ArrayList<Movie> getObjectNameFromDiscAndChilds(String xmlFile,String nameFromDisc,String disc){

        ArrayList<Movie> filmi = new ArrayList<Movie>();
        Movie x = null;

        if(this.existsNameFromDiscXML(xmlFile,nameFromDisc,disc)){

            try
            {
                File file = new File(xmlFile);

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(xmlFile);
                document.getDocumentElement().normalize ();

                NodeList listOfDiscs = document.getElementsByTagName("disc");

                int i = 0;
                while( i < listOfDiscs.getLength()){

                    Node discNode = listOfDiscs.item(i);
                    String discT = discNode.getAttributes().getNamedItem("disc").getNodeValue();

                    NodeList nameFromDiscNL = discNode.getChildNodes();

                    int j = 0;
                    while(j < nameFromDiscNL.getLength()){

                        Node nameFromDiscNode = nameFromDiscNL.item(j);
                        String nameFromDiscT = nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue();

                        if(nameFromDiscT.toLowerCase().equals(nameFromDisc.toLowerCase()) && !discT.toLowerCase().equals(disc.toLowerCase()) ) {

                            NodeList nameNL = nameFromDiscNode.getChildNodes();

                            int z = 0;
                            while(z < nameNL.getLength()){

                                Node nameNode = nameNL.item(z);
                                NodeList nameChildNL = nameNode.getChildNodes();

                                String name = nameNode.getAttributes().getNamedItem("name").getNodeValue();
                                String genre = "";
                                String rating = "";
                                String description = "";
                                String stars = "";
                                String director = "";
                                String duration = "";
                                String releaseDate = "";
                                String url = "";
                                String imageSrc = "";

                                int u = 0;
                                while(u < nameChildNL.getLength())
                                {
                                    Node nameChild = nameChildNL.item(u);
                                    String childAttrib = nameChild.getNodeName();

                                    if(childAttrib.equals("genre")){
                                        genre = nameChild.getAttributes().getNamedItem("genre").getNodeValue();
                                    }
                                    if(childAttrib.equals("rating")){
                                        rating = nameChild.getAttributes().getNamedItem("rating").getNodeValue();
                                    }
                                    if(childAttrib.equals("description")){
                                        description = nameChild.getAttributes().getNamedItem("description").getNodeValue();
                                    }
                                    if(childAttrib.equals("stars")){
                                        stars = nameChild.getAttributes().getNamedItem("stars").getNodeValue();
                                    }
                                    if(childAttrib.equals("director")){
                                        director = nameChild.getAttributes().getNamedItem("director").getNodeValue();
                                    }
                                    if(childAttrib.equals("duration")){
                                        duration = nameChild.getAttributes().getNamedItem("duration").getNodeValue();
                                    }
                                    if(childAttrib.equals("releaseDate")){
                                        releaseDate = nameChild.getAttributes().getNamedItem("releaseDate").getNodeValue();
                                    }
                                    if(childAttrib.equals("url")){
                                        url = nameChild.getAttributes().getNamedItem("url").getNodeValue();
                                    }
                                    if(childAttrib.equals("imageSrc")){
                                        imageSrc = nameChild.getAttributes().getNamedItem("imageSrc").getNodeValue();
                                    }

                                    ++u;
                                }

                                x = new Movie(disc,name,genre,rating,description,stars,"",director,duration,"",releaseDate,nameFromDisc,url,imageSrc);
                                filmi.add(x);
                                ++z;
                            }
                            return filmi;
                        }

                        ++j;
                    }

                    ++i;
                }

            }catch(ParserConfigurationException pce){
                pce.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: XMLParser.getObjectNameFromDiscAndChilds");
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: XMLParser.getObjectNameFromDiscAndChilds");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: XMLParser.getObjectNameFromDiscAndChilds");
            }

        }

        return filmi;

    }

    @SuppressWarnings("unused")
    public boolean existsDiscXML(String xmlFile,String disc){

        disc = disc.toLowerCase();

        try
        {

            File file = new File(xmlFile);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize ();

            NodeList listOfDiscs = document.getElementsByTagName("disc");

            int i = 0;
            while( i < listOfDiscs.getLength()){

                Node discNode = listOfDiscs.item(i);

                if(disc.toLowerCase().equals(discNode.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase())) return true;

                ++i;
            }

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.existsDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.existsDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.existsDiscXML");
        }

        return false;
    }


    public String updateDiscXML(String xmlFile, String discOld, String discNew){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(discOld.toLowerCase()) )
                {
                    doc.getElementsByTagName("disc").item(i).getAttributes().getNamedItem("disc").setNodeValue(discNew);
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateDiscXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateDiscXML");
        }

        return "Disk " + discOld + " je bil uspešno zamenjan z diskom " + discNew;
    }

    public Document removeNameChildrenXML(String xmlFile,Document doc,String disc,String nameFromDisc,String name,String nameChild){

        NodeList listOfDiscs = doc.getElementsByTagName("disc");
        Element discE = null;

        int i = 0;
        while( i < listOfDiscs.getLength()){

            discE = (Element)listOfDiscs.item(i);

            if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
            {
                NodeList listNameFromDisc = discE.getChildNodes();

                Element nameFromDiscE = null;
                int j = 0;
                while(j < listNameFromDisc.getLength()){

                    nameFromDiscE = (Element)listNameFromDisc.item(j);

                    if(listNameFromDisc.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase().equals(nameFromDisc.toLowerCase()))
                    {

                        NodeList listName = nameFromDiscE.getChildNodes();
                        Element nameE = null;
                        int k = 0;
                        while(k < listName.getLength()){

                            nameE = (Element)listName.item(k);

                            if(listName.item(k).getAttributes().getNamedItem("name").getNodeValue().toLowerCase().equals(name.toLowerCase()))
                            {
                                NodeList listChild = nameE.getChildNodes();

                                int l = 0;
                                while(l < listChild.getLength()){

                                    if(listChild.item(l).getAttributes().item(0).getNodeName().toLowerCase().equals(nameChild.toLowerCase()))
                                    {
                                        doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).removeChild(doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l));
                                    }
                                    ++l;
                                }

                            }
                            ++k;
                        }

                    }
                    ++j;
                }

            }

            ++i;
        }
        return doc;
    }

    public void removeNameXML(String xmlFile, String disc , String nameFromDisc, String name){

        try
        {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    Element nameFromDiscE = null;
                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        nameFromDiscE = (Element)listNameFromDisc.item(j);

                        if(listNameFromDisc.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase().equals(nameFromDisc.toLowerCase()))
                        {

                            NodeList listName = nameFromDiscE.getChildNodes();
                            Element nameE = null;
                            int k = 0;
                            while(k < listName.getLength()){

                                nameE = (Element)listName.item(k);

                                if(listName.item(k).getAttributes().getNamedItem("name").getNodeValue().toLowerCase().equals(name.toLowerCase()))
                                {
                                    NodeList listChild = nameE.getChildNodes();

                                    String nameChild;
                                    int l = 0;
                                    while(l < listChild.getLength()){
                                        nameChild = listChild.item(l).getAttributes().item(0).getNodeName();
                                        doc = this.removeNameChildrenXML(xmlFile,doc,disc,nameFromDisc,name,nameChild);
                                        ++l;
                                    }
                                    doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).removeChild(doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k));
                                }
                                ++k;
                            }
                        }
                        ++j;
                    }
                }

                ++i;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameXML");
        }

    }

    public Document removeNameXML(String xmlFile, Document doc, String disc , String nameFromDisc, String name){

        NodeList listOfDiscs = doc.getElementsByTagName("disc");
        Element discE = null;

        int i = 0;
        while( i < listOfDiscs.getLength()){

            discE = (Element)listOfDiscs.item(i);

            if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
            {
                NodeList listNameFromDisc = discE.getChildNodes();

                Element nameFromDiscE = null;
                int j = 0;
                while(j < listNameFromDisc.getLength()){

                    nameFromDiscE = (Element)listNameFromDisc.item(j);

                    if(listNameFromDisc.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase().equals(nameFromDisc.toLowerCase()))
                    {

                        NodeList listName = nameFromDiscE.getChildNodes();
                        Element nameE = null;
                        int k = 0;
                        while(k < listName.getLength()){

                            nameE = (Element)listName.item(k);

                            if(listName.item(k).getAttributes().getNamedItem("name").getNodeValue().toLowerCase().equals(name.toLowerCase()))
                            {
                                NodeList listChild = nameE.getChildNodes();

                                String nameChild;
                                int l = 0;
                                while(l < listChild.getLength()){
                                    nameChild = listChild.item(l).getAttributes().item(0).getNodeName();
                                    doc = this.removeNameChildrenXML(xmlFile,doc,disc,nameFromDisc,name,nameChild);
                                    ++l;
                                }
                                doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).removeChild(doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k));
                            }
                            ++k;
                        }
                    }
                    ++j;
                }
            }

            ++i;
        }

        return doc;
    }

    public String removeNameFromDiscXML(String xmlFile, String disc, String movie){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();
                    String name;

                    Element nameFromDisc = null;
                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        nameFromDisc = (Element)listNameFromDisc.item(j);

                        if(listNameFromDisc.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase().equals(movie.toLowerCase()))
                        {

                            NodeList listName = nameFromDisc.getChildNodes();
                            int k = 0;
                            while(k < listName.getLength()){

                                name = listName.item(k).getAttributes().getNamedItem("name").getNodeValue();
                                doc = this.removeNameXML(xmlFile,doc,disc,movie,name);

                                ++k;
                            }
                            doc.getElementsByTagName("disc").item(i).removeChild(doc.getElementsByTagName("disc").item(i).getChildNodes().item(j));
                        }
                        ++j;
                    }
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameFromDiscXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameFromDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameFromDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeNameFromDiscXML");
        }

        return "Filem " + movie + " z vsemi podfilmi je bil uspešno odstranjen iz diska " + disc;
    }

    public String updateNameChildAttribXML(String xmlFile, String disc, String movie,String name,String childAttribName,String newAttribVal){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        Node nameFromDiscNode = listNameFromDisc.item(j);

                        if(nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().equals(movie))
                        {

                            NodeList nameNL = nameFromDiscNode.getChildNodes();

                            int k = 0;
                            while(k < nameNL.getLength()){

                                Node nameNode = nameNL.item(k);

                                if(nameNode.getAttributes().getNamedItem("name").getNodeValue().equals(name))
                                {

                                    NodeList nameChildNL = nameNode.getChildNodes();

                                    int l = 0;
                                    while(l < nameChildNL.getLength())
                                    {
                                        Node nameChild = nameChildNL.item(l);
                                        if(nameChild.getNodeName().equals(childAttribName)){

                                            doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l).getAttributes().getNamedItem(childAttribName).setNodeValue(newAttribVal);

                                        }

                                        ++l;
                                    }

                                }
                                ++k;
                            }

                        }
                        ++j;
                    }
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameChildAttribXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameChildAttribXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameChildAttribXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameChildAttribXML");
        }

        return "Movie " + movie + " has been changed";
    }

    public String updateNameXML(String xmlFile, String disc, String movie,String name, String newName){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        Node nameFromDiscNode = listNameFromDisc.item(j);

                        if(nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().equals(movie))
                        {

                            NodeList nameNL = nameFromDiscNode.getChildNodes();

                            int k = 0;
                            while(k < nameNL.getLength()){

                                Node nameNode = nameNL.item(k);

                                if(nameNode.getAttributes().getNamedItem("name").getNodeValue().equals(name))
                                {
                                    doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getAttributes().getNamedItem("name").setNodeValue(newName);
                                }
                                ++k;
                            }

                        }
                        ++j;
                    }
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameXML");
        }

        return "Movie " + movie + " has been changed on";
    }

    public String updateNameFromDiscXML(String xmlFile, String disc, String movie, String newMovie){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        if(listNameFromDisc.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue().equals(movie))
                        {
                            doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getAttributes().getNamedItem("nameFromDisc").setNodeValue(newMovie);
                        }
                        ++j;
                    }
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameFromDiscXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameFromDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameFromDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.updateNameFromDiscXML");
        }

        return "Filem " + movie + " je bil popravljen na " + newMovie;
    }

    public void reduceDiscXML(String xmlFile, String disc){

        String nameFromDisc;
        String name;

        try
        {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            ServicePersistent sp = new ServicePersistent();

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        nameFromDisc = listNameFromDisc.item(j).getAttributes().getNamedItem("nameFromDisc").getNodeValue();

                        Element nameE = null;

                        int counter = 0;
                        int k = 0;
                        while(k < doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().getLength()){

                            nameE = (Element)doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k);

                            NodeList listChild = nameE.getChildNodes();

                            name = doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getAttributes().getNamedItem("name").getNodeValue();

                            String nameTemp = sp.cleanString(name.toLowerCase());
                            String movieTemp = sp.cleanString(nameFromDisc.toLowerCase());

                            if(!nameTemp.equals(movieTemp) && counter > 0){

                                int l = 0;
                                while(l < listChild.getLength()){

                                    doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).removeChild(doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l));

                                    ++l;
                                }

                                doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).removeChild(doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k));
                                --k;
                            }

                            ++counter;
                            ++k;
                        }
                        ++j;
                    }
                }

                ++i;
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.reduceDiscXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.reduceDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.reduceDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.reduceDiscXML");
        }

    }


    public String removeDiscXML(String xmlFile, String disc){

        try
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    Element nameFromDisc = null;
                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        nameFromDisc = (Element)listNameFromDisc.item(j);
                        this.removeNameFromDiscXML(xmlFile, disc, nameFromDisc.getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase());

                        ++j;
                    }
                }
                ++i;
            }

            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            listOfDiscs = doc.getElementsByTagName("disc");
            discE = null;
            i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    doc.getDocumentElement().removeChild(doc.getElementsByTagName("disc").item(i));
                }
                ++i;
            }

            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeDiscXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.removeDiscXML");
        }

        return "Disk " + disc + " z vsemi filmi je bil uspešno odstranjen iz XMLja!";
    }

    public static void convertImageURLToByteXML(String xmlFile,String disc){

        try
        {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            ServicePersistent sp = new ServicePersistent();
            Pictures pic = new Pictures();
            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        Node nameFromDiscNode = listNameFromDisc.item(j);
                        NodeList nameNL = nameFromDiscNode.getChildNodes();

                        int k = 0;
                        while(k < nameNL.getLength()){

                            Node nameNode = nameNL.item(k);

                            NodeList nameChildNL = nameNode.getChildNodes();

                            int l = 0;
                            while(l < nameChildNL.getLength())
                            {
                                Node nameChild = nameChildNL.item(l);
                                if(nameChild.getNodeName().equals("imageSrc")){

                                    String path = doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l).getAttributes().getNamedItem("imageSrc").getNodeValue();

                                    final String http = "HTTP:";
                                    final String imdb = "imdb_fb_logo";
                                    String nameTemp = nameNode.getAttributes().getNamedItem("name").getNodeValue().toLowerCase();
                                    String nameFromDiscTemp = nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase();

                                    nameTemp = sp.cleanString(nameTemp);
                                    nameFromDiscTemp = sp.cleanString(nameFromDiscTemp);

                                    if(path != null && !path.equals("") && nameFromDiscTemp.equals(nameTemp) && path.length() > http.length() && !path.contains(imdb) && (path.contains(http) || path.contains(http.toLowerCase()))){
                                        System.out.println(nameTemp);
                                        path = ServicePersistent.getEncodedStringByteArray(path);
                                        byte[] im = ServicePersistent.decodeStringToByteArray(path);
                                        BufferedImage bi = pic.resizeImage(380, 330, im);
                                        path = ServicePersistent.getEncodedStringBufferedImage(bi);
                                    }

                                    doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l).getAttributes().getNamedItem("imageSrc").setNodeValue(path);

                                }

                                ++l;
                            }

                            ++k;
                        }


                        ++j;
                    }
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        }

        System.out.println("Converting pictures on ByteString OK!");
    }

    public static void resizeImageByteXML(String xmlFile,String disc,final int WIDTH,final int HEIGHT){

        try
        {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = null;
            doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listOfDiscs = doc.getElementsByTagName("disc");
            Element discE = null;

            ServicePersistent sp = new ServicePersistent();

            int i = 0;
            while( i < listOfDiscs.getLength()){

                discE = (Element)listOfDiscs.item(i);

                if( discE.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()) )
                {
                    NodeList listNameFromDisc = discE.getChildNodes();

                    int j = 0;
                    while(j < listNameFromDisc.getLength()){

                        Node nameFromDiscNode = listNameFromDisc.item(j);
                        NodeList nameNL = nameFromDiscNode.getChildNodes();

                        int k = 0;
                        while(k < nameNL.getLength()){

                            Node nameNode = nameNL.item(k);

                            NodeList nameChildNL = nameNode.getChildNodes();

                            int l = 0;
                            while(l < nameChildNL.getLength())
                            {
                                Node nameChild = nameChildNL.item(l);
                                if(nameChild.getNodeName().equals("imageSrc")){

                                    String path = doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l).getAttributes().getNamedItem("imageSrc").getNodeValue();

                                    final String http = "HTTP:";
                                    String nameTemp = nameNode.getAttributes().getNamedItem("name").getNodeValue().toLowerCase();
                                    String nameFromDiscTemp = nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase();

                                    nameTemp = sp.cleanString(nameTemp);
                                    nameFromDiscTemp = sp.cleanString(nameFromDiscTemp);

                                    Pictures pic = new Pictures();
                                    if(path != null && !path.equals("") && nameFromDiscTemp.equals(nameTemp) && !path.contains(http) && !path.contains(http.toLowerCase())){

                                        byte[] im = ServicePersistent.decodeStringToByteArray(path);
                                        BufferedImage bi = pic.resizeImage(380, 330, im);
                                        path = ServicePersistent.getEncodedStringBufferedImage(bi);

                                    }

                                    doc.getElementsByTagName("disc").item(i).getChildNodes().item(j).getChildNodes().item(k).getChildNodes().item(l).getAttributes().getNamedItem("imageSrc").setNodeValue(path);

                                }

                                ++l;
                            }

                            System.out.println(k);
                            ++k;
                        }


                        ++j;
                    }
                }

                ++i;

            }


            //write the content into xml file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result =  new StreamResult(new File(xmlFile));
            transformer.transform(source, result);


        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        }
        catch(TransformerException tfe){
            tfe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.convertImageURLToByteXML");
        }

        System.out.println("Converting pictures on ByteString OK!");
    }

    @SuppressWarnings("unused")
    public boolean existsNameFromDiscXML(String xmlFile,String nameFromDisc, String disc){

        nameFromDisc = nameFromDisc.toLowerCase();

        try
        {

            File file = new File(xmlFile);

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize ();

            NodeList listOfDiscs = document.getElementsByTagName("disc");

            int i = 0;
            while( i < listOfDiscs.getLength()){

                Node discNode = listOfDiscs.item(i);

                if(discNode.getAttributes().getNamedItem("disc").getNodeValue().toLowerCase().equals(disc.toLowerCase()))
                {
                    NodeList nameFromDiscNL = discNode.getChildNodes();

                    int j = 0;
                    while(j < nameFromDiscNL.getLength()){

                        Node nameFromDiscNode = nameFromDiscNL.item(j);

                        if(nameFromDisc.toLowerCase().equals(nameFromDiscNode.getAttributes().getNamedItem("nameFromDisc").getNodeValue().toLowerCase())) return true;

                        ++j;
                    }
                }

                ++i;
            }

        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.existsNameFromDiscXML");
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.existsNameFromDiscXML");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: XMLParser.existsNameFromDiscXML");
        }

        return false;
    }

}
