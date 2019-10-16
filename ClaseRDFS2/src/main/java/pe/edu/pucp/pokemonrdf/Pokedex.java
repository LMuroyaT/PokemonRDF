package pe.edu.pucp.pokemonrdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;

public class Pokedex {

    private static final String DIR_BASE = "/Users/gerardocardosoyllanes/Documents/GitHub/PokemonRDF";

    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        Resource pkmn = Utilidades.crearRecurso(Utilidades.NS, "pkmn", model);
        Resource common_pkmn = Utilidades.crearRecurso(Utilidades.NS, "common_pkmn", model);
        Resource extinct_pkmn = Utilidades.crearRecurso(Utilidades.NS, "extinct_pkmn", model);
        Resource legendary_pkmn = Utilidades.crearRecurso(Utilidades.NS, "legendary_pkmn", model);
        Resource element = Utilidades.crearRecurso(Utilidades.NS, "element", model);
        Resource physical_move = Utilidades.crearRecurso(Utilidades.NS, "physical_move", model);
        Resource special_move = Utilidades.crearRecurso(Utilidades.NS, "special_move", model);
        Resource stats_move = Utilidades.crearRecurso(Utilidades.NS, "stats_move", model);
        Resource move = Utilidades.crearRecurso(Utilidades.NS, "move", model);

        Property evolvesByLvlTo = Utilidades.crearPropiedad(Utilidades.NS, "evolvesByLvlTo", model);
        Property evolvesByRockTo = Utilidades.crearPropiedad(Utilidades.NS, "evolvesByRockTo", model);
        Property evolvesByTradeTo = Utilidades.crearPropiedad(Utilidades.NS, "evolvesByTradeTo", model);
        Property evolvesTo = Utilidades.crearPropiedad(Utilidades.NS, "evolvesTo", model);
        Property hasPkmnElement = Utilidades.crearPropiedad(Utilidades.NS, "hasPkmnElement", model);
        Property hasMoveElement = Utilidades.crearPropiedad(Utilidades.NS, "hasMoveElement", model);
        Property learnsMove = Utilidades.crearPropiedad(Utilidades.NS, "learnsMove", model);
        Property learnsMoveByLvl = Utilidades.crearPropiedad(Utilidades.NS, "learnsMoveByLvl", model);
        Property learnsMoveByTMHM = Utilidades.crearPropiedad(Utilidades.NS, "learnsMoveByTMHM", model);
        Property strongAgainst = Utilidades.crearPropiedad(Utilidades.NS, "strongAgainst", model);

        model.add(common_pkmn, RDFS.subClassOf, pkmn);
        model.add(extinct_pkmn, RDFS.subClassOf, pkmn);
        model.add(legendary_pkmn, RDFS.subClassOf, pkmn);

        model.add(physical_move, RDFS.subClassOf, move);
        model.add(special_move, RDFS.subClassOf, move);
        model.add(stats_move, RDFS.subClassOf, move);

        model.add(evolvesTo, RDFS.domain, pkmn);
        model.add(evolvesTo, RDFS.range, pkmn);
        model.add(evolvesByLvlTo, RDFS.subPropertyOf, evolvesTo);
        model.add(evolvesByRockTo, RDFS.subPropertyOf, evolvesTo);
        model.add(evolvesByTradeTo, RDFS.subPropertyOf, evolvesTo);

        model.add(hasPkmnElement, RDFS.domain, pkmn);
        model.add(hasPkmnElement, RDFS.range, element);

        model.add(hasMoveElement, RDFS.domain, move);
        model.add(hasMoveElement, RDFS.range, element);

        model.add(learnsMove, RDFS.domain, pkmn);
        model.add(learnsMove, RDFS.range, move);

        model.add(learnsMoveByLvl, RDFS.subPropertyOf, learnsMove);
        model.add(learnsMoveByTMHM, RDFS.subPropertyOf, learnsMove);

        model.add(strongAgainst, RDFS.domain, element);
        model.add(strongAgainst, RDFS.range, element);

        System.out.print("Clase common_pkmn: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/Pokemon_Normal.csv", 1, common_pkmn, Utilidades.NS, model);
        System.out.print("Clase extinct_pkmn: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/pokemon_extinct.csv", 1, extinct_pkmn, Utilidades.NS, model);
        System.out.print("Clase legendary_pkmn: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/pokemon_legendary.csv", 1, legendary_pkmn, Utilidades.NS, model);

        System.out.print("Clase element: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/element.csv", 1, element, Utilidades.NS, model);

        System.out.print("Clase physical_move: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/physical_movement.csv", 1, physical_move, Utilidades.NS, model);
        System.out.print("Clase special_move: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/special_movement.csv", 1, special_move, Utilidades.NS, model);
        System.out.print("Clase stats_move: ");
        Utilidades.leerArchivoMiembro(DIR_BASE + "/Files/stats_movement.csv", 1, stats_move, Utilidades.NS, model);

        System.out.println("Relacion evolvesByLvl: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/evolvesByLvl.csv", 1, evolvesByLvlTo, Utilidades.NS, model);
        System.out.println("Relacion evolvesByRock: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/evolvesByRock.csv", 1, evolvesByRockTo, Utilidades.NS, model);
        System.out.println("Relacion evolvesByTrade: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/evolvesByTrade.csv", 1, evolvesByTradeTo, Utilidades.NS, model);

        System.out.println("Relacion hasPkmnElement: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/pkmnHasElement.csv", 1, hasPkmnElement, Utilidades.NS, model);

        System.out.println("Relacion hasMoveElement: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/moveHasElement.csv", 1, hasMoveElement, Utilidades.NS, model);

        System.out.println("Relacion learnsMoveByLvl: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/learnMoveLvl.csv", 1, learnsMoveByLvl, Utilidades.NS, model);

        System.out.println("Relacion learnsMoveByTMHM: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/learnMoveTM.csv", 1, learnsMoveByTMHM, Utilidades.NS, model);

        System.out.println("Relacion strongAgainst: ");
        Utilidades.leerArchivoPropiedad(DIR_BASE + "/Files/elementStrongAgainst.csv", 1, strongAgainst, Utilidades.NS, model);

        Utilidades.grabarRDF(DIR_BASE + "/Files/output.rdf", model);
    }
}
