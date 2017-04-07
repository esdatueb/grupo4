/*
 * Apache 2.0
 * Universidad El Bosque  * 
 */
package co.edu.uelbosque.essatueb.sortbigfiles;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
                ArrayList<Persona> personas = new ArrayList<>();
                FileInputStream fis = new FileInputStream(file1);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                FileInputStream fis2 = new FileInputStream(file2);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2));
                String linea;
                String linea2;
                EdadComparator comparador = new EdadComparator();
                String[] primeraPersona = br.readLine().split(",");
                int edadPrimeraPersona = Integer.parseInt(primeraPersona[0]);
                Persona personaMayor = new Persona(edadPrimeraPersona, primeraPersona[1], primeraPersona[2], primeraPersona[3], primeraPersona[4]);
                while((linea = br.readLine()) != null && (linea2 = br2.readLine()) !=null){
                    if(linea == null){
                        bw.write(linea2);
                         bw.newLine();
                    }else if(linea2 == null){
                        bw.write(linea);
                         bw.newLine();
                     } else{
                        String[] arreglo;
                        int edad;
                        personas.add(personaMayor);
                        //convierte a la linea del primer archivo en una persona
                        arreglo = linea.split(",");
                        edad = Integer.parseInt(arreglo[0]);
                        Persona persona1 = new Persona(edad,arreglo[1],arreglo[2],arreglo[3],arreglo[4]);
                        personas.add(persona1);
                        //convierte a la linea del segundo archivo en otra persona
                        arreglo = linea2.split(",");
                        edad = Integer.parseInt(arreglo[0]);
                        Persona persona2 = new Persona(edad,arreglo[1],arreglo[2],arreglo[3],arreglo[4]);
                        personas.add(persona2);
                        //compara las edades de las personas
                        personas.sort(comparador);

                        linea = personas.get(0).getEdad()+","+personas.get(0).getDocumento()+","+personas.get(0).getNombre()
                                +","+personas.get(0).getTipoDoc()+","+personas.get(0).getTipoSangre();
                        linea2 = personas.get(1).getEdad()+","+personas.get(1).getDocumento()+","+personas.get(1).getNombre()
                                +","+personas.get(1).getTipoDoc()+","+personas.get(1).getTipoSangre();
                        personaMayor = personas.get(2);

                        bw.write(linea);
                        bw.write(linea2);
                        bw.newLine();
                        personas.clear();
                    }
                }
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

