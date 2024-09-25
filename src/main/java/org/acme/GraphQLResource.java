package org.acme;

import jakarta.validation.Valid;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import java.util.List;

@GraphQLApi
public class GraphQLResource {

  @Query
  public @NonNull Foo getFoo() {
    return new Foo("Alice");
  }

  public List<Foo> page(@Source Foo foo, @Valid Pagination pagination) {
    return List.of(foo);
  }

  public @NonNull List<Foo> pageWithNonNull(@Source Foo foo, @Valid Pagination pagination) {
    return List.of(foo);
  }
}
