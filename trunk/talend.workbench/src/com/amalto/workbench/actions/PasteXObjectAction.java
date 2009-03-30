package com.amalto.workbench.actions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.WorkbenchClipboard;
import com.amalto.workbench.views.ServerView;
import com.amalto.workbench.webservices.WSDataCluster;
import com.amalto.workbench.webservices.WSDataClusterPK;
import com.amalto.workbench.webservices.WSDataModel;
import com.amalto.workbench.webservices.WSDataModelPK;
import com.amalto.workbench.webservices.WSExistsDataCluster;
import com.amalto.workbench.webservices.WSExistsDataModel;
import com.amalto.workbench.webservices.WSExistsMenu;
import com.amalto.workbench.webservices.WSExistsRole;
import com.amalto.workbench.webservices.WSExistsRoutingRule;
import com.amalto.workbench.webservices.WSExistsStoredProcedure;
import com.amalto.workbench.webservices.WSExistsTransformer;
import com.amalto.workbench.webservices.WSExistsView;
import com.amalto.workbench.webservices.WSGetDataCluster;
import com.amalto.workbench.webservices.WSGetDataModel;
import com.amalto.workbench.webservices.WSGetInboundAdaptor;
import com.amalto.workbench.webservices.WSGetMenu;
import com.amalto.workbench.webservices.WSGetOutboundAdaptor;
import com.amalto.workbench.webservices.WSGetRole;
import com.amalto.workbench.webservices.WSGetRoutingRule;
import com.amalto.workbench.webservices.WSGetStoredProcedure;
import com.amalto.workbench.webservices.WSGetTransformer;
import com.amalto.workbench.webservices.WSGetView;
import com.amalto.workbench.webservices.WSInboundAdaptor;
import com.amalto.workbench.webservices.WSInboundAdaptorPK;
import com.amalto.workbench.webservices.WSMenu;
import com.amalto.workbench.webservices.WSMenuPK;
import com.amalto.workbench.webservices.WSOutboundAdaptor;
import com.amalto.workbench.webservices.WSOutboundAdaptorPK;
import com.amalto.workbench.webservices.WSPutDataCluster;
import com.amalto.workbench.webservices.WSPutDataModel;
import com.amalto.workbench.webservices.WSPutInboundAdaptor;
import com.amalto.workbench.webservices.WSPutMenu;
import com.amalto.workbench.webservices.WSPutOutboundAdaptor;
import com.amalto.workbench.webservices.WSPutRole;
import com.amalto.workbench.webservices.WSPutRoutingRule;
import com.amalto.workbench.webservices.WSPutStoredProcedure;
import com.amalto.workbench.webservices.WSPutTransformer;
import com.amalto.workbench.webservices.WSPutView;
import com.amalto.workbench.webservices.WSRole;
import com.amalto.workbench.webservices.WSRolePK;
import com.amalto.workbench.webservices.WSRoutingRule;
import com.amalto.workbench.webservices.WSRoutingRulePK;
import com.amalto.workbench.webservices.WSStoredProcedure;
import com.amalto.workbench.webservices.WSStoredProcedurePK;
import com.amalto.workbench.webservices.WSTransformer;
import com.amalto.workbench.webservices.WSTransformerPK;
import com.amalto.workbench.webservices.WSView;
import com.amalto.workbench.webservices.WSViewPK;
import com.amalto.workbench.webservices.XtentisPort;

public class PasteXObjectAction extends Action{

	private ServerView view = null;

	
	/*
	public CopyXObjectAction(TreeObject xobject, IWorkbenchPage page) {
		super();
		this.xobject = xobject;
		this.page = page;
		setDetails();
	}
	*/
	
	public PasteXObjectAction(ServerView view) {
		super();
		this.view = view;
		setDetails();
	}
	
	private void setDetails() {
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.amalto.workbench", "icons/paste.gif"));
		setText("Paste");
		setToolTipText("Paste this instance of the Xtentis Object");		
	}
	
	public void run() {
		TreeObject selected = (TreeObject)((IStructuredSelection)view.getViewer().getSelection()).getFirstElement();
		try {
			super.run();
			if (this.view == null) {
				MessageDialog.openError(view.getSite().getShell(), "Paste Error", "This function must be called from the Object View");
				return;
			}

			XtentisPort destPort = Util.getPort(
					new URL(selected.getEndpointAddress()),
					selected.getUsername(),
					selected.getPassword()
			);
			
			ArrayList<TreeObject> list = WorkbenchClipboard.getWorkbenchClipboard().get();
			
			for (Iterator<TreeObject> iter = list.iterator(); iter.hasNext(); ) {
//				is an XObject necessarily
				TreeObject xobject = iter.next();
				
	            switch(xobject.getType()) {
		           	case TreeObject.DATA_MODEL: {
		           		WSDataModelPK key = (WSDataModelPK)xobject.getWsKey();
		           		WSDataModelPK newKey = new WSDataModelPK(key.getPk());
		           		if (destPort.existsDataModel(new WSExistsDataModel((WSDataModelPK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Data Model with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSDataModelPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSDataModel originalDataModel = originalPort.getDataModel(new WSGetDataModel(key));
		           		WSDataModel newDataModel = new WSDataModel(
		           				newKey.getPk(),
		           				originalDataModel.getDescription(),
		           				originalDataModel.getXsdSchema()
		           		);
		           		//write the new model
		           		destPort.putDataModel(new WSPutDataModel(newDataModel));
		           		}break;
		           	case TreeObject.VIEW: {
		           		WSViewPK key = (WSViewPK)xobject.getWsKey();
		           		WSViewPK newKey = new WSViewPK(key.getPk());
		           		if (destPort.existsView(new WSExistsView((WSViewPK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A View with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSViewPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSView originalView = originalPort.getView(new WSGetView(key));
		           		WSView newView = new WSView(
		           				newKey.getPk(),
		           				originalView.getDescription(),
		           				originalView.getViewableBusinessElements(),
		           				originalView.getWhereConditions(),
		           				originalView.getSearchableBusinessElements()
		           		);
		           		//write the new model
		           		destPort.putView(new WSPutView(newView));
		           		} break;
		           	case TreeObject.DATA_CLUSTER: {
		           		WSDataClusterPK key = (WSDataClusterPK)xobject.getWsKey();
		           		WSDataClusterPK newKey = new WSDataClusterPK(key.getPk());
		           		if (destPort.existsDataCluster(new WSExistsDataCluster((WSDataClusterPK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Data Cluster with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSDataClusterPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSDataCluster originalDataCluster = originalPort.getDataCluster(new WSGetDataCluster(key));
		           		WSDataCluster newDataCluster = new WSDataCluster(
		           				newKey.getPk(),
		           				originalDataCluster.getDescription(),
		           				originalDataCluster.getVocabulary()
		           		);
		           		//write the new model
		           		destPort.putDataCluster(new WSPutDataCluster(newDataCluster));
		           		} break;
		           	case TreeObject.STORED_PROCEDURE: {
		           		WSStoredProcedurePK key = (WSStoredProcedurePK)xobject.getWsKey();
		           		WSStoredProcedurePK newKey = new WSStoredProcedurePK(key.getPk());
		           		if (destPort.existsStoredProcedure(new WSExistsStoredProcedure((WSStoredProcedurePK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Stored Procedure with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSStoredProcedurePK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSStoredProcedure originalStoredProcedure = originalPort.getStoredProcedure(new WSGetStoredProcedure(key));
		           		WSStoredProcedure newStoredProcedure = new WSStoredProcedure(
		           				newKey.getPk(),
		           				originalStoredProcedure.getDescription(),
		           				originalStoredProcedure.getProcedure()
		           		);
		           		//write the new model
		           		destPort.putStoredProcedure(new WSPutStoredProcedure(newStoredProcedure));
		           		} break;
		           	case TreeObject.ROLE: {
		           		WSRolePK key = (WSRolePK)xobject.getWsKey();
		           		WSRolePK newKey = new WSRolePK(key.getPk());
		           		if (destPort.existsRole(new WSExistsRole((WSRolePK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Role with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSRolePK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSRole originalRole = originalPort.getRole(new WSGetRole(key));
		           		WSRole newRole = new WSRole(
		           				newKey.getPk(),
		           				originalRole.getDescription(),
		           				originalRole.getSpecification()
		           		);
		           		//write the new model
		           		destPort.putRole(new WSPutRole(newRole));
		           		} break;	  	           		
		           	case TreeObject.ROUTING_RULE: {
		           		WSRoutingRulePK key = (WSRoutingRulePK)xobject.getWsKey();
		           		WSRoutingRulePK newKey = new WSRoutingRulePK(key.getPk());
		           		if (destPort.existsRoutingRule(new WSExistsRoutingRule((WSRoutingRulePK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Routing Rule with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSRoutingRulePK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSRoutingRule originalRoutingRule = originalPort.getRoutingRule(new WSGetRoutingRule(key));
		           		WSRoutingRule newRoutingRule = new WSRoutingRule(
		           				newKey.getPk(),
		           				originalRoutingRule.getDescription(),
		           				originalRoutingRule.isSynchronous(),
		           				originalRoutingRule.getConcept(),
		           				originalRoutingRule.getServiceJNDI(),
		           				originalRoutingRule.getParameters(),
		           				originalRoutingRule.getWsRoutingRuleExpressions()
		           		);
		           		//write the new model
		           		destPort.putRoutingRule(new WSPutRoutingRule(newRoutingRule));
		           		} break;	  	           		
		           	case TreeObject.TRANSFORMER: {
		           		WSTransformerPK key = (WSTransformerPK)xobject.getWsKey();
		           		WSTransformerPK newKey = new WSTransformerPK(key.getPk());
		           		if (destPort.existsTransformer(new WSExistsTransformer((WSTransformerPK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Transformer with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSTransformerPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSTransformer originalTransformer = originalPort.getTransformer(new WSGetTransformer(key));
		           		WSTransformer newTransformer = new WSTransformer(
		           				newKey.getPk(),
		           				originalTransformer.getDescription(),
		           				originalTransformer.getPluginSpecs()
		           		);
		           		//write the new model
		           		destPort.putTransformer(new WSPutTransformer(newTransformer));
		           		} break;
		           	case TreeObject.MENU: {
		           		WSMenuPK key = (WSMenuPK)xobject.getWsKey();
		           		WSMenuPK newKey = new WSMenuPK(key.getPk());
		           		if (destPort.existsMenu(new WSExistsMenu((WSMenuPK)xobject.getWsKey())).is_true()) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A Menu with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSMenuPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSMenu originalMenu = originalPort.getMenu(new WSGetMenu(key));
		           		WSMenu newMenu = new WSMenu(
		           				newKey.getPk(),
		           				originalMenu.getDescription(),
		           				originalMenu.getMenuEntries()
		           		);
		           		//write the new model
		           		destPort.putMenu(new WSPutMenu(newMenu));
		           		} break;
		           	case TreeObject.INBOUND_ADAPTOR: {
		           		WSInboundAdaptorPK key = (WSInboundAdaptorPK)xobject.getWsKey();
		           		WSInboundAdaptorPK newKey = new WSInboundAdaptorPK(key.getPk());
		           		boolean exists = false;
		           		try {
		           			destPort.getInboundAdaptor(new WSGetInboundAdaptor((WSInboundAdaptorPK)xobject.getWsKey()));
		           			exists = true;
		           		} catch (Exception e) {}
		           		if (exists) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A InboundAdaptor with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSInboundAdaptorPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSInboundAdaptor originalInboundAdaptor = originalPort.getInboundAdaptor(new WSGetInboundAdaptor(key));
		           		WSInboundAdaptor newInboundAdaptor = new WSInboundAdaptor(
		           				newKey.getPk(),
		           				originalInboundAdaptor.getDescription(),
		           				originalInboundAdaptor.getWsDataModelPK(),
		           				originalInboundAdaptor.getWsSourcePK(),
		           				originalInboundAdaptor.getXslt(),
		           				originalInboundAdaptor.getWSInboundPluginPKs(),
		           				originalInboundAdaptor.getWsPreTransformPKs(),
		           				originalInboundAdaptor.getUpdates()
		           		);
		           		//write the new model
		           		destPort.putInboundAdaptor(new WSPutInboundAdaptor(newInboundAdaptor));
		           		} break;
		           	case TreeObject.OUTBOUND_ADAPTOR: {
		           		WSOutboundAdaptorPK key = (WSOutboundAdaptorPK)xobject.getWsKey();
		           		WSOutboundAdaptorPK newKey = new WSOutboundAdaptorPK(key.getPk());
		           		boolean exists = false;
		           		try {
		           			destPort.getOutboundAdaptor(new WSGetOutboundAdaptor((WSOutboundAdaptorPK)xobject.getWsKey()));
		           			exists = true;
		           		} catch (Exception e) {}
		           		if (exists) {
			           		InputDialog id = new InputDialog(
			           				view.getSite().getShell(),
			           				"Pasting instance "+key.getPk(),
			           				"A OutboundAdaptor with the name \""+key.getPk()+"\" already exists.\nEnter a new name if you do not want to overwriite the existing object",
			           				"Copy of "+(selected.getEndpointAddress().equals(xobject.getEndpointAddress()) ? "": xobject.getEndpointAddress().split(":")[0]+" ")+key.getPk(),
			           				new IInputValidator() {
			           					public String isValid(String newText) {
			           						if ((newText==null) || "".equals(newText)) return "The name cannot be empty";
			           						return null;
			           					};
			           				}
			           		);
			           		id.setBlockOnOpen(true);
			           		if (id.open() == Window.CANCEL) return;
			           		newKey = new WSOutboundAdaptorPK(id.getValue()); 
		           		}
		           		//fetch the copied model
	       				XtentisPort originalPort = Util.getPort(
	       						new URL(xobject.getEndpointAddress()),
	       						xobject.getUsername(),
	       						xobject.getPassword()
	       				);
		           		WSOutboundAdaptor originalOutboundAdaptor = originalPort.getOutboundAdaptor(new WSGetOutboundAdaptor(key));
		           		WSOutboundAdaptor newOutboundAdaptor = new WSOutboundAdaptor(
		           				newKey.getPk(),
		           				originalOutboundAdaptor.getDescription(),
		           				originalOutboundAdaptor.getWsDataModelPK(),
		           				originalOutboundAdaptor.getWsDestinationPK(),
		           				originalOutboundAdaptor.getXslt(),
		           				originalOutboundAdaptor.getWSOutboundPluginPKs(),
		           				originalOutboundAdaptor.getWsPreTransformPKs()
		           		);
		           		//write the new model
		           		destPort.putOutboundAdaptor(new WSPutOutboundAdaptor(newOutboundAdaptor));
		           		} break;	  		           			           				           				           		
		           	default:
		           		
	            }//switch
				
			}
			           
       
		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openError(
					view.getSite().getShell(),
					"Error", 
					"An error occured trying to copy: "+e.getLocalizedMessage()
			);
		}	finally {
			//refresh view
			try {
				(new ServerRefreshAction(this.view,selected.getServerRoot())).run();
			} catch (Exception e) {}
		}
	}
	public void runWithEvent(Event event) {
		super.runWithEvent(event);
	}
	


}