package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.graphql.client.GraphQLClientException;
import io.smallrye.graphql.client.GraphQLError;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class GraphQLResourceTest {

  @Inject
  Client client;

  private final Pagination invalidParams = new Pagination(-1, -1);

  private final String extraErrorMessage = """
          The field at path '/foo/response' was declared as a non null type,
          but the code involved in retrieving data has wrongly returned a null value.
           The graphql specification requires that the parent field be set to null,
          or if that is non nullable that it bubble up null to its parent and so on.
          The non-nullable type is '[Foo]' within parent type 'Foo'
          """
          .replaceAll("\\n", " ")
          .stripTrailing();

  @Test
  void page() {
    GraphQLClientException exception =
        assertThrows(GraphQLClientException.class,
            () -> client.workingQuery(invalidParams));
    List<GraphQLError> errors = exception.getErrors();
    assertEquals(2, errors.size());
    assertTrue(errors.get(0).getMessage().startsWith("validation failed"));
    assertTrue(errors.get(1).getMessage().startsWith("validation failed"));
  }

  @Test
  void pageWithNonNull() {
    GraphQLClientException exception =
        assertThrows(GraphQLClientException.class,
            () -> client.queryWithBuggyValidation(invalidParams));
    List<GraphQLError> errors = exception.getErrors();
    assertEquals(3, errors.size());
    assertTrue(errors.get(0).getMessage().startsWith("validation failed"));
    assertTrue(errors.get(1).getMessage().startsWith("validation failed"));
    assertEquals(extraErrorMessage, errors.get(2).getMessage());
  }

  @Test
  void pageWithValidParams() {
    Pagination validParams = new Pagination(1, 0);
    List<Foo> response = client.queryWithBuggyValidation(validParams).response();
    assertEquals(1, response.size());

    assertEquals(response, client.queryWithBuggyValidation(validParams).response());
  }

}