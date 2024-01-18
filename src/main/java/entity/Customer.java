package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer {
    @Id
    private String custId;
    private String custName;
    private String number;
    private String email;

    public Customer(String custId, String custName, String number, String email) {
        this.custId = custId;
        this.custName = custName;
        this.number = number;
        this.email = email;
    }
}
