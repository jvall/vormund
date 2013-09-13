/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cs408.vormund.gui;

import javax.swing.JOptionPane;

import edu.cs408.vormund.DBHelpers;

/**
 *
 * @author isabellee
 */
public class Notes extends javax.swing.JFrame {

	/**
	 * Creates new form NewBank
	 */
	//DBHelpers DBHelp = new DBHelpers();
	public Notes() {
		initComponents();
	}
	
	DBHelpers dbHelper;

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
		
		dbHelper = new DBHelpers();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jLabel1.setText("Vormund");

		titlenote.setText("Title:");

		donebutton2.setText("Done");
		donebutton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				donebutton2MouseClicked(evt);
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

	private void donebutton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_donebutton2MouseClicked
		// TODO add your handling code here:

		//Check title
		//Add to database
		UserAccount user_acc = new UserAccount();
		String n_text = notearea.getText().toString();
		String n_title = notetitle.getText().toString();
		Boolean done = true;
		
    	if(n_text.length() == 0)
    	{
    		JOptionPane.showMessageDialog(null,"Please enter a title for the note");
    	}
    	else if(n_title.length() == 0)
    	{
    		JOptionPane.showMessageDialog(null,"Please enter a note");
    	}
		
		//Add to database
		if(!user_acc.updating)
		{        	
    		int result = dbHelper.newNote(n_title, n_text);
    		if(result == -1)
    		{
    			JOptionPane.showMessageDialog(null,"New note creation failed");
    			return;
    		}
			
			
			
			//DBHelp.updateNote(socialid, n_title, n_text);
			user_acc.updating = false;
			done = true;
		}
		else
		{
			//Isabel needs to find a way of tracking the data id
			//dbHelper.updateNote(?, n_title, n_text);
			done = true;
		}
		
		if(done == true){
			new UserAccount().setVisible(true);
			//dispose
			dispose();
		}
	}//GEN-LAST:event_donebutton2MouseClicked

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Notes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Notes().setVisible(true);
			}
		});
	}
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton donebutton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTextArea notearea;
	public javax.swing.JTextField notetitle;
	private javax.swing.JLabel titlenote;
	// End of variables declaration//GEN-END:variables
}
