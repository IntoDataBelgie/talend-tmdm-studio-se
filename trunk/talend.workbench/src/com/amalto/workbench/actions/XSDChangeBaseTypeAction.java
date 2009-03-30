package com.amalto.workbench.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.amalto.workbench.dialogs.SimpleTypeInputDialog;
import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.providers.XSDTreeContentProvider;
import com.amalto.workbench.utils.Util;

public class XSDChangeBaseTypeAction extends Action implements SelectionListener{

	private DataModelMainPage page = null;
	private XSDSchema schema = null;
	private SimpleTypeInputDialog dialog = null;
	private String typeName = null;
	private boolean builtIn = false;
	
	public XSDChangeBaseTypeAction(DataModelMainPage page) {
		super();
		this.page = page;
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.amalto.workbench", "icons/change_to_simple.gif"));
		setText("Change Base Type");
		setToolTipText("Change the Base Type of the Element Simple Type");
		setDescription(getToolTipText());
	}
	
	public void run() {
		try {
			super.run();
			schema = ((XSDTreeContentProvider)page.getTreeViewer().getContentProvider()).getXsdSchema();
			
			IStructuredSelection selection = (IStructuredSelection) page.getTreeViewer().getSelection();
			XSDSimpleTypeDefinition typedef = (XSDSimpleTypeDefinition)selection.getFirstElement();
						
			//Cannot change the simple type definition of built in type
			if (schema.getSchemaForSchemaNamespace().equals(typedef.getTargetNamespace())) return;
			
			//build list of custom types and built in types
			ArrayList customTypes = new ArrayList();
			for (Iterator iter =  schema.getTypeDefinitions().iterator(); iter.hasNext(); ) {
				XSDTypeDefinition type = (XSDTypeDefinition) iter.next();
				if (type instanceof XSDSimpleTypeDefinition)
					customTypes.add(type.getName());
			}
			ArrayList builtInTypes = new ArrayList();
			for (Iterator iter =  schema.getSchemaForSchema().getTypeDefinitions().iterator(); iter.hasNext(); ) {
				XSDTypeDefinition type = (XSDTypeDefinition) iter.next();
				if (type instanceof XSDSimpleTypeDefinition)
					builtInTypes.add(type.getName());
			}
			
			dialog = new SimpleTypeInputDialog(
					this,
					page.getSite().getShell(),
					"Change Base Type",
					customTypes,
					builtInTypes
			);
			
			dialog.setBlockOnOpen(true);
			int ret = dialog.open();
			if (ret == Window.CANCEL) return;
			
			//backup current Base Type
			XSDTypeDefinition current = typedef.getBaseTypeDefinition();
							
			//set new one
			if (builtIn) {
				typedef.setBaseTypeDefinition(schema.resolveSimpleTypeDefinition(schema.getSchemaForSchemaNamespace(), typeName));
			} else {
				//check if simple type definition already exists
				XSDSimpleTypeDefinition std = schema.resolveSimpleTypeDefinition(typeName);
				if (!schema.getTypeDefinitions().contains(std)) {
					std.setBaseTypeDefinition(schema.resolveSimpleTypeDefinition(schema.getSchemaForSchemaNamespace(), "string"));
					schema.getContents().add(std);
				}
				typedef.setBaseTypeDefinition(std);
			}
			
			//remove current facets
			typedef.getFacetContents().removeAll(typedef.getFacetContents());
			
			typedef.updateElement();
			
       	    //remove current if no more in use
       	    if (	(current.getName()!=null) &&  //anonymous type
       	    		(!schema.getSchemaForSchemaNamespace().equals(current.getTargetNamespace()))
       	    	){
       	    	List eut =Util.findElementsUsingType(schema, current.getTargetNamespace(), current.getName()); 
       	    	if (eut.size()==0)
       	    		schema.getContents().remove(current);
       	    }
			
			page.getTreeViewer().refresh(true);
			page.markDirty();
			
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					page.getSite().getShell(),
					"Error", 
					"An error occured trying to change the Base Type Definition: "+e.getLocalizedMessage()
			);
		}		
	}
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	
	/********************************
	 * Listener to input dialog
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	public void widgetSelected(SelectionEvent e) {
		if (dialog.getReturnCode()==-1) return;
		typeName = dialog.getTypeName();
		builtIn = dialog.isBuiltIn();
		
		//if built in, check that the type actually exists
		if (builtIn) {
			boolean found =false;
            for (Iterator iter =  schema.getSchemaForSchema().getTypeDefinitions().iterator(); iter.hasNext(); ) {
				XSDTypeDefinition type = (XSDTypeDefinition) iter.next();
				if (type instanceof XSDSimpleTypeDefinition)
					if (type.getName().equals(typeName)) {
						found = true;
						break;
					}
            }
            if (!found) {
    			MessageDialog.openError(
    					page.getSite().getShell(),
    					"Error", 
    					"The built-in type "+typeName+" does not exist"
    			);            	
				return;
			}			
		}
		dialog.close();		
	}
	
	
	

}