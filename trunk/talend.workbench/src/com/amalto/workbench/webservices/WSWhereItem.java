// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, construire R40)
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSWhereItem {
    protected com.amalto.workbench.webservices.WSWhereCondition whereCondition;
    protected com.amalto.workbench.webservices.WSWhereAnd whereAnd;
    protected com.amalto.workbench.webservices.WSWhereOr whereOr;
    
    public WSWhereItem() {
    }
    
    public WSWhereItem(com.amalto.workbench.webservices.WSWhereCondition whereCondition, com.amalto.workbench.webservices.WSWhereAnd whereAnd, com.amalto.workbench.webservices.WSWhereOr whereOr) {
        this.whereCondition = whereCondition;
        this.whereAnd = whereAnd;
        this.whereOr = whereOr;
    }
    
    public com.amalto.workbench.webservices.WSWhereCondition getWhereCondition() {
        return whereCondition;
    }
    
    public void setWhereCondition(com.amalto.workbench.webservices.WSWhereCondition whereCondition) {
        this.whereCondition = whereCondition;
    }
    
    public com.amalto.workbench.webservices.WSWhereAnd getWhereAnd() {
        return whereAnd;
    }
    
    public void setWhereAnd(com.amalto.workbench.webservices.WSWhereAnd whereAnd) {
        this.whereAnd = whereAnd;
    }
    
    public com.amalto.workbench.webservices.WSWhereOr getWhereOr() {
        return whereOr;
    }
    
    public void setWhereOr(com.amalto.workbench.webservices.WSWhereOr whereOr) {
        this.whereOr = whereOr;
    }
}
