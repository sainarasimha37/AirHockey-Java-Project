package airHockey;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserHome frame = new UserHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
    }

    public UserHome() {

    }

    /**
     * Create the frame.
     */
    public UserHome(String userSes) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(450, 190, 1014, 597);
        setBounds(450, 190, 1014, 800);

        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 39));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setTitle("Student-Login");
                    obj.setVisible(true);
                }
                dispose();
                UserLogin obj = new UserLogin();

                obj.setTitle("Student-Login");
                obj.setVisible(true);

            }
        });
        btnNewButton.setBounds(247, 118, 491, 114);
        contentPane.add(btnNewButton);
        JButton button = new JButton("Change-password\r\n");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(userSes);
                bo.setTitle("Change Password");
                bo.setVisible(true);

            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 35));
        button.setBounds(247, 320, 491, 114);
        contentPane.add(button);
        
        JButton playButton = new JButton("Play");
        playButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
        			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        			/*
        			 * LookAndFeel lat = UIManager.getLookAndFeel(); UIDefaults defaults =
        			 * lat.getDefaults(); defaults.replace(key, value); for(Object key:
        			 * UIManager.getLookAndFeel().getDefaults().keySet()) { System.out.println(key +
        			 * " = " + UIManager.get(key)); }
        			 */
        		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        				| UnsupportedLookAndFeelException e1) {
        			e1.printStackTrace();
        		}
        		new AirHockey();// Add your code here for what should happen when the play button is clicked
            }
        });
        playButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
        playButton.setBounds(247, 522, 491, 114);
        contentPane.add(playButton);

        
        
    }
}