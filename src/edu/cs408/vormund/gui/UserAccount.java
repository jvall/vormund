/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cs408.vormund.gui;

import java.util.*;
import javax.swing.*;
import edu.cs408.vormund.*;

/**
 *
 * @author isabellee
 */
public class UserAccount extends javax.swing.JFrame {

	/**
	 * Creates new form UserAccount
	 */
	private DBHelpers helpers;


	public static boolean updating = false;

	private ArrayList<BankInfo> banks;
	private ArrayList<WebInfo> webs;
	private ArrayList<NoteInfo> notes;
	private ArrayList<SSNInfo> ssn;

	//SubCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " "}));

	public UserAccount(DBHelpers h) {
		initComponents();
		helpers = h;

		banks = helpers.getBanks();
		webs = helpers.getWebs();
		notes = helpers.getNotes();
		ssn = helpers.getSocials();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	//initComponet function here
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		addbutton = new javax.swing.JButton();
		removebutton = new javax.swing.JButton();
		logout = new javax.swing.JButton();
		MainCB = new javax.swing.JComboBox();
		SubCB = new javax.swing.JComboBox();
		jScrollPane1 = new javax.swing.JScrollPane();
		userinfotext = new javax.swing.JTextArea();
    userinfotext.setEditable(false);
		showbutton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("Vormund");

		addbutton.setText("Add");
		addbutton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				addbuttonMouseClicked(evt);
			}
		});

		removebutton.setText("Remove");
		removebutton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				removebuttonMouseClicked(evt);
			}
		});

		logout.setText("Log out");
		logout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutActionPerformed(evt);
			}
		});

		MainCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Category", "Bank", "Website", "Notes", "SSN" }));
		MainCB.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
        userinfotext.setText("");
        String selectedText = (String)MainCB.getItemAt(MainCB.getSelectedIndex());
        if( selectedText.compareTo("Bank")==0 ) {
          return;
        } else if( selectedText.compareTo("Website")==0 ) {
          userinfotext.setText("ERROR: MySQL Syntax Error");
        }
		    SubCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Category"}));
        SubCB.setSelectedIndex(0);
				MainCBItemStateChanged(evt);
			}
		});

		SubCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Category" }));
		SubCB.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				SubCBItemStateChanged(evt);
			}
		});

		userinfotext.setColumns(20);
		userinfotext.setRows(5);
		jScrollPane1.setViewportView(userinfotext);

		showbutton.setText("Update");
		showbutton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				showbuttonMouseClicked(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout.createSequentialGroup()
										.add(308, 308, 308)
										.add(jLabel1))
										.add(layout.createSequentialGroup()
												.add(17, 17, 17)
												.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(SubCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(MainCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
														.add(18, 18, 18)
														.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 348, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(18, 18, 18)
														.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
																.add(showbutton)
																.add(addbutton)
																.add(removebutton)
																.add(logout))))
																.addContainerGap(58, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.add(19, 19, 19)
						.add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(69, 69, 69)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout.createSequentialGroup()
										.add(15, 15, 15)
										.add(MainCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(55, 55, 55)
										.add(SubCB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 213, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(layout.createSequentialGroup()
												.add(showbutton)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
												.add(addbutton)
												.add(18, 18, 18)
												.add(removebutton)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
												.add(logout)))
												.addContainerGap(30, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
		// TODO add your handling code here:
		new LoginWindow().setVisible(true);
    this.dispose();
	}//GEN-LAST:event_logoutActionPerformed

	private void addbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addbuttonMouseClicked
		// TODO add your handling code here:

		String temp = MainCB.getSelectedItem().toString();

		if(temp.compareTo("Bank") == 0)
		{
			new NewBank(helpers, this).setVisible(true);
		}
		else if(temp.compareTo("Website") == 0)
		{
			new Website(helpers).setVisible(true);
		}
		else if(temp.compareTo("Notes") == 0)
		{
			new Notes(helpers, this).setVisible(true);
		}
		else if(temp.compareTo("SSN") == 0)
		{
			new SSN(helpers, this).setVisible(true);
		}

	}//GEN-LAST:event_addbuttonMouseClicked

	private void removebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removebuttonMouseClicked
		// TODO add your handling code here:
		String mcb = MainCB.getSelectedItem().toString();
		int scb = SubCB.getSelectedIndex();

		if(mcb.compareTo("Bank") == 0)
		{
			if(banks.size() > 0 && scb != 0)
			{
				userinfotext.setText("");
				BankInfo selectedBank = banks.get(SubCB.getSelectedIndex() - 1);
				helpers.delete(selectedBank.getRecordID());
				banks = helpers.getBanks();
				refreshBanksList();
			}
		}
		else if(mcb.compareTo("Website") == 0)
		{
			if(webs.size() > 0)
			{
				userinfotext.setText("");
				WebInfo selectedWeb = webs.get(SubCB.getSelectedIndex() - 1);
				helpers.delete(selectedWeb.getRecordID());
				webs = helpers.getWebs();
				refreshWebsList();
			}
		}
		else if(mcb.compareTo("Notes") == 0)
		{
			if(notes.size() > 0 && scb != 0)
			{
				userinfotext.setText("");
				NoteInfo selectedNote = notes.get(SubCB.getSelectedIndex() - 1);
				helpers.delete(selectedNote.getRecordID());
				notes = helpers.getNotes();
				refreshNotesList();
			}
		}
		else if(mcb.compareTo("SSN") == 0)
		{
			if( ssn.size() > 0 && scb != 0 )
			{
        userinfotext.setText("");
				SSNInfo selectedSNN = ssn.get(SubCB.getSelectedIndex()-1);
        helpers.delete(selectedSNN.getRecordID());
				ssn = helpers.getSocials();
				refreshSocialsList();
				//delete function
			}
		}
	}//GEN-LAST:event_removebuttonMouseClicked

    private void SubCBItemStateChanged(java.awt.event.ItemEvent evt) {
		int secd_cat = SubCB.getSelectedIndex();
		String mcb = MainCB.getSelectedItem().toString();


		if(mcb.compareTo("Bank") == 0)
		{
			if(banks.size() > 0 && secd_cat != 0)
			{
				BankInfo selectedBank = banks.get(secd_cat-1);

				userinfotext.setText("Name: " + selectedBank.getBankName() +"\n"
		                + "Address: " + selectedBank.getBankAddress() + "\nAccount #: " + selectedBank.getAccountNumber() + "\nRouting #: "
		                + selectedBank.getRoutingNumber() + "\nAccount type: " + selectedBank.getAccountType());
			}
		}
		else if(mcb.compareTo("Website") == 0)
		{
			if(webs.size() > 0 && secd_cat != 0)
			{
				WebInfo selectedWeb = webs.get(secd_cat-1);

				userinfotext.setText("Name: " + selectedWeb.getName() +"\n"
		                + "URL: " + selectedWeb.getUrl() + "\nUsername: "
		                + selectedWeb.getUserName() + "\nPassword: " + selectedWeb.getPassword());

			}
		}
		else if(mcb.compareTo("Notes") == 0)
		{
			if(notes.size() > 0 && secd_cat != 0)
			{
				NoteInfo selectedNote = notes.get(secd_cat-1);
				userinfotext.setText("Title: "+ selectedNote.getName() + "\n\n" + selectedNote.getNote());
			}
		}
		else if(mcb.compareTo("SSN") == 0)
		{
			if(ssn.size() > 0 && secd_cat != 0)
			{
				SSNInfo selectedSNN = ssn.get(secd_cat-1);
				userinfotext.setText( selectedSNN.getName() + "\n" + selectedSNN.getSSN());
			}
		}
    }

	//showbutton is updatebutton
	private void showbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showbuttonMouseClicked
		// TODO add your handling code here:
		updating = true;
		String main = MainCB.getSelectedItem().toString();
		String sub = SubCB.getSelectedItem().toString();

		if(main.compareTo("Bank") == 0)
		{
			if(SubCB.getItemCount() > 1 && SubCB.getSelectedIndex() != 0)
			{
				new NewBank(helpers, banks.get(SubCB.getSelectedIndex() - 1).getRecordID(), this).setVisible(true);
			}
		}
		if(main.compareTo("Website") == 0)
		{
			if(SubCB.getItemCount() > 1 && SubCB.getSelectedIndex() != 0)
			{
				new Website(helpers, webs.get(SubCB.getSelectedIndex() - 1).getRecordID()).setVisible(true);
			}
		}
		if(main.compareTo("Notes") == 0)
		{
			if(SubCB.getItemCount() > 1 && SubCB.getSelectedIndex() != 0)
			{
				new Notes(helpers, notes.get(SubCB.getSelectedIndex() - 1).getRecordID(), this).setVisible(true);
			}
		}
		if(main.compareTo("SSN") == 0)
		{
      if( SubCB.getItemCount() > 1 ) {
        if( SubCB.getSelectedIndex() != 0 ) {
          new SSN(helpers, ssn.get(SubCB.getSelectedIndex()-1).getRecordID(), this).setVisible(true);
        }
      }
			//new SSN(helpers).setVisible(true);
		}

	}//GEN-LAST:event_showbuttonMouseClicked

	private void MainCBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MainCBItemStateChanged
		// TODO add your handling code here:
		String temp = MainCB.getSelectedItem().toString();

		if(temp.compareTo("Bank") == 0)
		{
      JOptionPane.showMessageDialog(null,"No banks in the database! Please add new banks!");
			refreshBanksList();
		}
		else if(temp.compareTo("Website") == 0)
		{
      JOptionPane.showMessageDialog(null,"No website in the database! Please add new websites!");
			refreshWebsList();
		}
		else if(temp.compareTo("Notes") == 0)
		{
      JOptionPane.showMessageDialog(null,"No notes in the database! Please add new notes!");
      refreshBanksList();
		}
		else if(temp.compareTo("SSN") == 0)
		{
      JOptionPane.showMessageDialog(null,"No social security numbers in the database! Please add new social security numbers!");
		}
		else if (temp.equals("Category")) {
			String names [] = {"Category"};
			SubCB.setModel(new javax.swing.DefaultComboBoxModel(names));
		}

	}//GEN-LAST:event_MainCBItemStateChanged

	public void refreshBanksList() {
		banks = helpers.getBanks();

		repaint();

		//SubCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Banks"}));

		String names[] = new String[banks.size() + 1];

		int i = 1;
		names[0] = "Banks";
		for (BankInfo b : banks) {
			names[i++] = b.getBankName();
			System.out.println(b.getBankName());
		}

		SubCB.setModel(new javax.swing.DefaultComboBoxModel(names));
	}

	public void refreshSocialsList() {
		ssn = helpers.getSocials();

	    repaint();
	    String names[] = new String[ssn.size()+1];
	    int i=1;
	    names[0]="Socials";
	    for(SSNInfo s:ssn) {
	      names[i++] = s.getName();
	    }
	    SubCB.setModel(new javax.swing.DefaultComboBoxModel(names));
	  }

	public void refreshNotesList() {
		notes = helpers.getNotes();
		repaint();

		String names[] = new String[notes.size() + 1];
		int i = 1;
		names[0] = "Notes";
		for (NoteInfo n : notes) {
			names[i++] = n.getName();
		}

		SubCB.setModel(new javax.swing.DefaultComboBoxModel(names));
	}

	private void refreshWebsList() {
		repaint();

		String names[] = new String[webs.size() + 1];
		int i = 1;
		names[0] = "Webs";
		for (WebInfo w : webs) {
			names[i++] = w.getName();
		}

		SubCB.setModel(new javax.swing.DefaultComboBoxModel(names));
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	public javax.swing.JComboBox MainCB;
	public javax.swing.JComboBox SubCB;
	private javax.swing.JButton addbutton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton logout;
	private javax.swing.JButton removebutton;
	private javax.swing.JButton showbutton;
	private javax.swing.JTextArea userinfotext;
	// End of variables declaration//GEN-END:variables
}
