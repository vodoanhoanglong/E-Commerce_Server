package dev.ecommerce.resolvers.auth;

public class FormCreateCategoriesInput {
    public FormCreateCategoriesInput(String alias, String name, String description){
        this.alias = alias;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String name ;
    String  alias;
    String description;
}
