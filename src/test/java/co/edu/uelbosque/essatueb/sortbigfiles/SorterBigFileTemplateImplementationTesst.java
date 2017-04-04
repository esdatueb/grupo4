/*
 * Apache 2.0
 * Universidad El Bosque  * 
 */
package co.edu.uelbosque.essatueb.sortbigfiles;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Queue;

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Se va a realizar el ordenamiento con 8 lineas, 2 por archivo.
 * 
 * @author user
 */
public class SorterBigFileTemplateImplementationTesst {
    String linesToSort[];
    
    public SorterBigFileTemplateImplementationTesst() {
        //edad,documento,nombre,tipo_doc,tipo_sangre
        String linesToSort[]={
            "3,34291072748,LKUQU YlghdKdjrkMtlADzRzxDIJkJLWuHNpVLBd ENWOfgBAs,cc,O-",
            "76,853839744348,DpvkaHTMD tqFpcgsUKfSymYZMqbQjTsA DBWxoAMIRrX ypOj,ti,B-",
            "60,681223679333,E vVJlfEFGThhQGvpsdgKbajqOsOtcxYKTTufaYKHS OIyjYRn,cc,B-",
            "25,116147548891,LGT togSmRLglRhkCOcuCK MJKZPmoVXUQd ejgqb ymKGxmtA,ti,AB+",
            "19,513959866017, Ul KxWGvRvyfAInRJf WQVtd TKl kTavCKTyq pNOwnwJ fQ,ti,O-",
            "55,309700548648,v FptQuEKuamfULwkbjYyxuhCBYXDEnZn  H Kls mGlgq rsS,cc,AB-",
            "28,724699781741,Z mXQQCdPPW wGP ZUUFfcVOtoHKfXw Q dGzFJ uHtRmizldu,ce,O+",
            "39,732996939681,XRaFCgCYlsI ZgT vo P CYFNbZDlw jopOQwqO JvX DSrZfI,ce,O+"
        };
        this.linesToSort=linesToSort;
    }
    
    /*@Test
    public void shouldHasMoreLinesToReadFromBuffer() throws FileNotFoundException{
        SorterBigFileTemplateImpl sbfti=new SorterBigFileTemplateImpl();
        sbfti.setBufferReader(getBufferedReader(sbfti.getToSort()));
        assertTrue(sbfti.hasMoreLines());
    }
    */
    public BufferedReader getBufferedReader(File in) throws FileNotFoundException{
        /*StringBuffer sb=new StringBuffer(690);
        for (int i = 0; i < linesToSort.length; i++) {
            String string = linesToSort[i];
            sb.append(string+"\n");
        }
        ByteArrayInputStream sr=new ByteArrayInputStream(sb.toString().getBytes());*/
        FileReader fr = new FileReader(in);
        BufferedReader br=new BufferedReader(fr);
        return br;
    }
    
    /*@Test
    public void shouldReturnFirstTwoLines() throws FileNotFoundException{
        SorterBigFileTemplateImpl sbfti=new SorterBigFileTemplateImpl(null,2,null);
        sbfti.setBufferReader(getBufferedReader(sbfti.getToSort()));
        String lines[]=sbfti.getNextLines();
                     //resultado, esperado
        assertEquals(lines.length, 2);
        assertEquals(lines[0], linesToSort[0]);
        assertEquals(lines[1], linesToSort[1]);
    }*/
    
    @Test
    public void saveToNewFile() throws FileNotFoundException{
        String dir = System.getProperty("user.dir");
        File test_txt=new File("D:\\entrada.csv");
        File out_dir=new File("D:\\out");
        SorterBigFileTemplateImpl sbfti=new SorterBigFileTemplateImpl(test_txt,100,out_dir);
        sbfti.setBufferReader(getBufferedReader(sbfti.getToSort()));
        sbfti.saveToNewFile(sbfti.getNextLines(), 0);      
    }
    
    @Test
    public void particionarArchivos() throws IOException{
        String dir = System.getProperty("user.dir");
        File test_txt=new File("D:\\entrada.csv");
        File out_files=new File("D:\\out1");
        SorterBigFileTemplateImpl sbfti=new SorterBigFileTemplateImpl(test_txt,4,out_files);
        sbfti.setBufferReader(getBufferedReader(sbfti.getToSort()));
        sbfti.breakFileInChunksAndSortIt(new EdadComparator());
        long totalArchivos=Files.list(out_files.toPath()).count();
        Assert.assertEquals(totalArchivos, 100/4);
    }
   
    /**
     * Prueba el método mergeFiles()
     * @throws IOException
     */
    @Test
    public void mergeFilesTest() throws IOException{
    	File merge1 = new File("merge1.txt");
    	File merge2 = new File("merge2.txt");
    	FileWriter fw1 = new FileWriter(merge1);
    	FileWriter fw2 = new FileWriter(merge2);
    	SorterBigFileTemplateImpl sbfti = new SorterBigFileTemplateImpl();
    	String linea1 = null;
    	String linea2 = null;
    	
    	fw1.write("Linea 1");
    	fw2.write("Linea 2");
    	File resultado = sbfti.mergeFiles(merge1, merge2);
    	
    	FileReader fr = new FileReader(resultado);
    	BufferedReader br = new BufferedReader(fr);
    	linea1 = br.readLine();
    	linea2 = br.readLine();
    	br.close();
    	fr.close();
    	fw1.close();
    	fw2.close();
    	
    	Assert.assertTrue(linea1.equals("Linea 1")&&linea2.equals("Linea 2"));
    	
    }
    
    /**
     * Prueba el método getFilesToOrder()
     */
    
    @Test
    public void getFilesToOrderTest(){
    	File fileToOrder1 = new File("fileToOrder1.txt");
    	File fileToOrder2 = new File("fileToOrder2.txt");
    	SorterBigFileTemplateImpl sbfti = new SorterBigFileTemplateImpl();
    	Queue<File> archivosAOrdenar = sbfti.getFilesToOrder();
    	
    	Assert.assertTrue(archivosAOrdenar.contains(fileToOrder1)&&archivosAOrdenar.contains(fileToOrder2));
    }
}
