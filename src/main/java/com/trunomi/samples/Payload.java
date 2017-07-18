package com.trunomi.samples;

/**
 * Created by John Sotiropoulos on 17/07/2017.
 */
public class Payload
{
    private String consentDefinitionName;

    private String moc;

    private String program;

    private GenericFields genericFields;

    private String customData;

    private String[] dataTypeId;

    private String consentDefinitionId;

    private String contextName;

    public String getConsentDefinitionName ()
    {
        return consentDefinitionName;
    }

    public void setConsentDefinitionName (String consentDefinitionName)
    {
        this.consentDefinitionName = consentDefinitionName;
    }

    public String getMoc ()
    {
        return moc;
    }

    public void setMoc (String moc)
    {
        this.moc = moc;
    }

    public String getProgram ()
    {
        return program;
    }

    public void setProgram (String program)
    {
        this.program = program;
    }

    public GenericFields getGenericFields ()
    {
        return genericFields;
    }

    public void setGenericFields (GenericFields genericFields)
    {
        this.genericFields = genericFields;
    }

    public String getCustomData ()
    {
        return customData;
    }

    public void setCustomData (String customData)
    {
        this.customData = customData;
    }

    public String[] getDataTypeId ()
    {
        return dataTypeId;
    }

    public void setDataTypeId (String[] dataTypeId)
    {
        this.dataTypeId = dataTypeId;
    }

    public String getConsentDefinitionId ()
    {
        return consentDefinitionId;
    }

    public void setConsentDefinitionId (String consentDefinitionId)
    {
        this.consentDefinitionId = consentDefinitionId;
    }

    public String getContextName ()
    {
        return contextName;
    }

    public void setContextName (String contextName)
    {
        this.contextName = contextName;
    }

    @Override
    public String toString()
    {
        return "Class [consentDefinitionName = "+consentDefinitionName+", moc = "+moc+", program = "+program+", genericFields = "+genericFields+", customData = "+customData+", dataTypeId = "+dataTypeId+", consentDefinitionId = "+consentDefinitionId+", contextName = "+contextName+"]";
    }
}