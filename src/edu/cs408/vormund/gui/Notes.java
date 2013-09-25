/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cs408.vormund.gui;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import edu.cs408.vormund.BankInfo;
import edu.cs408.vormund.DBHelpers;
import edu.cs408.vormund.NoteInfo;

/**
 *
 * @author isabellee
 */
public class Notes extends javax.swing.JFrame {

	/**
	 * Creates new form NewBank
	 */
	private DBHelpers helpers;

	private boolean isUpdating;

	private int data_id;

	private UserAccount parent;

    public Notes(DBHelpers h, UserAccount parent) {
        helpers = h;
        isUpdating = false;
        data_id = -1;
        initComponents();
        this.parent = parent;
    }

    public Notes(DBHelpers h, int data_id, UserAccount parent) {
    	helpers = h;
        isUpdating = true;
        this.data_id = data_id;
        initComponents();
        this.parent = parent;
    }

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		titlenote = new javax.swing.JLabel();
		notetitle = new javax.swing.JTextField();
		donebutton2 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		notearea = new javax.swing.JTextArea();

		if(data_id != -1)
        {
	        NoteInfo note = helpers.getNote(data_id);
	        notearea.setText(note.getNote().toString());
	        notetitle.setText(note.getName());
        }

		setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

		jLabel1.setText("Vormund");

		titlenote.setText("Title:");

		donebutton2.setText("Done");
		donebutton2.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				donebutton2MouseClicked(e);
        	}
        });

		notearea.setColumns(20);
		notearea.setRows(5);
		jScrollPane1.setViewportView(notearea);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
						.addContainerGap(174, Short.MAX_VALUE)
						.add(jLabel1)
						.add(169, 169, 169))
						.add(layout.createSequentialGroup()
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(layout.createSequentialGroup()
												.add(65, 65, 65)
												.add(titlenote)
												.add(18, 18, 18)
												.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
														.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
														.add(notetitle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)))
														.add(layout.createSequentialGroup()
																.add(160, 160, 160)
																.add(donebutton2)))
																.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.add(21, 21, 21)
						.add(jLabel1)
						.add(18, 18, 18)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(titlenote)
								.add(notetitle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
								.add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 158, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(18, 18, 18)
								.add(donebutton2)
								.addContainerGap(24, Short.MAX_VALUE))
				);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void donebutton2MouseClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_donebutton2MouseClicked
		// TODO add your handling code here:

		//Check title
		//Add to database
		//UserAccount user_acc = new UserAccount(helpers);
		String n_title = notearea.getText().toString();
		String n_text = notetitle.getText().toString();
		Boolean done = true;

    	if(n_title.length() == 0)
    	{
    		//JOptionPane.showMessageDialog(null,"Please enter a title for the note");
    		//return;
    	}
    	else if(n_text.length() == 0)
    	{
    		JOptionPane.showMessageDialog(null,"Please enter a note");
    		//return;
    	}


    	if(!isUpdating){
    		int result = helpers.newNote(n_title, n_text);
    		if(result == -1)
    		{
    			JOptionPane.showMessageDialog(null,"New note creation failed");
    			done = false;
    		}

			//helpers.updateNote(socialid, n_title, n_text);
			//user_acc.updating = false;
			done = true;
    	}
    	else
    	{
            if( helpers.updateNote(data_id, n_title, n_text) == -1 ) {
            	JOptionPane.showMessageDialog(null, "There was an issue updating the database.");
	            done = false;
	        } else {
	            done = true;
	        }
    	}


		if(done == true){
			//new UserAccount(helpers).setVisible(true);
			//dispose
			parent.refreshNotesList();
			//dispose();
		}
	}//GEN-LAST:event_donebutton2MouseClicked

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton donebutton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTextArea notearea;
	public javax.swing.JTextField notetitle;
	private javax.swing.JLabel titlenote;
	// End of variables declaration//GEN-END:variables
}
