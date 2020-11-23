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

    public Expense() {}

    public Expense(Integer id, String concept, Double quantity, String category) {
        this.id = id;
        this.concept = concept;
        this.quantity = quantity;
        this.category = category;
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
}
