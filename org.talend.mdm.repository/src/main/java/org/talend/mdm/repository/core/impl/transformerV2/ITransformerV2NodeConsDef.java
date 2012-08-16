// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2012 Talend �C www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.mdm.repository.core.impl.transformerV2;


public interface ITransformerV2NodeConsDef {
    public static final String TYPE_PROCESS = "process";//$NON-NLS-1$
    
    public static final String TYPE_BEFORESAVE = "beforeSaving";//$NON-NLS-1$
    public static final String TYPE_BEFOREDEL = "beforeDeleting";//$NON-NLS-1$
    public static final String TYPE_ENTITYACTION = "RunnableCommon";//$NON-NLS-1$
    public static final String TYPE_WELCOMEACTION = "RunnableStandlone";//$NON-NLS-1$
    public static final String TYPE_SMARTVIEW = "smartView";//$NON-NLS-1$
    public static final String TYPE_OTHER = "other";//$NON-NLS-1$
    
    public static final String Prefix_BEFOREDEL = "beforeDeleting_";//$NON-NLS-1$
    public static final String Prefix_BEFORESAVE = "beforeSaving_";//$NON-NLS-1$
    public static final String Prefix_RUNNABLE = "Runnable_";//$NON-NLS-1$
    public static final String Prefix_STANDLONE = "Runnable#";//$NON-NLS-1$
    public static final String Prefix_SMARTVIEW = "Smart_view_";//$NON-NLS-1$
    
    public static final String PATH_BEFORESAVE = "beforesaving";//$NON-NLS-1$
    public static final String PATH_BEFOREDEL = "beforedeleing";//$NON-NLS-1$
    public static final String PATH_ENTITYACTION = "entityaction";//$NON-NLS-1$
    public static final String PATH_WELCOMEACTION = "welcomeaction";//$NON-NLS-1$
    public static final String PATH_SMARTVIEW = "smartview";//$NON-NLS-1$
    public static final String PATH_OTHER = "other";//$NON-NLS-1$
}
