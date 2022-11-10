package dev.ecommerce.shared.auth;

import dev.ecommerce.models.Users;
import dev.ecommerce.shared.errors.CustomMessageError;
import dev.ecommerce.shared.resources.Directives;
import dev.ecommerce.shared.resources.Errors;
import dev.ecommerce.shared.resources.Headers;
import dev.ecommerce.shared.resources.Roles;
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

        if (environment.getAppliedDirective(Directives.HasRoles.getName()) == null) {
            return environment.getElement();
        }

        List<String> schemaDirectiveRole = environment.getAppliedDirective(Directives.HasRoles.getName())
                .getArgument(Directives.HasRoles.getFirstArg())
                .getValue();
        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher<?> authDataFetcher =
                dataFetchingEnvironment -> {
                    GraphQLContext graphQlContext = dataFetchingEnvironment.getGraphQlContext();
                    Users currentUser = graphQlContext.get(Headers.CurrentUser.getValue());
                    String userRole = Roles.ANONYMOUS.getValue();
                    if(currentUser != null && currentUser.getRole() != null){
                        userRole = currentUser.getRole();
                    }
                    if (schemaDirectiveRole.contains(userRole)) {
                        return originalDataFetcher.get(dataFetchingEnvironment);
                    } else {
                        throw new CustomMessageError(Errors.PermissionDenied.getValue());
                    }
                };

        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }
}
