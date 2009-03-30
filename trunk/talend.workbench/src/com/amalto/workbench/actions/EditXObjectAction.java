package com.amalto.workbench.actions;

import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.providers.XObjectEditorInput;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.views.ServerView;
import com.amalto.workbench.webservices.WSDataCluster;
import com.amalto.workbench.webservices.WSDataClusterPK;
import com.amalto.workbench.webservices.WSDataModel;
import com.amalto.workbench.webservices.WSDataModelPK;
import com.amalto.workbench.webservices.WSDestination;
import com.amalto.workbench.webservices.WSDestinationPK;
import com.amalto.workbench.webservices.WSGetDataCluster;
import com.amalto.workbench.webservices.WSGetDataModel;
import com.amalto.workbench.webservices.WSGetDestination;
import com.amalto.workbench.webservices.WSGetInboundAdaptor;
import com.amalto.workbench.webservices.WSGetInboundPlugin;
import com.amalto.workbench.webservices.WSGetMenu;
import com.amalto.workbench.webservices.WSGetOutboundAdaptor;
import com.amalto.workbench.webservices.WSGetOutboundPlugin;
import com.amalto.workbench.webservices.WSGetRole;
import com.amalto.workbench.webservices.WSGetRoutingRule;
import com.amalto.workbench.webservices.WSGetSource;
import com.amalto.workbench.webservices.WSGetStoredProcedure;
import com.amalto.workbench.webservices.WSGetTransformer;
import com.amalto.workbench.webservices.WSGetView;
import com.amalto.workbench.webservices.WSInboundAdaptor;
import com.amalto.workbench.webservices.WSInboundAdaptorPK;
import com.amalto.workbench.webservices.WSInboundPlugin;
import com.amalto.workbench.webservices.WSInboundPluginPK;
import com.amalto.workbench.webservices.WSMenu;
import com.amalto.workbench.webservices.WSMenuPK;
import com.amalto.workbench.webservices.WSOutboundAdaptor;
import com.amalto.workbench.webservices.WSOutboundAdaptorPK;
import com.amalto.workbench.webservices.WSOutboundPlugin;
import com.amalto.workbench.webservices.WSOutboundPluginPK;
import com.amalto.workbench.webservices.WSRole;
import com.amalto.workbench.webservices.WSRolePK;
import com.amalto.workbench.webservices.WSRoutingRule;
import com.amalto.workbench.webservices.WSRoutingRulePK;
import com.amalto.workbench.webservices.WSSource;
import com.amalto.workbench.webservices.WSSourcePK;
import com.amalto.workbench.webservices.WSStoredProcedure;
import com.amalto.workbench.webservices.WSStoredProcedurePK;
import com.amalto.workbench.webservices.WSTransformer;
import com.amalto.workbench.webservices.WSTransformerPK;
import com.amalto.workbench.webservices.WSView;
import com.amalto.workbench.webservices.WSViewPK;
import com.amalto.workbench.webservices.XtentisPort;

public class EditXObjectAction extends Action{

	private ServerView view = null;
	private TreeObject xobject = null;
	private IWorkbenchPage page = null;
	
	
	public EditXObjectAction(TreeObject xobject, IWorkbenchPage page) {
		super();
		this.xobject = xobject;
		this.page = page;
		setDetails();
	}
	
	public EditXObjectAction(ServerView view) {
		super();
		this.view = view;
		setDetails();
	}
	
	private void setDetails() {
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.amalto.workbench", "icons/edit.gif"));
		setText("Edit");
		setToolTipText("Edit/View this instance of the Xtentis Object");		
	}
	
	public void run() {
		try {
			super.run();
			if (this.view != null) { //called from ServerView
				ISelection selection = view.getViewer().getSelection();
				xobject = (TreeObject)((IStructuredSelection)selection).getFirstElement();
			}
            
            if (!xobject.isXObject()) return;
            
//          Access to server and get port
			XtentisPort port = Util.getPort(
					new URL(xobject.getEndpointAddress()),
					xobject.getUsername(),
					xobject.getPassword()
			);
              
            switch(xobject.getType()) {
	           	case TreeObject.SOURCE:
	           		WSSource wsSource = port.getSource(new WSGetSource((WSSourcePK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsSource);
	           		break;
	           	case TreeObject.DESTINATION:
	           		WSDestination wsDestination = port.getDestination(new WSGetDestination((WSDestinationPK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsDestination);
	           		break;
	           	case TreeObject.DATA_MODEL:
	           		WSDataModel wsDataModel = port.getDataModel(new WSGetDataModel((WSDataModelPK)xobject.getWsKey())); 
	           		xobject.setWsObject(wsDataModel);
	           		break;
	           	case TreeObject.INBOUND_ADAPTOR:
	           		WSInboundAdaptor wsInboundAdaptor = port.getInboundAdaptor(new WSGetInboundAdaptor((WSInboundAdaptorPK)xobject.getWsKey())); 
	           		xobject.setWsObject(wsInboundAdaptor);
	           		break;
	           	case TreeObject.INBOUND_PLUGIN:
	           		WSInboundPlugin wsInboundPlugin = port.getInboundPlugin(new WSGetInboundPlugin((WSInboundPluginPK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsInboundPlugin);
	           		break;
	           	case TreeObject.OUTBOUND_ADAPTOR:
	           		WSOutboundAdaptor wsOutboundAdaptor = port.getOutboundAdaptor(new WSGetOutboundAdaptor((WSOutboundAdaptorPK)xobject.getWsKey())); 
	           		xobject.setWsObject(wsOutboundAdaptor);
	           		break;
	           	case TreeObject.OUTBOUND_PLUGIN:
	           		WSOutboundPlugin wsOutboundPlugin = port.getOutboundPlugin(new WSGetOutboundPlugin((WSOutboundPluginPK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsOutboundPlugin);
	           		break;
	           	case TreeObject.VIEW:
	           		WSView wsView = port.getView(new WSGetView((WSViewPK)xobject.getWsKey())); 
	           		xobject.setWsObject(wsView); 
	           		break;
	           	case TreeObject.DATA_CLUSTER:
	           		WSDataCluster wsDataCluster = port.getDataCluster(new WSGetDataCluster((WSDataClusterPK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsDataCluster);
	           		break;
	           	case TreeObject.STORED_PROCEDURE:
	           		WSStoredProcedure wsStoredProcedure = port.getStoredProcedure(new WSGetStoredProcedure((WSStoredProcedurePK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsStoredProcedure);
	           		break;	  
	           	case TreeObject.ROLE:
	           		WSRole wsRole = port.getRole(new WSGetRole((WSRolePK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsRole);
	           		break;	  	           		
	           	case TreeObject.ROUTING_RULE:
	           		WSRoutingRule wsRoutingRule = port.getRoutingRule(new WSGetRoutingRule((WSRoutingRulePK)xobject.getWsKey())	); 
	           		xobject.setWsObject(wsRoutingRule);
	           		break;	  	           		
	           	case TreeObject.TRANSFORMER:
	           		WSTransformer wsTranformer = port.getTransformer(new WSGetTransformer((WSTransformerPK)xobject.getWsKey())); 
	           		xobject.setWsObject(wsTranformer);
	           		break;
	           	case TreeObject.MENU:
	           		WSMenu wsMenu = port.getMenu(new WSGetMenu((WSMenuPK)xobject.getWsKey())); 
	           		xobject.setWsObject(wsMenu);
	           		break;	  		           			           		
	           	default:
	           		MessageDialog.openError(view.getSite().getShell(), "Error", "Unknown Xtentis Object Type: "+xobject.getType());
	           		return;
            }//switch
            
            if (page==null) this.page = view.getSite().getWorkbenchWindow().getActivePage();
            
       		this.page.openEditor(
                    new XObjectEditorInput(xobject,xobject.getDisplayName()),
                    "com.amalto.workbench.editors.XObjectEditor"
           	);
       
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					view.getSite().getShell(),
					"Error", 
					"An error occured trying to open the editor: "+e.getLocalizedMessage()
			);
		}		
	}
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	


}