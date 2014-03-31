package com.epam;

import com.epam.entities.Catalog;
import com.epam.entities.Category;
import com.epam.entities.Product;
import com.epam.entities.SubCategory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLGenerator {
    public static void main(String[] args) throws JAXBException {
        Catalog catalog=new Catalog();

        List<Category> categories=new ArrayList<Category>();
        catalog.setCategories(categories);
        Category c1=new Category("c1");
        Category c2=new Category("c2");
        categories.add(c1);
        categories.add(c2);

        List<SubCategory> subCategoryList1=new ArrayList<SubCategory>();
        SubCategory s1 = new SubCategory("s1");
        SubCategory s2=new SubCategory("s2");
        subCategoryList1.add(s1);
        subCategoryList1.add(s2);
        c1.setSubCategories(subCategoryList1);

        List<SubCategory> subCategoryList2=new ArrayList<SubCategory>();
        SubCategory s3=new SubCategory("s3");
        SubCategory s4=new SubCategory("s4");
        subCategoryList2.add(s3);
        subCategoryList2.add(s4);
        c2.setSubCategories(subCategoryList2);

        List<Product> productList1 = new ArrayList<Product>();
        productList1.add(new Product("producer1", "as001", "12-12-2012", "white", 100));
        productList1.add(new Product("producer2", "as002", "02-02-2012", "black", 200));
        s1.setProducts(productList1);

        List<Product> productList2=new ArrayList<Product>();
        productList2.add(new Product("producer3","as003", "12-12-2012","white",300 ));
        productList2.add(new Product("producer4", "as004", "02-02-2012", "black", 400));
        s2.setProducts(productList2);

        File file = new File("catalog.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(catalog, file);
        jaxbMarshaller.marshal(catalog, System.out);

    }
}
