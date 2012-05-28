// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.widgets.composites;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.amalto.workbench.detailtabs.sections.BasePropertySection;
import com.amalto.workbench.dialogs.datamodel.SelectXPathDialog;
import com.amalto.workbench.i18n.Messages;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.models.infoextractor.IAllDataModelHolder;

public class SimpleXPathComposite extends Composite {

    private Text txtXPath;

    private Button btnSelectXPath;

    private Label lblTitle;

    private String defaultDataModelForSelect = "";//$NON-NLS-1$

    private IAllDataModelHolder allDataModelHolder;

    public static final String DEFAULTTITLE = Messages.getString("EnterXpathForeignKey");//$NON-NLS-1$
    protected BasePropertySection section;

    private Button btnSep;

    private Boolean sepFk = true;
    public SimpleXPathComposite(Composite parent, int style, String title, IAllDataModelHolder allDataModelHolder,
            String defaultDataModelForSelect,final BasePropertySection section) {
        super(parent, style);
        this.section=section;
        this.defaultDataModelForSelect = defaultDataModelForSelect;
        this.allDataModelHolder = allDataModelHolder;

        final GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.horizontalSpacing = 0;
        setLayout(gridLayout);

        if (title != null && !"".trim().equals(title)) {//$NON-NLS-1$
            lblTitle = new Label(this, SWT.NONE);
            final GridData gd_lblTitle = new GridData(SWT.FILL, SWT.CENTER, true, false);
            lblTitle.setLayoutData(gd_lblTitle);
            lblTitle.setText(title);
        }

        final Composite composite = new Composite(this, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        final GridLayout gridLayout_1 = new GridLayout();
        gridLayout_1.marginWidth = 0;
        gridLayout_1.marginHeight = 0;
        gridLayout_1.numColumns = 2;
        composite.setLayout(gridLayout_1);

        txtXPath = new Text(composite, SWT.BORDER);
        final GridData gd_txtXPath = new GridData(SWT.FILL, SWT.CENTER, true, false);
        txtXPath.setLayoutData(gd_txtXPath);
     
        txtXPath.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
              if( e.character =='\r'){ 
                  if(section!=null ){
                      section.autoCommit();
                  }
              }
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        btnSelectXPath = new Button(composite, SWT.NONE);
        btnSelectXPath.setImage(ImageCache.getCreatedImage(EImage.DOTS_BUTTON.getPath()));
        btnSelectXPath.setToolTipText(Messages.getString("SchematronExpressBuilder_selectXPath"));

        btnSep = new Button(composite, SWT.CHECK);
        btnSep.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
        btnSep.setText(Messages.getString("SimpleXpathInputDialog_sepFkTabPanel"));//$NON-NLS-1$
        btnSep.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                sepFk = btnSep.getSelection();
                section.autoCommit();
            }
        });

        initUIListeners();
    }
    public String getXPath() {
        return txtXPath.getText().trim().replaceAll("'|\"", "");//$NON-NLS-1$//$NON-NLS-2$
    }

    public String getFKSep() {
        return sepFk.toString();
    }

    public void setFKSep(Boolean sepFk) {
        this.sepFk = sepFk;
        btnSep.setSelection(sepFk);
    }
    public void setXPath(String xpath) {
        txtXPath.setText(xpath);
    }

    public void setDefaultDataModelForSelect(String defaultDataModelForSelect) {
        this.defaultDataModelForSelect = defaultDataModelForSelect;
    }

    public String getDefaultDataModelForSelect() {
        return defaultDataModelForSelect;
    }

    private void initUIListeners() {

        btnSelectXPath.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                SelectXPathDialog dialog = new SelectXPathDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                        allDataModelHolder, defaultDataModelForSelect);

                if (dialog.open() != Window.OK)
                    return;

                txtXPath.setText(dialog.getSelectedXPath());
                if(section!=null ){
                	section.autoCommit();
                }
            }
        });
    }

}
