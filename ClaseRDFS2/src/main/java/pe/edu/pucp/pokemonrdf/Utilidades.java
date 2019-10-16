package pe.edu.pucp.pokemonrdf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.vocabulary.RDF;

public class Utilidades {

    public static String NS = "http://www.pucp.edu.pe/conocimiento#";

    public static Property crearPropiedad(String NS, String id, Model model) {
        String propertyURI = NS + id.trim();
        
        return model.createProperty(propertyURI);
    }

    public static Resource crearRecurso(String NS, String id, Model model) {
        String resourceURI = NS + id.trim();
        
        return model.createResource(resourceURI);
    }

    public static void grabarRDF(String nmRDFFile, Model model) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(nmRDFFile.trim());
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrio un error al crear el archivo.");
        }
        model.write(output, "RDF/XML-ABBREV");
    }

    public static Boolean existenAfirmaciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto) {
        Boolean hayAfirmaciones;
        Selector selector = new SimpleSelector(Sujeto, predicado, objeto);
        StmtIterator iter = inf.listStatements(selector);
        hayAfirmaciones = iter.hasNext();
        
        return hayAfirmaciones;
    }

    public static void mostrarDerivaciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto) {
        PrintWriter out = new PrintWriter(System.out);
        for (StmtIterator i = inf.listStatements(Sujeto, predicado, objeto); i.hasNext();) {
            Statement s = i.nextStatement();
            System.out.println(" Statement is " + s);
            for (Iterator id = inf.getDerivation(s); id.hasNext();) {
                Derivation deriv = (Derivation) id.next();
                deriv.printTrace(out, true);
            }
        }
        out.flush();
    }

    public static void mostrarDeclaraciones(InfModel inf, Resource Sujeto, Property predicado, Resource objeto) {
        Selector selector = new SimpleSelector(Sujeto, predicado, objeto);
        StmtIterator iter = inf.listStatements(selector);
        while (iter.hasNext()) {
            System.out.println(iter.nextStatement().toString());
        }
    }

    public static Resource obtenerRecurso(String NS, String id, Model model) {
        String resourceURI = NS + id.trim();
        
        return model.getResource(resourceURI);
    }

    public static Property obtenerPropiedad(String NS, String id, Model model) {
        String propertyURI = NS + id.trim();
        
        return model.getProperty(propertyURI);
    }

    public static int leerArchivoMiembro(String filePath,
            int skipRows,
            Resource clase,
            String namespace,
            Model model) {
        BufferedReader csvReader = null;
        int skippedRows = 0;
        ArrayList<String> temporal = new ArrayList<String>();
        try {
            csvReader = new BufferedReader(new FileReader(filePath));
        } catch (Exception e) {
            System.out.println("leerArchivoMiembro: error al abrir archivo " + filePath);
            System.out.println(e.getMessage());
            return -1;
        }
        String row;
        try {
            while ((row = csvReader.readLine()) != null) {
                if (skippedRows == skipRows) {
                    String[] data = row.split(",");
                    String dato = data[0];
                    // System.out.println(dato);
                    temporal.add(dato);
                    Resource recurso = Utilidades.crearRecurso(namespace, dato, model);
                    model.add(recurso, RDF.type, clase);
                } else {
                    skippedRows++;
                }
            }
        } catch (Exception e) {
            System.out.println("leerArchivoMiembro: error al leer el archivo.");
            System.out.println(e.getMessage());
            return -1;
        } finally {
            try {
                csvReader.close();
            } catch (Exception e) {
            }
            csvReader = null;
        }
        System.out.println("Se leyeron " + temporal.size() + " datos.");
        
        return 0;
    }

    public static int leerArchivoPropiedad(String filePath,
            int skipRows,
            Property predicado,
            String namespace,
            Model model) {
        BufferedReader csvReader = null;
        int skippedRows = 0;
        ArrayList<String> temporal = new ArrayList<String>();
        try {
            csvReader = new BufferedReader(new FileReader(filePath));
        } catch (Exception e) {
            System.out.println("leerArchivoPropiedad: error al abrir archivo " + filePath);
            System.out.println(e.getMessage());
            return -1;
        }
        String row;
        try {
            while ((row = csvReader.readLine()) != null) {
                if (skippedRows == skipRows) {
                    String[] data = row.split(",");
                    String dato1 = data[0];
                    String dato2 = data[1];
                    // System.out.println(dato1 + "-" + dato2);
                    temporal.add(dato1);
                    Resource sujeto = obtenerRecurso(NS, dato1, model);
                    Resource objeto = obtenerRecurso(NS, dato2, model);
                    model.add(sujeto, predicado, objeto);
                } else {
                    skippedRows++;
                }
            }
        } catch (Exception e) {
            System.out.println("leerArchivoMiembro: error al leer el archivo.");
            System.out.println(e.getMessage());
            return -1;
        } finally {
            try {
                csvReader.close();
            } catch (Exception e) {
            }
            csvReader = null;
        }
        System.out.println("Se leyeron " + temporal.size() + " relaciones.");
        
        return 0;
    }
}
