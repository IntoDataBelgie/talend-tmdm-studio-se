// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1.2_01, construire R40)
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSMenu {
    protected java.lang.String name;
    protected java.lang.String description;
    protected com.amalto.workbench.webservices.WSMenuEntry[] menuEntries;
    
    public WSMenu() {
    }
    
    public WSMenu(java.lang.String name, java.lang.String description, com.amalto.workbench.webservices.WSMenuEntry[] menuEntries) {
        this.name = name;
        this.description = description;
        this.menuEntries = menuEntries;
    }
    
    public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public java.lang.String getDescription() {
        return description;
    }
    
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    public com.amalto.workbench.webservices.WSMenuEntry[] getMenuEntries() {
        return menuEntries;
    }
    
    public void setMenuEntries(com.amalto.workbench.webservices.WSMenuEntry[] menuEntries) {
        this.menuEntries = menuEntries;
    }
}
