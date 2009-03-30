package com.amalto.workbench.actions;

import java.net.URL;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.views.ServerView;
import com.amalto.workbench.webservices.WSDataClusterPK;
import com.amalto.workbench.webservices.WSDataModelPK;
import com.amalto.workbench.webservices.WSDeleteDataCluster;
import com.amalto.workbench.webservices.WSDeleteDataModel;
import com.amalto.workbench.webservices.WSDeleteDestination;
import com.amalto.workbench.webservices.WSDeleteDocument;
import com.amalto.workbench.webservices.WSDeleteInboundAdaptor;
import com.amalto.workbench.webservices.WSDeleteInboundPlugin;
import com.amalto.workbench.webservices.WSDeleteMenu;
import com.amalto.workbench.webservices.WSDeleteOutboundAdaptor;
import com.amalto.workbench.webservices.WSDeleteOutboundPlugin;
import com.amalto.workbench.webservices.WSDeleteRole;
import com.amalto.workbench.webservices.WSDeleteRoutingRule;
import com.amalto.workbench.webservices.WSDeleteSource;
import com.amalto.workbench.webservices.WSDeleteStoredProcedure;
import com.amalto.workbench.webservices.WSDeleteTransformer;
import com.amalto.workbench.webservices.WSDeleteView;
import com.amalto.workbench.webservices.WSDestinationPK;
import com.amalto.workbench.webservices.WSDocumentPK;
import com.amalto.workbench.webservices.WSInboundAdaptorPK;
import com.amalto.workbench.webservices.WSInboundPluginPK;
import com.amalto.workbench.webservices.WSMenuPK;
import com.amalto.workbench.webservices.WSOutboundAdaptorPK;
import com.amalto.workbench.webservices.WSOutboundPluginPK;
import com.amalto.workbench.webservices.WSRolePK;
import com.amalto.workbench.webservices.WSRoutingRulePK;
import com.amalto.workbench.webservices.WSSourcePK;
import com.amalto.workbench.webservices.WSStoredProcedurePK;
import com.amalto.workbench.webservices.WSTransformerPK;
import com.amalto.workbench.webservices.WSViewPK;
import com.amalto.workbench.webservices.XtentisPort;

public class DeleteXObjectAction extends Action{

	private ServerView view = null;
	
	
	public DeleteXObjectAction(ServerView view) {
		super();
		this.view = view;
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.amalto.workbench", "icons/delete_obj.gif"));
		setText("Delete");
		setToolTipText("Delete this instance of the Xtentis Object");
	}
	
	public void run() {
		try {
			super.run();
			IStructuredSelection selection = (IStructuredSelection)view.getViewer().getSelection();
			for (Iterator iter = selection.iterator(); iter.hasNext(); ) {
				TreeObject xobject = (TreeObject) iter.next();
	            
	            if (!xobject.isXObject()) return;
	            
	            //ask for confimation
	            if (! MessageDialog.openConfirm(
	            		this.view.getSite().getShell(),
	            		"Delete Xtentis Object Instance",
	            		"Are you sure you want to delete "+xobject.getDisplayName()+" ?"
	            )) return;
	                        
	//          Access to server and get port
				XtentisPort port = Util.getPort(
						new URL(xobject.getEndpointAddress()),
						xobject.getUsername(),
						xobject.getPassword()
				);
	              
	            switch(xobject.getType()) {
	            
		           	case TreeObject.SOURCE:
		           		port.deleteSource(new WSDeleteSource((WSSourcePK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.DESTINATION:
		           		port.deleteDestination(new WSDeleteDestination((WSDestinationPK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.INBOUND_ADAPTOR:
		           		port.deleteInboundAdaptor(new WSDeleteInboundAdaptor((WSInboundAdaptorPK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.INBOUND_PLUGIN:
		           		port.deleteInboundPlugin(new WSDeleteInboundPlugin((WSInboundPluginPK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.OUTBOUND_ADAPTOR:
		           		port.deleteOutboundAdaptor(new WSDeleteOutboundAdaptor((WSOutboundAdaptorPK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.OUTBOUND_PLUGIN:
		           		port.deleteOutboundPlugin(new WSDeleteOutboundPlugin((WSOutboundPluginPK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.DATA_MODEL:
		           		port.deleteDataModel(new WSDeleteDataModel((WSDataModelPK)xobject.getWsKey()));
		           		break;
		           	case TreeObject.DOCUMENT:
		           		port.deleteDocument(new WSDeleteDocument((WSDocumentPK)xobject.getWsKey()));
		           		break;
		          	case TreeObject.VIEW:
		           		port.deleteView(new WSDeleteView((WSViewPK)xobject.getWsKey()));
		           		break;           		
		          	case TreeObject.DATA_CLUSTER:
		           		port.deleteDataCluster(new WSDeleteDataCluster((WSDataClusterPK)xobject.getWsKey()));
		           		break;      
		          	case TreeObject.STORED_PROCEDURE:
		           		port.deleteStoredProcedure(new WSDeleteStoredProcedure((WSStoredProcedurePK)xobject.getWsKey()));
		           		break;  
		          	case TreeObject.ROLE:
		           		port.deleteRole(new WSDeleteRole((WSRolePK)xobject.getWsKey()));
		           		break;  
		          	case TreeObject.ROUTING_RULE:
		           		port.deleteRoutingRule(new WSDeleteRoutingRule((WSRoutingRulePK)xobject.getWsKey()));
		           		break;  
		          	case TreeObject.TRANSFORMER:
		           		port.deleteTransformer(new WSDeleteTransformer((WSTransformerPK)xobject.getWsKey()));
		           		break;
		          	case TreeObject.MENU:
		           		port.deleteMenu(new WSDeleteMenu((WSMenuPK)xobject.getWsKey()));
		           		break;  	           		
		          	default:
		           		MessageDialog.openError(view.getSite().getShell(), "Error", "Unknown Xtentis Object Type: "+xobject.getType());
		           		return;
	            }//switch
	            
	       		xobject.getParent().removeChild(xobject);
	       		view.getViewer().refresh();
			}//for
       		//view.getSite().getWorkbenchWindow().get
                   
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					view.getSite().getShell(),
					"Error", 
					"An error occured trying to delete the Xtentis object: "+e.getLocalizedMessage()
			);
		}		
	}
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	


}