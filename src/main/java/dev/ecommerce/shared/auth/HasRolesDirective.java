package dev.ecommerce.shared.auth;

import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.resources.Errors;
import graphql.GraphQLContext;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

import java.util.List;

public class HasRolesDirective implements SchemaDirectiveWiring {

    @Override
    public GraphQLFieldDefinition onField(
            SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

        if (environment.getAppliedDirective("hasRoles") == null) {
            return environment.getElement();
        }

        List<String> schemaDirectiveRole = environment.getAppliedDirective("hasRoles").getArgument("roles").getValue();
        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher<?> authDataFetcher =
                dataFetchingEnvironment -> {
                    GraphQLContext graphQlContext = dataFetchingEnvironment.getGraphQlContext();
                    String userRole = graphQlContext.get("role");
                    System.out.println(userRole);
                    System.out.println(schemaDirectiveRole);
                    if (userRole != null && schemaDirectiveRole.contains(userRole)) {
                        return originalDataFetcher.get(dataFetchingEnvironment);
                    } else {
                        throw new CustomMessageError(Errors.PermissionDenied.getValue());
                    }
                };

        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }
}
