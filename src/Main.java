import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owl.align.Alignment;

import fr.inrialpes.exmo.align.impl.BasicAlignment;
import fr.inrialpes.exmo.align.parser.AlignmentParser;

public class Main {
	// Buat ngeload ontology dan transform query
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RULE_INF, null );

		// Load ontology 1
		model.read("file:///E:/Kuliah/TUGASAKHIR/TugasAkhirLatestFolder/Software/ontologyandmappingsanddatasources/endtoendtest/book1-test1.owl");

		// Query in ontology 1
		QueryExecution qe = QueryExecutionFactory.create( QueryFactory.read( "E:\\Kuliah\\TUGASAKHIR\\TugasAkhirLatestFolder\\Software\\ontologyandmappingsanddatasources\\endtoendtest\\book1-query1.sparql" ), model );
		ResultSet results = qe.execSelect();
		// PrefixMapping query;
		// Output query results	
		// ResultSetFormatter.out(System.out, results, query);

		// Load ontology 2
		model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RULE_INF, null );
		model.read("file:///E:/Kuliah/TUGASAKHIR/TugasAkhirLatestFolder/Software/ontologyandmappingsanddatasources/endtoendtest/book2-test1.owl");
			
		// Transform query
		String transformedQuery = null;
		String firstQuery = null;
		try {
		    InputStream in = new FileInputStream("E:/Kuliah/TUGASAKHIR/TugasAkhirLatestFolder/Software/ontologyandmappingsanddatasources/endtoendtest/book1-query1.sparql");
		    BufferedReader reader = new BufferedReader( new InputStreamReader(in) );
		    String line = null;
		    String queryString = "";
		    while ((line = reader.readLine()) != null) {
			queryString += line + "\n";
		    }
		    firstQuery = queryString;
		    Properties parameters = new Properties();
		    
		    AlignmentParser aparser = new AlignmentParser(0);
//		    Mapping
		    Alignment alu = aparser.parse("file:///E:/Kuliah/TUGASAKHIR/TugasAkhirLatestFolder/Software/align-4.9/html/tutorial/tutorial4/bookalignment.rdf");
		    BasicAlignment al = (BasicAlignment) alu;
			transformedQuery = ((BasicAlignment)al).rewriteQuery( queryString, parameters );
//		    transformedQuery = ((ObjectAlignment)al).
		} catch ( Exception ex ) { ex.printStackTrace(); }

		// Query ontology 2
		System.out.println(transformedQuery);
//		displayQueryAnswer( model, QueryFactory.create( transformedQuery ) );
		qe = QueryExecutionFactory.create( QueryFactory.create( firstQuery ), model );
		results = qe.execSelect();
		System.out.println(results);
		
		// Output query results	
		// ResultSetFormatter.out(System.out, results, query);
	}

}
