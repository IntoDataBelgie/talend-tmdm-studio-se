package com.amalto.workbench.actions;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;
import org.eclipse.xsd.XSDSchema;

import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.providers.XSDTreeContentProvider;

public class XSDEditConceptAction extends Action{

	protected DataModelMainPage page = null;
	protected XSDSchema schema = null;
	
	public XSDEditConceptAction(DataModelMainPage page) {
		super();
		this.page = page;
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.amalto.workbench", "icons/edit_obj.gif"));
		setText("Edit Concept");
		setToolTipText("Edit a Business Concept");
	}
	
	public void run() {
		try {
			super.run();
            schema = ((XSDTreeContentProvider)page.getTreeViewer().getContentProvider()).getXsdSchema();
            ISelection selection = page.getTreeViewer().getSelection();
            XSDElementDeclaration decl = (XSDElementDeclaration)((IStructuredSelection)selection).getFirstElement();
            String oldName = decl.getName();
            
       		InputDialog id = new InputDialog(
       				page.getSite().getShell(),
       				"Edit Concept",
       				"Enter a new Name for the Concept",
       				oldName,
       				new IInputValidator() {
       					public String isValid(String newText) {
       						if ((newText==null) || "".equals(newText)) return "The Concept Name cannot be empty";
       						EList list = schema.getElementDeclarations();
       						for (Iterator iter = list.iterator(); iter.hasNext(); ) {
								XSDElementDeclaration d = (XSDElementDeclaration) iter.next();
								if (d.getName().equals(newText)) return "This Concept already exists";
							}
       						return null;
       					};
       				}
       		);
            
       		id.setBlockOnOpen(true);
       		int ret = id.open();
       		if (ret == Dialog.CANCEL) return;
       		
       		decl.setName(id.getValue());
       		decl.updateElement();
       		
       	    //change unique key with new name of concept
       	    EList list = decl.getIdentityConstraintDefinitions();
       	    XSDIdentityConstraintDefinition toUpdate = null;
       	    for (Iterator iter = list.iterator(); iter.hasNext(); ) {
				XSDIdentityConstraintDefinition icd = (XSDIdentityConstraintDefinition) iter.next();
				if (icd.getName().equals(oldName)) {
					toUpdate = icd;
					break;
				}
			}
       	    if (toUpdate!=null) {
       	    	toUpdate.setName(id.getValue());
       	    	toUpdate.updateElement();
       	    }
       	           		
       		page.getTreeViewer().refresh(true);
       		page.markDirty();
       
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					page.getSite().getShell(),
					"Error", 
					"An error occured trying to edit a Concept: "+e.getLocalizedMessage()
			);
		}		
	}
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	


}