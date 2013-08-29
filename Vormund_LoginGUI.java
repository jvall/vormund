import java.awt.*;
import javax.swing.*;

public class gui {

	private static void LoginWindow()
	{
        //Create and set up the window.
		JFrame frame = new JFrame("Vormund");
        frame.setSize(600,500);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Title
        Font title_font = new Font("Verdana", Font.BOLD, 50);
        Font login_font = new Font("Verdana", Font.PLAIN,15);
        
        JPanel login = new JPanel();
        login.setLayout(new GridLayout(10, 10));

        JLabel title = new JLabel("Vormund");
		title.setFont(title_font);		
		title.setHorizontalAlignment(0);
		login.add(title, BorderLayout.PAGE_START);
		
		//Username and Password panel
		int width = 150;
		int height = 20;
		
		JPanel login_info = new JPanel();
		login_info.setPreferredSize(new Dimension(100,50));
		login.add(login_info, BorderLayout.LINE_START);
		
		JLabel username = new JLabel("Username");
		username.setFont(login_font);
		username.setPreferredSize(new Dimension(width,height));
		username.setHorizontalAlignment(4);
		login_info.add(username);
		
		JLabel password = new JLabel("Password");
		password.setPreferredSize(new Dimension(width,height));
		password.setFont(login_font);
		password.setHorizontalAlignment(4);
		login_info.add(password);

		JPanel login_txtfield = new JPanel();
		login_txtfield.setPreferredSize(new Dimension(100,70));
		login.add(login_txtfield, BorderLayout.CENTER);
		
		JTextField user = new JTextField(10);
		user.setPreferredSize(new Dimension(100,30));
		login_txtfield.add(user);
		
		JTextField passwd = new JTextField(10);
		passwd.setPreferredSize(new Dimension(100,30));
		login_txtfield.add(passwd);
		
		//Checking correct username/password panel
		JPanel Checking = new JPanel();
		Checking.setPreferredSize(new Dimension(width,80));
		login.add(Checking, BorderLayout.LINE_END);
		
		JLabel ch_user = new JLabel("The Username or Password is incorrect. Please try again.");
		ch_user.setPreferredSize(new Dimension(400,30));
		Checking.add(ch_user);
		
		JButton B_log = new JButton("Login");
		//B_log.setPreferredSize(new Dimension(100, 70));
		JButton B_newUser = new JButton("New User");
		//B_newUser.setPreferredSize(new Dimension(100, 70));
		login.add(B_log, BorderLayout.PAGE_END);
		login.add(B_newUser, BorderLayout.PAGE_END);
		
        frame.setContentPane(login);
        
        frame.setVisible(true);
	}
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginWindow();
            }
        });
    }	
}
