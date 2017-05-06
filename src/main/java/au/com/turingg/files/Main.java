package au.com.turingg.files;

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Arrays.asList;

/**
 * @author Behrang Saeedzadeh
 */
public class Main {

    public static void main(String[] args) throws IOException {
        final OrientGraphNoTx graph = new OrientGraphNoTx("plocal:databases/poc2", "admin", "admin");

        asList("DIR", "FILE").forEach(type -> {
            if (graph.getVertexType(type) == null) {
                graph.createVertexType(type);
            }
        });

        graph.getVertices().forEach(graph::removeVertex);
        graph.getEdges().forEach(graph::removeEdge);

        Path rootDir = Paths.get("/home");

        final OrientDbFileVisitor visitor = new OrientDbFileVisitor(graph);

        Files.walkFileTree(rootDir, visitor);
    }

}
