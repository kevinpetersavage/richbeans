/*-
 * Copyright © 2016 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package org.eclipse.richbeans.generator.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.richbeans.generator.example.TableBean;
import org.eclipse.richbeans.generator.example.TableExample;
import org.eclipse.richbeans.generator.example.TableItemBean;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.junit.Test;

public class TableExampleTest extends GuiGeneratorTestBase {
	@Test
	public void testUpdatesItems() throws Exception {
		TableBean updatingBean = new TableExample().createTestObject();
		metawidget = (Composite) guiGenerator.generateGui(updatingBean, shell);

		Table table = (Table)getNamedControl("list");
		assertThat(table.getItem(0).getText(0), is("kevin"));
		updatingBean.getList().get(0).setName("not kevin");

		assertThat(table.getItem(0).getText(0), is("not kevin"));
	}

	@Test
	public void testRemovesItems() throws Exception {
		TableBean updatingBean = new TableExample().createTestObject();
		metawidget = (Composite) guiGenerator.generateGui(updatingBean, shell);

		Table table = (Table)getNamedControl("list");
		assertThat(table.getItemCount(), is(1));
		updatingBean.clearList();

		assertThat(table.getItemCount(), is(0));
	}

	@Test
	public void testAddsItems() throws Exception {
		TableBean updatingBean = new TableExample().createTestObject();
		metawidget = (Composite) guiGenerator.generateGui(updatingBean, shell);

		Table table = (Table)getNamedControl("list");
		assertThat(table.getItemCount(), is(1));
		updatingBean.addItem(new TableItemBean());

		assertThat(table.getItemCount(), is(2));
	}

	@Test
	public void testTracksAddedItems() throws Exception {
		TableBean updatingBean = new TableExample().createTestObject();
		metawidget = (Composite) guiGenerator.generateGui(updatingBean, shell);
		Table table = (Table)getNamedControl("list");
		TableItemBean newBean = new TableItemBean();
		newBean.setName("Bob");
		updatingBean.addItem(newBean);

		assertThat(table.getItem(1).getText(0), is("Bob"));

		newBean.setName("Not Bob");

		assertThat(table.getItem(1).getText(), is("Not Bob"));
	}
}
