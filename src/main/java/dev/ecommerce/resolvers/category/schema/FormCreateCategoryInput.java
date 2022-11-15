package dev.ecommerce.resolvers.category.schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FormCreateCategoryInput {
    public FormCreateCategoryInput(String alias, String name, String description){
        this.alias = alias;
        this.name = name;
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank
    @NotNull
    String alias;
    @NotBlank
    @NotNull
    String name;
    String description;
}
