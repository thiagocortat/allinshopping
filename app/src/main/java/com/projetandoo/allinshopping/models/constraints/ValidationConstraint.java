package com.projetandoo.allinshopping.models.constraints;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidationConstraint
{

    private List<String> errors = new ArrayList<String>();

    protected void add(String s)
    {
        errors.add(s);
    }

    public  List<String>  errors()
    {
        return errors;
    }

    public boolean isValid()
    {
        this.errors.clear();
        this.validate();
        return errors.isEmpty();
    }

    protected void addAll(List<String> errors ){
        for(String error : errors ){
            this.add(error);
        }
    }

    protected abstract void validate();
}
