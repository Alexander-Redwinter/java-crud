package seichirino.Parts.entity;

import javax.persistence.*;

@Entity
@Table(name = "test", schema = "test", catalog = "")
public class Part {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "required")
    private boolean required;

    public Part() {
    }

    public Part(String name, Integer amount, boolean required) {
        this.name = name;
        this.amount = amount;
        this.required = required;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
