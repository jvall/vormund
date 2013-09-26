/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cs408.vormund.gui;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import edu.cs408.vormund.DBHelpers;

/**
 *
 * @author isabellee
 */
public class LoginWindow extends javax.swing.JFrame {

	/**
	 * Creates new form LoginWindow
	 */

	private DBHelpers helpers;

	public LoginWindow() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		helpers = new DBHelpers();

		title1 = new javax.swing.JLabel();
		username1 = new javax.swing.JLabel();
		password1 = new javax.swing.JLabel();
		usernamefield1 = new javax.swing.JTextField();
		passwordfield1 = new javax.swing.JTextField();
		loginbutton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		title1.setText("Vormund");

		username1.setText("Username");

		password1.setText("Password");

		loginbutton.setText("Login");
		loginbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginbuttonActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout.createSequentialGroup()
										.add(172, 172, 172)
										.add(title1))
										.add(layout.createSequentialGroup()
												.add(60, 60, 60)
												.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(username1)
														.add(password1))
														.add(34, 34, 34)
														.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
																.add(loginbutton)
																.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
																		.add(usernamefield1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
																		.add(passwordfield1)))))
																		.addContainerGap(114, Short.MAX_VALUE))
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.add(20, 20, 20)
						.add(title1)
						.add(34, 34, 34)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(username1)
								.add(usernamefield1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(20, 20, 20)
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
										.add(password1)
										.add(passwordfield1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(26, 26, 26)
										.add(loginbutton)
										.addContainerGap(99, Short.MAX_VALUE))
				);
		pack();
	}// </editor-fold>

	private void loginbuttonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:

    new LoginWindow().setVisible(true);
		//checking if username already exist or if its new
		String name = usernamefield1.getText().toString();
		String pass = passwordfield1.getText().toString();

		if (!helpers.checkUserExist(name)) {
			int yn  = JOptionPane.showConfirmDialog(null,"You are a new user! Do you want to create account?", "New User", JOptionPane.YES_NO_OPTION);
			if(yn == JOptionPane.YES_OPTION)
			{
				try {
					try {
						helpers.newUser(name, pass);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JOptionPane.showMessageDialog(null,"New User has been created!");

			}
			else
			{
				usernamefield1.setText("");
				passwordfield1.setText("");
			}
		}
		else if (helpers.checkLogin(name,pass))
		{
			new UserAccount(helpers).setVisible(true);
			dispose();
		}
		else
		{
			CommonDialogs.displayError("Invalid Login", "Wrong Username and/or Password!");
		}

	}

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
			java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(LoginWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new LoginWindow().setVisible(true);
			}
		});
	}
	// Variables declaration - do not modify
	private javax.swing.JButton loginbutton;
	private javax.swing.JLabel password1;
	private javax.swing.JTextField passwordfield1;
	private javax.swing.JLabel title1;
	private javax.swing.JLabel username1;
	private javax.swing.JTextField usernamefield1;
	// End of variables declaration
}
