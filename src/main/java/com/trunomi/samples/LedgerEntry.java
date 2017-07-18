package com.trunomi.samples;

/**
 * Created by John Sotiropoulos on 17/07/2017.
 */



public class LedgerEntry
{
    private String id;

    private String contextId;

    private String customerId;

    private String event;

    private String createdAt;

    private Payload payload;

    private String enterpriseId;

    private String previousLedgerEntryId;

    private String trucert;

    private String capturedAt;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getContextId ()
    {
        return contextId;
    }

    public void setContextId (String contextId)
    {
        this.contextId = contextId;
    }

    public String getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    public String getEvent ()
    {
        return event;
    }

    public void setEvent (String event)
    {
        this.event = event;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public Payload getPayload ()
    {
        return payload;
    }

    public void setPayload (Payload payload)
    {
        this.payload = payload;
    }

    public String getEnterpriseId ()
    {
        return enterpriseId;
    }

    public void setEnterpriseId (String enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public String getPreviousLedgerEntryId ()
    {
        return previousLedgerEntryId;
    }

    public void setPreviousLedgerEntryId (String previousLedgerEntryId)
    {
        this.previousLedgerEntryId = previousLedgerEntryId;
    }

    public String getTrucert ()
    {
        return trucert;
    }

    public void setTrucert (String trucert)
    {
        this.trucert = trucert;
    }

    public String getCapturedAt ()
    {
        return capturedAt;
    }

    public void setCapturedAt (String capturedAt)
    {
        this.capturedAt = capturedAt;
    }

    @Override
    public String toString()
    {
        return "Class [id = "+id+", contextId = "+contextId+", customerId = "+customerId+", event = "+event+", createdAt = "+createdAt+", payload = "+payload+", enterpriseId = "+enterpriseId+", previousLedgerEntryId = "+previousLedgerEntryId+", trucert = "+trucert+", capturedAt = "+capturedAt+"]";
    }
}

