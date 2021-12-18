package com.rolex.alphax.behavioral.mediator;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
public class ConcreteMediator implements Mediator{

    CustomerA customerA;
    CustomerB customerB;
    CustomerC customerC;
    CustomerD customerD;

    public void setCustomerA(CustomerA customerA) {
        this.customerA = customerA;
    }

    public void setCustomerB(CustomerB customerB) {
        this.customerB = customerB;
    }

    public void setCustomerC(CustomerC customerC) {
        this.customerC = customerC;
    }

    public void setCustomerD(CustomerD customerD) {
        this.customerD = customerD;
    }

    @Override
    public void notify(Customer customer, String msg) {
        if(customer instanceof CustomerA){
            customerB.receive(msg);
            customerC.receive(msg);
            customerD.receive(msg);
        }
        if(customer instanceof CustomerB){
            customerA.receive(msg);
            customerC.receive(msg);
            customerD.receive(msg);
        }
        if(customer instanceof CustomerC){
            customerA.receive(msg);
            customerB.receive(msg);
            customerD.receive(msg);
        }
        if(customer instanceof CustomerD){
            customerA.receive(msg);
            customerB.receive(msg);
            customerC.receive(msg);
        }
    }

}
