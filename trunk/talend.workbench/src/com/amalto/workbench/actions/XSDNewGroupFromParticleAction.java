package com.amalto.workbench.actions;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDSchemaBuildingTools;

import com.amalto.workbench.AmaltoWorbenchPlugin;
import com.amalto.workbench.dialogs.NewGroupDialog;
import com.amalto.workbench.editors.DataModelMainPage;
import com.amalto.workbench.providers.XSDTreeContentProvider;

public class XSDNewGroupFromParticleAction extends Action implements SelectionListener{

	private DataModelMainPage page = null;
	private XSDSchema schema = null;
	private NewGroupDialog dialog = null;
	private XSDParticle selParticle = null;
	
	private boolean isChoice;
	private boolean isAll;
	private int minOccurs;
	private int maxOccurs;
	
	public XSDNewGroupFromParticleAction(DataModelMainPage page) {
		super();
		this.page = page;
		setImageDescriptor(AmaltoWorbenchPlugin.imageDescriptorFromPlugin("com.amalto.workbench", "icons/add_obj.gif"));
		setText("Add Group (after)");
		setToolTipText("Add a new Group after this one. Add from the Type to add at First Position.");
	}
	
	public void run() {
		try {
			super.run();
            schema = ((XSDTreeContentProvider)page.getTreeViewer().getContentProvider()).getXsdSchema();
            
            IStructuredSelection selection = (IStructuredSelection)page.getTreeViewer().getSelection();
            selParticle = (XSDParticle) selection.getFirstElement();
            
            if (!(selParticle.getContainer() instanceof XSDModelGroup)) return;
            
            XSDModelGroup group = (XSDModelGroup) selParticle.getContainer();
            //get position of the selected particle in the container
            int index = 0;
            int i=0; 
            for (Iterator iter = group.getContents().iterator(); iter.hasNext(); ) {
				XSDParticle p = (XSDParticle) iter.next();
				if (p.equals(selParticle)) {
					index = i;
					break;
				}
				i++;
			}
  
            dialog = new NewGroupDialog(this,page.getSite().getShell());
            dialog.setBlockOnOpen(true);
       		int ret = dialog.open();
       		if (ret == Dialog.CANCEL) return;
       		
       		XSDFactory factory = XSDSchemaBuildingTools.getXSDFactory();
       		
  			//add an element declaration
   			XSDElementDeclaration subElement = factory.createXSDElementDeclaration();
    		subElement.setName("subelement");
    		subElement.setTypeDefinition(schema.resolveSimpleTypeDefinition(schema.getSchemaForSchemaNamespace(), "string"));
   			
    		XSDParticle subParticle = factory.createXSDParticle();
   			subParticle.setMinOccurs(1);
   			subParticle.setMaxOccurs(1);
   			subParticle.setContent(subElement);
   			subParticle.updateElement();
       		
       		XSDModelGroup newGroup = factory.createXSDModelGroup();
       		if (isChoice)
       			newGroup.setCompositor(XSDCompositor.CHOICE_LITERAL);
       		else if (isAll)
       			newGroup.setCompositor(XSDCompositor.ALL_LITERAL);
       		else
       			newGroup.setCompositor(XSDCompositor.SEQUENCE_LITERAL);
       		newGroup.getContents().add(0, subParticle);
       		newGroup.updateElement();
       		       		
       		XSDParticle particle = factory.createXSDParticle();
       		particle.setContent(newGroup);
       		particle.setMinOccurs(this.minOccurs);
       		particle.setMaxOccurs(this.maxOccurs);
       		
       		group.getContents().add(index+1,particle);
       		group.updateElement();
       		
       		page.getTreeViewer().refresh(true);
       		page.getTreeViewer().setSelection(new StructuredSelection(particle),true);
       		
       		page.markDirty();
       
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					page.getSite().getShell(),
					"Error", 
					"An error occured trying to create a new Business Element: "+e.getLocalizedMessage()
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
		if (dialog.getReturnCode() ==  -1) return; //there was a validation error	
		if (dialog.getReturnCode() ==  -1) return; //there was a validation error
		isChoice = dialog.isChoice();
		isAll = dialog.isAll();
		minOccurs = dialog.getMinOccurs();
		maxOccurs = dialog.getMaxOccurs();
		dialog.close();		
	}
	


}