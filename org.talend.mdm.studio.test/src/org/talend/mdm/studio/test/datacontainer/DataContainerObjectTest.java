// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.studio.test.datacontainer;

import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * DataContainerTest is a SWTBot test case for testing the new and delete functions of Data Container.
 * 
 * DOC rhou class global comment. Detailled comment
 * 
 */
public class DataContainerObjectTest extends DataContainerTest {

    @Before
    public void runBeforeEveryTest() {
    }

    @After
    public void runAfterEveryTest() {

    }

    @BeforeClass
    public static void runBeforeClass() {
        // run for one time before all test cases
    }

    @AfterClass
    public static void runAfterClass() {
        // run for one time after all test cases
    }

    @Test
    public void runTest() {
        dataContainerItem.contextMenu("New").click();
        // bot.sleep(1000);
        SWTBotShell newDataContainerShell = bot.shell("New Data Container");
        newDataContainerShell.activate();
        SWTBotText text = bot.textWithLabel("Enter a name for the New Instance");
        text.setText("TestDataContainer");
        sleep();
        bot.buttonWithTooltip("Add").click();
        sleep();
        bot.button("OK").click();
        bot.textWithLabel("Description").setText("This is a test for data container");
        bot.activeEditor().save();
        sleep();
        bot.activeEditor().close();
        sleep(2);
        dataContainerItem.getNode("TestDataContainer").contextMenu("Delete").click();
        sleep();
        bot.button("OK").click();
        sleep();
    }

}
