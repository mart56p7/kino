package kano.kino.service;


import kano.kino.model.ModelInterface;
import kano.kino.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Model factory for the Storage CRUD Controllers
 *
 * This factory can create the following Storage classes
 * Product,
 * ProductAttribute,
 * ProductDefinition,
 * ProductSize
 * */
@Service("ModelFactory")
public class ModelFactory {

    @Autowired
    public ModelFactory(){}

    /**
     * Called with the name of the model you wish to create
     * */
    public ModelInterface getModel(String modelname){
        ModelInterface model = null;
        switch(modelname.toUpperCase()){
            case "USER":
                model = new User();
                break;
        }
        return model;
    }
}
