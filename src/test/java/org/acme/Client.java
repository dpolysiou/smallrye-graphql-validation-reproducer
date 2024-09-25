package org.acme;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.graphql.client.typesafe.api.NestedParameter;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLClientApi
public interface Client {

  record QueryWithBug(@Name("pageWithNonNull") List<Foo> response) {}

  record WorkingQuery(@Name("page") List<Foo> response) {}

  @Query("foo")
  QueryWithBug queryWithBuggyValidation(@NestedParameter("response") Pagination pagination);

  @Query("foo")
  WorkingQuery workingQuery(@NestedParameter("response") Pagination pagination);
}
