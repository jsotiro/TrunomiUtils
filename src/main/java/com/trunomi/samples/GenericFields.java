package com.trunomi.samples;

/**
 * Created by John Sotiropoulos on 17/07/2017.
 */
public class GenericFields
{
    private String dataController;

    private String jurisdiction;

    private String[] products;

    private String[] preferences;

    public String getDataController ()
    {
        return dataController;
    }

    public void setDataController (String dataController)
    {
        this.dataController = dataController;
    }

    public String getJurisdiction ()
    {
        return jurisdiction;
    }

    public void setJurisdiction (String jurisdiction)
    {
        this.jurisdiction = jurisdiction;
    }

    public String[] getProducts ()
    {
        return products;
    }

    public void setProducts (String[] products)
    {
        this.products = products;
    }

    public String[] getPreferences ()
    {
        return preferences;
    }

    public void setPreferences (String[] preferences)
    {
        this.preferences = preferences;
    }

    @Override
    public String toString()
    {
        return "Class [dataController = "+dataController+", jurisdiction = "+jurisdiction+", products = "+products+", preferences = "+preferences+"]";
    }
}