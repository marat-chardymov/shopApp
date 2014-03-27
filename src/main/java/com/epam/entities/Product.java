package com.epam.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Marat_Chardymau on 3/19/14.
 */
@XmlRootElement
public class Product {
    private String producer;
    private String model;
    private String dateOfIssue;
    private String color;
//    @XmlElement(nillable = false)
    private boolean notInStock;
//    @XmlElement(nillable = false)
    private int price;

    public Product() {
    }

    public Product(String producer, String model, String dateOfIssue, String color, int price) {
        this.producer = producer;
        this.model = model;
        this.dateOfIssue = dateOfIssue;
        this.color = color;
        this.price = price;
    }

    public Product(String producer, String model, String dateOfIssue, String color, boolean notInStock) {
        this.producer = producer;
        this.model = model;
        this.dateOfIssue = dateOfIssue;
        this.color = color;
        this.notInStock = notInStock;
    }

    public Product(String model, String color, String dateOfIssue,
			int price, String producer, boolean notInStock) {
    	this.producer = producer;
        this.model = model;
        this.dateOfIssue = dateOfIssue;
        this.color = color;
        this.notInStock = notInStock;
        this.price=price;
	}

	public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isNotInStock() {
        return notInStock;
    }

    public void setNotInStock(boolean notInStock) {
        this.notInStock = notInStock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public boolean isInStock(){
    	return !this.notInStock;
    }
    
}
