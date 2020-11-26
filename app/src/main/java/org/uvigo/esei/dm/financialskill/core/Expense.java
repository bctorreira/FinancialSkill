package org.uvigo.esei.dm.financialskill.core;

public class Expense {
    /*public enum Category {
        FOOD,
        HOME,
        HEALTH,
        TRANSPORTATION,
        CLOTHING,
        EDUCATION,
        SPORTS,
        LEISURE,
        OTHERS,
        INCOME
    }*/

    private Integer id;
    private String concept;
    private Double quantity;
    private String category;
    private String description;

    public Expense() {}

    public Expense(String concept, Double quantity, String category, String description) {
        this.concept = concept;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
