package pe.edu.pucp.pokemonrdf;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import static pe.edu.pucp.pokemonrdf.Utilidades.existenAfirmaciones;

public class PokedexInferencias {

    private static final String DIR_BASE = "/Users/gerardocardosoyllanes/Documents/GitHub/PokemonRDF";

    public static void main(String[] args) {

        String inputFileName = DIR_BASE + "/Files/output.rdf";
        Model model = FileManager.get().loadModel(inputFileName);

        // Inferrencia 1 - subClassOf
        // Charmander pertenece a la clase common_pkmn, que a su vez es subclase de pkmn.
        // A partir del modelo se infiere que Charmander pertenece también a la clase pkmn.
        try {
            Resource charmander = Utilidades.obtenerRecurso(Utilidades.NS, "Charmander", model);
            Resource common_pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "common_pkmn", model);
            Resource pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "pkmn", model);
            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 1 - subClassOf");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, charmander, RDF.type, common_pkmn)) {
                System.out.println("La afirmacion es cierta. Charmander pertenece a common_pkmn");
            } else {
                System.out.println("La afirmacion no es cierta. Charmander NO pertenece a common_pkmn");
            }

            if (existenAfirmaciones(inf, charmander, RDF.type, pkmn)) {
                System.out.println("La afirmacion es cierta. Charmander pertenece a pkmn");
            } else {
                System.out.println("La afirmacion no es cierta. Charmander NO pertenece a pkmn");
            }
        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }

        // Inferrencia 2 - subPropertyOf
        try {
            Resource articuno = Utilidades.obtenerRecurso(Utilidades.NS, "Articuno", model);
            Resource leer = Utilidades.obtenerRecurso(Utilidades.NS, "Leer", model);
            Property learnsMoveByLvl = Utilidades.obtenerPropiedad(Utilidades.NS, "learnsMoveByLvl", model);
            Property learnsMoveByTMHM = Utilidades.obtenerPropiedad(Utilidades.NS, "learnsMoveByTMHM", model);
            Property learnsMove = Utilidades.obtenerPropiedad(Utilidades.NS, "learnsMove", model);

            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 2 - subPropertyOf");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, articuno, learnsMoveByLvl, leer)) {
                System.out.println("La afirmacion es cierta. Articuno aprende Leer por nivel.");
            } else {
                System.out.println("La afirmacion NO es cierta. Articuno NO aprende Leer por nivel.");
            }

            if (existenAfirmaciones(inf, articuno, learnsMoveByTMHM, leer)) {
                System.out.println("La afirmacion es cierta. Articuno aprende Leer por TM/HM.");
            } else {
                System.out.println("La afirmacion NO es cierta. Articuno NO aprende Leer por TM/HM.");
            }

            if (existenAfirmaciones(inf, articuno, learnsMove, leer)) {
                System.out.println("La afirmacion es cierta. Articuno aprende Leer.");
            } else {
                System.out.println("La afirmacion NO es cierta. Articuno NO aprende Leer.");
            }

        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }

        // Inferencia 3 - domain
        try {
            Resource charmander = Utilidades.obtenerRecurso(Utilidades.NS, "Charmander", model);
            Resource outrage = Utilidades.crearRecurso(Utilidades.NS, "Outrage", model);
            Property learnsMoveByTMHM = Utilidades.obtenerPropiedad(Utilidades.NS, "learnsMoveByTMHM", model);
            Resource move = Utilidades.obtenerRecurso(Utilidades.NS, "move", model);
            model.add(charmander, learnsMoveByTMHM, outrage);
            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 3 - domain");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, outrage, RDF.type, move)) {
                System.out.println("La afirmacion es cierta. Outrage es un tipo de move");
            } else {
                System.out.println("La afirmacion no es cierta. Outrage NO es un tipo de move");
            }
        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }

        // Inferencia 4 - domain/range
        try {
            Resource smoochum = Utilidades.crearRecurso(Utilidades.NS, "Smoochum", model);
            Resource jynx = Utilidades.obtenerRecurso(Utilidades.NS, "Jynx", model);
            Property evolvesByLvlTo = Utilidades.obtenerPropiedad(Utilidades.NS, "evolvesByLvlTo", model);
            Resource pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "pkmn", model);
            model.add(smoochum, evolvesByLvlTo, jynx);
            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 4 - domain/range");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, smoochum, RDF.type, pkmn)) {
                System.out.println("La afirmacion es cierta. Smoochun es un tipo de pokemon");
            } else {
                System.out.println("La afirmacion no es cierta. Smoochun NO es un tipo de pokemon");
            }
        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }

        // Inferencia 5 - domain y range
        // Umbreon es un pokemon de 2da generacion. Dark es un tipo de 2da generacion.
        // Umbreon es un pokemon tipo dark.
        // Se modifica el modelo creando los recursos umbreon y dark y agregando la relacion
        // que umbreon hasPkmnType dark. A partir de esto, el modelo infiere que umbreon es
        // un miembro de la clase pkmn y dark es un miembro de la clase element sin tener que 
        // especificarlo ya que la relación hasPkmnType tiene como dominio la clase pkmn y como
        // rango la clase element.
        try {
            Resource umbreon = Utilidades.crearRecurso(Utilidades.NS, "Umbreon", model);
            Resource dark = Utilidades.crearRecurso(Utilidades.NS, "Dark", model);
            Resource pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "pkmn", model);
            Resource element = Utilidades.obtenerRecurso(Utilidades.NS, "element", model);
            Property hasPkmnElement = Utilidades.obtenerPropiedad(Utilidades.NS, "hasPkmnElement", model);
            model.add(umbreon, hasPkmnElement, dark);

            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 5 - domain y range");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, umbreon, RDF.type, pkmn)) {
                System.out.println("La afirmacion es cierta. Umbreon pertenece a pkmn.");
            } else {
                System.out.println("La afirmacion no es cierta. Umbreon NO pertenece a pkmn.");
            }

            if (existenAfirmaciones(inf, dark, RDF.type, element)) {
                System.out.println("La afirmacion es cierta. Dark pertenece a element.");
            } else {
                System.out.println("La afirmacion no es cierta. Dark NO pertenece a element.");
            }

        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }
        
        // Inferencia 6 - subClassOf
        // Raikou pertenece a la clase legendary_pkmn, que a su vez es subclase de pkmn.
        // A partir del modelo se infiere que Raikou pertenece también a la clase pkmn.
        try {
            Resource raikou = Utilidades.crearRecurso(Utilidades.NS, "Raikou", model);
            Resource legendary_pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "legendary_pkmn", model);
            Resource pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "pkmn", model);
            model.add(raikou, RDF.type, legendary_pkmn);

            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 6 - subClassOf");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, raikou, RDF.type, legendary_pkmn)) {
                System.out.println("La afirmacion es cierta. Raikou pertenece a legendary_pkmn.");
            } else {
                System.out.println("La afirmacion no es cierta. Raikou pertenece a legendary_pkmn.");
            }

            if (existenAfirmaciones(inf, raikou, RDF.type, pkmn)) {
                System.out.println("La afirmacion es cierta. Raikou pertenece a pkmn.");
            } else {
                System.out.println("La afirmacion no es cierta. Raikou No pertenece a pkmn.");
            }

        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }

        // Inferencia 7 - subClassOf
        // anorith pertenece a la clase extinct_pkmn, que a su vez es subclase de pkmn.
        // A partir del modelo se infiere que armaldo su evolucion pertenece también a la clase pkmn.
        try {
            Resource anorith = Utilidades.crearRecurso(Utilidades.NS, "Anorith", model);
            Resource armaldo = Utilidades.crearRecurso(Utilidades.NS, "Armaldo", model);
            Resource extinct_pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "extinct_pkmn", model);
            Resource pkmn = Utilidades.obtenerRecurso(Utilidades.NS, "pkmn", model);
            Property evolvesByLvlTo = Utilidades.obtenerPropiedad(Utilidades.NS, "evolvesByLvlTo", model);
            anorith.addProperty(evolvesByLvlTo, armaldo);
            model.add(anorith, RDF.type, extinct_pkmn);

            InfModel inf = ModelFactory.createRDFSModel(model);

            System.out.println("Inferencia 7 - subClassOf");
            System.out.println("-----------------------------------------------------------------------");

            if (existenAfirmaciones(inf, anorith, RDF.type, extinct_pkmn)) {
                System.out.println("La afirmacion es cierta. Anorith pertenece a extinct_pkmn.");
            } else {
                System.out.println("La afirmacion no es cierta. Anorith No pertenece a extinct_pkmn.");
            }

            if (existenAfirmaciones(inf, armaldo, RDF.type, pkmn)) {
                System.out.println("La afirmacion es cierta. Armaldo pertenece a pkmn.");
            } else {
                System.out.println("La afirmacion no es cierta. Armaldo No pertenece a pkmn.");
            }

        } catch (Exception e) {
            System.out.println("Error en la inferencia: " + e.getMessage());
        } finally {
            System.out.println("\n");
        }
    }

}
