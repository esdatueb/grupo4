/*
 * Apache 2.0
 * Universidad El Bosque  * 
 */
package co.edu.uelbosque.essatueb.sortbigfiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SorterBigFileTemplateImpl extends SorterBigFileTemplate{

    public SorterBigFileTemplateImpl() {
        super(null, 0, null);
    }

    
    public SorterBigFileTemplateImpl(File toSort, int linesPerFile, File outPutDir) {
        super(toSort, linesPerFile, outPutDir);
    }
    @Override
    public File getToSort() {
        return toSort;
    }

    @Override
    public String[] getNextLines() {
        ArrayList<String> lineas=new ArrayList<>();
        for (int i = 0; i < this.linesPerFile; i++) {
            try {
                lineas.add(br.readLine());
               System.out.println(lineas.get(lineas.size()-1));
             } catch (IOException ex) {
                Logger.getLogger(SorterBigFileTemplateImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String l[]=new String[lineas.size()];
        int i=0;
        for (String string : lineas) {
               l[i]=lineas.get(i);
               i++;
        }
        return l;
    }

    @Override
    public boolean hasMoreLines() {
        try {
            return br.ready();
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public void saveToNewFile(String[] lines, int fileNumber) {
         this.outPutDir.mkdirs();
         try{
         FileWriter fw=new FileWriter(this.outPutDir+"/Part"+fileNumber+".txt");
         for (int i = 0; i < lines.length; i++) {
            String line = lines[i]+"\n";
            fw.write(line);
            fw.write(System.lineSeparator());
        }
         fw.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
        }
    }

    @Override
    protected File mergeFiles(File file1, File file2) {
        File archivoSalida = new File(this.outPutDir+"/archivoSalida.txt");
       FileWriter fw = null;
       BufferedWriter bw = null;
        try {
            fw = new FileWriter(archivoSalida, true);
            bw = new BufferedWriter(fw);
        } catch (IOException ex) {
            Logger.getLogger(SorterBigFileTemplateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FileInputStream fis = new FileInputStream(file1);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String linea;
            while((linea = br.readLine()) != null){
                System.out.println("Linea: "+linea);
                bw.write(linea);
                bw.newLine();
            }
            
            FileInputStream fis2 = new FileInputStream(file2);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));
          
            while((linea = br2.readLine()) != null){
                System.out.println("Linea: "+linea);
                bw.write(linea);
                bw.newLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SorterBigFileTemplateImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SorterBigFileTemplateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            bw.close();
            fw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(SorterBigFileTemplateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return archivoSalida;
    }

    @Override
    protected Queue<File> getFilesToOrder() {
       Queue cola = new LinkedList();
        String dir = System.getProperty("user.dir");
        File out_files=new File(dir+"/out");
        try {
            long totalArchivos=Files.list(out_files.toPath()).count();
            for (int i = 0; i < totalArchivos; i++) {
               
                File f = new File(dir+"/out"+"/Part"+i+".txt");
               
                cola.add(f);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(SorterBigFileTemplateImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cola;
        
    }

    BufferedReader br;
    void setBufferReader(BufferedReader br) {
       this.br=br;
    }
    
}

