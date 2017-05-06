package au.com.turingg.files;

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Stack;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * A {@link FileVisitor} that traverses a directory tree and stores details of and
 * relationships between files and directories inside an OrientDB database.
 *
 * @author Behrang Saeedzadeh
 */
public class OrientDbFileVisitor implements FileVisitor<Path> {

    private final OrientBaseGraph graph;

    private Stack<OrientVertex> dirStack = new Stack<>();

    public OrientDbFileVisitor(final OrientBaseGraph graph) {
        this.graph = graph;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        final FileDetails fileDetails = new FileDetails(dir);
        final OrientVertex vertex = graph.addVertex("class:DIR");

        populateVertex(vertex, fileDetails);

        dirStack.push(vertex);

        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (!attrs.isRegularFile()) {
            return CONTINUE;
        }

        final FileDetails fileDetails = new FileDetails(file);
        final OrientVertex vertex = graph.addVertex("class:FILE");

        populateVertex(vertex, fileDetails);

        graph.addEdge("class:hasChild", dirStack.peek(), vertex, null);

        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        dirStack.pop();
        return CONTINUE;
    }

    private void populateVertex(OrientVertex vertex, FileDetails fileDetails) {
        vertex.setProperty("name", fileDetails.getName());
        vertex.setProperty("path", fileDetails.getAbsolutePath());
        vertex.setProperty("type", fileDetails.getType());
        vertex.setProperty("ext", fileDetails.getExtension() == null ? "" : fileDetails.getExtension());
        vertex.setProperty("mime", fileDetails.getMimeType() == null ? "" : fileDetails.getMimeType());
        vertex.setProperty("size", fileDetails.getSize() == null ? "" : fileDetails.getSize());
    }
}
